package test;

import controller.MedicaoController;
import dto.MedicaoDTO;
import model.Comodo;
import model.Residencia;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TesteMedicaoController {
    public static void main(String[] args) {
        MedicaoController medicaoController = new MedicaoController();

        try {
            // Criar uma nova medição
            MedicaoDTO novaMedicao = new MedicaoDTO(
                null,
                LocalDateTime.now(),
                -65,
                50.0,
                "Baixa interferência",
                "5GHz",
                1L,
                1L 
            );
            novaMedicao = medicaoController.criarMedicao(novaMedicao);
            System.out.println("Medição criada com ID: " + novaMedicao.getId());

            // Listar todas as medições
            System.out.println("Listando todas as medições:");
            medicaoController.listarMedicoes().forEach(m -> {
                System.out.println("ID: " + m.getId() + ", Nível de Sinal: " + m.getNivelSinal());
            });

            // Deletar uma medição
            medicaoController.deletarMedicao(novaMedicao.getId());
            System.out.println("Medição deletada com sucesso!");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
