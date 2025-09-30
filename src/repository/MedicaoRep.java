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
        // Se o ID não foi definido, usar o próximo ID disponível
        if (medicao.getId() == null) {
            Long proximoId = obterProximoId();
            medicao.setId(proximoId);
        }
        
        String sql = "INSERT INTO medicao (id, data_hora, nivel_sinal, velocidade, interferencia, banda, comodo_id, residencia_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, medicao.getId());
            stmt.setTimestamp(2, Timestamp.valueOf(medicao.getDataHora()));
            stmt.setInt(3, medicao.getNivelSinal());
            stmt.setDouble(4, medicao.getVelocidade());
            stmt.setString(5, medicao.getInterferencia());
            stmt.setString(6, medicao.getBanda());
            stmt.setLong(7, medicao.getComodo().getId());
            stmt.setLong(8, medicao.getResidencia().getId());
            stmt.executeUpdate();
        }
    }
    
    // Método auxiliar para obter o próximo ID disponível
    private Long obterProximoId() throws SQLException, IOException {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM medicao";
        
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 1L;
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
                        rs.getString("banda"), // Nova coluna
                        new Comodo(rs.getLong("comodo_id"), null, null), // Apenas o ID do cômodo
                        new Residencia(rs.getLong("residencia_id"), null, null, null) // Apenas o ID da residência
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
                    rs.getString("banda"),
                    new Comodo(rs.getLong("comodo_id"), null, null),
                    new Residencia(rs.getLong("residencia_id"), null, null, null)
                );
                medicoes.add(medicao);
            }
        }
        return medicoes;
    }

    // Método para atualizar uma medição
    public void atualizar(Medicao medicao) throws SQLException, IOException {
        String sql = "UPDATE medicao SET data_hora = ?, nivel_sinal = ?, velocidade = ?, interferencia = ?, banda = ?, comodo_id = ?, residencia_id = ? WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(medicao.getDataHora()));
            stmt.setInt(2, medicao.getNivelSinal());
            stmt.setDouble(3, medicao.getVelocidade());
            stmt.setString(4, medicao.getInterferencia());
            stmt.setString(5, medicao.getBanda());
            stmt.setLong(6, medicao.getComodo().getId());
            stmt.setLong(7, medicao.getResidencia().getId());
            stmt.setLong(8, medicao.getId());
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