package test;

import controller.ResidenciaController;
import dto.ResidenciaDTO;

import java.io.IOException;
import java.sql.SQLException;

public class TesteResidenciaController {
    public static void main(String[] args) {
        ResidenciaController residenciaController = new ResidenciaController();

        try {
            // Criar uma nova residência
            ResidenciaDTO novaResidencia = new ResidenciaDTO(null, "Residência 1", "Rua A, 123");
            novaResidencia = residenciaController.criarResidencia(novaResidencia);
            System.out.println("Residência criada com ID: " + novaResidencia.getId());

            // Buscar uma residência pelo ID
            ResidenciaDTO residencia = residenciaController.buscarResidencia(novaResidencia.getId());
            System.out.println("Residência encontrada: " + residencia.getNome());

            // Atualizar uma residência
            residencia.setNome("Residência Atualizada");
            residenciaController.atualizarResidencia(residencia.getId(), residencia);
            System.out.println("Residência atualizada!");

            // Listar todas as residências
            System.out.println("Listando todas as residências:");
            residenciaController.listarResidencias().forEach(r -> System.out.println(r.getNome()));

            // Deletar uma residência
            residenciaController.deletarResidencia(residencia.getId());
            System.out.println("Residência deletada!");

            // Tentar buscar uma residência deletada
            try {
                residenciaController.buscarResidencia(residencia.getId());
            } catch (SQLException e) {
                System.out.println("Erro esperado: " + e.getMessage());
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
