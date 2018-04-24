package br.com.alura.ceep.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.ceep.model.Cor;
import br.com.alura.ceep.model.Nota;


public class NotaDAO extends SQLiteOpenHelper {

    public static final String NOTAS = "Notas";
    public static final String ID = "id";
    public static final String TITULO = "titulo";
    public static final String DESCRICAO = "descricao";
    public static final String COR = "cor";
    public static final String POSICAO = "posicao";
    public static final String DATABASE_NAME = "Ceep";
    public static final int VERSAO = 1;

    public NotaDAO(Context context) {
        super(context, DATABASE_NAME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String criaTabela = "CREATE TABLE " + NOTAS + "(" +
                ID + " INTEGER PRIMARY KEY, " +
                TITULO + " TEXT," +
                DESCRICAO + " TEXT," +
                COR + " TEXT," +
                POSICAO + " INTEGER);";
        sqLiteDatabase.execSQL(criaTabela);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<Nota> todas() {
        String buscaTodasNotas = "SELECT * FROM " + NOTAS + " ORDER BY " + POSICAO;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(buscaTodasNotas, null);
        return pegaDados(cursor);
    }

    @NonNull
    private List<Nota> pegaDados(Cursor c) {
        List<Nota> notas = new ArrayList<>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(ID));
            String titulo = c.getString(c.getColumnIndex(TITULO));
            String descricao = c.getString(c.getColumnIndex(DESCRICAO));
            String cor = c.getString(c.getColumnIndex(COR));
            int posicao = c.getInt(c.getColumnIndex(POSICAO));
            Nota nota = new Nota(id, titulo, descricao, Cor.valueOf(cor), posicao);
            notas.add(nota);
        }
        return notas;
    }

    public Nota insere(Nota nota) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues notaComDados = populaDados(nota);
        long idGerado = db.insert(NOTAS, null, notaComDados);
        nota.setId(idGerado);
        return nota;
    }

    @NonNull
    private ContentValues populaDados(Nota nota) {
        ContentValues dados = new ContentValues();
        dados.put(ID, nota.getId());
        dados.put(TITULO, nota.getTitulo());
        dados.put(DESCRICAO, nota.getDescricao());
        dados.put(COR, nota.getCor().toString());
        dados.put(POSICAO, nota.getPosicao().toString());
        return dados;
    }

    public void altera(Nota nota) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues notaComDados = populaDados(nota);
        String[] params = {nota.getId().toString()};
        db.update(NOTAS, notaComDados, ID + "= ?", params);
    }

    public void remove(Nota nota) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {nota.getId().toString()};
        Integer posicaoNotaRemovida = nota.getPosicao();
        db.delete(NOTAS, ID + "= ?", params);
        ajustaPosicao(posicaoNotaRemovida);
    }

    private void ajustaPosicao(int posicao) {
        String diminuiUmaPosicao = "UPDATE " + NOTAS +
                " SET " + POSICAO + " = " + POSICAO + -1 +
                " WHERE " + POSICAO + " > " + posicao;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(diminuiUmaPosicao);
    }

    public void troca(Nota notaInicial, Nota notaFinal) {
        Integer posicaoInicial = notaInicial.getPosicao();
        Integer posicaoFinal = notaFinal.getPosicao();

        trocaPosicao(notaInicial, posicaoFinal);
        trocaPosicao(notaFinal, posicaoInicial);
    }

    private void trocaPosicao(Nota nota, Integer posicao) {
        nota.setPosicao(posicao);
        altera(nota);
    }

}
