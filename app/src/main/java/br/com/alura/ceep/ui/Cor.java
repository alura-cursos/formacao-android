package br.com.alura.ceep.ui;

import java.util.Arrays;
import java.util.List;

public enum Cor {
    AZUL {
        @Override
        public String toString() {
            return "#ff0000";
        }
    }, BRANCO {
        @Override
        public String toString() {
            return "#ffffff";
        }
    }, VERMELHO {
        @Override
        public String toString() {
            return "#00ff00";
        }
    };

    public static List<Cor> todas() {
        return Arrays.asList(BRANCO, VERMELHO, AZUL);
    }
}
