package test;

import controller.ComodoController;
import dto.ComodoDTO;

public class TesteComodoController {
    public static void main(String[] args) {
        ComodoController comodoController = new ComodoController();

        try {
            // Criar um novo cômodo
            ComodoDTO novoComodo = new ComodoDTO(null, "Sala", 1L);
            novoComodo = comodoController.criarComodo(novoComodo);
            System.out.println("Cômodo criado com ID: " + novoComodo.getId());

            // Buscar um cômodo pelo ID
            ComodoDTO comodo = comodoController.buscarComodo(novoComodo.getId());
            System.out.println("Cômodo encontrado: " + comodo.getNome());

            // Atualizar um cômodo
            comodo.setNome("Sala de Estar");
            comodoController.atualizarComodo(comodo.getId(), comodo);
            System.out.println("Cômodo atualizado!");

            // Listar todos os cômodos
            System.out.println("Listando todos os cômodos:");
            comodoController.buscarTodosComodos().forEach(c -> System.out.println(c.getNome()));

            // Deletar um cômodo
            comodoController.deletarComodo(comodo.getId());
            System.out.println("Cômodo deletado!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
