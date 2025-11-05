package br.com.utfpr.avaliawifi.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "medicao")
public class Medicao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private double posicaoX;
    
    @Column(nullable = false)
    private double posicaoY;
    
    @Column(nullable = false)
    private double intensidadeDbm;
    
    @Column(nullable = false)
    private LocalDateTime dataHora;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comodo_id")
    private Comodo comodo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residencia_id")
    private Residencia residencia;
    
    // Construtores
    public Medicao() {}
    
    public Medicao(double posicaoX, double posicaoY, double intensidadeDbm, LocalDateTime dataHora, 
                   Comodo comodo, Residencia residencia) {
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.intensidadeDbm = intensidadeDbm;
        this.dataHora = dataHora;
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
    
    public double getPosicaoX() {
        return posicaoX;
    }
    
    public void setPosicaoX(double posicaoX) {
        this.posicaoX = posicaoX;
    }
    
    public double getPosicaoY() {
        return posicaoY;
    }
    
    public void setPosicaoY(double posicaoY) {
        this.posicaoY = posicaoY;
    }
    
    public double getIntensidadeDbm() {
        return intensidadeDbm;
    }
    
    public void setIntensidadeDbm(double intensidadeDbm) {
        this.intensidadeDbm = intensidadeDbm;
    }
    
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
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