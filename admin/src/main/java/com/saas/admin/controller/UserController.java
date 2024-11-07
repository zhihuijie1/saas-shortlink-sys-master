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
}
