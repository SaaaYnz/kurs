package org.example.amozov_kurs.Models;

public class Service {
    private Integer idService;
    private String serviceName;

    public Service(Integer idService, String serviceName) {
        this.idService = idService;
        this.serviceName = serviceName;
    }

    public Integer getIdService() { return  idService; }

    public String getServiceName() { return  serviceName; }
}
