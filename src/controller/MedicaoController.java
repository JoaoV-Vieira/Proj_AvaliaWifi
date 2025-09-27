package controller;

import model.Medicao;
import repository.MedicaoRep;
import java.util.List;

public class MedicaoController {

    private MedicaoRep medicaoRep;

    public MedicaoController() {
        this.medicaoRep = new MedicaoRep();
    }

    public Medicao criarMedicao(Medicao medicao) {
        medicaoRep.salvar(medicao);
        return medicao;
    }

    public Medicao atualizarMedicao(Long id, Medicao medicao) {
        Medicao existente = medicaoRep.buscarPorId(id);
        if (existente != null) {
            medicaoRep.atualizar(medicao);
            return medicao;
        }
        return null;
    }

    public Medicao buscarMedicao(Long id) {
        return medicaoRep.buscarPorId(id);
    }

    public List<Medicao> buscarTodasMedicoes() {
        return medicaoRep.buscarTodas();
    }
}