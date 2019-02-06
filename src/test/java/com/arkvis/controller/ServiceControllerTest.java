package com.arkvis.controller;

import com.arkvis.service.service.ServiceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ServiceControllerTest {
    private static final String URI = "/services";

    @InjectMocks
    private ServiceController serviceController;

    @Mock
    private ServiceService serviceService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(serviceController).build();
    }

    @Test
    public void should_returnOkStatus_when_gettingServices() throws Exception {
        mockMvc.perform(get(URI)).andExpect(status().isOk());
    }
}
