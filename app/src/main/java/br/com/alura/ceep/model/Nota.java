package br.com.alura.ceep.model;

import java.io.Serializable;

public class Nota implements Serializable {

    private Long id;
    private String titulo;
    private String descricao;
    private Cor cor;
    private Integer posicao;

    public Nota(Long id, String titulo, String descricao, Cor cor, Integer posicao) {
        this(titulo, descricao, cor);
        this.posicao = posicao;
        this.id = id;
    }

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

    public Long getId() {
        return id;
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

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public void setId(long id) {
        this.id = id;
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