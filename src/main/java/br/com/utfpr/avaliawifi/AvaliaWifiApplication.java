package br.com.utfpr.avaliawifi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AvaliaWifiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AvaliaWifiApplication.class, args);
        
        System.out.println();
        System.out.println("==========================================");
        System.out.println("    SISTEMA AVALIAWIFI - WEB");
        System.out.println("    Monitoramento de Redes Wi-Fi");
        System.out.println("==========================================");
        System.out.println();
        System.out.println("🌐 Aplicação iniciada com sucesso!");
        System.out.println("📱 Acesse: http://localhost:8080/avaliawifi");
        System.out.println("🛠️  Console H2: http://localhost:8080/avaliawifi/h2-console");
        System.out.println();
        System.out.println("💾 Banco de dados: H2 (arquivo local)");
        System.out.println("📁 Local dos dados: ./data/avaliawifi.mv.db");
        System.out.println();
    }
}