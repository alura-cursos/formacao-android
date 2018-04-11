package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import br.com.alura.ceep.R;
import br.com.alura.ceep.preferences.ApresentacaoPreferences;

public class ApresentacaoActivity extends AppCompatActivity {

    private static final int TEMPO_PRIMEIRA_APRESENTACAO = 1500;
    private static final int TEMPO_JA_APRESENTADO = 500;
    private ApresentacaoPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apresentacao);
        preferences = new ApresentacaoPreferences(this);
        if (preferences.naoFoiApresentado()) {
            mostraApresentacao(TEMPO_PRIMEIRA_APRESENTACAO);
        }
        mostraApresentacao(TEMPO_JA_APRESENTADO);
    }

    private void mostraApresentacao(int tempoApresentacao) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                preferences.apresentado();
                vaiParaListaNotasActivity();
            }
        }, tempoApresentacao);
    }

    private void vaiParaListaNotasActivity() {
        Intent abreListaNotasActivity = new Intent(this, ListaNotasActivity.class);
        startActivity(abreListaNotasActivity);
        finish();
    }
}
