package br.com.utfpr.avaliawifi.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comodo")
public class Comodo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(nullable = false)
    private double largura;
    
    @Column(nullable = false)
    private double comprimento;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residencia_id")
    private Residencia residencia;
    
    // Relacionamento com medições - quando um cômodo é excluído, suas medições também são
    @OneToMany(mappedBy = "comodo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Medicao> medicoes = new ArrayList<>();
    
    // Construtores
    public Comodo() {}
    
    public Comodo(String nome, double largura, double comprimento, Residencia residencia) {
        this.nome = nome;
        this.largura = largura;
        this.comprimento = comprimento;
        this.residencia = residencia;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public double getLargura() {
        return largura;
    }
    
    public void setLargura(double largura) {
        this.largura = largura;
    }
    
    public double getComprimento() {
        return comprimento;
    }
    
    public void setComprimento(double comprimento) {
        this.comprimento = comprimento;
    }
    
    public Residencia getResidencia() {
        return residencia;
    }
    
    public void setResidencia(Residencia residencia) {
        this.residencia = residencia;
    }
    
    public List<Medicao> getMedicoes() {
        return medicoes;
    }
    
    public void setMedicoes(List<Medicao> medicoes) {
        this.medicoes = medicoes;
    }
}