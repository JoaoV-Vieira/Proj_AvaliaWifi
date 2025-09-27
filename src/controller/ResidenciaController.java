package controller;

import model.Residencia;
import repository.ResidenciaRep;
import service.AvaliaWifiService;

import java.util.List;

public class ResidenciaController {

    private AvaliaWifiService avaliaWifiService;
    private ResidenciaRep residenciaRep;

    public ResidenciaController(AvaliaWifiService avaliaWifiService) {
        this.avaliaWifiService = avaliaWifiService;
        this.residenciaRep = new ResidenciaRep();
    }

    public Residencia criarResidencia(Residencia residencia) {
        residenciaRep.salvar(residencia);
        return residencia;
    }

    public Residencia atualizarResidencia(Long id, Residencia residencia) {
        Residencia existente = residenciaRep.buscarPorId(id);
        if (existente != null) {
            residenciaRep.atualizar(residencia);
            return residencia;
        }
        return null;
    }

    public Residencia buscarResidencia(Long id) {
        return residenciaRep.buscarPorId(id);
    }

    public List<Residencia> buscarTodasResidencias() {
        return residenciaRep.buscarTodas();
    }

}