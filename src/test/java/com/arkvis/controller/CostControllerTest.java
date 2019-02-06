package com.arkvis.controller;

import com.arkvis.service.cost.CostService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class CostControllerTest {

    private static final String URI = "/customers/TEST/cost";

    @InjectMocks
    private CostController costController;

    @Mock
    private CostService costService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(costController).build();
    }

    @Test
    public void should_returnOkStatus_when_gettingCost() throws Exception {
        mockMvc.perform(get(URI))
                .andExpect(status().isOk());

    }
}
