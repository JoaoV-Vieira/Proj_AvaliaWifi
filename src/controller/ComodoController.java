package controller;

import model.Comodo;
import repository.ComodoRep;
import java.util.List;

public class ComodoController {

    private ComodoRep comodoRep;

    public ComodoController() {
        this.comodoRep = new ComodoRep();
    }

    public Comodo criarComodo(Comodo comodo) {
        comodoRep.salvar(comodo);
        return comodo;
    }

    public Comodo atualizarComodo(Long id, Comodo comodo) {
        Comodo existente = comodoRep.buscarPorId(id);
        if (existente != null) {
            comodoRep.atualizar(comodo);
            return comodo;
        }
        return null;
    }

    public Comodo buscarComodo(Long id) {
        return comodoRep.buscarPorId(id);
    }

    public List<Comodo> buscarTodosComodos() {
        return comodoRep.buscarTodos();
    }
}