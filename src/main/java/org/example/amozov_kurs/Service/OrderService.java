package org.example.amozov_kurs.Service;

import org.example.amozov_kurs.DAO.ServiceDAO;
import org.example.amozov_kurs.Models.Service;

import java.time.LocalDate;

public class OrderService {
    private final ServiceDAO serviceDAO;

    public OrderService() {
        this.serviceDAO = new ServiceDAO();
    }

    public OrderService(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }
    public String addOrder(int idCar, int idUser, Service idService, LocalDate orderDate) {
        if (orderDate == null) {
            return "empty";
        }
        if (orderDate.isBefore(LocalDate.now())) {
            return "past_date";
        }
        boolean added = serviceDAO.addOrder(idCar, idUser, idService.getIdService(), orderDate);
        return added ? "ok" : "fail";
    }
}
