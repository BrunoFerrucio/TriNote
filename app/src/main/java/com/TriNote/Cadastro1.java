package com.TriNote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.TriNote.Adapters.AdaptersUsuario;
import com.TriNote.Databases.DBUsuario;
import com.TriNote.Retrofit.Inicializador;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cadastro1 extends AppCompatActivity {
    public static final int Codigo_foto = 1;
    public static final int Codigo_galeria = 2;
    private AlertDialog Alerta;
    List<AdaptersUsuario> usuarioList;
    byte[] imgByte;
    TextView titleBar;
    Button BtnProx, btnImagPerf;
    ImageButton BtnNome, BtnUsuario, BtnEmail;
    EditText mail, mail2, usuUsuario, usuNome;
    ImageView FotoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro1);

        titleBar = findViewById(R.id.textView4);
        mail = findViewById(R.id.CadMail);
        mail2 = findViewById(R.id.CadMail2);
        usuUsuario = findViewById(R.id.CadUsuName);
        usuNome = findViewById(R.id.CadUsuNome);
        btnImagPerf = findViewById(R.id.CadBtnImagPerf);
        FotoPerfil = findViewById(R.id.imagePerfil);
        BtnProx = findViewById(R.id.btnProx);
        BtnNome = findViewById(R.id.LogBtnExclamaUsu);
        BtnUsuario = findViewById(R.id.btnExclamaUsuario);
        BtnEmail = findViewById(R.id.btnExclamaEmail);

        btnImagPerf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Cadastro1.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, Codigo_foto);
                } else {
                    String[] permission = new String[]{Manifest.permission.CAMERA};
                    ActivityCompat.requestPermissions(Cadastro1.this, permission, 0);
                }
            }
        });

        BtnNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensagem = "Nome usado para interagir no fale-conosco e nos demais casos formais";
                alert(mensagem);
                usuNome.requestFocus();
            }
        });
        BtnUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensagem = "Nome usado para interagir com outros usuários.";
                alert(mensagem);
                usuUsuario.requestFocus();
            }
        });
        BtnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensagem = "Digite um e-mail que você tem fácil acesso" + "\n" +
                        "pois mandaremos informações importantes lá";
                alert(mensagem);
                mail.requestFocus();
            }
        });
        BtnProx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerificarCampos1();
            }
        });
    }

    private void VerificarCampos1() {
        titleBar.setText("Conectado.");
        if (usuNome.getText().toString().equals("") && usuUsuario.getText().toString().equals("")
                && mail.getText().toString().equals("") && mail2.getText().toString().equals("")) {
            Toast tosta = Toast.makeText(Cadastro1.this, "Preencha os campos", Toast.LENGTH_SHORT);
            tosta.setGravity(Gravity.CENTER, 0, 0);
            tosta.show();
            usuNome.requestFocus();
        } else if (usuNome.getText().toString().equals("") && usuUsuario.getText().toString().length() > 0
                && mail.getText().toString().length() > 0 && mail2.getText().toString().length() > 0) {
            Toast tosta = Toast.makeText(Cadastro1.this, "Escreva seu nome", Toast.LENGTH_SHORT);
            tosta.setGravity(Gravity.CENTER, 0, 0);
            tosta.show();
            usuUsuario.requestFocus();
        } else if (usuNome.getText().toString().length() > 0 && usuUsuario.getText().toString().equals("")
                && mail.getText().toString().equals("") && mail2.getText().toString().equals("")) {
            Toast tosta = Toast.makeText(Cadastro1.this, "Escreva os demais campos", Toast.LENGTH_SHORT);
            tosta.setGravity(Gravity.CENTER, 0, 0);
            tosta.show();
            usuUsuario.requestFocus();
        } else if (usuNome.getText().toString().length() > 0 && usuUsuario.getText().toString().equals("")
                && mail.getText().toString().length() > 0 && mail2.getText().toString().length() > 0) {
            Toast tosta = Toast.makeText(Cadastro1.this, "Escreva seu usuário", Toast.LENGTH_SHORT);
            tosta.setGravity(Gravity.CENTER, 0, 0);
            tosta.show();
            usuUsuario.requestFocus();
        } else if (usuNome.getText().toString().length() > 0 && usuUsuario.getText().toString().trim().length() > 0
                && mail.getText().toString().trim().equals("") && mail2.getText().toString().trim().length() > 0) {
            Toast tosta = Toast.makeText(Cadastro1.this, "Escreva seu e-mail", Toast.LENGTH_SHORT);
            tosta.setGravity(Gravity.CENTER, 0, 0);
            tosta.show();
            mail.requestFocus();
        } else if (usuNome.getText().toString().length() > 0 && usuUsuario.getText().toString().trim().length() > 0
                && mail.getText().toString().trim().equals("") && mail2.getText().toString().trim().equals("")) {
            Toast tosta = Toast.makeText(Cadastro1.this, "Escreva seu e-mail", Toast.LENGTH_SHORT);
            tosta.setGravity(Gravity.CENTER, 0, 0);
            tosta.show();
            mail.requestFocus();
        } else if (usuNome.getText().toString().length() > 0 && usuUsuario.getText().toString().trim().length() > 0
                && mail.getText().toString().trim().length() > 0 && mail2.getText().toString().trim().equals("")) {
            Toast tosta = Toast.makeText(Cadastro1.this, "Escreva novamente seu e-mail", Toast.LENGTH_SHORT);
            tosta.setGravity(Gravity.CENTER, 0, 0);
            tosta.show();
            mail2.requestFocus();
        } else if (usuNome.getText().toString().length() > 0 && usuUsuario.getText().toString().trim().length() > 0 && mail.getText().toString().trim().length() > 0
                && mail2.getText().toString().trim().length() > 0) {
            //Tudo preenchido
            titleBar.setText("Conectado..");
            consultaNomeUsuario();
        }
    } //Verifica se os campos estão preenchidos

    private void igualdade() {
        titleBar.setText("Conectado...");
        DBUsuario sql = new DBUsuario(this);
        final String email2 = mail2.getText().toString();
        List<AdaptersUsuario> usuarioList;

        if (mail.getText().toString().equals(email2)) {
            /*Inicializador retrofit = new Inicializador();
            retrofit.servico().ConsultarEmailUsuario().enqueue(new Callback<List<AdaptersUsuario>>() {
                @Override
                public void onResponse(Call<List<AdaptersUsuario>> call, Response<List<AdaptersUsuario>> response) {
                    assert response.body() != null;
                    for(AdaptersUsuario usuario : response.body()){
                        if(usuario.getEmailUsuario().equals(email2)){
                            String text = "O e-mail " + email2 + " já está sendo utilizado";
                            alert(text);
                        }
                        else if(!usuario.getEmailUsuario().equals(email2)){
                            avancar(); //Alert
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<AdaptersUsuario>> call, Throwable t) {
                    String text = "Erro na consulta";
                    alert(text);
                }
            });*/
            usuarioList = sql.TodosUsuarios();
            if(usuarioList != null){
                for (AdaptersUsuario adapter : usuarioList) {
                    if (adapter.getEmailUsuario().equals(email2)) {
                        String text = "O e-mail " + email2 + " já está sendo utilizado";
                        alert(text);
                    } else {
                        avancar();
                    }
                }
            }
            if(usuarioList != null && usuarioList.size() == 0){
                avancar();
            }
        } else if (!mail.getText().toString().equals(email2)) {
            String text = "Escreva e-mails iguais:" + "\n" + email2 + "\n" + mail.getText().toString();
            alert(text);
            mail.requestFocus();
        }
    } //Igualdade dos e-mails

    public void Dados_cad1() {
        //antes daqui vai para o alert que confima a gravação dos dados
        String nome = usuNome.getText().toString();
        String usuario = usuUsuario.getText().toString();
        String email = mail.getText().toString();

        AdaptersUsuario adaptersUsuario = new AdaptersUsuario();
        adaptersUsuario.setIdUsuario(UUID.randomUUID().toString());
        adaptersUsuario.setNomeUsuario(nome);
        adaptersUsuario.setLoginUsuario(usuario);
        adaptersUsuario.setEmailUsuario(email);

        if (imgByte != null) {
            adaptersUsuario.setFoto(imgByte);
        }

        Intent intent = new Intent(this, Cadastro2.class);
        intent.putExtra("nome", adaptersUsuario);
        startActivity(intent);
        finish();
    } //informações vão para os adaptersUsuario

    public void avancar() {
        titleBar.setText("Tudo pronto");
        Alerta = new AlertDialog.Builder(Cadastro1.this)
                .setTitle("Atenção")
                .setMessage("Ao avançar você não poderá modificar seus dados até o fim do cadastro" + "\n"
                        + "Nome: " + usuNome.getText().toString() + "\n"
                        + "Usuário: " + usuUsuario.getText().toString() + "\n"
                        + "E-mail: " + mail.getText().toString() + "\n"
                        + "Deseja avançar ?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dados_cad1();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        titleBar.setText("Faça parte...");
                        usuUsuario.requestFocus();
                    }
                })
                .setCancelable(false)
                .show();
    } //Avisa que ao anvaçar os dados não podem ser alterados

    private void consultaNomeUsuario() {
        final String loginUsuario = usuUsuario.getText().toString();

        /*Inicializador retrofit = new Inicializador();
        retrofit.servico().ConsultarNomeUsuario().enqueue(new Callback<List<AdaptersUsuario>>() {
            @Override
            public void onResponse(@NotNull Call<List<AdaptersUsuario>> call, @NotNull Response<List<AdaptersUsuario>> response) {
                assert response.body() != null;
                for(AdaptersUsuario login : response.body()){
                    if(login.getLoginUsuario().equals(loginUsuario)){
                        String text = "O usuário " + loginUsuario + " já exite, por favor escolha outro nome";
                        alert(text);
                    }
                    else if(!login.getLoginUsuario().equals(loginUsuario)){
                        igualdade();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<AdaptersUsuario>> call, @NotNull Throwable t) {
                String text = "Erro ao comparar";
                alert(text);
            }
        });*/

        DBUsuario sql = new DBUsuario(this);
        usuarioList = sql.TodosUsuarios();

        if (usuarioList != null) {
            for (AdaptersUsuario adapter : usuarioList) {
                if (adapter.getLoginUsuario().equals(loginUsuario)) {
                    String text = "O usuário " + loginUsuario + " já exite, por favor escolha outro nome";
                    alert(text);
                } else {
                    igualdade();
                }
            }
        }
        if(usuarioList != null && usuarioList.size() == 0){
            igualdade();
        }
        else if(usuarioList == null){
            String mensagem = "Erro";
            alert(mensagem);
        }
    } //Verifica se o nome de usuário já existe

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Codigo_foto && resultCode == RESULT_OK) {
            Bundle extra = data.getExtras();
            assert extra != null;
            Bitmap miniaturaFoto = (Bitmap) extra.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            assert miniaturaFoto != null;
            miniaturaFoto.compress(Bitmap.CompressFormat.JPEG, 95, stream);
            imgByte = stream.toByteArray();
            FotoPerfil.setImageBitmap(miniaturaFoto);
        } //Tratamento foto da camera

        else if (requestCode == Codigo_galeria && resultCode == RESULT_OK) {
            Uri selectImg = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            assert selectImg != null;
            Cursor cursor = getContentResolver().query(selectImg, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bundle extra = data.getExtras();
            assert extra != null;
            Bitmap miniFoto = (Bitmap) extra.get("data");

            FotoPerfil.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            assert miniFoto != null;
            miniFoto.compress(Bitmap.CompressFormat.JPEG, 98, stream);
            imgByte = stream.toByteArray();
        } //Tratamento foto da galeria
    }

    private void alert(String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro1.this);
        builder.setMessage(mensagem);
        Alerta = builder.create();
        Alerta.show();
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro1.this);
        builder.setTitle("Oii");
        builder.setMessage("Deseja realmente sair do cadastro ?");
        builder.setPositiveButton("Sair", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Cadastro1.this, Login.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        Alerta = builder.create();
        Alerta.show();
        this.finish();
    }
}