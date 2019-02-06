package com.arkvis.service.cost;

import com.arkvis.model.Cost;
import com.arkvis.model.CustomerService;
import com.arkvis.model.Device;
import com.arkvis.model.Service;
import com.arkvis.repository.device.DeviceRepository;
import com.arkvis.repository.service.ServiceRepository;
import com.arkvis.service.customerservice.CustomerServiceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class CostServiceImplTest {
    private static final String WINDOWS_DEVICE_TYPE = "WINDOWS";

    private static final String MAC_DEVICE_TYPE = "MAC";

    private static final String ANTIVIRUS_SERVICE = "ANTIVIRUS";

    private static final String CLOUDBERRY_SERVICE = "CLOUDBERRY";

    private static final String TEAMVIEWER_SERVICE = "TEAMVIEWER";

    @InjectMocks
    private CostServiceImpl costService;

    @Mock
    private CustomerServiceService customerServiceService;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        standaloneSetup(costService).build();
    }

    @Test
    public void should_returnCostWithZeroTotal_when_gettingCostForCustomerWithNoServices() {
        when(customerServiceService.getServicesForCustomer(anyString())).thenReturn(Collections.emptyList());
        when(deviceRepository.findByCustomerId(anyString())).thenReturn(Collections.emptyList());

        Cost cost = costService.calculateCostForCustomer("TEST_ID");
        assertNotNull(cost);
        assertEquals("$0.00", cost.getTotal());
    }

    @Test
    public void should_returnCorrectCost_when_gettingCostForCustomerServices() {
        when(customerServiceService.getServicesForCustomer(anyString())).thenReturn(createServiceList());
        when(deviceRepository.findByCustomerId(anyString())).thenReturn(createDeviceList());

        when(serviceRepository.findOne(ANTIVIRUS_SERVICE)).thenReturn(createService(5, 7));
        when(serviceRepository.findOne(CLOUDBERRY_SERVICE)).thenReturn(createService(3, 3));
        when(serviceRepository.findOne(TEAMVIEWER_SERVICE)).thenReturn(createService(1, 1));

        Cost cost = costService.calculateCostForCustomer("TEST_ID");
        assertNotNull(cost);
        assertEquals("$71.00", cost.getTotal());
    }

    private Service createService(int windowsCost, int macCost) {
        Service service = new Service();
        service.setWindowsCost(new BigDecimal(windowsCost));
        service.setMacCost(new BigDecimal(macCost));
        return service;
    }

    private List<Device> createDeviceList() {
        return Arrays.asList(
                createDevice(WINDOWS_DEVICE_TYPE),
                createDevice(WINDOWS_DEVICE_TYPE),
                createDevice(MAC_DEVICE_TYPE),
                createDevice(MAC_DEVICE_TYPE),
                createDevice(MAC_DEVICE_TYPE));
    }

    private Device createDevice(String deviceType) {
        Device device = new Device();
        device.setType(deviceType);
        return device;
    }

    private List<CustomerService> createServiceList() {
        return Arrays.asList(
                createCustomerService(ANTIVIRUS_SERVICE),
                createCustomerService(CLOUDBERRY_SERVICE),
                createCustomerService(TEAMVIEWER_SERVICE));
    }

    private CustomerService createCustomerService(String serviceName) {
        CustomerService customerService = new CustomerService();
        customerService.setServiceName(serviceName);
        return customerService;
    }
}
