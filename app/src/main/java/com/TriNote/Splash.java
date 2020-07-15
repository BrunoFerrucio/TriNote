package com.TriNote;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.TriNote.Usuario.AdapterUsuario;
import com.TriNote.Usuario.DBDadoSessao;
import com.TriNote.Usuario.Dados;

import java.util.List;

public class Splash extends AppCompatActivity {
    private static final long SPLASH_DISPLAY_LENGTH = 1000; //Variavel com o tempo do Splash em milissegundos
    boolean log = false;
    List<AdapterUsuario> usuarioList;
    TextView nome;

    @Override
    protected void onCreate(Bundle circle) {
        super.onCreate(circle);
        setContentView(R.layout.splash);

        nome = findViewById(R.id.txtSplashAviso);

        DBDadoSessao sql = new DBDadoSessao(this);
        usuarioList = sql.RetornaDados();

        if (usuarioList != null && !usuarioList.isEmpty()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    log = true;
                    Intent mainIntent = new Intent(Splash.this, Anotacoes.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(Splash.this, Login.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }
}