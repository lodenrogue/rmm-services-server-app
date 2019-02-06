package com.arkvis.controller;

import com.arkvis.model.Device;
import com.arkvis.service.device.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class DeviceControllerTest {

    private static final String URI = "/devices";

    @InjectMocks
    private DeviceController deviceController;

    @Mock
    private DeviceService deviceService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(deviceController).build();
    }

    @Test
    public void should_returnCreatedStatus_when_creatingDeviceSuccessfully() throws Exception {
        Device device = createDevice("CUST", "SYS", "TYPE");
        performPost(URI, device)
                .andExpect(status().isCreated());
    }

    @Test
    public void should_returnUnprocessableEntityStatus_when_creatingDeviceButHasMissingFields() throws Exception {
        performPost(URI, new Device())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_returnOkStatus_when_gettingDevice() throws Exception {
        mockMvc.perform(get(URI + "/123"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_returnOkStatus_when_updatingDeviceSuccessfully() throws Exception {
        Device device = createDevice("CUSTOMER", "SYSTEM", "TYPE");
        performPut(URI + "/123", device)
                .andExpect(status().isOk());
    }

    @Test
    public void should_returnUnprocessableEntityStatus_when_updatingDeviceButHasMissingFields() throws Exception {
        performPut(URI + "/123", new Device())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_returnOkStatus_when_deletingDevice() throws Exception {
        mockMvc.perform(delete(URI + "/123"))
                .andExpect(status().isOk());
    }

    private Device createDevice(String customerId, String system, String type) {
        Device device = new Device();
        device.setCustomerId(customerId);
        device.setSystemName(system);
        device.setType(type);
        return device;
    }

    private ResultActions performPut(String uri, Object payload) throws Exception {
        return mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(payload)));
    }

    private ResultActions performPost(String uri, Object payload) throws Exception {
        return mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(payload)));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
