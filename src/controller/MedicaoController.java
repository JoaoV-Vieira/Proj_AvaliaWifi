package controller;

import dto.MedicaoDTO;
import service.MedicaoService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MedicaoController {

    private MedicaoService medicaoService;

    public MedicaoController() {
        this.medicaoService = new MedicaoService();
    }

    public MedicaoDTO criarMedicao(MedicaoDTO medicaoDTO) throws SQLException, IOException {
        return medicaoService.criarMedicao(medicaoDTO);
    }

    public List<MedicaoDTO> listarMedicoes() throws SQLException, IOException {
        return medicaoService.listarMedicoes();
    }

    public void deletarMedicao(Long id) throws SQLException, IOException {
        medicaoService.deletarMedicao(id);
    }
}