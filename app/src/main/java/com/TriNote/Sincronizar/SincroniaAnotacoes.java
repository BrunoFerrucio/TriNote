/*
package com.TriNote.Sincronizar;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.TriNote.Adapters.AdapterAnotacoes;
import com.TriNote.Databases.DataBaseNotas;
import com.TriNote.Retrofit.Inicializador;
import com.TriNote.Usuario.Dados;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SincroniaAnotacoes {
    public static boolean sucessoCadastrar = true;
    public static boolean erro = false;
    Context context;
    List<AdapterAnotacoes> anotacoesOnline;
    List<AdapterAnotacoes> anotacoesOffline;

    public SincroniaAnotacoes(Context context) {
        this.context = context;
    }

    public void SincronizarAnotacoes() {
        Inicializador retrofit = new Inicializador();
        retrofit.servico().Anotacoes(Dados.getDadoId()).enqueue(new Callback<List<AdapterAnotacoes>>() {
            @Override
            public void onResponse(Call<List<AdapterAnotacoes>> call, Response<List<AdapterAnotacoes>> response) {
                anotacoesOnline = response.body();

                DataBaseNotas sql = new DataBaseNotas(context);
                anotacoesOffline = sql.CarregaAnotacoes();

                CarregaAnotacoesOnline();
                CarregaAnotacoesOffline();

                AtualizaAnotacoesOnline();
                AtualizaAnotacoesOffline();
            }

            @Override
            public void onFailure(Call<List<AdapterAnotacoes>> call, Throwable t) {
                Toast.makeText(context, "Não foi possivel se conectar a rede", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AtualizaAnotacoesOnline() {
        List<AdapterAnotacoes> anotacoesList = new ArrayList<>();

        for (AdapterAnotacoes notasOf : anotacoesOffline) {
            boolean atualizar = false;

            for (AdapterAnotacoes notasOn : anotacoesOnline) {
                if (notasOn.getIdAnotacao() == notasOf.getIdAnotacao()) {
                    if (!notasOf.getDataHotaCriacao().equals(notasOn.getDataHotaAlteracao()) ||
                            !notasOf.getDataHotaAlteracao().equals("")) {
                        atualizar = true;
                        anotacoesList.add(notasOn);
                    }
                }
            }
        }
        DataBaseNotas sql = new DataBaseNotas(context);
        for (AdapterAnotacoes notasAtualizar : anotacoesList) {
            sql.AtualizaAnotacao(notasAtualizar);
        }
    }

    private void AtualizaAnotacoesOffline() {
        List<AdapterAnotacoes> anotacoesList = new ArrayList<>();

        for (AdapterAnotacoes notasOn : anotacoesOnline) {
            boolean atualizar = false;

            for (AdapterAnotacoes notasOf : anotacoesOffline) {
                if (notasOf.getDataHotaCriacao() != notasOf.getDataHotaAlteracao()) {
                    anotacoesList.add(notasOf);
                }
            }
        }
        Inicializador retrofit = new Inicializador();
        for (AdapterAnotacoes notasAtualizar : anotacoesList) {
            retrofit.servico().AtualizaNotas(notasAtualizar.getIdAnotacao(), notasAtualizar).enqueue(new Callback<List<AdapterAnotacoes>>() {
                @Override
                public void onResponse(Call<List<AdapterAnotacoes>> call, Response<List<AdapterAnotacoes>> response) {
                    //Item não foi atualizado
                }

                @Override
                public void onFailure(Call<List<AdapterAnotacoes>> call, Throwable t) {
                    erro = true;
                    Log.d("Erro ao atualizar", t.toString());
                }
            });
        }
        if (erro) {
            Toast.makeText(context, "Erro ao sincronizar", Toast.LENGTH_SHORT).show();
        }
    }

    private void CarregaAnotacoesOffline() {
        List<AdapterAnotacoes> anotacoesList = new ArrayList<>();

        for (AdapterAnotacoes notasOff : anotacoesOffline) {
            boolean encontrado = false;

            for (AdapterAnotacoes notasOn : anotacoesOnline) {
                if (notasOn.getIdAnotacao() == notasOff.getIdAnotacao()) {
                    encontrado = true;
                }
            }
            if (encontrado == false) {
                anotacoesList.add(notasOff);
            }
        }
        Inicializador retrofit = new Inicializador();
        for (AdapterAnotacoes notasNovas : anotacoesList) {
            retrofit.servico().CriarAnotacao(notasNovas, Dados.getDadoId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    sucessoCadastrar = false;
                }
            });
        }
        if (sucessoCadastrar == false) {
            Toast.makeText(context, "Falha ao carregar anotações na nuvem", Toast.LENGTH_SHORT).show();
        }
    } //Salva anotações offline na nuvem

    private void CarregaAnotacoesOnline() {
        List<AdapterAnotacoes> anotacoesList = new ArrayList<>();

        for (AdapterAnotacoes anotacaoOn : anotacoesOnline) {
            boolean encontrado = false;

            for (AdapterAnotacoes anotacoesOf : anotacoesOffline) {
                if (anotacaoOn.getIdAnotacao() == anotacoesOf.getIdAnotacao()) {
                    encontrado = true;
                }
            }
            if (encontrado == false) {
                anotacoesList.add(anotacaoOn);
            } //Não foi encontrado nenhum igual offline

            DataBaseNotas sql = new DataBaseNotas(context);
            for (AdapterAnotacoes notasNovas : anotacoesList) {
                sql.CriarAnotacao(notasNovas);
            }
        }
    } //Salva anotações online no banco do smartphone
}*/
