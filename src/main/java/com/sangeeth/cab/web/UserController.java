package com.sangeeth.cab.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sangeeth.cab.contract.User;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/user")
    public User getUser(HttpSession session) {
        return CurrentUser.require(session);
    }
}
