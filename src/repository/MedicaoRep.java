package repository;

import model.Comodo;
import model.Medicao;
import model.Residencia;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicaoRep {

    // Método para salvar uma nova medição no banco de dados
    public void salvar(Medicao medicao) throws SQLException, IOException {
        String sql = "INSERT INTO medicao (data_hora, nivel_sinal, velocidade, interferencia, comodo_id, residencia_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, Timestamp.valueOf(medicao.getDataHora()));
            stmt.setInt(2, medicao.getNivelSinal());
            stmt.setDouble(3, medicao.getVelocidade());
            stmt.setString(4, medicao.getInterferencia());
            stmt.setLong(5, medicao.getComodo().getId());
            stmt.setLong(6, medicao.getResidencia().getId());
            stmt.executeUpdate();

            // Recupera o ID gerado automaticamente
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    medicao.setId(rs.getLong(1));
                }
            }
        }
    }

    // Método para buscar uma medição pelo ID
    public Medicao buscarPorId(Long id) throws SQLException, IOException {
        String sql = "SELECT * FROM medicao WHERE id = ?";
        Medicao medicao = null;

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    medicao = new Medicao(
                        rs.getLong("id"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        rs.getInt("nivel_sinal"),
                        rs.getDouble("velocidade"),
                        rs.getString("interferencia"),
                        new Comodo(rs.getLong("comodo_id"), null, null), // Apenas o ID do cômodo
                        new Residencia(rs.getLong("residencia_id"), null, null) // Apenas o ID da residência
                    );
                }
            }
        }
        return medicao;
    }

    // Método para buscar todas as medições
    public List<Medicao> buscarTodas() throws SQLException, IOException {
        String sql = "SELECT * FROM medicao";
        List<Medicao> medicoes = new ArrayList<>();

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Medicao medicao = new Medicao(
                    rs.getLong("id"),
                    rs.getTimestamp("data_hora").toLocalDateTime(),
                    rs.getInt("nivel_sinal"),
                    rs.getDouble("velocidade"),
                    rs.getString("interferencia"),
                    new Comodo(rs.getLong("comodo_id"), null, null),
                    new Residencia(rs.getLong("residencia_id"), null, null)
                );
                medicoes.add(medicao);
            }
        }
        return medicoes;
    }

    // Método para atualizar uma medição
    public void atualizar(Medicao medicao) throws SQLException, IOException {
        String sql = "UPDATE medicao SET data_hora = ?, nivel_sinal = ?, velocidade = ?, interferencia = ?, comodo_id = ?, residencia_id = ? WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(medicao.getDataHora()));
            stmt.setInt(2, medicao.getNivelSinal());
            stmt.setDouble(3, medicao.getVelocidade());
            stmt.setString(4, medicao.getInterferencia());
            stmt.setLong(5, medicao.getComodo().getId());
            stmt.setLong(6, medicao.getResidencia().getId());
            stmt.setLong(7, medicao.getId());
            stmt.executeUpdate();
        }
    }

    // Método para deletar uma medição pelo ID
    public void deletar(Long id) throws SQLException, IOException {
        String sql = "DELETE FROM medicao WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}