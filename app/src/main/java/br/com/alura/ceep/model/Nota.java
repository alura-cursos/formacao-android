package br.com.alura.ceep.model;

import java.io.Serializable;

import br.com.alura.ceep.ui.Cor;

public class Nota implements Serializable {

    private final String titulo;
    private final String descricao;
    private Cor cor;

    public Nota(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.cor = Cor.BRANCO;
    }

    public Nota(String titulo, String descricao, Cor cor) {
        this(titulo, descricao);
        this.cor = cor;
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
}