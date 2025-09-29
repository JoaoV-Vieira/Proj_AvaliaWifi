package service;

import controller.ComodoController;
import controller.MedicaoController;
import controller.ResidenciaController;
import dto.ComodoDTO;
import dto.MedicaoDTO;
import dto.ResidenciaDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class AvaliaWifi {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ResidenciaController residenciaController = new ResidenciaController();
    private static final ComodoController comodoController = new ComodoController();
    private static final MedicaoController medicaoController = new MedicaoController();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Cadastrar Nova Residência");
            System.out.println("2. Selecionar Residência Existente");
            System.out.println("3. Deletar Residência");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            try {
                switch (opcao) {
                    case 1 -> cadastrarResidencia();
                    case 2 -> selecionarResidencia();
                    case 3 -> deletarResidencia();
                    case 0 -> {
                        System.out.println("Saindo do sistema...");
                        return;
                    }
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static void cadastrarResidencia() throws SQLException, IOException {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Cliente: ");
        String cliente = scanner.nextLine();
        ResidenciaDTO residencia = new ResidenciaDTO(null, nome, endereco, cliente);
        residencia = residenciaController.criarResidencia(residencia);
        System.out.println("Residência criada com ID: " + residencia.getId());
    }

    private static void selecionarResidencia() throws SQLException, IOException {
        List<ResidenciaDTO> residencias = residenciaController.listarResidencias();
        if (residencias.isEmpty()) {
            System.out.println("Nenhuma residência cadastrada.");
            return;
        }

        System.out.println("\n=== Residências Cadastradas ===");
        residencias.forEach(r -> System.out.println(r.getId() + " - " + r.getNome() + " (" + r.getCliente() + ")"));
        System.out.print("Digite o ID da residência que deseja selecionar: ");
        Long residenciaId = scanner.nextLong();
        scanner.nextLine();

        ResidenciaDTO residenciaSelecionada = residencias.stream()
                .filter(r -> r.getId().equals(residenciaId))
                .findFirst()
                .orElse(null);

        if (residenciaSelecionada == null) {
            System.out.println("Residência não encontrada.");
            return;
        }

        gerenciarResidenciaSelecionada(residenciaSelecionada);
    }

    private static void deletarResidencia() throws SQLException, IOException {
        List<ResidenciaDTO> residencias = residenciaController.listarResidencias();
        if (residencias.isEmpty()) {
            System.out.println("Nenhuma residência cadastrada.");
            return;
        }

        System.out.println("\n=== Residências Cadastradas ===");
        residencias.forEach(r -> System.out.println(r.getId() + " - " + r.getNome() + " (" + r.getCliente() + ")"));
        System.out.print("Digite o ID da residência que deseja deletar: ");
        Long residenciaId = scanner.nextLong();
        scanner.nextLine();

        residenciaController.deletarResidencia(residenciaId);
        System.out.println("Residência deletada com sucesso!");
    }

    private static void gerenciarResidenciaSelecionada(ResidenciaDTO residencia) throws SQLException, IOException {
        while (true) {
            System.out.println("\n=== Gerenciar Residência: " + residencia.getNome() + " ===");
            System.out.println("1. Cadastrar Cômodo");
            System.out.println("2. Cadastrar Medição");
            System.out.println("3. Listar Cômodos");
            System.out.println("4. Listar Medições");
            System.out.println("5. Excluir Cômodo");
            System.out.println("6. Excluir Medição");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarComodo(residencia);
                case 2 -> cadastrarMedicao(residencia);
                case 3 -> listarComodos(residencia);
                case 4 -> listarMedicoes(residencia);
                case 5 -> excluirComodo(residencia);
                case 6 -> excluirMedicao(residencia);
                case 0 -> {
                    System.out.println("Voltando ao menu principal...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarComodo(ResidenciaDTO residencia) throws SQLException, IOException {
        System.out.print("Nome do Cômodo: ");
        String nome = scanner.nextLine();
        ComodoDTO comodo = new ComodoDTO(null, nome, residencia.getId());
        comodo = comodoController.criarComodo(comodo);
        System.out.println("Cômodo criado com ID: " + comodo.getId());
    }

    private static void cadastrarMedicao(ResidenciaDTO residencia) throws SQLException, IOException {
        List<ComodoDTO> comodos = comodoController.buscarTodosComodos();
        comodos = comodos.stream().filter(c -> c.getResidenciaId().equals(residencia.getId())).toList();

        if (comodos.isEmpty()) {
            System.out.println("Nenhum cômodo cadastrado para esta residência.");
            return;
        }

        System.out.println("\n=== Cômodos da Residência ===");
        comodos.forEach(c -> System.out.println(c.getId() + " - " + c.getNome()));
        System.out.print("Digite o ID do cômodo para cadastrar a medição: ");
        Long comodoId = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Data e Hora (DD/MM/YYYY HH:MM) [pressione Enter para usar o horário atual]: ");
        String dataHoraInput = scanner.nextLine();

        // Converter a data e hora para LocalDateTime ou usar o horário atual
        LocalDateTime dataHora;
        if (dataHoraInput.isEmpty()) {
            dataHora = LocalDateTime.now();
            System.out.println("Usando o horário atual: " + dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            try {
                dataHora = LocalDateTime.parse(dataHoraInput, formatter);
            } catch (Exception e) {
                System.out.println("Formato de data e hora inválido. Use o formato DD/MM/YYYY HH:MM.");
                return;
            }
        }

        System.out.print("Nível de Sinal: ");
        int nivelSinal = scanner.nextInt();
        System.out.print("Velocidade: ");
        double velocidade = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Interferência: ");
        String interferencia = scanner.nextLine();
        System.out.print("Banda (2.4GHz/5GHz): ");
        String banda = scanner.nextLine();

        MedicaoDTO medicao = new MedicaoDTO(null, dataHora, nivelSinal, velocidade, interferencia, banda, comodoId, residencia.getId());
        medicao = medicaoController.criarMedicao(medicao);
        System.out.println("Medição criada com ID: " + medicao.getId());
    }

    private static void listarComodos(ResidenciaDTO residencia) throws SQLException, IOException {
        List<ComodoDTO> comodos = comodoController.buscarTodosComodos();
        comodos = comodos.stream().filter(c -> c.getResidenciaId().equals(residencia.getId())).toList();

        if (comodos.isEmpty()) {
            System.out.println("Nenhum cômodo cadastrado para esta residência.");
            return;
        }

        System.out.println("\n=== Cômodos da Residência ===");
        System.out.println("Residência: " + residencia.getNome() + " | Cliente: " + residencia.getCliente());
        System.out.println("-----------------------------------");

        comodos.forEach(c -> System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome()));
    }

    private static void listarMedicoes(ResidenciaDTO residencia) throws SQLException, IOException {
        List<ComodoDTO> comodos = comodoController.buscarTodosComodos()
            .stream()
            .filter(c -> c.getResidenciaId().equals(residencia.getId()))
            .toList();

        if (comodos.isEmpty()) {
            System.out.println("Nenhum cômodo cadastrado para esta residência.");
            return;
        }

        System.out.println("\n=== Listar Medições ===");
        System.out.println("Residência: " + residencia.getNome() + " | Cliente: " + residencia.getCliente());
        System.out.println("-----------------------------------");

        System.out.println("1. Listar medições de TODOS os cômodos");
        System.out.println("2. Listar medições de um cômodo específico");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        List<MedicaoDTO> medicoes = medicaoController.listarMedicoes()
            .stream()
            .filter(m -> m.getResidenciaId().equals(residencia.getId()))
            .toList();

        switch (opcao) {
            case 1 -> {
                if (medicoes.isEmpty()) {
                    System.out.println("Nenhuma medição cadastrada para esta residência.");
                    return;
                }
                System.out.println("\n=== Medições de TODOS os cômodos ===");
                medicoes.forEach(m -> {
                    ComodoDTO comodo = comodos.stream().filter(c -> c.getId().equals(m.getComodoId())).findFirst().orElse(null);
                    System.out.println("ID: " + m.getId());
                    System.out.println("Data e Hora: " + m.getDataHora());
                    System.out.println("Nível de Sinal: " + m.getNivelSinal());
                    System.out.println("Velocidade: " + m.getVelocidade());
                    System.out.println("Interferência: " + m.getInterferencia());
                    System.out.println("Banda: " + m.getBanda());
                    System.out.println("Cômodo: " + (comodo != null ? comodo.getNome() : "Desconhecido"));
                    System.out.println("-----------------------------------");
                });
            }
            case 2 -> {
                System.out.println("\n=== Cômodos da Residência ===");
                comodos.forEach(c -> System.out.println(c.getId() + " - " + c.getNome()));
                System.out.print("Digite o ID do cômodo: ");
                Long comodoId = scanner.nextLong();
                scanner.nextLine();

                List<MedicaoDTO> medicoesComodo = medicoes.stream()
                    .filter(m -> m.getComodoId().equals(comodoId))
                    .toList();

                if (medicoesComodo.isEmpty()) {
                    System.out.println("Nenhuma medição cadastrada para este cômodo.");
                    return;
                }
                System.out.println("\n=== Medições do cômodo selecionado ===");
                medicoesComodo.forEach(m -> {
                    System.out.println("ID: " + m.getId());
                    System.out.println("Data e Hora: " + m.getDataHora());
                    System.out.println("Nível de Sinal: " + m.getNivelSinal());
                    System.out.println("Velocidade: " + m.getVelocidade());
                    System.out.println("Interferência: " + m.getInterferencia());
                    System.out.println("Banda: " + m.getBanda());
                    System.out.println("-----------------------------------");
                });
            }
            case 0 -> {
                System.out.println("Voltando...");
                return;
            }
            default -> System.out.println("Opção inválida!");
        }
    }

    private static void excluirComodo(ResidenciaDTO residencia) throws SQLException, IOException {
        List<ComodoDTO> comodos = comodoController.buscarTodosComodos()
                .stream()
                .filter(c -> c.getResidenciaId().equals(residencia.getId()))
                .toList();

        if (comodos.isEmpty()) {
            System.out.println("Nenhum cômodo cadastrado para esta residência.");
            return;
        }

        System.out.println("\n=== Cômodos da Residência ===");
        comodos.forEach(c -> System.out.println(c.getId() + " - " + c.getNome()));
        System.out.print("Digite o ID do cômodo que deseja excluir: ");
        Long comodoId = scanner.nextLong();
        scanner.nextLine();

        comodoController.deletarComodo(comodoId);
        System.out.println("Cômodo excluído com sucesso!");
    }

    private static void excluirMedicao(ResidenciaDTO residencia) throws SQLException, IOException {
        List<MedicaoDTO> medicoes = medicaoController.listarMedicoes()
                .stream()
                .filter(m -> m.getResidenciaId().equals(residencia.getId()))
                .toList();

        if (medicoes.isEmpty()) {
            System.out.println("Nenhuma medição cadastrada para esta residência.");
            return;
        }

        System.out.println("\n=== Medições da Residência ===");
        medicoes.forEach(m -> System.out.println(m.getId() + " - " + m.getDataHora() + " | Cômodo ID: " + m.getComodoId()));
        System.out.print("Digite o ID da medição que deseja excluir: ");
        Long medicaoId = scanner.nextLong();
        scanner.nextLine();

        medicaoController.deletarMedicao(medicaoId);
        System.out.println("Medição excluída com sucesso!");
    }
}