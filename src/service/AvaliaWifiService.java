package service;

import model.Medicao;
import java.util.List;

/**
 * Serviço responsável por avaliar a qualidade do sinal Wi-Fi nas medições realizadas.
 */
public class AvaliaWifiService {

    /**
     * Calcula o nível médio do sinal das medições informadas.
     *
     * @param medicoes Lista de medições realizadas.
     * @return Nível médio do sinal em dBm.
     * @throws IllegalArgumentException se a lista for nula ou vazia.
     */
    public double avaliarNivelSinal(List<Medicao> medicoes) {
        if (medicoes == null || medicoes.isEmpty()) {
            throw new IllegalArgumentException("A lista de medições não pode ser nula ou vazia.");
        }

        double somaSinal = medicoes.stream()
                                   .mapToDouble(Medicao::getNivelSinal)
                                   .sum();

        return somaSinal / medicoes.size();
    }

    /**
     * Verifica se há interferência significativa no sinal.
     *
     * @param nivelSinal Nível médio do sinal em dBm.
     * @return true se houver interferência, false caso contrário.
     */
    public boolean verificarInterferencia(double nivelSinal) {
        final int LIMITE_INTERFERENCIA = -70;
        return nivelSinal < LIMITE_INTERFERENCIA;
    }

    /**
     * Sugere melhorias para o sinal Wi-Fi com base no nível médio.
     *
     * @param nivelSinal Nível médio do sinal em dBm.
     * @return Sugestão de melhoria.
     */
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