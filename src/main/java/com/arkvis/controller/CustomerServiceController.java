package com.arkvis.controller;

import com.arkvis.error.ErrorUtils;
import com.arkvis.error.MissingFieldsError;
import com.arkvis.error.UnprocessableEntityException;
import com.arkvis.model.Cost;
import com.arkvis.model.CustomerService;
import com.arkvis.service.customerservice.CustomerServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerServiceController {

    @Autowired
    private CustomerServiceService customerServiceService;

    @PostMapping("/customers/{customerId}/services")
    public ResponseEntity<?> createCustomerService(
            @PathVariable("customerId") String customerId,
            @Valid @RequestBody CustomerService customerService,
            @ApiIgnore Errors errors) {

        List<String> missingFields = ErrorUtils.getMissingFields(errors);

        if (!missingFields.isEmpty()) {
            MissingFieldsError mfe = new MissingFieldsError(missingFields);
            throw new UnprocessableEntityException(String.format("Missing fields are: %s", mfe.getMissingFields().toString()));
        }

        customerService.setCustomerId(customerId);
        CustomerService createdService = customerServiceService.createCustomerService(customerService);
        return new ResponseEntity<>(createdService, HttpStatus.CREATED);
    }

    @GetMapping("/customers/{customerId}/services")
    public ResponseEntity<?> getCustomerServices(@PathVariable("customerId") String customerId) {
        List<CustomerService> customerServices = customerServiceService.getServicesForCustomer(customerId);
        return new ResponseEntity<>(customerServices, HttpStatus.OK);

    }

    @DeleteMapping("/customers/{customerId}/services/{serviceName}")
    public ResponseEntity<?> deleteCustomerService(@PathVariable("customerId") String customerId, @PathVariable("serviceName") String serviceName) {
        customerServiceService.deleteCustomerService(customerId, serviceName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
