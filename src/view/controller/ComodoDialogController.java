package view.controller;

import dto.ComodoDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ComodoService;
import view.AvaliaWifiApplication;

import java.io.IOException;
import java.sql.SQLException;

public class ComodoDialogController {

    @FXML
    private TextField nomeField;

    private ComodoDTO comodo;
    private Long residenciaId;
    private MainViewController mainController;
    private ComodoService comodoService;

    public void initialize() {
        this.comodoService = AvaliaWifiApplication.getComodoService();
    }

    public void setComodo(ComodoDTO comodo, Long residenciaId) {
        this.comodo = comodo;
        this.residenciaId = residenciaId;
        
        if (comodo != null) {
            // Modo edição - preencher campos
            nomeField.setText(comodo.getNome());
        }
    }

    public void setMainController(MainViewController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleSalvar() {
        if (validarCampos()) {
            try {
                if (comodo == null) {
                    // Novo registro
                    ComodoDTO novoComodo = new ComodoDTO(
                        null,
                        nomeField.getText().trim(),
                        residenciaId
                    );
                    comodoService.criarComodo(novoComodo);
                    mostrarSucesso("Cômodo cadastrado com sucesso!");
                } else {
                    // Edição
                    comodo.setNome(nomeField.getText().trim());
                    comodoService.atualizarComodo(comodo.getId(), comodo);
                    mostrarSucesso("Cômodo atualizado com sucesso!");
                }
                
                // Atualizar a tela principal
                if (mainController != null) {
                    mainController.atualizarComodos();
                }
                
                // Fechar diálogo
                fecharJanela();
                
            } catch (SQLException | IOException e) {
                mostrarErro("Erro ao salvar cômodo", e.getMessage());
            }
        }
    }

    @FXML
    private void handleCancelar() {
        fecharJanela();
    }

    private boolean validarCampos() {
        if (nomeField.getText() == null || nomeField.getText().trim().isEmpty()) {
            mostrarErro("Campo obrigatório", "Nome do cômodo é obrigatório");
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