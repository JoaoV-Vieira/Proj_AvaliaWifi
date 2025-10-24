package view.controller;

import dto.ComodoDTO;
import dto.MedicaoDTO;
import dto.ResidenciaDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import service.ComodoService;
import service.MedicaoService;
import service.ResidenciaService;
import view.AvaliaWifiApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class MedicaoDialogController {

    @FXML private DatePicker dataPicker;
    @FXML private TextField horaField;
    @FXML private TextField nivelSinalField;
    @FXML private TextField velocidadeField;
    @FXML private TextField interferenciaField;
    @FXML private ComboBox<String> bandaComboBox;
    @FXML private ComboBox<ComodoDTO> comodoComboBox;
    @FXML private Label residenciaLabel;

    private MedicaoDTO medicao;
    private Long residenciaId;
    private Long comodoId;
    private MainViewController mainController;
    
    private MedicaoService medicaoService;
    private ComodoService comodoService;
    private ResidenciaService residenciaService;

    public void initialize() {
        this.medicaoService = AvaliaWifiApplication.getMedicaoService();
        this.comodoService = AvaliaWifiApplication.getComodoService();
        this.residenciaService = AvaliaWifiApplication.getResidenciaService();
        
        configurarBandaComboBox();
        configurarComodoComboBox();
        configurarCampos();
    }

    private void configurarBandaComboBox() {
        ObservableList<String> bandas = FXCollections.observableArrayList(
            "2.4GHz", "5GHz", "6GHz"
        );
        bandaComboBox.setItems(bandas);
    }

    private void configurarComodoComboBox() {
        comodoComboBox.setConverter(new StringConverter<ComodoDTO>() {
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

    private void configurarCampos() {
        // Configurar formato de hora
        horaField.setPromptText("HH:mm");
        
        // Configurar campos numéricos
        nivelSinalField.setPromptText("Ex: -50");
        velocidadeField.setPromptText("Ex: 100.5");
        interferenciaField.setPromptText("Ex: Baixa, Média, Alta");
    }

    public void setMedicao(MedicaoDTO medicao, Long residenciaId, Long comodoId) {
        this.medicao = medicao;
        this.residenciaId = residenciaId;
        this.comodoId = comodoId;
        
        carregarDadosResidencia();
        carregarComodos();
        
        if (medicao != null) {
            // Modo edição - preencher campos
            preencherCampos();
        } else {
            // Modo novo - definir valores padrão
            definirValoresPadrao();
        }
    }

    private void carregarDadosResidencia() {
        try {
            ResidenciaDTO residencia = residenciaService.buscarResidencia(residenciaId);
            if (residencia != null) {
                residenciaLabel.setText("Residência: " + residencia.getNome());
            }
        } catch (SQLException | IOException e) {
            mostrarErro("Erro ao carregar residência", e.getMessage());
        }
    }

    private void carregarComodos() {
        try {
            List<ComodoDTO> comodos = comodoService.listarComodos();
            // Filtrar por residência
            List<ComodoDTO> comodosFiltrados = comodos.stream()
                .filter(c -> c.getResidenciaId().equals(residenciaId))
                .collect(java.util.stream.Collectors.toList());
            ObservableList<ComodoDTO> comodoList = FXCollections.observableArrayList(comodosFiltrados);
            comodoComboBox.setItems(comodoList);
            
            // Se há um cômodo específico, selecioná-lo
            if (comodoId != null) {
                ComodoDTO comodoSelecionado = comodosFiltrados.stream()
                    .filter(c -> c.getId().equals(comodoId))
                    .findFirst()
                    .orElse(null);
                comodoComboBox.setValue(comodoSelecionado);
            }
        } catch (SQLException | IOException e) {
            mostrarErro("Erro ao carregar cômodos", e.getMessage());
        }
    }

    private void preencherCampos() {
        if (medicao.getDataHora() != null) {
            LocalDateTime dataHora = medicao.getDataHora();
            dataPicker.setValue(dataHora.toLocalDate());
            horaField.setText(dataHora.format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        
        nivelSinalField.setText(String.valueOf(medicao.getNivelSinal()));
        velocidadeField.setText(String.valueOf(medicao.getVelocidade()));
        interferenciaField.setText(medicao.getInterferencia());
        bandaComboBox.setValue(medicao.getBanda());
    }

    private void definirValoresPadrao() {
        dataPicker.setValue(LocalDateTime.now().toLocalDate());
        horaField.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        bandaComboBox.setValue("2.4GHz");
    }

    public void setMainController(MainViewController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleSalvar() {
        if (validarCampos()) {
            try {
                LocalDateTime dataHora = obterDataHora();
                ComodoDTO comodoSelecionado = comodoComboBox.getValue();
                
                if (medicao == null) {
                    // Novo registro
                    MedicaoDTO novaMedicao = new MedicaoDTO(
                        null,
                        dataHora,
                        Integer.parseInt(nivelSinalField.getText().trim()),
                        Double.parseDouble(velocidadeField.getText().trim()),
                        interferenciaField.getText().trim(),
                        bandaComboBox.getValue(),
                        comodoSelecionado.getId(),
                        residenciaId
                    );
                    medicaoService.criarMedicao(novaMedicao);
                    mostrarSucesso("Medição cadastrada com sucesso!");
                } else {
                    // Edição
                    medicao.setDataHora(dataHora);
                    medicao.setNivelSinal(Integer.parseInt(nivelSinalField.getText().trim()));
                    medicao.setVelocidade(Double.parseDouble(velocidadeField.getText().trim()));
                    medicao.setInterferencia(interferenciaField.getText().trim());
                    medicao.setBanda(bandaComboBox.getValue());
                    medicao.setComodoId(comodoSelecionado.getId());
                    medicaoService.atualizarMedicao(medicao.getId(), medicao);
                    mostrarSucesso("Medição atualizada com sucesso!");
                }
                
                // Atualizar a tela principal
                if (mainController != null) {
                    mainController.atualizarMedicoes();
                }
                
                // Fechar diálogo
                fecharJanela();
                
            } catch (SQLException | IOException e) {
                mostrarErro("Erro ao salvar medição", e.getMessage());
            } catch (NumberFormatException e) {
                mostrarErro("Erro de formato", "Verifique os campos numéricos");
            } catch (DateTimeParseException e) {
                mostrarErro("Erro de data/hora", "Formato de hora inválido (use HH:mm)");
            }
        }
    }

    @FXML
    private void handleCancelar() {
        fecharJanela();
    }

    private boolean validarCampos() {
        StringBuilder erros = new StringBuilder();
        
        if (dataPicker.getValue() == null) {
            erros.append("• Data é obrigatória\n");
        }
        
        if (horaField.getText() == null || horaField.getText().trim().isEmpty()) {
            erros.append("• Hora é obrigatória\n");
        }
        
        if (nivelSinalField.getText() == null || nivelSinalField.getText().trim().isEmpty()) {
            erros.append("• Nível do sinal é obrigatório\n");
        } else {
            try {
                Integer.parseInt(nivelSinalField.getText().trim());
            } catch (NumberFormatException e) {
                erros.append("• Nível do sinal deve ser um número inteiro\n");
            }
        }
        
        if (velocidadeField.getText() == null || velocidadeField.getText().trim().isEmpty()) {
            erros.append("• Velocidade é obrigatória\n");
        } else {
            try {
                Double.parseDouble(velocidadeField.getText().trim());
            } catch (NumberFormatException e) {
                erros.append("• Velocidade deve ser um número\n");
            }
        }
        
        if (interferenciaField.getText() == null || interferenciaField.getText().trim().isEmpty()) {
            erros.append("• Interferência é obrigatória\n");
        }
        
        if (bandaComboBox.getValue() == null) {
            erros.append("• Banda é obrigatória\n");
        }
        
        if (comodoComboBox.getValue() == null) {
            erros.append("• Cômodo é obrigatório\n");
        }
        
        if (erros.length() > 0) {
            mostrarErro("Campos obrigatórios", erros.toString());
            return false;
        }
        
        return true;
    }

    private LocalDateTime obterDataHora() throws DateTimeParseException {
        String horaTexto = horaField.getText().trim();
        if (!horaTexto.matches("\\d{2}:\\d{2}")) {
            throw new DateTimeParseException("Formato de hora inválido", horaTexto, 0);
        }
        
        String[] partesHora = horaTexto.split(":");
        int hora = Integer.parseInt(partesHora[0]);
        int minuto = Integer.parseInt(partesHora[1]);
        
        return dataPicker.getValue().atTime(hora, minuto);
    }

    private void fecharJanela() {
        Stage stage = (Stage) dataPicker.getScene().getWindow();
        stage.close();
    }

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
}