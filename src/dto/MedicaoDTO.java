package dto;

import java.time.LocalDateTime;

public class MedicaoDTO {

    private Long id;
    private LocalDateTime dataHora;
    private int nivelSinal;
    private double velocidade;
    private String interferencia;
    private String banda; // Nova coluna para a banda do Wi-Fi
    private Long comodoId;
    private Long residenciaId;

    public MedicaoDTO() {
    }

    public MedicaoDTO(Long id, LocalDateTime dataHora, int nivelSinal, double velocidade, String interferencia, String banda, Long comodoId, Long residenciaId) {
        this.id = id;
        this.dataHora = dataHora;
        this.nivelSinal = nivelSinal;
        this.velocidade = velocidade;
        this.interferencia = interferencia;
        this.banda = banda;
        this.comodoId = comodoId;
        this.residenciaId = residenciaId;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public int getNivelSinal() {
        return nivelSinal;
    }

    public void setNivelSinal(int nivelSinal) {
        this.nivelSinal = nivelSinal;
    }

    public double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }

    public String getInterferencia() {
        return interferencia;
    }

    public void setInterferencia(String interferencia) {
        this.interferencia = interferencia;
    }

    public String getBanda() {
        return banda;
    }

    public void setBanda(String banda) {
        this.banda = banda;
    }

    public Long getComodoId() {
        return comodoId;
    }

    public void setComodoId(Long comodoId) {
        this.comodoId = comodoId;
    }

    public Long getResidenciaId() {
        return residenciaId;
    }

    public void setResidenciaId(Long residenciaId) {
        this.residenciaId = residenciaId;
    }
}