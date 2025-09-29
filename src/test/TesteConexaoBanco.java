package test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import repository.ConexaoBanco;

public class TesteConexaoBanco {
    public static void main(String[] args) {
        try {
            System.out.println("Tentando conectar ao banco...");
            Connection conexao = ConexaoBanco.conectar();

            if (conexao != null) {
                System.out.println("Conexão bem-sucedida!");
                ConexaoBanco.desconectar();
                System.out.println("Conexão encerrada.");
            } else {
                System.out.println("Falha ao conectar ao banco.");
            }
        } catch (SQLException | IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}