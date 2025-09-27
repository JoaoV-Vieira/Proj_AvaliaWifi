package repository;

import model.Residencia;
import java.util.ArrayList;
import java.util.List;

public class ResidenciaRep {
    
    private List<Residencia> residencias;

    public ResidenciaRep() {
        this.residencias = new ArrayList<>();
    }

    public void salvar(Residencia residencia) {
        residencias.add(residencia);
    }

    public Residencia buscarPorId(Long id) {
        return residencias.stream()
                          .filter(residencia -> residencia.getId().equals(id))
                          .findFirst()
                          .orElse(null);
    }

    public List<Residencia> buscarTodas() {
        return new ArrayList<>(residencias);
    }

    public void atualizar(Residencia residencia) {
        for (int i = 0; i < residencias.size(); i++) {
            if (residencias.get(i).getId().equals(residencia.getId())) {
                residencias.set(i, residencia);
                return;
            }
        }
    }

    public void deletar(Long id) {
        residencias.removeIf(residencia -> residencia.getId().equals(id));
    }
}