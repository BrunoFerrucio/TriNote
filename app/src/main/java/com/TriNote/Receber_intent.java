package com.TriNote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.TriNote.Adapters.AdapterAnotacoes;
import com.TriNote.Adapters.AdaptersUsuario;
import com.TriNote.Databases.DBUsuario;
import com.TriNote.Databases.DataBaseNotas;
import com.TriNote.Usuario.AdapterUsuario;
import com.TriNote.Usuario.DBDadoSessao;
import com.TriNote.Usuario.Dados;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Receber_intent extends AppCompatActivity {
    private AlertDialog Alert;
    List<AdapterUsuario> usuarioList;
    EditText titulo, conteudo;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receber_intent);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Salve como uma anotação");

        titulo = findViewById(R.id.EdtTitleRecebe);
        conteudo = findViewById(R.id.EdtContentRecebe);
        img = findViewById(R.id.ImageRecebe);

        DBDadoSessao sql = new DBDadoSessao(this);
        usuarioList = sql.RetornaDados();

        if (usuarioList != null) {
            Intent intent = getIntent(); //Recebe a intent de compartilhamento
            String action = intent.getAction(); //Pega a ação da intent
            String type = intent.getType(); //Pega o tipo da intent
            if (Intent.ACTION_SEND.equals(action) && type != null) {
                if ("text/*".equals(type)) {
                    handleSendText(intent);
                } else if (type.startsWith("image/")) {
                    handleSendImage(intent);
                }
            } //Filtra o que foi recebido do compartilhamento

        } else {
            Alert = new AlertDialog.Builder(this)
                    .setMessage("Antes faça login no aplicativo para continuar")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Receber_intent.this, Login.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                    })
                    .setNeutralButton("Depois", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); //fecha o alert
                            finishAffinity();
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            conteudo.setText(sharedText);
        }
    } //Recebe Texto compartilhado

    void handleSendImage(Intent intent) {
        Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            img.setImageURI(imageUri);
        }
    } //Recebe Imagem compartilhada

    @Override
    public void onBackPressed() {
        if (titulo.getText().toString().equals("") && conteudo.getText().toString().equals("") && img.getDrawable() == null) {
            finishAffinity();
            super.onBackPressed();
        }
        else {
            salvarAnotacao();
        }
    }

    private void salvarAnotacao() {
        DataBaseNotas sql = new DataBaseNotas(this);
        String Titulo = titulo.getText().toString();
        String Conteudo = conteudo.getText().toString();

        AdapterAnotacoes anotacoes = new AdapterAnotacoes();
        String id = UUID.randomUUID().toString();
        anotacoes.setIdAnotacao(id);
        anotacoes.setUsuarioCria(Dados.getDadoId());
        anotacoes.setTitulo(Titulo);
        anotacoes.setConteudo(Conteudo);

        if (img.getDrawable() != null) {
            Drawable drawable = img.getDrawable();
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            byte[] imgByte = stream.toByteArray();
            anotacoes.setImagem(imgByte);
        }

        sql.CriarAnotacao(anotacoes);
        startActivity(new Intent(this, Anotacoes.class));
        finishAffinity();
    } //Salva
}