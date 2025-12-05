package org.example.amozov_kurs.Models;

import java.time.LocalDate;
import java.util.Date;

public class User {
    private Integer id_users;
    private String first_name;
    private String last_name;
    private String role;
    private String email;
    private String login;
    private String password;
    private Date birthday;
    private String phone_number;

    public User(Integer id_users,
                String first_name,
                String last_name,
                String role,
                String email,
                String login,
                String passord,
                Date birthday,
                String phone_number) {
        this.id_users = id_users;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = role;
        this.email = email;
        this.login = login;
        this.password = passord;
        this.birthday = birthday;
        this.phone_number = phone_number;
    }

    public Integer getId_users() { return id_users; }
    public String getFirst_name() { return first_name; }
    public String getLast_name() { return last_name; }
    public String getRole() { return role; }
    public String getEmail() { return email; }
    public String getLogin() { return login; }
    public Date getBirthday() { return birthday; }
    public String getPhone_number() { return phone_number; }
    public String getPassword() {return password; }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

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