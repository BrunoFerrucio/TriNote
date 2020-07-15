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
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.TriNote.Adapters.AdaptersUsuario;
import com.TriNote.BaseAdapters.BaseAdapterListAmigos;
import com.TriNote.Databases.DBUsuario;
import com.TriNote.Databases.DataBaseNotas;
import com.TriNote.Retrofit.Inicializador;
import com.TriNote.Usuario.DBDadoSessao;
import com.TriNote.Usuario.Dados;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class Amizades extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    AlertDialog alerta;
    Boolean amigos = false;
    EditText nome;
    ImageButton procura;
    Button btnMisto;
    TextView txtSemMigos;
    ListView lstAmigos;
    List<AdaptersUsuario> usuariosAdapter;
    DBUsuario sql = new DBUsuario(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amizades);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Amizades");

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

        navigationView.setNavigationItemSelectedListener(this); //Seleciona item do menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        nome = findViewById(R.id.edtAmigoNome);
        procura = findViewById(R.id.btnProcAmigo);
        txtSemMigos = findViewById(R.id.txtSemAmigos);
        lstAmigos = findViewById(R.id.ListView);

        btnMisto = findViewById(R.id.btnAmigo);

        carregaAmigos();

        procura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procurarUsuarios();
            }
        });
    }

    private void carregaAmigos() {
        /*Inicializador retrofit = new Inicializador();
        retrofit.servico().Amizades(Dados.getDadoId()).enqueue(new Callback<List<AdaptersUsuario>>() {
            @Override
            public void onResponse(Call<List<AdaptersUsuario>> call, Response<List<AdaptersUsuario>> response) {
                usuariosAdapter = response.body();
                BaseAdapterListAmigos adapterList = new BaseAdapterListAmigos(Amizades.this, usuariosAdapter);
                lstAmigos.setAdapter(adapterList);

                amigos = true;
            } //Se tiver amigos

            @Override
            public void onFailure(Call<List<AdaptersUsuario>> call, Throwable t) {
                lstAmigos.setVisibility(View.INVISIBLE);
                txtSemMigos.setVisibility(View.VISIBLE);
            } //Sem amigos
        });*/
        usuariosAdapter = sql.ProcuraUsuarios();
        for (AdaptersUsuario adapters : usuariosAdapter) {
            if (usuariosAdapter.size() == 0) {
                txtSemMigos.setVisibility(View.VISIBLE);
            } else {
                BaseAdapterListAmigos listAmigos = new BaseAdapterListAmigos(Amizades.this, usuariosAdapter);
                lstAmigos.setAdapter(listAmigos);
            }
        }
    }

    private void procurarUsuarios() {
        txtSemMigos.setVisibility(View.INVISIBLE);
        String amigo = nome.getText().toString();

        if (amigo.equals("")) {
            Toast toast = Toast.makeText(Amizades.this, "Digite um nome", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            /*Inicializador retrofit = new Inicializador();
            retrofit.servico().OutrosUsuario().enqueue(new Callback<List<AdaptersUsuario>>() {
                @Override
                public void onResponse(Call<List<AdaptersUsuario>> call, Response<List<AdaptersUsuario>> response) {
                    lstAmigos.setVisibility(View.VISIBLE);
                    txtSemMigos.setVisibility(View.INVISIBLE);

                    usuariosAdapter = response.body();
                    BaseAdapterListAmigos adapterList = new BaseAdapterListAmigos(Amizades.this, usuariosAdapter);
                    lstAmigos.setAdapter(adapterList);
                } //A usuários com esse nome

                @Override
                public void onFailure(Call<List<AdaptersUsuario>> call, Throwable t) {
                    txtSemMigos.setText("Não foi possivel, achar amigos com esse nome");
                }//Não há usuários com nome pesquisado
            });*/
            usuariosAdapter = sql.ProcuraUsuarios();
            for (AdaptersUsuario adapter : usuariosAdapter) {
                if (adapter.getLoginUsuario().equals(amigo)) {
                    BaseAdapterListAmigos BaseAdap = new BaseAdapterListAmigos(Amizades.this, usuariosAdapter);
                    lstAmigos.setAdapter(BaseAdap);
                    txtSemMigos.setVisibility(View.INVISIBLE);
                } else {
                    txtSemMigos.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navPerfil) {
            Intent intent = new Intent(this, Perfil.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.navHome) {
            Intent intent = new Intent(this, Anotacoes.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.navAmigos) {
            /*Intent intent = new Intent(this, Amizades.class);
            startActivity(intent);
            finish();*/
        } /*else if (id == R.id.navDuvi) {
            Intent intent = new Intent(this, DuvidasFrequentes.class);
            startActivity(intent);
        }*/ else if (id == R.id.navAssina) {
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

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_amigos, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final AdaptersUsuario adapter = usuariosAdapter.get(info.position);

        if (item.getItemId() == R.id.menuAmigoRemover) {
            removerAmigo(adapter);
        } else if (item.getItemId() == R.id.menuAmigoAdc) {
            adicionarAmigo(adapter);
        }
        return true;
    }

    private void adicionarAmigo(final AdaptersUsuario adapter) {
        Inicializador retrofit = new Inicializador();
        if (amigos == false) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Amizades.this);
            builder.setMessage("Deseja adicionar " + adapter.getNomeUsuario() + " como seu amigo ?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    /*retrofit.servico().adicionarAmigo(adapter.getIdUsuario()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast toast = Toast.makeText(Amizades.this, "Você e " + adapter.getLoginUsuario() + " são amigos agora", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0,0);
                            toast.show();
                        }//Amigo adicionado

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast toast = Toast.makeText(Amizades.this, "Falha ao adicionar " + adapter.getLoginUsuario() + " aos seus amigos", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0,0);
                            toast.show();
                        } //Falha ao adicionar
                    });*/

                }
            });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alerta = builder.create();
            alerta.show();
        } else if (amigos == true) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Amizades.this);
            builder.setMessage("Vocês já são amigos");
            alerta = builder.create();
            alerta.show();
        }
    }

    private void removerAmigo(final AdaptersUsuario adapter) {
        if (amigos == true) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Amizades.this);
            builder.setMessage("Seu amigo será removido, deseja continuar ?");
            builder.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    /*Inicializador retrofit = new Inicializador();
                    retrofit.servico().removerAmizade(adapter.getIdUsuario()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast toast = Toast.makeText(Amizades.this, "Vocês não são mais amigos", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0,0);
                            toast.show();
                        }//Perderam amizade

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast toast = Toast.makeText(Amizades.this, "Falha ao remover amizade", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0,0);
                            toast.show();
                        } //continuam amigos
                    });*/

                }
            });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alerta = builder.create();
            alerta.show();
        } else if (amigos == false) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Amizades.this);
            builder.setMessage("Vocês não são amigos para remover");
            alerta = builder.create();
            alerta.show();
        }
    }

    private void Logoff() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja realmente sair da sua conta " + Dados.getDadoNome() + " ?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast tost = Toast.makeText(Amizades.this, "Até a proxima " + Dados.getDadoLogin(), Toast.LENGTH_SHORT);
                tost.setGravity(Gravity.CENTER, 0, 0);
                Intent intent = new Intent(Amizades.this, Login.class);
                tost.show();

                Dados.setDadoId(null);
                Dados.setDadoTelefone(null);
                Dados.setDadoEmail(null);
                Dados.setDadoCapa(null);
                Dados.setDadoFoto(null);
                Dados.setDadoLogin(null);
                Dados.setDadoNome(null);

                DBDadoSessao sql = new DBDadoSessao(Amizades.this);
                sql.ExcluirDados();

                DataBaseNotas notas = new DataBaseNotas(Amizades.this);
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
        carregaAmigos();
        super.onRestart();
    }
}