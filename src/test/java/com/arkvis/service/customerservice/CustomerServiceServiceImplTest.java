package com.arkvis.service.customerservice;

import com.arkvis.error.ConflictException;
import com.arkvis.error.NotFoundException;
import com.arkvis.model.CustomerService;
import com.arkvis.model.Service;
import com.arkvis.repository.customerservice.CustomerServiceRepository;
import com.arkvis.repository.service.ServiceRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class CustomerServiceServiceImplTest {

    @InjectMocks
    private CustomerServiceServiceImpl customerServiceService;

    @Mock
    private CustomerServiceRepository customerServiceRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        standaloneSetup(customerServiceService).build();
    }

    @Test(expected = NotFoundException.class)
    public void should_throwNotFoundException_when_creatingCustomerServiceButServiceNotFound() {
        when(serviceRepository.findOne(anyString())).thenReturn(null);
        customerServiceService.createCustomerService(createCustomerService("TEST_SERVICE"));
    }

    @Test(expected = ConflictException.class)
    public void should_throwConflictException_when_creatingCustomerServiceButServiceAlreadyExistsForCustomer() {
        when(serviceRepository.findOne(anyString())).thenReturn(new Service());
        when(customerServiceRepository.findByCustomerIdAndServiceName(any(), any())).thenReturn(new CustomerService());
        customerServiceService.createCustomerService(createCustomerService("SERVICE"));
    }

    @Test
    public void should_saveCustomerServiceInRepository_when_creatingCustomerService() {
        when(serviceRepository.findOne(anyString())).thenReturn(new Service());
        when(customerServiceRepository.findByCustomerIdAndServiceName(any(), any())).thenReturn(null);

        customerServiceService.createCustomerService(createCustomerService("TEST"));
        verify(customerServiceRepository).save(any(CustomerService.class));
    }

    @Test
    public void should_returnCustomerServiceWithId_when_creatingCustomerService() {
        when(serviceRepository.findOne(anyString())).thenReturn(new Service());
        when(customerServiceRepository.findByCustomerIdAndServiceName(any(), any())).thenReturn(null);

        CustomerService customerService = customerServiceService.createCustomerService(createCustomerService("TEST"));
        assertNotNull(customerService);
        assertNotNull(customerService.getId());
    }

    @Test
    public void should_returnList_when_gettingServicesForCustomer() {
        when(customerServiceRepository.findByCustomerId(anyString())).thenReturn(Collections.emptyList());
        List<CustomerService> customerServices = customerServiceService.getServicesForCustomer("TEST_ID");
        assertNotNull(customerServices);

    }

    @Test(expected = NotFoundException.class)
    public void should_throwNotFoundException_when_deletingCustomerServiceThatDoesNotExist() {
        when(customerServiceRepository.findByCustomerIdAndServiceName(anyString(), anyString())).thenReturn(null);
        customerServiceService.deleteCustomerService("TEST_ID", "TEST_SERVICE");
    }

    @Test
    public void should_deleteCustomerServiceFromRepository_when_deletingCustomerService() {
        CustomerService customerService = new CustomerService();
        customerService.setId("TEST_ID");

        when(customerServiceRepository.findByCustomerIdAndServiceName(anyString(), anyString())).thenReturn(customerService);

        customerServiceService.deleteCustomerService("TEST_ID", "TEST_SERVICE");
        verify(customerServiceRepository).delete(anyString());
    }

    private CustomerService createCustomerService(String serviceName) {
        CustomerService customerService = new CustomerService();
        customerService.setServiceName(serviceName);
        return customerService;
    }
}
