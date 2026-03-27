package com.example.security.spring_security_demo.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    /**
     * Page d'accueil publique
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    /**
     * Page d'accueil après connexion
     */
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Accueil - Spring Security Demo");
        model.addAttribute("message", "Bienvenue sur notre application Spring Security !");
        return "home";
    }

    /**
     * Page de login personnalisée
     */
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {

        if (error != null) {
            model.addAttribute("errorMessage", "Nom d'utilisateur ou mot de passe incorrect");
        }

        if (logout != null) {
            model.addAttribute("logoutMessage", "Vous avez été déconnecté avec succès");
        }

        return "login";
    }

    /**
     * Page d'accès refusé
     */
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    /**
     * Page publique (accessible sans authentification)
     */
    @GetMapping("/public/info")
    public String publicInfo(Model model) {
        model.addAttribute("info", "Cette page est accessible sans authentification.");
        return "public-info";
    }

    /**
     * Espace utilisateur standard
     */
    @GetMapping("/user/dashboard")
    public String userDashboard(Model model) {
        model.addAttribute("role", "USER");
        model.addAttribute("features", new String[]{
                "Consulter son profil",
                "Voir ses commandes",
                "Modifier ses informations"
        });
        return "dashboard";
    }

    /**
     * Espace manager
     */
    @GetMapping("/manager/dashboard")
    public String managerDashboard(Model model) {
        model.addAttribute("role", "MANAGER");
        model.addAttribute("features", new String[]{
                "Gérer les équipes",
                "Approuver les demandes",
                "Générer des rapports"
        });
        return "dashboard";
    }

    /**
     * Espace administrateur
     */
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("role", "ADMIN");
        model.addAttribute("features", new String[]{
                "Gérer les utilisateurs",
                "Configurer l'application",
                "Voir les logs système",
                "Gérer les permissions"
        });
        return "dashboard";
    }

    /**
     * API REST pour les tests (optionnel)
     */
    @GetMapping("/api/user/info")
    public String apiUserInfo() {
        return "Informations utilisateur (REST API)";
    }
}