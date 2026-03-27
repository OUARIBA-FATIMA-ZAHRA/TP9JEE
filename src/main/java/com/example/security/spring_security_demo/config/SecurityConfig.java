package com.example.security.spring_security_demo.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configuration des utilisateurs en mémoire avec leurs rôles
     * {noop} signifie que le mot de passe n'est pas encodé (uniquement pour le développement)
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // Création de l'utilisateur ADMIN
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}1234")
                .roles("ADMIN")
                .build();

        // Création de l'utilisateur USER
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}1111")
                .roles("USER")
                .build();

        // Création d'un utilisateur MANAGER pour démonstration
        UserDetails manager = User.builder()
                .username("manager")
                .password("{noop}2222")
                .roles("MANAGER")
                .build();

        return new InMemoryUserDetailsManager(admin, user, manager);
    }

    /**
     * Configuration des règles de sécurité pour les URLs
     * Version corrigée sans AntPathRequestMatcher déprécié
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Désactiver CSRF pour les API REST (optionnel, à activer en production)
                .csrf(csrf -> csrf.disable())

                // Configuration des autorisations
                .authorizeHttpRequests(auth -> auth
                        // Pages publiques - utilisation des patterns directement
                        .requestMatchers("/", "/home", "/public/**").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/login", "/error").permitAll()

                        // Pages protégées par rôles
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN", "MANAGER")

                        // Toutes les autres requêtes nécessitent une authentification
                        .anyRequest().authenticated()
                )

                // Configuration du formulaire de connexion
                .formLogin(form -> form
                        .loginPage("/login")  // Page de login personnalisée
                        .loginProcessingUrl("/authenticate")  // URL de traitement du login
                        .defaultSuccessUrl("/home", true)  // Redirection après succès
                        .failureUrl("/login?error=true")  // Redirection après échec
                        .permitAll()  // Permet à tous d'accéder à la page de login
                )

                // Configuration de la déconnexion - version sans AntPathRequestMatcher
                .logout(logout -> logout
                        .logoutUrl("/logout")  // URL de déconnexion
                        .logoutSuccessUrl("/login?logout=true")  // Redirection après déconnexion
                        .invalidateHttpSession(true)  // Invalider la session
                        .clearAuthentication(true)  // Effacer l'authentification
                        .deleteCookies("JSESSIONID")  // Supprimer les cookies
                        .permitAll()
                )

                // Configuration des en-têtes de sécurité
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.deny())
                        .xssProtection(xss -> xss.disable())
                );

        return http.build();
    }
}