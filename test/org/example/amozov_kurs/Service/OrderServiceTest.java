package org.example.amozov_kurs.Service;

import org.example.amozov_kurs.DAO.OrderDAO;
import org.example.amozov_kurs.DAO.ServiceDAO;
import org.example.amozov_kurs.Models.Service;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @Test
    void addOrderSuccess() {
        OrderDAO orderDAO = mock(OrderDAO.class);
        when(orderDAO.addOrder(10, 10, 1, LocalDate.now().plusDays(2))).thenReturn(true);
        OrderService orderService = new OrderService(orderDAO);
        Service s = new Service();
        s.setIdService(1);
        String result = orderService.addOrder(10, 10, s, LocalDate.now().plusDays(2));
        assertEquals("ok", result);
    }
    @Test
    void addOrderEmpty() {
        OrderDAO orderDAO = mock(OrderDAO.class);
        OrderService orderService = new OrderService(orderDAO);
        Service s = new Service();
        s.setIdService(1);
        String result = orderService.addOrder(10, 10, null, null);
        assertEquals("empty", result);
    }

    @Test
    void addOrderFail() {
        OrderDAO orderDAO = mock(OrderDAO.class);
        OrderService orderService = new OrderService(orderDAO);
        when(orderDAO.addOrder(10, 10, 1, LocalDate.now().plusDays(2))).thenReturn(false);
        Service s = new Service();
        s.setIdService(1);
        String result = orderService.addOrder(10, 10, s, LocalDate.now().plusDays(2));
        assertEquals("fail", result);
    }

}