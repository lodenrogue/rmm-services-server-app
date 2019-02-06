package com.arkvis.controller;

import com.arkvis.error.ErrorUtils;
import com.arkvis.error.MissingFieldsError;
import com.arkvis.error.UnprocessableEntityException;
import com.arkvis.model.Device;
import com.arkvis.service.device.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/devices")
    public ResponseEntity<?> createDevice(@Valid @RequestBody Device device, @ApiIgnore Errors errors) {
        List<String> missingFields = ErrorUtils.getMissingFields(errors);

        if (!missingFields.isEmpty()) {
            MissingFieldsError mfe = new MissingFieldsError(missingFields);
            throw new UnprocessableEntityException(String.format("Missing fields are: %s", mfe.getMissingFields().toString()));
        }

        Device createdDevice = deviceService.createDevice(device);
        return new ResponseEntity<>(createdDevice, HttpStatus.CREATED);
    }

    @GetMapping("/devices/{id}")
    public ResponseEntity<?> getDevice(@PathVariable("id") String id) {
        Device device = deviceService.getDevice(id);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @PutMapping("/devices/{id}")
    public ResponseEntity<?> updateDevice(@PathVariable("id") String id, @Valid @RequestBody Device device, @ApiIgnore Errors errors) {
        List<String> missingFields = ErrorUtils.getMissingFields(errors);

        if (!missingFields.isEmpty()) {
            MissingFieldsError mfe = new MissingFieldsError(missingFields);
            throw new UnprocessableEntityException(String.format("Missing fields are: %s", mfe.getMissingFields().toString()));
        }

        device.setId(id);
        deviceService.updateDevice(device);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/devices/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable("id") String id) {
        deviceService.deleteDevice(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
