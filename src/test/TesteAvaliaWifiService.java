package test;

import model.Medicao;
import model.Comodo;
import model.Residencia;
import service.AvaliaWifiService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TesteAvaliaWifiService {
    public static void main(String[] args) {
        AvaliaWifiService service = new AvaliaWifiService();

        // Residência 1
        Residencia residencia1 = new Residencia(1L, "Casa 1", "Aqui, 123", "Joãozinho");
        Comodo sala1 = new Comodo(1L, "Sala", residencia1);
        Comodo quarto1 = new Comodo(2L, "Quarto", residencia1);

        List<Medicao> medicoes1 = new ArrayList<>();
        medicoes1.add(new Medicao(1L, LocalDateTime.now(), -48, 90.0, "Sem interferência", "5GHz", sala1, residencia1));
        medicoes1.add(new Medicao(2L, LocalDateTime.now(), -65, 40.0, "Pouca interferência", "5GHz", quarto1, residencia1));

        double nivelMedio1 = service.avaliarNivelSinal(medicoes1);
        System.out.println("Residência: " + residencia1.getNome());
        System.out.println("Endereço: " + residencia1.getEndereco());
        System.out.println("Nível médio do sinal: " + nivelMedio1 + " dBm");
        System.out.println("Há interferência? " + (service.verificarInterferencia(nivelMedio1) ? "Sim" : "Não"));
        System.out.println("Sugestão: " + service.sugerirMelhorias(nivelMedio1));
        System.out.println("-------");

        // Residência 2
        Residencia residencia2 = new Residencia(2L, "Casa 2", "Ali, 123", "Chuchuzinho");
        Comodo cozinha2 = new Comodo(3L, "Cozinha", residencia2);
        Comodo escritorio2 = new Comodo(4L, "Escritório", residencia2);

        List<Medicao> medicoes2 = new ArrayList<>();
        medicoes2.add(new Medicao(3L, LocalDateTime.now(), -72, 15.0, "Muita interferência", "2.4GHz", cozinha2, residencia2));
        medicoes2.add(new Medicao(4L, LocalDateTime.now(), -78, 10.0, "Muita interferência", "2.4GHz", escritorio2, residencia2));

        double nivelMedio2 = service.avaliarNivelSinal(medicoes2);
        System.out.println("Residência: " + residencia2.getNome());
        System.out.println("Endereço: " + residencia2.getEndereco());
        System.out.println("Nível médio do sinal: " + nivelMedio2 + " dBm");
        System.out.println("Há interferência? " + (service.verificarInterferencia(nivelMedio2) ? "Sim" : "Não"));
        System.out.println("Sugestão: " + service.sugerirMelhorias(nivelMedio2));
        System.out.println("-------");
    }
}