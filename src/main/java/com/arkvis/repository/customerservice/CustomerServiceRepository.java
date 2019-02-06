package com.arkvis.repository.customerservice;

import com.arkvis.model.CustomerService;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerServiceRepository extends CrudRepository<CustomerService, String> {

    CustomerService findByCustomerIdAndServiceName(String customerId, String serviceName);

    List<CustomerService> findByCustomerId(String customerId);
}
