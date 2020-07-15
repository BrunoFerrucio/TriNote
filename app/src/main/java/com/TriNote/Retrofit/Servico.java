package com.TriNote.Retrofit;

import com.TriNote.Adapters.AdapterAnotacoes;
import com.TriNote.Adapters.AdaptersUsuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Servico {
    @GET ("Usuarios")
    Call<List<AdaptersUsuario>> LoginUsuario(); //Puxa todos usuários e filtra na hora de usar
    @POST("Usuarios")
    Call<Void> CadastrarUsuario(@Body AdaptersUsuario adaptersUsuario); //Cadastra Usuário
    @GET("Usuarios")
    Call<List<AdaptersUsuario>> ConsultarNomeUsuario(); //Consulta se o nome de usuario já existe para ser unico
    @GET("Usuarios")
    Call<List<AdaptersUsuario>> ConsultarEmailUsuario();
    @GET("Usuarios")
    Call<List<AdaptersUsuario>> OutrosUsuario(); //procura todos usuarios para filtrar pelo nome escrito
    @DELETE("Usuarios/{id}")
    Call<Void> RemoverUsuario(@Path("id") int id); //Apaga dados usuários

    @POST("Anotacoes/{id}")
    Call<Void> CriarAnotacao(@Path("id") int id, @Body AdapterAnotacoes notas); //Cria anotação
    @GET("Anotacoes")
    Call<List<AdapterAnotacoes>> Anotacoes(); //Exibi as anotações
    @PUT("Anotacoes/{id}")
    Call<List<AdapterAnotacoes>> AtualizaNotas(@Path("id") int id, @Body AdapterAnotacoes anotacoes); //Atualiza anotações já salvas
    @DELETE("Anotacoes/{id}")
    Call<Void> ExcluirAnotacao(@Path("id") int id); //Apaga anotação

    @GET("Amigos/{id}")
    Call<List<AdaptersUsuario>> Amizades(@Path("usuario") int idUsuario); //Vê amigos do usuário logado
    @POST("Amigos/{id}")
    Call<Void> adicionarAmigo(@Path("id") int idAmigo); //Adiciona amigo
    @DELETE("Amigos/{id}")
    Call<Void> removerAmizade(@Path("id") int id); //Remove amigo
}
//Usuario = Usuario logado
//Amigo = Usuario que é amigo do usuário logado
//Usuarios = Usuários que não é amigo do logado e nem é o logado