package com.TriNote.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.TriNote.Adapters.AdapterDuvidas;

import java.util.ArrayList;
import java.util.List;

public class DBDuvidas extends SQLiteOpenHelper {
    public DBDuvidas(Context context) {
        super(context, "dbDuvidas", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE tblDuvidas(titulo TEXT, conteudo TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table tblDuvidas";
        db.execSQL(sql);
        onCreate(db);
    }

    public void CadastraDuvidas(AdapterDuvidas adapterDuvidas){
        SQLiteDatabase sql = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", adapterDuvidas.getDuvidaTitulo());
        values.put("conteudo", adapterDuvidas.getDuvidaConteudo());

        sql.insert("tblDuvidas", null, values);
    }

    public List<AdapterDuvidas> ListaDuvidas(){
        SQLiteDatabase sql = getReadableDatabase();
        List<AdapterDuvidas> duvidasList = new ArrayList<>();
        String[] colunas = {};
        Cursor cursor = sql.query("tblDuvidas", colunas, null, null,
                null, null, null);
        while (cursor.moveToNext()){
            AdapterDuvidas adapterDuvidas = new AdapterDuvidas();
            adapterDuvidas.setDuvidaTitulo(String.valueOf(cursor.getColumnIndex("titulo")));
            adapterDuvidas.setDuvidaConteudo(String.valueOf(cursor.getColumnIndex("conteudo")));

            duvidasList.add(adapterDuvidas);
        }
        return duvidasList;
    }
}
