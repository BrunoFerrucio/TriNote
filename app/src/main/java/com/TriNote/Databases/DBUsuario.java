package com.TriNote.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.TriNote.Adapters.AdaptersUsuario;

import java.util.ArrayList;
import java.util.List;

public class DBUsuario extends SQLiteOpenHelper {
    public DBUsuario(Context context) {
        super(context, "dbUsuario", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE tblUsuario(id TEXT, nome TEXT, email TEXT, telefone TEXT, " +
                "login TEXT, senha TEXT, foto BOOL, capa BOOL)";
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table tblUsuario";
        db.execSQL(sql);
        onCreate(db);
    }

    public void CadastrarUsuario(AdaptersUsuario adaptersUsuario){
        SQLiteDatabase sql = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", adaptersUsuario.getIdUsuario());
        contentValues.put("nome", adaptersUsuario.getNomeUsuario());
        contentValues.put("email", adaptersUsuario.getEmailUsuario());
        contentValues.put("telefone", adaptersUsuario.getTelefoneUsuario());
        contentValues.put("login", adaptersUsuario.getLoginUsuario());
        contentValues.put("senha", adaptersUsuario.getSenhaUsuario());
        contentValues.put("foto", adaptersUsuario.getFoto());

        sql.insert("tblUsuario",null, contentValues);
    }

    public AdaptersUsuario Login(String Usuario, String Senha) {
        final SQLiteDatabase sql = getReadableDatabase();
        final String[] login = {"nome", "senha", "nome", "email", "telefone"};
        final Cursor cursor = sql.query("tblUsuario",login, "nome = ? and senha = ?",
                new String[]{Usuario, Senha}, "nome", null,null,null);
        if(cursor.moveToFirst()){
            AdaptersUsuario adaptersUsuario = new AdaptersUsuario();
            adaptersUsuario.setLoginUsuario(String.valueOf(cursor.getColumnIndex("login")));
            adaptersUsuario.setSenhaUsuario(String.valueOf(cursor.getColumnIndex("senha")));
            adaptersUsuario.setNomeUsuario(String.valueOf(cursor.getColumnIndex("nome")));
            adaptersUsuario.setEmailUsuario(String.valueOf(cursor.getColumnIndex("email")));
            adaptersUsuario.setTelefoneUsuario(String.valueOf(cursor.getColumnIndex("telefone")));

            return adaptersUsuario;
        }
        else{
            return null;
        }
    }

    public List<AdaptersUsuario> TodosUsuarios() {
        SQLiteDatabase db = getReadableDatabase();
        String[] colunas = new String[]{"id", "nome", "email", "telefone", "login", "senha", "foto"};
        Cursor cursor = db.query("tblUsuario", colunas, null, null,
                null, null, null);
        List<AdaptersUsuario> usuarioList = new ArrayList<>();

        while (cursor.moveToNext()) {
            AdaptersUsuario adapter = new AdaptersUsuario();
            adapter.setIdUsuario(cursor.getString(cursor.getColumnIndex("id")));
            adapter.setNomeUsuario(cursor.getString(cursor.getColumnIndex("nome")));
            adapter.setEmailUsuario(cursor.getString(cursor.getColumnIndex("email")));
            adapter.setTelefoneUsuario(cursor.getString(cursor.getColumnIndex("telefone")));
            adapter.setLoginUsuario(cursor.getString(cursor.getColumnIndex("login")));
            adapter.setSenhaUsuario(cursor.getString(cursor.getColumnIndex("senha")));
            adapter.setFoto(cursor.getBlob(cursor.getColumnIndex("foto")));

            usuarioList.add(adapter);
        }
        return usuarioList;
    }

    public List<AdaptersUsuario> Usuario(){
        SQLiteDatabase sql = getReadableDatabase();
        String[] usuario = {"nome", "email"};
        Cursor cursor = sql.query("tblUsuario", usuario, null, null,
                null, null, null);
        List<AdaptersUsuario> usuariosList = new ArrayList<>();
        while(cursor.moveToNext()){
            AdaptersUsuario adapter = new AdaptersUsuario();
            adapter.setLoginUsuario(cursor.getString(cursor.getColumnIndex("nome")));
            adapter.setEmailUsuario(cursor.getString(cursor.getColumnIndex("email")));

            usuariosList.add(adapter);
        }

        return usuariosList;
    }

    public List<AdaptersUsuario> ProcuraUsuarios() {
        SQLiteDatabase sql = getReadableDatabase();
        String[] colunas = new String[]{"nome", "foto"};
        Cursor cursor = sql.query("tblUsuario",colunas, null,null,
                null,null, "nome", null);
        List<AdaptersUsuario> usuarios = new ArrayList<>();

        while(cursor.moveToNext()){
            AdaptersUsuario usuario = new AdaptersUsuario();
            usuario.setLoginUsuario(cursor.getString(cursor.getColumnIndex("nome")));

            String imgString = String.valueOf(cursor.getColumnIndex("foto"));
            byte[] imgByte = imgString.getBytes();
            usuario.setFoto(imgByte);

            usuarios.add(usuario);
        }

        return usuarios;
    }
}