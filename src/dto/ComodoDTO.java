package dto;

public class ComodoDTO {

    private Long id;
    private String nome;
    private Long residenciaId;

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

    public Long getResidenciaId() {
        return residenciaId;
    }

    public void setResidenciaId(Long residenciaId) {
        this.residenciaId = residenciaId;
    }
}