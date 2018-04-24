package br.com.alura.ceep.model;

import java.io.Serializable;

public class Nota implements Serializable {

    private String titulo;
    private String descricao;
    private Cor cor;

    public Nota(String titulo, String descricao) {
        this();
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Nota(String titulo, String descricao, Cor cor) {
        this(titulo, descricao);
        this.cor = cor;
    }

    public Nota() {
        this.cor = Cor.BRANCO;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Cor getCor() {
        return cor;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", cor=" + cor +
                '}';
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }
}