package com.example.um2;

public class Usuario {
    private String userName;
    private String email;
    private String password;
    private String dateOfBirth;

    // Constructor vacío requerido por Firebase
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(String userName, String email, String password, String dateOfBirth) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    // Métodos getters y setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}

