package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import service.*;

public class AvaliaWifiApplication extends Application {

    // Services principais que serão compartilhadas entre as views
    private static ResidenciaService residenciaService;
    private static ComodoService comodoService;
    private static MedicaoService medicaoService;
    private static RelatorioService relatorioService;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Inicializar services
        initializeServices();
        
        // Carregar a tela principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/MainView.fxml"));
        Parent root = loader.load();
        
        // Configurar a janela principal
        primaryStage.setTitle("AvaliaWiFi - Sistema de Monitoramento de Redes Wi-Fi");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        
        // Adicionar ícone (opcional)
        // primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/icon.png")));
        
        primaryStage.show();
    }

    /**
     * Inicializa todos os services que serão utilizados pelas views
     */
    private void initializeServices() {
        residenciaService = new ResidenciaService();
        comodoService = new ComodoService();
        medicaoService = new MedicaoService();
        relatorioService = new RelatorioService();
    }

    // Métodos estáticos para acessar os services das views
    public static ResidenciaService getResidenciaService() {
        return residenciaService;
    }

    public static ComodoService getComodoService() {
        return comodoService;
    }

    public static MedicaoService getMedicaoService() {
        return medicaoService;
    }

    public static RelatorioService getRelatorioService() {
        return relatorioService;
    }

    public static void main(String[] args) {
        launch(args);
    }
}