package br.com.utfpr.avaliawifi.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "residencia")
public class Residencia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(nullable = false, length = 200)
    private String endereco;
    
    @Column(nullable = false, length = 100)
    private String cliente;
    
    // Relacionamentos com cascade para exclusão automática dos filhos
    @OneToMany(mappedBy = "residencia", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comodo> comodos = new ArrayList<>();
    
    @OneToMany(mappedBy = "residencia", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Medicao> medicoes = new ArrayList<>();
    
    // Construtores
    public Residencia() {}
    
    public Residencia(String nome, String endereco, String cliente) {
        this.nome = nome;
        this.endereco = endereco;
        this.cliente = cliente;
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
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public String getCliente() {
        return cliente;
    }
    
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    
    public List<Comodo> getComodos() {
        return comodos;
    }
    
    public void setComodos(List<Comodo> comodos) {
        this.comodos = comodos;
    }
    
    public List<Medicao> getMedicoes() {
        return medicoes;
    }
    
    public void setMedicoes(List<Medicao> medicoes) {
        this.medicoes = medicoes;
    }
}