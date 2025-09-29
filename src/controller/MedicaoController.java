package controller;

import model.Medicao;
import repository.MedicaoRep;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MedicaoController {

    private MedicaoRep medicaoRep;

    public MedicaoController() {
        this.medicaoRep = new MedicaoRep();
    }

    public Medicao criarMedicao(Medicao medicao) throws SQLException, IOException {
        medicaoRep.salvar(medicao);
        return medicao;
    }

    public Medicao atualizarMedicao(Long id, Medicao medicao) throws SQLException, IOException {
        Medicao existente = medicaoRep.buscarPorId(id);
        if (existente != null) {
            medicaoRep.atualizar(medicao);
            return medicao;
        }
        return null;
    }

    public Medicao buscarMedicao(Long id) throws SQLException, IOException {
        return medicaoRep.buscarPorId(id);
    }

    public List<Medicao> buscarTodasMedicoes() throws SQLException, IOException {
        return medicaoRep.buscarTodas();
    }

    public void deletarMedicao(Long id) throws SQLException, IOException {
        medicaoRep.deletar(id);
    }
}