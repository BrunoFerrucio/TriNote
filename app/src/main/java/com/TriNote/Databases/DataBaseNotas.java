package com.TriNote.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.TriNote.Adapters.AdapterAnotacoes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBaseNotas extends SQLiteOpenHelper {
    public DataBaseNotas(Context context) {
        super(context, "tblNotas", null, 7);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE tblNotas(id TEXT PRIMARY KEY, usuCria TEXT, usuAltera TEXT, " +
                "usuComenta TEXT, titulo TEXT, conteudo TEXT, font TEXT, dataHoraCria TEXT," +
                "dataHoraAltera TEXT, dataHoraComenta TEXT, imagem BLOB, status TEXT)";
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table tblNotas";
        db.execSQL(sql);
        onCreate(db);
    }

    public void CriarAnotacao(AdapterAnotacoes notas) {
        SQLiteDatabase sql = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", notas.getIdAnotacao());
        contentValues.put("usuCria", notas.getUsuarioCria());
        contentValues.put("titulo", notas.getTitulo());
        contentValues.put("conteudo", notas.getConteudo());
        contentValues.put("font", notas.getFont());
        contentValues.put("imagem", notas.getImagem());

        sql.insert("tblNotas", null, contentValues);
    }

    public void AtualizaAnotacao(AdapterAnotacoes notas) {
        SQLiteDatabase sql = getWritableDatabase();
        ContentValues contentValues = new ContentValues(); //pega conteudo
        contentValues.put("usuAltera", notas.getUsuarioAltera());
        contentValues.put("titulo", notas.getTitulo());
        contentValues.put("conteudo",notas.getConteudo());
        contentValues.put("Imagem",notas.getImagem());

        String[] arg = new String[]{ notas.getIdAnotacao() }; //argumento para identificar

        sql.update("tblNotas",contentValues, "id=?", arg); //passa o id da anotação para atualizar
    }

    public List<AdapterAnotacoes> CarregaAnotacoes() {
        SQLiteDatabase sql = getReadableDatabase();
        String[] colunas = new String[]{"id", "titulo", "conteudo", "imagem", "font"};
        Cursor cursor = sql.query("tblNotas", colunas, null, null,
                null, null, null, null);
        List<AdapterAnotacoes> anotacoes = new ArrayList<>();

        while (cursor.moveToNext()) {
            AdapterAnotacoes notas = new AdapterAnotacoes();
            notas.setIdAnotacao(cursor.getString(cursor.getColumnIndex("id")));
            notas.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));
            notas.setConteudo(cursor.getString(cursor.getColumnIndex("conteudo")));
            notas.setImagem(cursor.getBlob(cursor.getColumnIndex("imagem")));
            notas.setFont(cursor.getString(cursor.getColumnIndex("font")));
            anotacoes.add(notas);
        }

        return anotacoes;
    }

    public void ExluirAnotacao(AdapterAnotacoes notas) {
        SQLiteDatabase sql = getWritableDatabase();

        //String[] arg = new String[]{notas.getIdAnotacao()};

        sql.delete("tblNotas", "id=?", new String[]{notas.getIdAnotacao()});
    }

    public void ApagaTudo(){
        SQLiteDatabase sql = getWritableDatabase();
        sql.delete("tblNotas", null, null);
    }
}
