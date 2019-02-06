package com.arkvis.controller;

import com.arkvis.model.Cost;
import com.arkvis.service.cost.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CostController {

    @Autowired
    private CostService costService;

    @GetMapping("/customers/{customerId}/cost")
    public ResponseEntity<?> getCustomerServiceCost(@PathVariable("customerId") String customerId) {
        Cost cost = costService.calculateCostForCustomer(customerId);
        return new ResponseEntity<>(cost, HttpStatus.OK);
    }
}
