package repository;

import model.Residencia;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResidenciaRep {

    // Método para salvar uma nova residência no banco de dados
    public void salvar(Residencia residencia) throws SQLException, IOException {
        String sql = "INSERT INTO residencia (nome, endereco) VALUES (?, ?)";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, residencia.getNome());
            stmt.setString(2, residencia.getEndereco());
            stmt.executeUpdate();

            // Recupera o ID gerado automaticamente
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    residencia.setId(rs.getLong(1));
                }
            }
        }
    }

    // Método para buscar uma residência pelo ID
    public Residencia buscarPorId(Long id) throws SQLException, IOException {
        String sql = "SELECT * FROM residencia WHERE id = ?";
        Residencia residencia = null;

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    residencia = new Residencia(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("endereco")
                    );
                }
            }
        }
        return residencia;
    }

    // Método para buscar todas as residências
    public List<Residencia> buscarTodas() throws SQLException, IOException {
        String sql = "SELECT * FROM residencia";
        List<Residencia> residencias = new ArrayList<>();

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Residencia residencia = new Residencia(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getString("endereco")
                );
                residencias.add(residencia);
            }
        }
        return residencias;
    }

    // Método para atualizar uma residência
    public void atualizar(Residencia residencia) throws SQLException, IOException {
        String sql = "UPDATE residencia SET nome = ?, endereco = ? WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, residencia.getNome());
            stmt.setString(2, residencia.getEndereco());
            stmt.setLong(3, residencia.getId());
            stmt.executeUpdate();
        }
    }

    // Método para deletar uma residência pelo ID
    public void deletar(Long id) throws SQLException, IOException {
        String sql = "DELETE FROM residencia WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}