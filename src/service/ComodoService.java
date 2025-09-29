package service;

import dto.ComodoDTO;
import model.Comodo;
import model.Residencia;
import repository.ComodoRep;
import repository.ResidenciaRep;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ComodoService {

    private ComodoRep comodoRep;
    private ResidenciaRep residenciaRep;

    public ComodoService() {
        this.comodoRep = new ComodoRep();
        this.residenciaRep = new ResidenciaRep();
    }

    public ComodoDTO criarComodo(ComodoDTO comodoDTO) throws SQLException, IOException {
        Residencia residencia = residenciaRep.buscarPorId(comodoDTO.getResidenciaId());
        if (residencia == null) {
            throw new SQLException("Residência com ID " + comodoDTO.getResidenciaId() + " não encontrada.");
        }

        Comodo comodo = new Comodo(null, comodoDTO.getNome(), residencia); // ID deve ser null
        comodoRep.salvar(comodo);
        comodoDTO.setId(comodo.getId());
        return comodoDTO;
    }

    public ComodoDTO atualizarComodo(Long id, ComodoDTO comodoDTO) throws SQLException, IOException {
        Comodo existente = comodoRep.buscarPorId(id);
        if (existente != null) {
            Comodo comodo = new Comodo(
                id,
                comodoDTO.getNome(),
                new Residencia(comodoDTO.getResidenciaId(), null, null, null)
            );
            comodoRep.atualizar(comodo);
            return comodoDTO;
        }
        return null;
    }

    public ComodoDTO buscarComodo(Long id) throws SQLException, IOException {
        Comodo comodo = comodoRep.buscarPorId(id);
        if (comodo != null) {
            return new ComodoDTO(
                comodo.getId(),
                comodo.getNome(),
                comodo.getResidencia().getId()
            );
        }
        return null;
    }

    public List<ComodoDTO> listarComodos() throws SQLException, IOException {
        return comodoRep.buscarTodos().stream()
            .map(comodo -> new ComodoDTO(
                comodo.getId(),
                comodo.getNome(),
                comodo.getResidencia().getId()
            ))
            .collect(Collectors.toList());
    }

    public void deletarComodo(Long id) throws SQLException, IOException {
        comodoRep.deletar(id);
    }
}