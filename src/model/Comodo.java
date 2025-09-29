package model;

public class Comodo {

    private Long id;
    private String nome;
    private Residencia residencia;

    public Comodo() {
    }

    public Comodo(Long id, String nome, Residencia residencia) {
        this.id = id;
        this.nome = nome;
        this.residencia = residencia;
    }

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

    public Residencia getResidencia() {
        return residencia;
    }

    public void setResidencia(Residencia residencia) {
        this.residencia = residencia;
    }
}
