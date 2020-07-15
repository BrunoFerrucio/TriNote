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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TriNote.Adapters.AdapterAnotacoes;
import com.TriNote.BaseAdapters.BaseAdapterGridAnotacoes;
import com.TriNote.Databases.DataBaseNotas;
import com.TriNote.Usuario.AdapterUsuario;
import com.TriNote.Usuario.DBDadoSessao;
import com.TriNote.Usuario.Dados;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class Anotacoes extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    GridView gridView;
    FloatingActionButton fab;
    List<AdapterAnotacoes> anotacoesList;
    List<AdapterUsuario> usuarioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anotacoes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Anotações");

        sessaoBanco();
        carregaClasse();

        drawer = findViewById(R.id.drawer_layout); //layout geral
        NavigationView navigationView = findViewById(R.id.nav_view); //menu lateral

        View headerView = navigationView.getHeaderView(0);
        TextView headerNome = headerView.findViewById(R.id.navNome);
        TextView headerEmail = headerView.findViewById(R.id.navEmail);
        ImageView headerImg = headerView.findViewById(R.id.navPerf);
        LinearLayout layout = headerView.findViewById(R.id.LinearLayout);
        headerNome.setText(Dados.getDadoNome());
        headerEmail.setText(Dados.getDadoEmail());
        if (Dados.getDadoFoto() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(Dados.getDadoFoto(), 0, Dados.getDadoFoto().length);
            headerImg.setImageBitmap(bitmap);
        }
        if (Dados.getDadoCapa() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(Dados.getDadoCapa(), 0, Dados.getDadoCapa().length);
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            layout.setBackground(drawable);
        }

        navigationView.setNavigationItemSelectedListener(Anotacoes.this); //Seleciona item do menu

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        fab = findViewById(R.id.fab);

        gridView = findViewById(R.id.GridView);

        atualizaAnotacoes();

        gridView.setVerticalSpacing(10);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AdapterAnotacoes anotacoes = new AdapterAnotacoes();

                String idnota = ((TextView) view.findViewById(R.id.IDNotas)).getText().toString();
                String titulo = ((TextView) view.findViewById(R.id.NotaTitulo)).getText().toString();
                String conteudo = ((TextView) view.findViewById(R.id.NotaCorpo)).getText().toString();
                String fonte = ((TextView) view.findViewById(R.id.TemaNotas)).getText().toString();
                ImageView imagem = view.findViewById(R.id.NotaImagem);

                if (imagem.getDrawable() != null) {
                    Drawable drawable = imagem.getDrawable();
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                    byte[] imgByte = stream.toByteArray();
                    anotacoes.setImagem(imgByte);
                }

                anotacoes.setIdAnotacao(idnota);
                anotacoes.setTitulo(titulo);
                anotacoes.setConteudo(conteudo);
                anotacoes.setFont(fonte);

                Intent intent = new Intent(Anotacoes.this, CriarAnotacoes.class);
                intent.putExtra("anotacao", anotacoes);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Anotacoes.this, CriarAnotacoes.class);
                startActivity(intent);
            }
        });
    }

    private void sessaoBanco() {
        DBDadoSessao sql = new DBDadoSessao(this);
        usuarioList = sql.RetornaDados();
    }

    private void atualizaAnotacoes() {
       /* SincroniaAnotacoes sincroniaAnotacoes = new SincroniaAnotacoes(this);
        sincroniaAnotacoes.SincronizarAnotacoes();*/

        DataBaseNotas notas = new DataBaseNotas(this);
        anotacoesList = notas.CarregaAnotacoes();
        BaseAdapterGridAnotacoes adapter = new BaseAdapterGridAnotacoes(Anotacoes.this, anotacoesList);
        gridView.setAdapter(adapter);

        /*Inicializador retrofit = new Inicializador();
        retrofit.servico().Anotacoes().enqueue(new Callback<List<AdapterAnotacoes>>() {
            @Override
            public void onResponse(Call<List<AdapterAnotacoes>> call, Response<List<AdapterAnotacoes>> response) {
                anotacoesList = response.body();
                if(anotacoesList == null){
                    Toast.makeText(Anotacoes.this, response.toString(), Toast.LENGTH_SHORT).show();
                }
                else{
                    for(AdapterAnotacoes adapterNotas : anotacoesList){
                        if(adapterNotas.getUsuarioCria() == Dados.getDadoId()){
                            BaseAdapterGridAnotacoes adapter = new BaseAdapterGridAnotacoes(Anotacoes.this, anotacoesList);
                            gridView.setAdapter(adapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AdapterAnotacoes>> call, Throwable t) {
                AlertDialog alert;
                AlertDialog.Builder builder = new AlertDialog.Builder(Anotacoes.this);
                builder.setMessage(t+"");
                alert = builder.create();
                alert.show();
            }
        });*/
    } //Usa classe de sincronia para puxar notas online ou offline

    private void carregaClasse() {
        for (AdapterUsuario usuario : usuarioList) {
            Dados.setDadoId(usuario.getIdUsuario());
            Dados.setDadoNome(usuario.getNomeUsuario());
            Dados.setDadoLogin(usuario.getLoginUsuario());
            Dados.setDadoEmail(usuario.getEmailUsuario());
            Dados.setDadoCapa(usuario.getCapaUsuario());
            Dados.setDadoTelefone(usuario.getTelefoneUsuario());
            Dados.setDadoFoto(usuario.getFotoUsuario());
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_anotacoes, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.MenuNotasSincro) {
            View layout = findViewById(android.R.id.content);
            Snackbar.make(layout, "Anotações sincronizadas", Snackbar.LENGTH_LONG).show();
            atualizaAnotacoes();
            /*SincroniaAnotacoes sincroniaAnotacoes = new SincroniaAnotacoes(this);
            sincroniaAnotacoes.SincronizarAnotacoes();*/
        }
        return true;
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navPerfil) {
            Intent intent = new Intent(this, Perfil.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.navHome) {
            /*Intent intent = new Intent(this, AdapterAnotacoes.class);
            startActivity(intent);*/
        } else if (id == R.id.navAmigos) {
            Intent intent = new Intent(this, Amizades.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.navAssina) {
            Intent intent = new Intent(this, StatusAssinatura.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.navFale) {
            String site = "http://www.trinote.somee.com";
            Uri uri = Uri.parse(site);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else if (id == R.id.navLogoff) {
            Logoff();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    } //ação para cada item selecionado

    private void Logoff() {
        AlertDialog alerta;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja realmente sair da sua conta " + Dados.getDadoNome() + " ?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast tost = Toast.makeText(Anotacoes.this, "Até a proxima " + Dados.getDadoLogin(), Toast.LENGTH_SHORT);
                tost.setGravity(Gravity.CENTER, 0, 0);
                Intent intent = new Intent(Anotacoes.this, Login.class);
                tost.show();

                Dados.setDadoId(null);
                Dados.setDadoTelefone(null);
                Dados.setDadoEmail(null);
                Dados.setDadoCapa(null);
                Dados.setDadoFoto(null);
                Dados.setDadoLogin(null);
                Dados.setDadoNome(null);

                DBDadoSessao sql = new DBDadoSessao(Anotacoes.this);
                sql.ExcluirDados();

                DataBaseNotas notas = new DataBaseNotas(Anotacoes.this);
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

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    } //Controla ação do botão de voltar

    protected void onRestart() {
        atualizaAnotacoes();
        sessaoBanco();
        carregaClasse();
        super.onRestart();
    } //Após o onPause e onStop
}