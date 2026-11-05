package com.linkflow.dto;

import jakarta.validation.constraints.*;

public class RegisterRequest {

    @Size(max = 100, message = "Name cannot exceed 100 characters")
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    @Size(min = 8, max = 72, message = "Password must be between 8 and 72 characters")
    @NotBlank(message = "Password is required")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
