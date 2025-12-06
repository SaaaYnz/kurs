package org.example.amozov_kurs.Models;

public class Manufacturer {
    private Integer idManufacture;
    private String nameManufacture;
    private String country;

    public Manufacturer(Integer idManufacture,
                        String nameManufacture,
                        String country) {
        this.idManufacture = idManufacture;
        this.nameManufacture = nameManufacture;
        this.country = country;
    }

    public Integer getIdManufacture() { return idManufacture; }

    public String getNameManufacture() { return nameManufacture; }

    public String getCountry() { return country; }
}
