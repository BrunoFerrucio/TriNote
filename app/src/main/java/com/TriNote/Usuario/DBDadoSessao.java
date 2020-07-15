package com.TriNote.Usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.TriNote.Adapters.AdaptersUsuario;

import java.util.ArrayList;
import java.util.List;

public class DBDadoSessao extends SQLiteOpenHelper {
    public DBDadoSessao(@Nullable Context context) {
        super(context, "DBDados", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE tblUsuario(id TEXT, nome TEXT, login TEXT, email TEXT, telefone TEXT, foto BLOB, capa BLOB)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE tblUsuario";
        db.execSQL(sql);
        onCreate(db);
    }

    public void SalvaSessao(AdaptersUsuario adapter){
        SQLiteDatabase sql = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", adapter.getIdUsuario());
        values.put("nome", adapter.getNomeUsuario());
        values.put("login", adapter.getLoginUsuario());
        values.put("email", adapter.getEmailUsuario());
        values.put("telefone", adapter.getTelefoneUsuario());
        values.put("foto", adapter.getFoto());
        values.put("capa", adapter.getCapa());

        sql.insert("tblUsuario", null, values);
    }

    public List<AdapterUsuario> RetornaDados(){
        SQLiteDatabase sql = getReadableDatabase();
        String[] colunas = {};
        Cursor cursor = sql.query("tblUsuario", colunas, null, null,
                null, null, null);
        List<AdapterUsuario> dadosList = new ArrayList<>();

        while (cursor.moveToNext()){
            AdapterUsuario adapter = new AdapterUsuario();
            adapter.setIdUsuario(cursor.getString(cursor.getColumnIndex("id")));
            adapter.setNomeUsuario(cursor.getString(cursor.getColumnIndex("nome")));
            adapter.setEmailUsuario(cursor.getString(cursor.getColumnIndex("email")));
            adapter.setLoginUsuario(cursor.getString(cursor.getColumnIndex("login")));
            adapter.setTelefoneUsuario(cursor.getString(cursor.getColumnIndex("telefone")));
            adapter.setFotoUsuario(cursor.getBlob(cursor.getColumnIndex("foto")));
            adapter.setCapaUsuario(cursor.getBlob(cursor.getColumnIndex("capa")));

            dadosList.add(adapter);
        }
        return dadosList;
    }

    public void ExcluirDados(){
        SQLiteDatabase sql = getWritableDatabase();

        sql.delete("tblUsuario", null, null);
    }
}