package org.example.amozov_kurs.Models;

public class User {
    private Integer id_users;
    private String first_name;
    private String last_name;
    private String role; // client, consultant, admin
    private String email;
    private String login;

    public User(Integer id_users,
                String first_name,
                String last_name,
                String role,
                String email,
                String login) {
        this.id_users = id_users;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = role;
        this.email = email;
        this.login = login;
    }

    public Integer getId_users() { return id_users; }

    public String getFirst_name() { return first_name; }

    public String getLast_name() { return last_name; }

    public String getRole() { return role; }

    public String getEmail() { return email; }

    public String getLogin() { return login; }

    // Методы для проверки ролей
    public boolean isClient() {
        return "client".equals(role);
    }

    public boolean isEmployee() {
        return "employee".equals(role);
    }

    public boolean isAdmin() {
        return "admin".equals(role);
    }
}