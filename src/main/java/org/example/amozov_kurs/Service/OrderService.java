package org.example.amozov_kurs.Service;

import org.example.amozov_kurs.DAO.OrderDAO;
import org.example.amozov_kurs.DAO.ServiceDAO;
import org.example.amozov_kurs.Models.Service;

import java.time.LocalDate;

public class OrderService {
    private final OrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
    }

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }
    public String addOrder(int idCar, int idUser, Service idService, LocalDate orderDate) {
        if (orderDate == null) {
            return "empty";
        }
        if (orderDate.isBefore(LocalDate.now())) {
            return "past_date";
        }
        boolean added = orderDAO.addOrder(idCar, idUser, idService.getIdService(), orderDate);
        return added ? "ok" : "fail";
    }
}
