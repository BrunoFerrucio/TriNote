package com.TriNote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.TriNote.Adapters.AdaptersUsuario;
import com.TriNote.Databases.DBUsuario;
import com.TriNote.Mascaras.MascaraNumTel;
import com.TriNote.Retrofit.Inicializador;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cadastro2 extends AppCompatActivity {
    private AlertDialog Alerta;
    EditText Tele, Pass, Pass2;
    Button Cad;
    CheckBox Termos;
    TextView NomeUsu, InfoSenha, txtTermos;
    ImageButton BtnVer1, BtnVer2;

    AdaptersUsuario adaptersUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro2);

        Tele = findViewById(R.id.EdtTele);
        Pass = findViewById(R.id.Pass1);
        Pass2 = findViewById(R.id.Pass2);
        Cad = findViewById(R.id.BtnCad);
        Termos = findViewById(R.id.CBox);
        NomeUsu = findViewById(R.id.CadNomeusu);
        BtnVer1 = findViewById(R.id.BtnVisible01);
        BtnVer2 = findViewById(R.id.BtnVisible02);
        InfoSenha = findViewById(R.id.CadTxtInfoSenha);
        txtTermos = findViewById(R.id.txtBox);

        adaptersUsuario = (AdaptersUsuario) this.getIntent().getSerializableExtra("nome"); //recebe informações do intent
        if(adaptersUsuario !=null){
            NomeUsu.setText(adaptersUsuario.getNomeUsuario());
        } //se o adapter for diferente de nulo

        Tele.requestFocus(); //foco no campo de telefone
        Tele.addTextChangedListener(MascaraNumTel.insert("(##) #####-####", Tele)); //Mascara do telefone

        BtnVer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSenha1(Pass, BtnVer1);
            }
        }); //ImageButton ver a senha
        BtnVer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSenha2(Pass2, BtnVer2);
            }
        }); //ImageButton ver a senha

        Cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsuCampos();
            }
        }); //botão cadastro

        txtTermos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irTermos();
            }
        });
    }

    private void mostrarSenha1(EditText pass, ImageButton btnVer1) {
        if (pass.getInputType() == 129) {
            pass.setInputType(1);
            btnVer1.setImageDrawable(ContextCompat.getDrawable(Cadastro2.this, R.drawable.ic_visible));
        } else if (pass.getInputType() == 1) {
            pass.setInputType(129);
            btnVer1.setImageDrawable(ContextCompat.getDrawable(Cadastro2.this, R.drawable.ic_visibility_off));
        }
    } //mostra a senha

    private void mostrarSenha2(EditText pass2, ImageButton btnVer2) {
        if (pass2.getInputType() == 129) {
            pass2.setInputType(1);
            btnVer2.setImageDrawable(ContextCompat.getDrawable(Cadastro2.this, R.drawable.ic_visible));
        } else if (pass2.getInputType() == 1) {
            pass2.setInputType(129);
            btnVer2.setImageDrawable(ContextCompat.getDrawable(Cadastro2.this, R.drawable.ic_visibility_off));
        }
    } //mostra a senha

    private void irTermos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro2.this);

        builder.setMessage("Você será redirecionado para outra tela");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent troca = new Intent(Cadastro2.this, Termos.class);
                startActivity(troca);
            }
        });

        builder.setNegativeButton("Prefiro ficar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        Alerta = builder.create();
        Alerta.show();
    } //Vai para tela de termos de uso

    public void ConsuCampos() {
        if(Tele.getText().toString().trim().equals("") && Pass.getText().toString().trim().equals("")
                && Pass2.getText().toString().trim().equals("")){
            Toast tost = Toast.makeText(Cadastro2.this, "Preencha os campos", Toast.LENGTH_SHORT);
            tost.setGravity(Gravity.CENTER, 0,0);
            tost.show();
            Tele.requestFocus();
        }
        else if(Tele.getText().toString().trim().equals("") && Pass.getText().toString().trim().length()>0
                && Pass2.getText().toString().trim().length()>0){
            Toast tost = Toast.makeText(Cadastro2.this, "Digite seu número de telefone", Toast.LENGTH_SHORT);
            tost.setGravity(Gravity.CENTER, 0,0);
            tost.show();
            Tele.requestFocus();
        }
        else if(Tele.getText().toString().trim().length()>0 && Pass.getText().toString().trim().equals("")
                && Pass2.getText().toString().trim().length()>0){
            Toast tost = Toast.makeText(Cadastro2.this, "Digite sua senha", Toast.LENGTH_SHORT);
            tost.setGravity(Gravity.CENTER, 0,0);
            tost.show();
            Pass.requestFocus();
        }
        else if(Tele.getText().toString().trim().length()>0 && Pass.getText().toString().trim().equals("")
                && Pass2.getText().toString().trim().equals("")){
            Toast tost = Toast.makeText(Cadastro2.this, "Digite sua senha", Toast.LENGTH_SHORT);
            tost.setGravity(Gravity.CENTER, 0,0);
            tost.show();
            Pass.requestFocus();
        }
        else if(Tele.getText().toString().trim().length()>0 && Pass.getText().toString().trim().length()>0
                && Pass2.getText().toString().trim().equals("")){
            Toast tost = Toast.makeText(Cadastro2.this, "Digite novamente sua senha", Toast.LENGTH_SHORT);
            tost.setGravity(Gravity.CENTER, 0,0);
            tost.show();
            Pass2.requestFocus();
        }
        else if (Tele.getText().toString().trim().length()>0 && Pass.getText().toString().trim().length()>0
                && Pass2.getText().toString().trim().length()>0 && !Termos.isChecked()){
            Toast tost = Toast.makeText(Cadastro2.this, "Aceite os termos", Toast.LENGTH_SHORT);
            tost.setGravity(Gravity.CENTER, 0,0);
            tost.show();
        }
        else if(Tele.getText().toString().trim().length()>0 && Pass.getText().toString().trim().length()>0
                && Pass2.getText().toString().trim().length()>0 && Termos.isChecked()){
            igualsenha();
        }
    } //Verifica os campos

    private void igualsenha() {
        String senha2 = Pass2.getText().toString();

        if(Pass.getText().toString().equals(senha2) && Pass.getText().toString().length()>=7
                && Pass2.getText().toString().length()>=7){
            salvardados();
        }
        else{
            Toast.makeText(Cadastro2.this, "Escreva senhas iguais e com 7 caracteres", Toast.LENGTH_LONG).show();
            Pass.requestFocus();
        }
    } //Verifica igualdade das senhas

    private void salvardados() {
        String senha = Pass.getText().toString();
        String fone = Tele.getText().toString();

        adaptersUsuario.setTelefoneUsuario(fone);
        adaptersUsuario.setSenhaUsuario(senha);

        /*Inicializador retrofit = new Inicializador();
        retrofit.servico().CadastrarUsuario(adaptersUsuario).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                cadastroComple();
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro2.this);
                builder.setMessage("Erro de resposta servidor");
                Alerta = builder.create();
                Alerta.show();
            }
        });*/

        DBUsuario sql = new DBUsuario(this);
        sql.CadastrarUsuario(adaptersUsuario);
        cadastroComple();
    } //Salva dados do cadastro

    public void cadastroComple() {
        Alerta = new AlertDialog.Builder(Cadastro2.this)
                .setTitle("Quase lá...")
                .setMessage("Verifique seu e-mail para finalizar o cadastro" + "\n"
                    + "Até breve")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Cadastro2.this, Login.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                })
                .setNeutralButton("Ir app de E-mail", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent email = new Intent(Intent.ACTION_MAIN);
                        email.addCategory(Intent.CATEGORY_APP_EMAIL);
                        finish();
                        startActivity(email);
                        dialog.dismiss(); //fecha o alert
                    }
                })
                .setCancelable(false)
                .show();
    } //Alert de cadastro completo

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Oi");
        builder.setMessage("Você está prestes a sair do cadastro, deseja realmente parar seu cadastro ?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adaptersUsuario = null;
                Intent login = new Intent(Cadastro2.this, Login.class);
                startActivity(login);
                finish();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        Alerta = builder.create();
        Alerta.show();
    } //Se o botão de voltar for precionado, surge alert
}