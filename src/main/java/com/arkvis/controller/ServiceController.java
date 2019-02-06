package com.arkvis.controller;

import com.arkvis.model.Service;
import com.arkvis.service.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @GetMapping("/services")
    public ResponseEntity<?> getServices() {
        List<Service> services = serviceService.getAllServices();
        return new ResponseEntity<>(services, HttpStatus.OK);
    }
}
