package com.arkvis.repository.device;

import com.arkvis.model.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeviceRepository extends CrudRepository<Device, String> {

    List<Device> findByCustomerId(String customerId);
}
