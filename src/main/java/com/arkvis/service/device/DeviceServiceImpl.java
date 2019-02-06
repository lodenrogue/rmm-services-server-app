package com.arkvis.service.device;

import com.arkvis.error.NotFoundException;
import com.arkvis.model.Device;
import com.arkvis.repository.device.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public Device createDevice(Device device) {
        device.setId(UUID.randomUUID().toString());
        deviceRepository.save(device);
        return device;
    }

    @Override
    public Device getDevice(String id) {
        Device device = deviceRepository.findOne(id);
        if (device == null) {
            throw new NotFoundException("No device found with id " + id);
        }
        return device;
    }

    @Override
    public void updateDevice(Device device) {
        deviceRepository.save(device);
    }

    @Override
    public void deleteDevice(String id) {
        try {
            deviceRepository.delete(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("No device found with id " + id);
        }

    }
}
