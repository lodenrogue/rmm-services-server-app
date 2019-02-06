package com.arkvis.service.cost;

import com.arkvis.model.Cost;
import com.arkvis.model.CustomerService;
import com.arkvis.model.Device;
import com.arkvis.model.Service;
import com.arkvis.repository.device.DeviceRepository;
import com.arkvis.repository.service.ServiceRepository;
import com.arkvis.service.customerservice.CustomerServiceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class CostServiceImpl implements CostService {
    private static final String WINDOWS_TYPE = "WINDOWS";

    private static final String MAC_TYPE = "MAC";

    private static final BigDecimal COST_PER_DEVICE = new BigDecimal("4.00");

    private static final String CURRENCY_SYMBOL = "$";

    @Autowired
    private CustomerServiceService customerServiceService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public Cost calculateCostForCustomer(String customerId) {
        List<CustomerService> customerServices = customerServiceService.getServicesForCustomer(customerId);
        List<String> serviceNames = getServiceNames(customerServices);

        List<Service> services = getServices(serviceNames);
        List<Device> devices = deviceRepository.findByCustomerId(customerId);

        return calculateCost(services, devices);
    }

    private List<String> getServiceNames(List<CustomerService> customerServices) {
        return customerServices.stream()
                .map(CustomerService::getServiceName)
                .collect(Collectors.toList());
    }

    private List<Service> getServices(List<String> serviceNames) {
        return serviceNames.stream()
                .map(name -> serviceRepository.findOne(name))
                .collect(Collectors.toList());
    }

    private Cost calculateCost(List<Service> services, List<Device> devices) {
        long windowsCount = getCount(WINDOWS_TYPE, devices);
        long macCount = getCount(MAC_TYPE, devices);
        long totalCount = windowsCount + macCount;

        BigDecimal deviceTotal = COST_PER_DEVICE.multiply(new BigDecimal(totalCount));

        BigDecimal serviceTotal = services.stream()
                .map(service -> calculateCost(windowsCount, macCount, service))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal grandTotal = deviceTotal.add(serviceTotal);

        Cost cost = new Cost();
        cost.setTotal(CURRENCY_SYMBOL + grandTotal.toString());
        return cost;
    }

    private long getCount(String deviceType, List<Device> devices) {
        return devices.stream()
                .map(Device::getType)
                .filter(type -> type.toUpperCase().contains(deviceType))
                .count();
    }

    private BigDecimal calculateCost(long windowsCount, long macCount, Service service) {
        BigDecimal windowsTotal = new BigDecimal(windowsCount).multiply(service.getWindowsCost());
        BigDecimal macTotal = new BigDecimal(macCount).multiply(service.getMacCost());
        return windowsTotal.add(macTotal);
    }
}
