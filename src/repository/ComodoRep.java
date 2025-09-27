package repository;

import model.Comodo;
import java.util.ArrayList;
import java.util.List;

public class ComodoRep {
    
    private List<Comodo> comodos;

    public ComodoRep() {
        this.comodos = new ArrayList<>();
    }

    public void salvar(Comodo comodo) {
        comodos.add(comodo);
    }

    public Comodo buscarPorId(Long id) {
        return comodos.stream()
                      .filter(comodo -> comodo.getId().equals(id))
                      .findFirst()
                      .orElse(null);
    }

    public List<Comodo> buscarTodos() {
        return new ArrayList<>(comodos);
    }

    public void atualizar(Comodo comodo) {
        for (int i = 0; i < comodos.size(); i++) {
            if (comodos.get(i).getId().equals(comodo.getId())) {
                comodos.set(i, comodo);
                return;
            }
        }
    }

    public void deletar(Long id) {
        comodos.removeIf(comodo -> comodo.getId().equals(id));
    }
}