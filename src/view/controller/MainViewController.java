package view.controller;

import dto.ComodoDTO;
import dto.MedicaoDTO;
import dto.ResidenciaDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import service.ComodoService;
import service.MedicaoService;
import service.RelatorioService;
import service.ResidenciaService;
import view.AvaliaWifiApplication;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    // Services (acessados via aplicacao principal)
    private ResidenciaService residenciaService;
    private ComodoService comodoService;
    private MedicaoService medicaoService;
    private RelatorioService relatorioService;

    // FXML Components - TabPane Principal
    @FXML private TabPane mainTabPane;
    
    // FXML Components - Aba Residências
    @FXML private TableView<ResidenciaDTO> residenciaTable;
    @FXML private TableColumn<ResidenciaDTO, Long> residenciaIdColumn;
    @FXML private TableColumn<ResidenciaDTO, String> residenciaNomeColumn;
    @FXML private TableColumn<ResidenciaDTO, String> residenciaEnderecoColumn;
    @FXML private TableColumn<ResidenciaDTO, String> residenciaClienteColumn;
    @FXML private Button btnNovaResidencia;
    @FXML private Button btnEditarResidencia;
    @FXML private Button btnExcluirResidencia;
    
    // FXML Components - Aba Cômodos
    @FXML private TableView<ComodoDTO> comodoTable;
    @FXML private TableColumn<ComodoDTO, Long> comodoIdColumn;
    @FXML private TableColumn<ComodoDTO, String> comodoNomeColumn;
    @FXML private TableColumn<ComodoDTO, Long> comodoResidenciaColumn;
    @FXML private ComboBox<ResidenciaDTO> residenciaComboBox;
    @FXML private Button btnNovoComodo;
    @FXML private Button btnEditarComodo;
    @FXML private Button btnExcluirComodo;
    
    // FXML Components - Aba Medições
    @FXML private TableView<MedicaoDTO> medicaoTable;
    @FXML private TableColumn<MedicaoDTO, Long> medicaoIdColumn;
    @FXML private TableColumn<MedicaoDTO, String> medicaoDataColumn;
    @FXML private TableColumn<MedicaoDTO, Integer> medicaoSinalColumn;
    @FXML private TableColumn<MedicaoDTO, Double> medicaoVelocidadeColumn;
    @FXML private TableColumn<MedicaoDTO, String> medicaoInterferenciaColumn;
    @FXML private TableColumn<MedicaoDTO, String> medicaoBandaColumn;
    @FXML private TableColumn<MedicaoDTO, String> medicaoComodoColumn;
    @FXML private ComboBox<ResidenciaDTO> residenciaMedicaoComboBox;
    @FXML private ComboBox<ComodoDTO> comodoMedicaoComboBox;
    @FXML private Button btnNovaMedicao;
    @FXML private Button btnEditarMedicao;
    @FXML private Button btnExcluirMedicao;
    
    // FXML Components - Aba Relatórios
    @FXML private ComboBox<ResidenciaDTO> residenciaRelatorioComboBox;
    @FXML private TextArea relatorioTextArea;
    @FXML private Button btnGerarRelatorio;
    @FXML private Button btnExportarRelatorio;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Obter referências dos services
        this.residenciaService = AvaliaWifiApplication.getResidenciaService();
        this.comodoService = AvaliaWifiApplication.getComodoService();
        this.medicaoService = AvaliaWifiApplication.getMedicaoService();
        this.relatorioService = AvaliaWifiApplication.getRelatorioService();

        // Configurar tabelas
        configurarTabelaResidencias();
        configurarTabelaComodos();
        configurarTabelaMedicoes();
        
        // Configurar ComboBoxes
        configurarComboBoxes();
        
        // Carregar dados iniciais
        carregarDadosIniciais();
        
        // Configurar listeners
        configurarListeners();
    }

    private void configurarTabelaResidencias() {
        residenciaIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        residenciaNomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        residenciaEnderecoColumn.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        residenciaClienteColumn.setCellValueFactory(new PropertyValueFactory<>("cliente"));
    }

    private void configurarTabelaComodos() {
        comodoIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        comodoNomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        comodoResidenciaColumn.setCellValueFactory(new PropertyValueFactory<>("residenciaId"));
    }

    private void configurarTabelaMedicoes() {
        medicaoIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        medicaoDataColumn.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        medicaoSinalColumn.setCellValueFactory(new PropertyValueFactory<>("nivelSinal"));
        medicaoVelocidadeColumn.setCellValueFactory(new PropertyValueFactory<>("velocidade"));
        medicaoInterferenciaColumn.setCellValueFactory(new PropertyValueFactory<>("interferencia"));
        medicaoBandaColumn.setCellValueFactory(new PropertyValueFactory<>("banda"));
        medicaoComodoColumn.setCellValueFactory(new PropertyValueFactory<>("comodoId"));
    }

    private void configurarComboBoxes() {
        // Configurar conversão de texto para ComboBoxes
        residenciaComboBox.setConverter(new StringConverter<ResidenciaDTO>() {
            @Override
            public String toString(ResidenciaDTO residencia) {
                return residencia != null ? residencia.getNome() : "";
            }

            @Override
            public ResidenciaDTO fromString(String string) {
                return null; // Não usado em ComboBox
            }
        });

        // Aplicar o mesmo para outros ComboBoxes
        residenciaMedicaoComboBox.setConverter(residenciaComboBox.getConverter());
        residenciaRelatorioComboBox.setConverter(residenciaComboBox.getConverter());
        
        comodoMedicaoComboBox.setConverter(new StringConverter<ComodoDTO>() {
            @Override
            public String toString(ComodoDTO comodo) {
                return comodo != null ? comodo.getNome() : "";
            }

            @Override
            public ComodoDTO fromString(String string) {
                return null;
            }
        });
    }

    private void configurarListeners() {
        // Listener para quando selecionar residência no ComboBox de cômodos
        residenciaComboBox.setOnAction(e -> {
            ResidenciaDTO residenciaSelecionada = residenciaComboBox.getValue();
            if (residenciaSelecionada != null) {
                carregarComodosPorResidencia(residenciaSelecionada.getId());
            }
        });

        // Listener para quando selecionar residência no ComboBox de medições
        residenciaMedicaoComboBox.setOnAction(e -> {
            ResidenciaDTO residenciaSelecionada = residenciaMedicaoComboBox.getValue();
            if (residenciaSelecionada != null) {
                carregarComodosPorResidenciaMedicao(residenciaSelecionada.getId());
                carregarMedicoesPorResidencia(residenciaSelecionada.getId());
            }
        });

        // Listener para quando selecionar cômodo no ComboBox de medições
        comodoMedicaoComboBox.setOnAction(e -> {
            ComodoDTO comodoSelecionado = comodoMedicaoComboBox.getValue();
            if (comodoSelecionado != null) {
                carregarMedicoesPorComodo(comodoSelecionado.getId());
            }
        });
    }

    private void carregarDadosIniciais() {
        try {
            carregarResidencias();
            carregarComboBoxResidencias();
        } catch (Exception e) {
            mostrarErro("Erro ao carregar dados iniciais", e.getMessage());
        }
    }

    // Métodos de carregamento de dados usando as Services
    private void carregarResidencias() {
        try {
            List<ResidenciaDTO> residencias = residenciaService.listarResidencias();
            ObservableList<ResidenciaDTO> residenciaList = FXCollections.observableArrayList(residencias);
            residenciaTable.setItems(residenciaList);
        } catch (SQLException | IOException e) {
            mostrarErro("Erro ao carregar residências", e.getMessage());
        }
    }

    private void carregarComboBoxResidencias() {
        try {
            List<ResidenciaDTO> residencias = residenciaService.listarResidencias();
            ObservableList<ResidenciaDTO> residenciaList = FXCollections.observableArrayList(residencias);
            
            residenciaComboBox.setItems(residenciaList);
            residenciaMedicaoComboBox.setItems(residenciaList);
            residenciaRelatorioComboBox.setItems(residenciaList);
        } catch (SQLException | IOException e) {
            mostrarErro("Erro ao carregar lista de residências", e.getMessage());
        }
    }

    private void carregarComodosPorResidencia(Long residenciaId) {
        try {
            List<ComodoDTO> comodos = comodoService.listarComodos();
            // Filtrar por residência no lado da aplicacao
            List<ComodoDTO> comodosFiltrados = comodos.stream()
                .filter(c -> c.getResidenciaId().equals(residenciaId))
                .collect(java.util.stream.Collectors.toList());
            ObservableList<ComodoDTO> comodoList = FXCollections.observableArrayList(comodosFiltrados);
            comodoTable.setItems(comodoList);
        } catch (SQLException | IOException e) {
            mostrarErro("Erro ao carregar cômodos", e.getMessage());
        }
    }

    private void carregarComodosPorResidenciaMedicao(Long residenciaId) {
        try {
            List<ComodoDTO> comodos = comodoService.listarComodos();
            // Filtrar por residência
            List<ComodoDTO> comodosFiltrados = comodos.stream()
                .filter(c -> c.getResidenciaId().equals(residenciaId))
                .collect(java.util.stream.Collectors.toList());
            ObservableList<ComodoDTO> comodoList = FXCollections.observableArrayList(comodosFiltrados);
            comodoMedicaoComboBox.setItems(comodoList);
        } catch (SQLException | IOException e) {
            mostrarErro("Erro ao carregar cômodos para medição", e.getMessage());
        }
    }

    private void carregarMedicoesPorResidencia(Long residenciaId) {
        try {
            List<MedicaoDTO> medicoes = medicaoService.listarMedicoes();
            // Filtrar por residência
            List<MedicaoDTO> medicoesFiltradas = medicoes.stream()
                .filter(m -> m.getResidenciaId().equals(residenciaId))
                .collect(java.util.stream.Collectors.toList());
            ObservableList<MedicaoDTO> medicaoList = FXCollections.observableArrayList(medicoesFiltradas);
            medicaoTable.setItems(medicaoList);
        } catch (SQLException | IOException e) {
            mostrarErro("Erro ao carregar medições por residência", e.getMessage());
        }
    }

    private void carregarMedicoesPorComodo(Long comodoId) {
        try {
            List<MedicaoDTO> medicoes = medicaoService.listarMedicoes();
            // Filtrar por cômodo
            List<MedicaoDTO> medicoesFiltradas = medicoes.stream()
                .filter(m -> m.getComodoId().equals(comodoId))
                .collect(java.util.stream.Collectors.toList());
            ObservableList<MedicaoDTO> medicaoList = FXCollections.observableArrayList(medicoesFiltradas);
            medicaoTable.setItems(medicaoList);
        } catch (SQLException | IOException e) {
            mostrarErro("Erro ao carregar medições por cômodo", e.getMessage());
        }
    }

    // Event Handlers - Residências
    @FXML
    private void handleNovaResidencia() {
        abrirDialogoResidencia(null);
    }

    @FXML
    private void handleEditarResidencia() {
        ResidenciaDTO residenciaSelecionada = residenciaTable.getSelectionModel().getSelectedItem();
        if (residenciaSelecionada != null) {
            abrirDialogoResidencia(residenciaSelecionada);
        } else {
            mostrarAviso("Selecione uma residência para editar");
        }
    }

    @FXML
    private void handleExcluirResidencia() {
        ResidenciaDTO residenciaSelecionada = residenciaTable.getSelectionModel().getSelectedItem();
        if (residenciaSelecionada != null) {
            if (confirmarExclusao("residência " + residenciaSelecionada.getNome())) {
                try {
                    residenciaService.deletarResidencia(residenciaSelecionada.getId());
                    carregarResidencias();
                    carregarComboBoxResidencias();
                    mostrarSucesso("Residência excluída com sucesso!");
                } catch (SQLException | IOException e) {
                    mostrarErro("Erro ao excluir residência", e.getMessage());
                }
            }
        } else {
            mostrarAviso("Selecione uma residência para excluir");
        }
    }

    // Event Handlers - Cômodos
    @FXML
    private void handleNovoComodo() {
        ResidenciaDTO residenciaSelecionada = residenciaComboBox.getValue();
        if (residenciaSelecionada != null) {
            abrirDialogoComodo(null, residenciaSelecionada.getId());
        } else {
            mostrarAviso("Selecione uma residência primeiro");
        }
    }

    @FXML
    private void handleEditarComodo() {
        ComodoDTO comodoSelecionado = comodoTable.getSelectionModel().getSelectedItem();
        if (comodoSelecionado != null) {
            abrirDialogoComodo(comodoSelecionado, comodoSelecionado.getResidenciaId());
        } else {
            mostrarAviso("Selecione um cômodo para editar");
        }
    }

    @FXML
    private void handleExcluirComodo() {
        ComodoDTO comodoSelecionado = comodoTable.getSelectionModel().getSelectedItem();
        if (comodoSelecionado != null) {
            if (confirmarExclusao("cômodo " + comodoSelecionado.getNome())) {
                try {
                    comodoService.deletarComodo(comodoSelecionado.getId());
                    ResidenciaDTO residenciaSelecionada = residenciaComboBox.getValue();
                    if (residenciaSelecionada != null) {
                        carregarComodosPorResidencia(residenciaSelecionada.getId());
                    }
                    mostrarSucesso("Cômodo excluído com sucesso!");
                } catch (SQLException | IOException e) {
                    mostrarErro("Erro ao excluir cômodo", e.getMessage());
                }
            }
        } else {
            mostrarAviso("Selecione um cômodo para excluir");
        }
    }

    // Event Handlers - Medições
    @FXML
    private void handleNovaMedicao() {
        ComodoDTO comodoSelecionado = comodoMedicaoComboBox.getValue();
        ResidenciaDTO residenciaSelecionada = residenciaMedicaoComboBox.getValue();
        
        if (residenciaSelecionada != null && comodoSelecionado != null) {
            abrirDialogoMedicao(null, residenciaSelecionada.getId(), comodoSelecionado.getId());
        } else {
            mostrarAviso("Selecione uma residência e um cômodo primeiro");
        }
    }

    @FXML
    private void handleEditarMedicao() {
        MedicaoDTO medicaoSelecionada = medicaoTable.getSelectionModel().getSelectedItem();
        if (medicaoSelecionada != null) {
            abrirDialogoMedicao(medicaoSelecionada, medicaoSelecionada.getResidenciaId(), medicaoSelecionada.getComodoId());
        } else {
            mostrarAviso("Selecione uma medição para editar");
        }
    }

    @FXML
    private void handleExcluirMedicao() {
        MedicaoDTO medicaoSelecionada = medicaoTable.getSelectionModel().getSelectedItem();
        if (medicaoSelecionada != null) {
            if (confirmarExclusao("medição do dia " + medicaoSelecionada.getDataHora())) {
                try {
                    medicaoService.deletarMedicao(medicaoSelecionada.getId());
                    ComodoDTO comodoSelecionado = comodoMedicaoComboBox.getValue();
                    if (comodoSelecionado != null) {
                        carregarMedicoesPorComodo(comodoSelecionado.getId());
                    }
                    mostrarSucesso("Medição excluída com sucesso!");
                } catch (SQLException | IOException e) {
                    mostrarErro("Erro ao excluir medição", e.getMessage());
                }
            }
        } else {
            mostrarAviso("Selecione uma medição para excluir");
        }
    }

    // Event Handlers - Relatórios
    @FXML
    private void handleGerarRelatorio() {
        ResidenciaDTO residenciaSelecionada = residenciaRelatorioComboBox.getValue();
        if (residenciaSelecionada != null) {
            try {
                // Por enquanto, usar um método simples para gerar relatório
                StringBuilder relatorio = new StringBuilder();
                relatorio.append("RELATÓRIO GERAL Wi-Fi\n");
                relatorio.append("=====================================\n");
                relatorio.append("Residência: ").append(residenciaSelecionada.getNome()).append("\n");
                relatorio.append("Cliente: ").append(residenciaSelecionada.getCliente()).append("\n");
                relatorio.append("Endereço: ").append(residenciaSelecionada.getEndereco()).append("\n");
                relatorio.append("=====================================\n\n");
                
                // Adicionar informações dos cômodos e medições
                List<ComodoDTO> comodos = comodoService.listarComodos();
                List<ComodoDTO> comodosDaResidencia = comodos.stream()
                    .filter(c -> c.getResidenciaId().equals(residenciaSelecionada.getId()))
                    .collect(java.util.stream.Collectors.toList());
                
                relatorio.append("Cômodos cadastrados: ").append(comodosDaResidencia.size()).append("\n");
                for (ComodoDTO comodo : comodosDaResidencia) {
                    relatorio.append("- ").append(comodo.getNome()).append("\n");
                }
                
                relatorioTextArea.setText(relatorio.toString());
            } catch (SQLException | IOException e) {
                mostrarErro("Erro ao gerar relatório", e.getMessage());
            }
        } else {
            mostrarAviso("Selecione uma residência para gerar o relatório");
        }
    }

    @FXML
    private void handleExportarRelatorio() {
        // Implementação futura para exportar relatório
        mostrarAviso("Funcionalidade de exportação será implementada em breve");
    }

    // Métodos auxiliares para diálogos
    private void abrirDialogoResidencia(ResidenciaDTO residencia) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/ResidenciaDialog.fxml"));
            Parent root = loader.load();
            
            ResidenciaDialogController controller = loader.getController();
            controller.setResidencia(residencia);
            controller.setMainController(this);
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle(residencia == null ? "Nova Residência" : "Editar Residência");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();
            
        } catch (IOException e) {
            mostrarErro("Erro ao abrir diálogo", e.getMessage());
        }
    }

    private void abrirDialogoComodo(ComodoDTO comodo, Long residenciaId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/ComodoDialog.fxml"));
            Parent root = loader.load();
            
            ComodoDialogController controller = loader.getController();
            controller.setComodo(comodo, residenciaId);
            controller.setMainController(this);
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle(comodo == null ? "Novo Cômodo" : "Editar Cômodo");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();
            
        } catch (IOException e) {
            mostrarErro("Erro ao abrir diálogo", e.getMessage());
        }
    }

    private void abrirDialogoMedicao(MedicaoDTO medicao, Long residenciaId, Long comodoId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/MedicaoDialog.fxml"));
            Parent root = loader.load();
            
            MedicaoDialogController controller = loader.getController();
            controller.setMedicao(medicao, residenciaId, comodoId);
            controller.setMainController(this);
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle(medicao == null ? "Nova Medição" : "Editar Medição");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();
            
        } catch (IOException e) {
            mostrarErro("Erro ao abrir diálogo", e.getMessage());
        }
    }

    // Métodos públicos para atualizar dados (chamados pelos diálogos)
    public void atualizarResidencias() {
        carregarResidencias();
        carregarComboBoxResidencias();
    }

    public void atualizarComodos() {
        ResidenciaDTO residenciaSelecionada = residenciaComboBox.getValue();
        if (residenciaSelecionada != null) {
            carregarComodosPorResidencia(residenciaSelecionada.getId());
        }
    }

    public void atualizarMedicoes() {
        ComodoDTO comodoSelecionado = comodoMedicaoComboBox.getValue();
        if (comodoSelecionado != null) {
            carregarMedicoesPorComodo(comodoSelecionado.getId());
        }
    }

    // Métodos de utilidade para mensagens
    private void mostrarSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarAviso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private boolean confirmarExclusao(String item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Tem certeza que deseja excluir?");
        alert.setContentText("Esta ação não pode ser desfeita para: " + item);
        
        return alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .isPresent();
    }
}