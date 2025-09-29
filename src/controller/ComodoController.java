package controller;

import dto.ComodoDTO;
import service.ComodoService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ComodoController {

    private ComodoService comodoService;

    public ComodoController() {
        this.comodoService = new ComodoService();
    }

    public ComodoDTO criarComodo(ComodoDTO comodoDTO) throws SQLException, IOException {
        return comodoService.criarComodo(comodoDTO);
    }

    public ComodoDTO atualizarComodo(Long id, ComodoDTO comodoDTO) throws SQLException, IOException {
        return comodoService.atualizarComodo(id, comodoDTO);
    }

    public ComodoDTO buscarComodo(Long id) throws SQLException, IOException {
        return comodoService.buscarComodo(id);
    }

    public List<ComodoDTO> buscarTodosComodos() throws SQLException, IOException {
        return comodoService.listarComodos();
    }

    public void deletarComodo(Long id) throws SQLException, IOException {
        comodoService.deletarComodo(id);
    }
}