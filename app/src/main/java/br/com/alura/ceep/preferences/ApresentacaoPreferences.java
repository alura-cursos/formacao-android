package br.com.alura.ceep.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class ApresentacaoPreferences {


    private static final String CHAVE_SHARED_PREFERENCES = "br.com.alura.ceep.preferences.ApresentacaoPreferences";
    public static final String APRESENTOU = "Apresentou";
    private final SharedPreferences sharedPreferences;

    public ApresentacaoPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(CHAVE_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void apresentado() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(APRESENTOU, true);
        editor.commit();
    }

    public boolean naoFoiApresentado() {
        return !sharedPreferences.getBoolean(APRESENTOU, false);
    }

}
