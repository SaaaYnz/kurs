package org.example.amozov_kurs.Models;

public class Car {
    private Integer idCars;
    private Integer idManufacturers;
    private String manufacturerName;
    private String modelName;
    private String bodyType;
    private Integer year;
    private Integer price;
    private String engineType;
    private String transmission;
    private String imagePath;

    public Car(Integer idCars,
               Integer idManufacturers,
               String modelName,
               String bodyType,
               Integer year,
               Integer price,
               String engineType,
               String transmission,
               String imagePath) {
        this.idCars = idCars;
        this.idManufacturers = idManufacturers;
        this.modelName = modelName;
        this.bodyType = bodyType;
        this.year = year;
        this.price = price;
        this.engineType = engineType;
        this.transmission = transmission;
        this.imagePath = imagePath;
    }

    public Integer getIdCars() { return idCars; }

    public Integer getIdManufacturers() { return idManufacturers; }

    public void setManufacturerName(String manufacturerName) { this.manufacturerName = manufacturerName; }

    public String getModelName() { return modelName; }

    public String getBodyType() { return bodyType; }

    public Integer getYear() { return year; }

    public Integer getPrice() { return price; }

    public String getEngineType() { return engineType; }

    public String getTransmission() { return transmission;}

    public String getManufacturerName() { return manufacturerName; }

    public String getImagePath() { return imagePath; }
}