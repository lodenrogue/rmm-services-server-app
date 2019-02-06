package com.arkvis.controller;

import com.arkvis.model.CustomerService;
import com.arkvis.service.customerservice.CustomerServiceService;
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

public class CustomerServiceControllerTest {

    private static final String URI = "/customers/TEST/services";

    @InjectMocks
    private CustomerServiceController customerServiceController;

    @Mock
    private CustomerServiceService customerServiceService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(customerServiceController).build();
    }

    @Test
    public void should_returnCreatedStatus_when_creatingCustomerServiceSuccessfully() throws Exception {
        CustomerService customerService = createCustomerService("TEST_SERVICE");
        performPost(URI, customerService)
                .andExpect(status().isCreated());
    }

    @Test
    public void should_returnUnprocessableEntityStatus_when_creatingCustomerServiceButMissingServiceName() throws Exception {
        performPost(URI, new CustomerService())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_returnOkStatus_when_deletingCustomerService() throws Exception {
        mockMvc.perform(delete(URI + "/SERVICE_NAME"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_returnOkStatus_when_gettingCustomerServices() throws Exception {
        mockMvc.perform(get(URI))
                .andExpect(status().isOk());
    }

    private CustomerService createCustomerService(String serviceName) {
        CustomerService customerService = new CustomerService();
        customerService.setServiceName(serviceName);
        return customerService;
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
