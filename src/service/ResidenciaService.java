package service;

import dto.ResidenciaDTO;
import model.Residencia;
import repository.ResidenciaRep;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ResidenciaService {

    private ResidenciaRep residenciaRep;

    public ResidenciaService() {
        this.residenciaRep = new ResidenciaRep();
    }

    public ResidenciaDTO criarResidencia(ResidenciaDTO residenciaDTO) throws SQLException, IOException {
        Residencia residencia = new Residencia(
            null,
            residenciaDTO.getNome(),
            residenciaDTO.getEndereco(),
            residenciaDTO.getCliente()
        );
        residenciaRep.salvar(residencia);
        residenciaDTO.setId(residencia.getId());
        return residenciaDTO;
    }

    public ResidenciaDTO atualizarResidencia(Long id, ResidenciaDTO residenciaDTO) throws SQLException, IOException {
        Residencia existente = residenciaRep.buscarPorId(id);
        if (existente != null) {
            Residencia residencia = new Residencia(
                id,
                residenciaDTO.getNome(),
                residenciaDTO.getEndereco(),
                residenciaDTO.getCliente()
            );
            residenciaRep.atualizar(residencia);
            return residenciaDTO;
        }
        throw new SQLException("Residência com ID " + id + " não encontrada.");
    }

    public ResidenciaDTO buscarResidencia(Long id) throws SQLException, IOException {
        Residencia residencia = residenciaRep.buscarPorId(id);
        if (residencia != null) {
            return new ResidenciaDTO(
                residencia.getId(),
                residencia.getNome(),
                residencia.getEndereco(),
                residencia.getCliente()
            );
        }
        throw new SQLException("Residência com ID " + id + " não encontrada.");
    }

    public List<ResidenciaDTO> listarResidencias() throws SQLException, IOException {
        return residenciaRep.buscarTodas().stream()
            .map(residencia -> new ResidenciaDTO(
                residencia.getId(),
                residencia.getNome(),
                residencia.getEndereco(),
                residencia.getCliente()
            ))
            .collect(Collectors.toList());
    }

    public void deletarResidencia(Long id) throws SQLException, IOException {
        Residencia existente = residenciaRep.buscarPorId(id);
        if (existente != null) {
            residenciaRep.deletar(id);
        } else {
            throw new SQLException("Residência com ID " + id + " não encontrada.");
        }
    }
}
