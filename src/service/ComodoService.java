package service;

import dto.ComodoDTO;
import model.Comodo;
import model.Residencia;
import repository.ComodoRep;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ComodoService {

    private ComodoRep comodoRep;

    public ComodoService() {
        this.comodoRep = new ComodoRep();
    }

    public ComodoDTO criarComodo(ComodoDTO comodoDTO) throws SQLException, IOException {
        Comodo comodo = new Comodo(
            null,
            comodoDTO.getNome(),
            new Residencia(comodoDTO.getResidenciaId(), null, null)
        );
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
                new Residencia(comodoDTO.getResidenciaId(), null, null)
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