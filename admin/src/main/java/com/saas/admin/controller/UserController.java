package com.saas.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/short-link/admin/v1/user/{username}")
    public String getByUsername(@PathVariable("username") String username) {
        return "hello " + username;
    }

    @GetMapping("/api/short-link/admin/v1/user/id/{id}")
    public String getById(@PathVariable("id") Long id) {
        return "User ID: " + id;
    }

    @GetMapping("/api/short-link/admin/v1/user/email/{email}")
    public String getByEmail(@PathVariable("email") String email) {
        return "User email: " + email;
    }

    @GetMapping("/api/short-link/admin/v1/user/phone/{phone}")
    public String getByPhone(@PathVariable("phone") String phone) {
        return "User phone: " + phone;
    }

    
}