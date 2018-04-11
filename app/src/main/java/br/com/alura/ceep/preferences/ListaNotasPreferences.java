package br.com.alura.ceep.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.alura.ceep.ui.recyclerview.LayoutManagerPreferences;

public class ListaNotasPreferences {

    private static final String CHAVE_SHARED_PREFERENCES = "br.com.alura.ceep.preferences.ListaNotasPreferences";
    private static final String LAYOUT_MANAGER = "LayoutManager";
    private final SharedPreferences sharedPreferences;

    public ListaNotasPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(CHAVE_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void salvaLayoutManager(LayoutManagerPreferences layoutManagerPreference) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAYOUT_MANAGER, layoutManagerPreference.toString());
        editor.commit();
    }

    public LayoutManagerPreferences devolveLayoutManagerSelecionado() {
        return LayoutManagerPreferences.valueOf(sharedPreferences
                .getString(LAYOUT_MANAGER, LayoutManagerPreferences.LINEAR.toString()));
    }
}
