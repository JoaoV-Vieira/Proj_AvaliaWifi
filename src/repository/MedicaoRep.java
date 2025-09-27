package repository;

import model.Medicao;
import java.util.ArrayList;
import java.util.List;

public class MedicaoRep {
    
    private List<Medicao> medicoes;

    public MedicaoRep() {
        this.medicoes = new ArrayList<>();
    }

    public void salvar(Medicao medicao) {
        medicoes.add(medicao);
    }

    public Medicao buscarPorId(Long id) {
        return medicoes.stream()
                       .filter(medicao -> medicao.getId().equals(id))
                       .findFirst()
                       .orElse(null);
    }

    public List<Medicao> buscarTodas() {
        return new ArrayList<>(medicoes);
    }

    public void atualizar(Medicao medicao) {
        for (int i = 0; i < medicoes.size(); i++) {
            if (medicoes.get(i).getId().equals(medicao.getId())) {
                medicoes.set(i, medicao);
                return;
            }
        }
    }

    public void deletar(Long id) {
        medicoes.removeIf(medicao -> medicao.getId().equals(id));
    }
}