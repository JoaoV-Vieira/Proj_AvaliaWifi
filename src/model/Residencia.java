package model;

public class Residencia {

    private Long id;
    private String nome;
    private String endereco;
    private String cliente;

    public Residencia() {
    }

    public Residencia(Long id, String nome, String endereco, String cliente) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.cliente = cliente;
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
}