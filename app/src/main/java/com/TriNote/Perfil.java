package com.TriNote;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.TriNote.Databases.DataBaseNotas;
import com.TriNote.Usuario.DBDadoSessao;
import com.TriNote.Usuario.Dados;
import com.google.android.material.navigation.NavigationView;


public class Perfil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    AlertDialog alerta;
    TextView UsuarioFrase, Usuario, Fone, Email;
    ImageView Foto;
    Button btnLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dados de " + Dados.getDadoLogin());

        drawer = findViewById(R.id.drawer_layout); //layout geral
        NavigationView navigationView = findViewById(R.id.nav_view); //menu lateral

        View headerView = navigationView.getHeaderView(0);
        TextView headerNome = headerView.findViewById(R.id.navNome);
        TextView headerEmail = headerView.findViewById(R.id.navEmail);
        ImageView headerImg = headerView.findViewById(R.id.navPerf);
        headerNome.setText(Dados.getDadoNome());
        headerEmail.setText(Dados.getDadoEmail());
        if(Dados.getDadoFoto() != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(Dados.getDadoFoto(), 0, Dados.getDadoFoto().length);
            headerImg.setImageBitmap(bitmap);
        }

        navigationView.setNavigationItemSelectedListener(Perfil.this); //Seleciona item do menu

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Foto = findViewById(R.id.PerfilImgFoto);
        UsuarioFrase = findViewById(R.id.PerfilTxtUsuarioFrase);
        Usuario = findViewById(R.id.PerfilTxtUsuario);
        Fone = findViewById(R.id.PerfilTxtFone);
        Email = findViewById(R.id.PerfilTxtEmail);
        btnLink = findViewById(R.id.PerfilBtnLink);

        if(Dados.getDadoFoto() != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(Dados.getDadoFoto(), 0, Dados.getDadoFoto().length);
            Foto.setImageBitmap(bitmap);
        }

        Usuario.setText(Dados.getDadoLogin());
        Fone.setText(Dados.getDadoTelefone());
        Email.setText(Dados.getDadoEmail());

        UsuarioFrase.setText("Olá "+ Usuario.getText().toString() +", aqui podemos ver algumas informações basicas sobre o seu perfil.");

        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trinote = "http://www.trinote.somee.com";
                Uri siteUrl = Uri.parse(trinote);
                Intent intent = new Intent(Intent.ACTION_VIEW, siteUrl);
                startActivity(intent);

            }
        });
    }
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.navPerfil){
            /*Intent intent = new Intent(this, Perfil.class);
            startActivity(intent);*/
        }
        else if(id == R.id.navHome){
            Intent intent = new Intent(this, Anotacoes.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.navAmigos){
            Intent intent = new Intent(this, Amizades.class);
            startActivity(intent);
            finish();
        }
        /*else if(id == R.id.navDuvi){
            Intent intent = new Intent(this, DuvidasFrequentes.class);
            startActivity(intent);
        }*/
        else if(id == R.id.navAssina){
            Intent intent = new Intent(this, StatusAssinatura.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.navFale){
            String site = "http://www.trinote.somee.com";
            Uri uri = Uri.parse(site);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        else if(id == R.id.navLogoff){
            Logoff();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    } //ação para cada item selecionado
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    } //Controla ação do botão de voltar
    public void Logoff(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja realmente sair da sua conta " + Dados.getDadoNome() + " ?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast tost = Toast.makeText(Perfil.this, "Até a proxima " + Dados.getDadoLogin(), Toast.LENGTH_SHORT);
                tost.setGravity(Gravity.CENTER, 0,0);
                Intent intent = new Intent(Perfil.this, Login.class);
                tost.show();

                Dados.setDadoId(null);
                Dados.setDadoTelefone(null);
                Dados.setDadoEmail(null);
                Dados.setDadoCapa(null);
                Dados.setDadoFoto(null);
                Dados.setDadoLogin(null);
                Dados.setDadoNome(null);

                DBDadoSessao sql = new DBDadoSessao(Perfil.this);
                sql.ExcluirDados();

                DataBaseNotas notas = new DataBaseNotas(Perfil.this);
                notas.ApagaTudo();

                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alerta = builder.create();
        alerta.show();
    } //Alert confirma logoff
}