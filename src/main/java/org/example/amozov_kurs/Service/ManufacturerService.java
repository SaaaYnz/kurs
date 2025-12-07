package org.example.amozov_kurs.Service;

import org.example.amozov_kurs.DAO.ManufacturerDAO;

public class ManufacturerService {
    private final ManufacturerDAO manufacturerDAO;

    public ManufacturerService() {
        this.manufacturerDAO = new ManufacturerDAO();
    }

    public ManufacturerService(ManufacturerDAO manufacturerDAO) {
        this.manufacturerDAO = manufacturerDAO;
    }

    public String addManufacturer(String name, String country) {
        if (name == null || name.isEmpty() || country == null || country.isEmpty()) {
            return "empty";
        }
        boolean success = manufacturerDAO.addManufacture(name, country);
        return success ? "ok" : "fail";
    }
}