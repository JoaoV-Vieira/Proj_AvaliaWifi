package model;

import java.time.LocalDateTime;

public class Medicao {

    private Long id;
    private LocalDateTime dataHora;
    private int nivelSinal;
    private double velocidade;
    private String interferencia;
    private String banda;
    private Comodo comodo;
    private Residencia residencia;

    public Medicao() {
    }

    public Medicao(Long id, LocalDateTime dataHora, int nivelSinal, double velocidade, String interferencia, String banda, Comodo comodo, Residencia residencia) {
        this.id = id;
        this.dataHora = dataHora;
        this.nivelSinal = nivelSinal;
        this.velocidade = velocidade;
        this.interferencia = interferencia;
        this.banda = banda;
        this.comodo = comodo;
        this.residencia = residencia;
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

    public Comodo getComodo() {
        return comodo;
    }

    public void setComodo(Comodo comodo) {
        this.comodo = comodo;
    }

    public Residencia getResidencia() {
        return residencia;
    }

    public void setResidencia(Residencia residencia) {
        this.residencia = residencia;
    }
}
