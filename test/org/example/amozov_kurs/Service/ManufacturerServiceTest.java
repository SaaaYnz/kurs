package org.example.amozov_kurs.Service;

import org.example.amozov_kurs.DAO.ManufacturerDAO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManufacturerServiceTest {

    @Test
    void addManufacturerSuccess() {
        ManufacturerDAO manufacturerDAO = mock(ManufacturerDAO.class);
        when(manufacturerDAO.addManufacture("Lamborghini", "Italy")).thenReturn(true);
        ManufacturerService manufacturerService = new ManufacturerService(manufacturerDAO);
        String result = manufacturerService.addManufacturer("Lamborghini", "Italy");
        assertEquals("ok", result);
        verify(manufacturerDAO, times(1)).addManufacture("Lamborghini", "Italy");
    }

    @Test
    void addManufacturerEmpty() {
        ManufacturerDAO manufacturerDAO = mock(ManufacturerDAO.class);
        ManufacturerService manufacturerService = new ManufacturerService(manufacturerDAO);
        String result = manufacturerService.addManufacturer("", "");
        assertEquals("empty", result);
        verify(manufacturerDAO, never()).addManufacture(anyString(), anyString());
    }

    @Test
    void addManufacturerFail() {
        ManufacturerDAO manufacturerDAO = mock(ManufacturerDAO.class);
        when(manufacturerDAO.addManufacture("Lamborghini", "Italy")).thenReturn(false);
        ManufacturerService manufacturerService = new ManufacturerService(manufacturerDAO);
        String result = manufacturerService.addManufacturer("Lamborghini", "Italy");
        assertEquals("fail", result);
    }
}