package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Cor;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.CoresAdapter;

import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;

public class FormularioNotaActivity extends AppCompatActivity {


    public static final String TITULO_APPBAR_INSERE = "Insere nota";
    public static final String TITULO_APPBAR_ALTERA = "Altera nota";
    private int posicaoRecibida = POSICAO_INVALIDA;
    private Nota nota;
    private EditText titulo;
    private EditText descricao;
    private ConstraintLayout constraintLayoutEntradas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        setTitle(TITULO_APPBAR_INSERE);
        inicializaCampos();

        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra(CHAVE_NOTA)) {
            setTitle(TITULO_APPBAR_ALTERA);
            nota = (Nota) dadosRecebidos
                    .getSerializableExtra(CHAVE_NOTA);
            posicaoRecibida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
            preencheCampos();
        } else {
            nota = new Nota();
        }

        configuraCores();
    }

    private void configuraCores() {
        RecyclerView listaCores = findViewById(R.id.formulario_nota_cores);
        List<Cor> cores = Cor.todas();
        CoresAdapter adapter = new CoresAdapter(this, cores);
        adapter.setOnItemClickListener(new CoresAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Cor cor) {
                nota.setCor(cor);
                aplicaFundo();
            }
        });
        listaCores.setAdapter(adapter);
    }

    private void preencheCampos() {
        titulo.setText(nota.getTitulo());
        descricao.setText(nota.getDescricao());
        aplicaFundo();
    }

    private void aplicaFundo() {
        constraintLayoutEntradas.setBackgroundColor(Color.parseColor(nota.getCor().getHex()));
    }

    private void inicializaCampos() {
        constraintLayoutEntradas = findViewById(R.id.formulario_nota_entradas);
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (ehMenuSalvaNota(item)) {
            preencheNota();
            retornaNota();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornaNota() {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, nota);
        resultadoInsercao.putExtra(CHAVE_POSICAO, posicaoRecibida);
        setResult(Activity.RESULT_OK, resultadoInsercao);
    }

    private void preencheNota() {
        this.nota.setTitulo(titulo.getText().toString());
        this.nota.setDescricao(descricao.getText().toString());
    }

    private boolean ehMenuSalvaNota(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_nota_ic_salva;
    }

}
