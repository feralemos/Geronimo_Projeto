package com.example.aluno.geronimo_projeto.modelo;

/**
 * Created by edson on 03/06/2016.
 */
public class Lutador {
    private Integer id_lutador;
    private String nome;
    private String sobrenome;
    private String categoria;
    private Double peso;


    public Integer getId_lutador() {
        return id_lutador;
    }

    public void setId_lutador(Integer id_lutador) {
        this.id_lutador = id_lutador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }
}
