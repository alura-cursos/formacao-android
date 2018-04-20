package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.preferences.ListaNotasPreferences;
import br.com.alura.ceep.ui.recyclerview.LayoutManagerPreferences;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;
import br.com.alura.ceep.ui.recyclerview.helper.callback.NotaItemTouchHelperCallback;

import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_ALTERA_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;

public class ListaNotasActivity extends AppCompatActivity {


    public static final String TITULO_APPBAR = "Notas";
    private ListaNotasAdapter adapter;
    private MenuItem linearMenu;
    private MenuItem staggeredGridMenu;
    private RecyclerView listaNotas;
    private ListaNotasPreferences preferences;
    private final NotaDAO DAO = new NotaDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        setTitle(TITULO_APPBAR);

        List<Nota> todasNotas = DAO.todas();
        configuraRecyclerView(todasNotas);
        configuraBotaoInsereNota();
    }

    private void configuraBotaoInsereNota() {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaFormularioNotaActivityInsere();
            }
        });
    }

    private void vaiParaFormularioNotaActivityInsere() {
        Intent iniciaFormularioNota =
                new Intent(ListaNotasActivity.this,
                        FormularioNotaActivity.class);
        startActivityForResult(iniciaFormularioNota,
                CODIGO_REQUISICAO_INSERE_NOTA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ehResultadoInsereNota(requestCode, data)) {
            if (resultadoOk(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                adiciona(notaRecebida);
            }
        }
        if (ehResultadoAlteraNota(requestCode, data)) {
            if (resultadoOk(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                altera(notaRecebida);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_notas, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        linearMenu = menu.findItem(R.id.menu_lista_notas_linear);
        staggeredGridMenu = menu.findItem(R.id.menu_lista_notas_staggered_grid);
        configuraLayoutManagerEscolhido();
        return super.onPrepareOptionsMenu(menu);
    }

    private void configuraLayoutManagerEscolhido() {
        preferences = new ListaNotasPreferences(this);
        LayoutManagerPreferences layoutsManager = preferences.devolveLayoutManagerSelecionado();
        if (layoutsManager.equals(LayoutManagerPreferences.LINEAR)) {
            mostraLinearLayout();
            return;
        }
        mostraStaggeredGridLayout();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_lista_notas_staggered_grid) {
            mostraStaggeredGridLayout();
            preferences.salvaLayoutManager(LayoutManagerPreferences.STAGGERED_GRID);
        }

        if (itemId == R.id.menu_lista_notas_linear) {
            mostraLinearLayout();
            preferences.salvaLayoutManager(LayoutManagerPreferences.LINEAR);
        }

        if(itemId == R.id.menu_lista_notas_ajuda){
            vaiParaFeedback();
        }

        return super.onOptionsItemSelected(item);
    }

    private void vaiParaFeedback() {
        Intent abreTelaFeedback = new Intent(this, FeedbackActivity.class);
        startActivity(abreTelaFeedback);
    }

    private void mostraStaggeredGridLayout() {
        staggeredGridMenu.setVisible(false);
        linearMenu.setVisible(true);
        listaNotas.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    private void mostraLinearLayout() {
        staggeredGridMenu.setVisible(true);
        linearMenu.setVisible(false);
        listaNotas.setLayoutManager(new LinearLayoutManager(this));
    }

    private void altera(Nota nota) {
        adapter.altera(nota);
        DAO.altera(nota);
    }

    private boolean ehResultadoAlteraNota(int requestCode, Intent data) {
        return ehCodigoRequisicaoAlteraNota(requestCode) &&
                temNota(data);
    }

    private boolean ehCodigoRequisicaoAlteraNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_ALTERA_NOTA;
    }

    private void adiciona(Nota nota) {
        Nota notaInserida = DAO.insere(nota);
        adapter.adiciona(notaInserida);
    }

    private boolean ehResultadoInsereNota(int requestCode, Intent data) {
        return ehCodigoRequisicaoInsereNota(requestCode) &&
                temNota(data);
    }

    private boolean temNota(Intent data) {
        return data != null && data.hasExtra(CHAVE_NOTA);
    }

    private boolean resultadoOk(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, listaNotas);
        configuraItemTouchHelper(listaNotas);
    }

    private void configuraItemTouchHelper(RecyclerView listaNotas) {
        ItemTouchHelper itemTouchHelper =
                new ItemTouchHelper(new NotaItemTouchHelperCallback(adapter, this));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, todasNotas);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(new ListaNotasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota) {
                vaiParaFormularioNotaActivityAltera(nota);
            }
        });
    }

    private void vaiParaFormularioNotaActivityAltera(Nota nota) {
        Intent abreFormularioComNota = new Intent(ListaNotasActivity.this,
                FormularioNotaActivity.class);
        abreFormularioComNota.putExtra(CHAVE_NOTA, nota);
        startActivityForResult(abreFormularioComNota, CODIGO_REQUISICAO_ALTERA_NOTA);
    }

}
