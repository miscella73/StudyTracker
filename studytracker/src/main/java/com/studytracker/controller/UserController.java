package com.studytracker.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studytracker.security.JwtUtil;
import com.studytracker.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String email, @RequestParam String password) {
        if (userService.emailExists(email)) {
            return "Email already in use.";
        }

        userService.register(email, password);
        return "Registeration success!";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        if (userService.login(email, password)) {
            String token = jwtUtil.generateToken(email);
            return "Bearer " + token;
        } else {
            return "Invalid email or password.";
        }
    }

    @GetMapping("/me")
    public String getCurrentUser() {

        System.out.println("✅ /api/me 호출됨");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("🔍 auth: " + auth);
        System.out.println("🔍 principal: " + auth.getPrincipal());

        return "Current user: " + auth.getPrincipal();
    }
}
