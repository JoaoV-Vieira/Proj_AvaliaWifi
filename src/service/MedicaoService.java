package service;

import dto.MedicaoDTO;
import model.Comodo;
import model.Medicao;
import model.Residencia;
import repository.MedicaoRep;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class MedicaoService {

    private MedicaoRep medicaoRep;

    public MedicaoService() {
        this.medicaoRep = new MedicaoRep();
    }

    public MedicaoDTO criarMedicao(MedicaoDTO medicaoDTO) throws SQLException, IOException {
        Medicao medicao = new Medicao(
            null,
            medicaoDTO.getDataHora(),
            medicaoDTO.getNivelSinal(),
            medicaoDTO.getVelocidade(),
            medicaoDTO.getInterferencia(),
            medicaoDTO.getBanda(),
            new Comodo(medicaoDTO.getComodoId(), null, null),
            new Residencia(medicaoDTO.getResidenciaId(), null, null, null)
        );
        medicaoRep.salvar(medicao);
        medicaoDTO.setId(medicao.getId());
        return medicaoDTO;
    }

    public List<MedicaoDTO> listarMedicoes() throws SQLException, IOException {
        return medicaoRep.buscarTodas().stream()
            .map(medicao -> new MedicaoDTO(
                medicao.getId(),
                medicao.getDataHora(),
                medicao.getNivelSinal(),
                medicao.getVelocidade(),
                medicao.getInterferencia(),
                medicao.getBanda(),
                medicao.getComodo().getId(),
                medicao.getResidencia().getId()
            ))
            .collect(Collectors.toList());
    }

    public MedicaoDTO atualizarMedicao(Long id, MedicaoDTO medicaoDTO) throws SQLException, IOException {
        Medicao existente = medicaoRep.buscarPorId(id);
        if (existente != null) {
            Medicao medicao = new Medicao(
                id,
                medicaoDTO.getDataHora(),
                medicaoDTO.getNivelSinal(),
                medicaoDTO.getVelocidade(),
                medicaoDTO.getInterferencia(),
                medicaoDTO.getBanda(),
                new Comodo(medicaoDTO.getComodoId(), null, null),
                new Residencia(medicaoDTO.getResidenciaId(), null, null, null)
            );
            medicaoRep.atualizar(medicao);
            return medicaoDTO;
        }
        return null;
    }

    public void deletarMedicao(Long id) throws SQLException, IOException {
        medicaoRep.deletar(id);
    }
}
