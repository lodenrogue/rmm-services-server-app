package com.arkvis.service.device;

import com.arkvis.model.Device;

public interface DeviceService {

    Device createDevice(Device device);

    Device getDevice(String id);

    void updateDevice(Device device);

    void deleteDevice(String id);
}
