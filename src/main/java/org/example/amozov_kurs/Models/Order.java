package org.example.amozov_kurs.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Order {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty carName;
    private final SimpleStringProperty userName;
    private final SimpleStringProperty serviceName;
    private final ObjectProperty<LocalDate> orderDate;
    private final SimpleStringProperty status;

    public Order(int id, String carName, String userName, String serviceName,
                 LocalDate orderDate, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.carName = new SimpleStringProperty(carName);
        this.userName = new SimpleStringProperty(userName);
        this.serviceName = new SimpleStringProperty(serviceName);
        this.orderDate = new SimpleObjectProperty<>(orderDate);
        this.status = new SimpleStringProperty(status);
    }

    public int getId() { return id.get(); }
    public String getCarName() { return carName.get(); }
    public String getUserName() { return userName.get(); }
    public String getServiceName() { return serviceName.get(); }
    public LocalDate getOrderDate() { return orderDate.get(); }
    public String getStatus() { return status.get(); }
}

