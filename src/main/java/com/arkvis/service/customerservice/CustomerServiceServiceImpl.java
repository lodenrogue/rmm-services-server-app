package com.arkvis.service.customerservice;

import com.arkvis.error.ConflictException;
import com.arkvis.error.NotFoundException;
import com.arkvis.model.Cost;
import com.arkvis.model.CustomerService;
import com.arkvis.model.Service;
import com.arkvis.repository.customerservice.CustomerServiceRepository;
import com.arkvis.repository.service.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
public class CustomerServiceServiceImpl implements CustomerServiceService {

    @Autowired
    private CustomerServiceRepository customerServiceRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public CustomerService createCustomerService(CustomerService customerService) {
        String id = UUID.randomUUID().toString();
        customerService.setId(id);

        String serviceName = customerService.getServiceName();
        if (!serviceExists(serviceName)) {
            throw new NotFoundException("No service found with given name " + serviceName);
        }

        String customerId = customerService.getCustomerId();
        if (customerServiceExists(customerId, serviceName)) {
            throw new ConflictException("Customer can only register for a particular service once");
        }

        customerServiceRepository.save(customerService);
        return customerService;
    }

    @Override
    public List<CustomerService> getServicesForCustomer(String customerId) {
        return customerServiceRepository.findByCustomerId(customerId);
    }

    @Override
    public void deleteCustomerService(String customerId, String serviceName) {
        CustomerService customerService = customerServiceRepository.findByCustomerIdAndServiceName(customerId, serviceName);
        if (customerService == null) {
            throw new NotFoundException("No customer service found for given customer and service name");
        }

        String id = customerService.getId();
        customerServiceRepository.delete(id);
    }

    private boolean customerServiceExists(String customerId, String serviceName) {
        CustomerService customerService = customerServiceRepository.findByCustomerIdAndServiceName(customerId, serviceName);
        return customerService != null;
    }

    private boolean serviceExists(String serviceName) {
        Service service = serviceRepository.findOne(serviceName);
        return service != null;
    }
}
