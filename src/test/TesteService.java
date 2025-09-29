package test;

import java.time.LocalDateTime;

import dto.ResidenciaDTO;
import dto.ComodoDTO;
import dto.MedicaoDTO;
import service.ResidenciaService;
import service.ComodoService;
import service.MedicaoService;

public class TesteService {
    public static void main(String[] args) {
        ResidenciaService residenciaService = new ResidenciaService();
        ComodoService comodoService = new ComodoService();
        MedicaoService medicaoService = new MedicaoService();

        try {
            
            ResidenciaDTO residenciaDTO = new ResidenciaDTO(null, "Residência 1", "Rua A, 123");
            residenciaDTO = residenciaService.criarResidencia(residenciaDTO);
            System.out.println("Residência criada com ID: " + residenciaDTO.getId());

            ComodoDTO comodoDTO = new ComodoDTO(null, "Sala", residenciaDTO.getId());
            comodoDTO = comodoService.criarComodo(comodoDTO);
            System.out.println("Cômodo criado com ID: " + comodoDTO.getId());

            MedicaoDTO medicaoDTO = new MedicaoDTO(null, LocalDateTime.now(), -65, 50.0, "Baixa interferência", comodoDTO.getId(), residenciaDTO.getId());
            medicaoDTO = medicaoService.criarMedicao(medicaoDTO);
            System.out.println("Medição criada com ID: " + medicaoDTO.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
