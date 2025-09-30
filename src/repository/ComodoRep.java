package repository;

import model.Comodo;
import model.Residencia;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComodoRep {

    // Método para salvar um novo cômodo no banco de dados
    public void salvar(Comodo comodo) throws SQLException, IOException {
        // Se o ID não foi definido, usar o próximo ID disponível
        if (comodo.getId() == null) {
            Long proximoId = obterProximoId();
            comodo.setId(proximoId);
        }
        
        String sql = "INSERT INTO comodo (id, nome, residencia_id) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, comodo.getId());
            stmt.setString(2, comodo.getNome());
            stmt.setLong(3, comodo.getResidencia().getId());
            stmt.executeUpdate();
        }
    }
    
    // Método auxiliar para obter o próximo ID disponível
    private Long obterProximoId() throws SQLException, IOException {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM comodo";
        
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 1L;
        }
    }

    // Método para buscar um cômodo pelo ID
    public Comodo buscarPorId(Long id) throws SQLException, IOException {
        String sql = "SELECT c.id, c.nome, c.residencia_id, r.nome AS residencia_nome, r.cliente AS residencia_cliente " +
                     "FROM comodo c " +
                     "JOIN residencia r ON c.residencia_id = r.id " +
                     "WHERE c.id = ?";
        Comodo comodo = null;

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Residencia residencia = new Residencia(
                        rs.getLong("residencia_id"),
                        rs.getString("residencia_nome"),
                        rs.getString("residencia_cliente"),
                        null
                    );
                    comodo = new Comodo(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        residencia
                    );
                }
            }
        }
        return comodo;
    }

    // Método para buscar todos os cômodos
    public List<Comodo> buscarTodos() throws SQLException, IOException {
        String sql = "SELECT c.id, c.nome, c.residencia_id, r.nome AS residencia_nome, r.cliente AS residencia_cliente " +
                     "FROM comodo c " +
                     "JOIN residencia r ON c.residencia_id = r.id";
        List<Comodo> comodos = new ArrayList<>();

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Residencia residencia = new Residencia(
                    rs.getLong("residencia_id"),
                    rs.getString("residencia_nome"),
                    rs.getString("residencia_cliente"),
                    null
                );
                Comodo comodo = new Comodo(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    residencia
                );
                comodos.add(comodo);
            }
        }
        return comodos;
    }

    // Método para atualizar um cômodo
    public void atualizar(Comodo comodo) throws SQLException, IOException {
        String sql = "UPDATE comodo SET nome = ?, residencia_id = ? WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, comodo.getNome());
            stmt.setLong(2, comodo.getResidencia().getId());
            stmt.setLong(3, comodo.getId());
            stmt.executeUpdate();
        }
    }

    // Método para deletar um cômodo pelo ID
    public void deletar(Long id) throws SQLException, IOException {
        String sql = "DELETE FROM comodo WHERE id = ?";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}