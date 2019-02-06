package com.arkvis.service.customerservice;

import com.arkvis.model.CustomerService;

import java.util.List;

public interface CustomerServiceService {

    CustomerService createCustomerService(CustomerService customerService);

    void deleteCustomerService(String customerId, String serviceName);

    List<CustomerService> getServicesForCustomer(String customerId);
}
