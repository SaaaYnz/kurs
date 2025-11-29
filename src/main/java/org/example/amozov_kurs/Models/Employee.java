package org.example.amozov_kurs.Models;

public class Employee {
    private Integer id_employees;
    private String first_name;
    private String last_name;
    private String role;
    private String email;
    private String login;

    public Employee(Integer id_employees,
                    String first_name,
                    String last_name,
                    String role,
                    String email,
                    String login) {
        this.id_employees = id_employees;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = role;
        this.email = email;
        this.login = login;
    }

    public Integer getId_employees() { return id_employees; }

    public String getFirst_name() { return first_name; }

    public String getLast_name() { return last_name; }

    public String getRole() { return role; }

    public String getEmail() { return email; }

    public String getLogin() { return login; }
}
