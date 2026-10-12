package com.medilabo.frontend_service.controller;

import com.medilabo.frontend_service.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

@Controller
public class LoginController {
    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        try {
            session.removeAttribute("token");
            String token = authService.login(username, password);
            session.setAttribute("token", token);
            return "redirect:/patients";
        }

        catch (HttpClientErrorException.Unauthorized e) {
            model.addAttribute("error", "Identifiants invalides");
            return "login";
        }
    }
}
