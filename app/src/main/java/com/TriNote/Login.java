package com.TriNote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.TriNote.Adapters.AdaptersUsuario;
import com.TriNote.Databases.DBUsuario;
import com.TriNote.Retrofit.Inicializador;
import com.TriNote.Usuario.DBDadoSessao;
import com.TriNote.Usuario.Dados;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private AlertDialog alerta;
    List<AdaptersUsuario> usuarioList;
    TextView LogMos;
    EditText LogUsu, LogPass;
    Button BtnLogar, BtnEsqueci, BtnCadas, Fale;
    ImageButton BtnVisible, BtnExlama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        LogMos = findViewById(R.id.LogEdtMostra);
        LogUsu = findViewById(R.id.LogEdtUsu);
        LogPass = findViewById(R.id.LogEdtPass);
        BtnLogar = findViewById(R.id.LogBtnLogar);
        BtnEsqueci = findViewById(R.id.LogBtnEsqueci);
        BtnCadas = findViewById(R.id.LogBtnCadas);
        Fale = findViewById(R.id.LogFale);
        BtnVisible = findViewById(R.id.BtnVisibleOff);
        BtnExlama = findViewById(R.id.BtnExclamaNome);

        BtnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarLogin();
            }
        }); //Botão de login

        BtnCadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irCadastro();
            }
        }); //Botão de Cadastro

        Fale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irFale();
            }
        }); //Botão do FaleConosco

        BtnVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSenha(LogPass, BtnVisible);
            }
        }); //Botão de ver a senha

        BtnExlama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertExclamacao();
            }
        });
    }

    private void alertExclamacao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("Digite seu nome de usuário definido no cadastrado");
        alerta = builder.create();
        LogUsu.requestFocus();
        alerta.show();
    }

    private void validarLogin() {
        DBUsuario sql = new DBUsuario(this);
        LogMos.setAllCaps(false);
        LogMos.setText("Consultando...");
        if (LogUsu.getText().toString().equals("") && LogPass.getText().toString().equals("")) {
            String text = "Digite seus dados";
            LogMos.setText(text);
            LogUsu.requestFocus();
        } else if (LogUsu.getText().toString().equals("") && LogPass.getText().toString().length() > 0) {
            String text = "Digite seu usuário";
            LogMos.setText(text);
            LogUsu.requestFocus();
        } else if (LogUsu.getText().toString().length() > 0 && LogPass.getText().toString().equals("")) {
            String text = "Digite sua senha";
            LogMos.setText(text);
            LogPass.requestFocus();
        } else if (LogUsu.getText().toString().length() > 0 && LogPass.getText().toString().length() > 0) {
            final String Usuario = LogUsu.getText().toString();
            final String Senha = LogPass.getText().toString();

            /*Inicializador retrofit = new Inicializador();
            retrofit.servico().LoginUsuario().enqueue(new Callback<List<AdaptersUsuario>>() {
                @Override
                public void onResponse(@NotNull Call<List<AdaptersUsuario>> call, @NotNull Response<List<AdaptersUsuario>> response) {
                    usuarioList = response.body();
                    if(usuarioList == null){
                        LogMos.setText("Erro de resposta servidor");
                        LogMos.setAllCaps(false);
                    }
                    else{
                        for (AdaptersUsuario usuario : usuarioList) {
                            if (usuario.getLoginUsuario().equals(Usuario) && usuario.getSenhaUsuario().equals(Senha)) {

                                DBDadoSessao dados = new DBDadoSessao(Login.this);
                                dados.SalvaSessao(usuario);

                                Intent intent = new Intent(Login.this, Anotacoes.class);
                                startActivity(intent);
                                finishAffinity();
                            }
                            else{
                                LogMos.setText("Incorreto");
                                LogMos.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                LogUsu.requestFocus();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<AdaptersUsuario>> call, @NotNull Throwable t) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    //builder.setMessage("Erro ao se conectar a rede");
                    builder.setMessage(t+"");
                    alerta = builder.create();
                    alerta.show();
                }
            });*/

            boolean log = false;
            usuarioList = sql.TodosUsuarios();
            if(usuarioList != null){
                LogMos.setText("Aguarde...");
                for(AdaptersUsuario adapter : usuarioList){
                    if(adapter.getLoginUsuario().equals(Usuario) && adapter.getSenhaUsuario().equals(Senha)){
                        log = true;

                        DBDadoSessao dados = new DBDadoSessao(this);
                        dados.SalvaSessao(adapter);

                        Dados.setDadoId(adapter.getIdUsuario());
                        Dados.setDadoNome(adapter.getNomeUsuario());
                        Dados.setDadoLogin(Usuario);
                        Dados.setDadoTelefone(adapter.getTelefoneUsuario());
                        Dados.setDadoEmail(adapter.getEmailUsuario());
                        Dados.setDadoFoto(adapter.getFoto());

                        Intent troca = new Intent(this, Anotacoes.class);
                        startActivity(troca);
                        finishAffinity();
                    }
                    if(!adapter.getLoginUsuario().equals(Usuario) || !adapter.getSenhaUsuario().equals(Senha) &&
                            log == false){
                        LogMos.setText("Incorreto");
                        LogMos.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        LogUsu.requestFocus();
                    }
                    else{
                        if(log != true){
                            LogMos.setText("Não encontrado");
                        }
                    }
                }
                if(usuarioList != null && usuarioList.size() == 0){
                    LogMos.setText("");
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Não há usuário cadastrado");
                    alerta = builder.create();
                    alerta.show();
                }
            }
            else{
                LogMos.setText("Usuario ou senha não encontrado");
            }
        }
    } //Validação de login

    private void irCadastro() {
        AdaptersUsuario adaptersUsuario = new AdaptersUsuario();
        if (LogUsu.getText().toString().equals("")) {
            Intent troca = new Intent(Login.this, Cadastro1.class);
            startActivity(troca);
            finishAffinity();
        } else if (LogUsu.getText().toString().length() > 0) {
            String nome = LogUsu.getText().toString();
            adaptersUsuario.setLoginUsuario(nome);

            Intent intent = new Intent(this, Cadastro1.class);
            intent.putExtra("nome", adaptersUsuario);
            startActivity(intent);
            finishAffinity();
        }
    } //Se tiver algo no campo do usuário manda para o cadastro

    private void irFale() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("Para sua melhor experiencia, iremos te redirecionar para nosso site");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String site = "http://www.trinote.somee.com";
                Uri UrlSite = Uri.parse(site);
                Intent intent = new Intent(Intent.ACTION_VIEW, UrlSite);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Quero ficar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerta = builder.create();
        alerta.show();
    } //Se tiver algo no campo do usuário manda para o FaleConosco

    private void mostrarSenha(EditText LogPass, ImageButton BtnVisible) {
        // 129 = oculto
        // 1 = texto
        if (LogPass.getInputType() == 129) {
            LogPass.setInputType(1);
            BtnVisible.setImageDrawable(ContextCompat.getDrawable(Login.this, R.drawable.ic_visible));
        } else if (LogPass.getInputType() == 1) {
            LogPass.setInputType(129);
            BtnVisible.setImageDrawable(ContextCompat.getDrawable(Login.this, R.drawable.ic_visibility_off));
        }
    } //Mostra a senha digitada
}