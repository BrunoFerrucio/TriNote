package com.TriNote.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Inicializador {
    Retrofit retrofit;

    public Inicializador(){
        retrofit = new Retrofit.Builder()
                //.baseUrl("http://trinote.somee.com/Api/")
                .baseUrl("https://wepapitri.azurewebsites.net/Api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public Servico servico(){
        return retrofit.create(Servico.class);
    }
}