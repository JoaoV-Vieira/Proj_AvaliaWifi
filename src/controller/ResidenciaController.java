package controller;

import dto.ResidenciaDTO;
import service.ResidenciaService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ResidenciaController {

    private ResidenciaService residenciaService;

    public ResidenciaController() {
        this.residenciaService = new ResidenciaService();
    }

    public ResidenciaDTO criarResidencia(ResidenciaDTO residenciaDTO) throws SQLException, IOException {
        return residenciaService.criarResidencia(residenciaDTO);
    }

    public ResidenciaDTO atualizarResidencia(Long id, ResidenciaDTO residenciaDTO) throws SQLException, IOException {
        return residenciaService.atualizarResidencia(id, residenciaDTO);
    }

    public ResidenciaDTO buscarResidencia(Long id) throws SQLException, IOException {
        return residenciaService.buscarResidencia(id);
    }

    public List<ResidenciaDTO> listarResidencias() throws SQLException, IOException {
        return residenciaService.listarResidencias();
    }

    public void deletarResidencia(Long id) throws SQLException, IOException {
        residenciaService.deletarResidencia(id);
    }
}