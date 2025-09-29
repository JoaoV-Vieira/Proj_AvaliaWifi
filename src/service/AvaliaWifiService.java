package service;

import model.Medicao;
import java.util.List;

public class AvaliaWifiService {

    public double avaliarNivelSinal(List<Medicao> medicoes) {
        if (medicoes == null || medicoes.isEmpty()) {
            throw new IllegalArgumentException("A lista de medições não pode ser nula ou vazia.");
        }

        double somaSinal = medicoes.stream()
                                   .mapToDouble(Medicao::getNivelSinal)
                                   .sum();

        return somaSinal / medicoes.size();
    }

    public boolean verificarInterferencia(double nivelSinal) {
        final int LIMITE_INTERFERENCIA = -70;
        return nivelSinal < LIMITE_INTERFERENCIA;
    }

    public String sugerirMelhorias(double nivelSinal) {
        if (nivelSinal >= -50) {
            return "O nível de sinal está excelente. Não são necessárias melhorias.";
        } else if (nivelSinal >= -70) {
            return "Considere reposicionar o roteador para melhor cobertura.";
        } else {
            return "O sinal está fraco. Considere adicionar um repetidor Wi-Fi ou reposicionar o roteador.";
        }
    }
}