package com.example.security.spring_security_demo;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class SpringSecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDemoApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		System.out.println("\n========================================");
		System.out.println(" Spring Security Demo est prêt !");
		System.out.println("========================================");
		System.out.println(" Application disponible sur : http://localhost:8080");
		System.out.println(" Page de connexion : http://localhost:8080/login");
		System.out.println("========================================");
		System.out.println(" Comptes de test :");
		System.out.println("   • Admin   : admin / 1234 (accès complet)");
		System.out.println("   • Manager : manager / 2222 (accès manager + user)");
		System.out.println("   • User    : user / 1111 (accès user uniquement)");
		System.out.println("========================================\n");
	}
}