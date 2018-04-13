package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
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
<<<<<<< a11ae99c85971827422cbbe29171bc566c5edce1
=======
import br.com.alura.ceep.model.Cor;
>>>>>>> ajuste e resolução de bugs
import br.com.alura.ceep.ui.recyclerview.adapter.CoresAdapter;

import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;

public class FormularioNotaActivity extends AppCompatActivity {


    private static final String TITULO_APPBAR_INSERE = "Insere nota";
    private static final String TITULO_APPBAR_ALTERA = "Altera nota";
    private EditText titulo;
    private EditText descricao;
    private Nota nota;
    private ConstraintLayout constraintLayoutEntradas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        setTitle(TITULO_APPBAR_INSERE);
        inicializaCampos();

        Intent dadosRecebidos = getIntent();
        inicializaNota(dadosRecebidos);
        configuraCores();
    }

    private void inicializaNota(Intent dados) {
        if (dados.hasExtra(CHAVE_NOTA)) {
            setTitle(TITULO_APPBAR_ALTERA);
            nota = (Nota) dados
                    .getSerializableExtra(CHAVE_NOTA);
            preencheCampos();
        } else if (nota == null) {
            this.nota = new Nota();
        }
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

    private void preencheNota() {
        this.nota.setTitulo(titulo.getText().toString());
        this.nota.setDescricao(descricao.getText().toString());
    }

    private void retornaNota() {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, nota);
        setResult(Activity.RESULT_OK, resultadoInsercao);
    }

    private boolean ehMenuSalvaNota(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_nota_ic_salva;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CHAVE_NOTA, nota);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            nota = (Nota) savedInstanceState.getSerializable(CHAVE_NOTA);
            preencheCampos();
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

}
