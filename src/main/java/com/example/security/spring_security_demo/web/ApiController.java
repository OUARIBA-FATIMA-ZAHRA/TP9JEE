package com.example.security.spring_security_demo.web;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    /**
     * Endpoint public - accessible sans authentification
     */
    @GetMapping("/public/hello")
    public Map<String, String> publicHello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from public API!");
        response.put("status", "This endpoint is accessible without authentication");
        return response;
    }

    /**
     * Endpoint utilisateur - accessible aux rôles USER et ADMIN
     */
    @GetMapping("/user/profile")
    public Map<String, Object> userProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User profile information");
        response.put("username", auth.getName());
        response.put("roles", auth.getAuthorities());
        response.put("accessibleBy", "USER, ADMIN roles");
        return response;
    }

    /**
     * Endpoint manager - accessible aux rôles MANAGER et ADMIN
     */
    @GetMapping("/manager/stats")
    public Map<String, Object> managerStats() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Manager statistics");
        response.put("username", auth.getName());
        response.put("roles", auth.getAuthorities());
        response.put("accessibleBy", "MANAGER, ADMIN roles");
        response.put("stats", Map.of(
                "totalUsers", 1245,
                "activeUsers", 892,
                "revenue", "125,000 MAD"
        ));
        return response;
    }

    /**
     * Endpoint admin - accessible uniquement au rôle ADMIN
     */
    @GetMapping("/admin/system")
    public Map<String, Object> adminSystem() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "System administration");
        response.put("username", auth.getName());
        response.put("roles", auth.getAuthorities());
        response.put("accessibleBy", "ADMIN only");
        response.put("systemInfo", Map.of(
                "version", "1.0.0",
                "environment", "Development",
                "uptime", "5 days",
                "memoryUsage", "512MB/2GB"
        ));
        return response;
    }

    /**
     * Endpoint pour obtenir les informations de l'utilisateur courant
     */
    @GetMapping("/me")
    public Map<String, Object> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();
        response.put("authenticated", auth.isAuthenticated());
        response.put("username", auth.getName());
        response.put("roles", auth.getAuthorities());
        response.put("principal", auth.getPrincipal());
        return response;
    }
}
