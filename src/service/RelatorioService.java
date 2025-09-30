package service;

import dto.MedicaoDTO;
import dto.ComodoDTO;
import dto.ResidenciaDTO;
import controller.MedicaoController;
import controller.ComodoController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RelatorioService {

    private final MedicaoController medicaoController;
    private final ComodoController comodoController;

    public RelatorioService() {
        this.medicaoController = new MedicaoController();
        this.comodoController = new ComodoController();
    }

    public void gerarRelatorioGeral(ResidenciaDTO residencia) throws SQLException, IOException {
        System.out.println("\n========================================");
        System.out.println("         RELATÓRIO GERAL Wi-Fi         ");
        System.out.println("========================================");
        System.out.println("Residência: " + residencia.getNome());
        System.out.println("Cliente: " + residencia.getCliente());
        System.out.println("Endereço: " + residencia.getEndereco());
        System.out.println("========================================");

        List<MedicaoDTO> medicoes = medicaoController.listarMedicoes()
                .stream()
                .filter(m -> m.getResidenciaId().equals(residencia.getId()))
                .collect(Collectors.toList());

        List<ComodoDTO> comodos = comodoController.buscarTodosComodos()
                .stream()
                .filter(c -> c.getResidenciaId().equals(residencia.getId()))
                .collect(Collectors.toList());

        if (medicoes.isEmpty()) {
            System.out.println("Nenhuma medição encontrada para esta residência.");
            return;
        }

        // Estatísticas gerais
        double nivelMedio = medicoes.stream().mapToInt(MedicaoDTO::getNivelSinal).average().orElse(0);
        double velocidadeMedia = medicoes.stream().mapToDouble(MedicaoDTO::getVelocidade).average().orElse(0);

        System.out.println("RESUMO GERAL:");
        System.out.println("- Total de Cômodos: " + comodos.size());
        System.out.println("- Total de Medições: " + medicoes.size());
        System.out.printf("- Nível de Sinal Médio: %.1f dBm%n", nivelMedio);
        System.out.printf("- Velocidade Média: %.1f Mbps%n", velocidadeMedia);

        // Análise por banda
        Map<String, List<MedicaoDTO>> medicoesPorBanda = medicoes.stream()
                .collect(Collectors.groupingBy(MedicaoDTO::getBanda));

        System.out.println("\nANÁLISE POR BANDA:");
        for (Map.Entry<String, List<MedicaoDTO>> entry : medicoesPorBanda.entrySet()) {
            String banda = entry.getKey();
            List<MedicaoDTO> medicoesBanda = entry.getValue();
            double nivelMedioBanda = medicoesBanda.stream().mapToInt(MedicaoDTO::getNivelSinal).average().orElse(0);
            double velocidadeMediaBanda = medicoesBanda.stream().mapToDouble(MedicaoDTO::getVelocidade).average().orElse(0);

            System.out.printf("- %s: %.1f dBm (%.1f Mbps) - %d medições%n", 
                    banda, nivelMedioBanda, velocidadeMediaBanda, medicoesBanda.size());
        }

        // Análise por cômodo
        System.out.println("\nANÁLISE POR CÔMODO:");
        for (ComodoDTO comodo : comodos) {
            List<MedicaoDTO> medicoesComodo = medicoes.stream()
                    .filter(m -> m.getComodoId().equals(comodo.getId()))
                    .collect(Collectors.toList());

            if (!medicoesComodo.isEmpty()) {
                double nivelMedioComodo = medicoesComodo.stream().mapToInt(MedicaoDTO::getNivelSinal).average().orElse(0);
                double velocidadeMediaComodo = medicoesComodo.stream().mapToDouble(MedicaoDTO::getVelocidade).average().orElse(0);

                System.out.printf("- %s: %.1f dBm (%.1f Mbps) - %d medições%n", 
                        comodo.getNome(), nivelMedioComodo, velocidadeMediaComodo, medicoesComodo.size());
            }
        }

        // Recomendações
        System.out.println("\nRECOMENDAÇÕES:");
        if (nivelMedio >= -50) {
            System.out.println("✓ Excelente cobertura Wi-Fi! Continue monitorando.");
        } else if (nivelMedio >= -70) {
            System.out.println("⚠ Cobertura adequada, mas considere otimizar posicionamento do roteador.");
        } else {
            System.out.println("❌ Sinal fraco detectado! Recomenda-se:");
            System.out.println("  - Reposicionar o roteador em local central");
            System.out.println("  - Considerar uso de repetidores Wi-Fi");
            System.out.println("  - Verificar interferências próximas");
        }

        System.out.println("========================================");
    }
}
