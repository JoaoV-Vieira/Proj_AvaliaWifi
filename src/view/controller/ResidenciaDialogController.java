package view.controller;

import dto.ResidenciaDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ResidenciaService;
import view.AvaliaWifiApplication;

import java.io.IOException;
import java.sql.SQLException;

public class ResidenciaDialogController {

    @FXML
    private TextField nomeField;
    
    @FXML
    private TextField enderecoField;
    
    @FXML
    private TextField clienteField;

    private ResidenciaDTO residencia;
    private MainViewController mainController;
    private ResidenciaService residenciaService;

    public void initialize() {
        this.residenciaService = AvaliaWifiApplication.getResidenciaService();
    }

    public void setResidencia(ResidenciaDTO residencia) {
        this.residencia = residencia;
        
        if (residencia != null) {
            // Modo edição - preencher campos
            nomeField.setText(residencia.getNome());
            enderecoField.setText(residencia.getEndereco());
            clienteField.setText(residencia.getCliente());
        }
    }

    public void setMainController(MainViewController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleSalvar() {
        if (validarCampos()) {
            try {
                if (residencia == null) {
                    // Novo registro
                    ResidenciaDTO novaResidencia = new ResidenciaDTO(
                        null,
                        nomeField.getText().trim(),
                        enderecoField.getText().trim(),
                        clienteField.getText().trim()
                    );
                    residenciaService.criarResidencia(novaResidencia);
                    mostrarSucesso("Residência cadastrada com sucesso!");
                } else {
                    // Edição
                    residencia.setNome(nomeField.getText().trim());
                    residencia.setEndereco(enderecoField.getText().trim());
                    residencia.setCliente(clienteField.getText().trim());
                    residenciaService.atualizarResidencia(residencia.getId(), residencia);
                    mostrarSucesso("Residência atualizada com sucesso!");
                }
                
                // Atualizar a tela principal
                if (mainController != null) {
                    mainController.atualizarResidencias();
                }
                
                // Fechar diálogo
                fecharJanela();
                
            } catch (SQLException | IOException e) {
                mostrarErro("Erro ao salvar residência", e.getMessage());
            }
        }
    }

    @FXML
    private void handleCancelar() {
        fecharJanela();
    }

    private boolean validarCampos() {
        StringBuilder erros = new StringBuilder();
        
        if (nomeField.getText() == null || nomeField.getText().trim().isEmpty()) {
            erros.append("• Nome é obrigatório\n");
        }
        
        if (enderecoField.getText() == null || enderecoField.getText().trim().isEmpty()) {
            erros.append("• Endereço é obrigatório\n");
        }
        
        if (clienteField.getText() == null || clienteField.getText().trim().isEmpty()) {
            erros.append("• Cliente é obrigatório\n");
        }
        
        if (erros.length() > 0) {
            mostrarErro("Campos obrigatórios", erros.toString());
            return false;
        }
        
        return true;
    }

    private void fecharJanela() {
        Stage stage = (Stage) nomeField.getScene().getWindow();
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