package com.arkvis.service.service;

import com.arkvis.model.Service;
import com.arkvis.repository.service.ServiceRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ServiceServiceImplTest {
    @InjectMocks
    private ServiceServiceImpl serviceService;

    @Mock
    private ServiceRepository serviceRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        standaloneSetup(serviceService).build();
    }

    @Test
    public void should_getServicesFromRepository_when_gettingServices() {
        when(serviceRepository.findAll()).thenReturn(Arrays.asList(new Service(), new Service()));

        serviceService.getAllServices();
        verify(serviceRepository).findAll();
    }
}
