package br.com.alura.ceep.model;

import java.util.Arrays;
import java.util.List;

public enum Cor {

    BRANCO, AZUL, AMARELO, VERMELHO, VERDE, LILAS, GRAY, BROWN, ROXO;


    private String hex;

    static {
        AZUL.hex = "#408EC9";
        BRANCO.hex = "#ffffff";
        VERMELHO.hex = "#EC2F4B";
        VERDE.hex = "#9ACD32";
        AMARELO.hex = "#f9f256";
        LILAS.hex = "#f1cbff";
        GRAY.hex = "#d2d4dc";
        BROWN.hex = "#a47c48";
        ROXO.hex = "#be29ec";
    }

    public String getHex() {
        return hex;
    }

    public static List<Cor> todas() {
        return Arrays.asList(values());
    }
}