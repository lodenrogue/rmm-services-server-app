package com.arkvis.service.device;

import com.arkvis.error.NotFoundException;
import com.arkvis.model.Device;
import com.arkvis.repository.device.DeviceRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class DeviceServiceImplTest {

    @InjectMocks
    private DeviceServiceImpl deviceService;

    @Mock
    private DeviceRepository deviceRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        standaloneSetup(deviceService).build();
    }

    @Test
    public void should_returnDeviceWithId_when_creatingDevice() {
        Device device = new Device();
        Device created = deviceService.createDevice(device);

        assertNotNull(created);
        assertNotNull(created.getId());
    }

    @Test(expected = NotFoundException.class)
    public void should_throwNotFoundException_when_gettingDeviceButNoDeviceFound() {
        when(deviceRepository.findOne(anyString())).thenReturn(null);
        deviceService.getDevice("TEST_ID");
    }

    @Test
    public void should_returnDevice_when_successfullyGettingDevice() {
        when(deviceRepository.findOne(anyString())).thenReturn(new Device());
        Device device = deviceService.getDevice("TEST_ID");
        assertNotNull(device);
    }

    @Test
    public void should_saveDeviceInRepository_when_updatingDevice() {
        deviceService.updateDevice(new Device());
        verify(deviceRepository).save(any(Device.class));
    }

    @Test
    public void should_deleteDeviceFromRepository_when_deletingDevice() {
        deviceService.deleteDevice("TEST_ID");
        verify(deviceRepository).delete(anyString());
    }

    @Test(expected = NotFoundException.class)
    public void should_throwNotFoundException_when_deletingDeviceButNoDeviceFoundWithGivenId() {
        doThrow(new EmptyResultDataAccessException(1)).when(deviceRepository).delete(anyString());
        deviceService.deleteDevice("TEST_ID");
    }
}
