package br.edu.trabalho.model;

public class Aluno {
    private Integer id;
    private String nome;
    private String matricula;
    private String curso;
    private String email;

    public Aluno() {
    }

    public Aluno(Integer id, String nome, String matricula, String curso, String email) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
        this.email = email;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
