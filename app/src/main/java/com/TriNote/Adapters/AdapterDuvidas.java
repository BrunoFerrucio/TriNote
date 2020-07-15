package com.TriNote.Adapters;

import java.io.Serializable;

public class AdapterDuvidas implements Serializable {
    String DuvidaTitulo;
    String DuvidaConteudo;

    public String getDuvidaTitulo() {
        return DuvidaTitulo;
    }
    public void setDuvidaTitulo(String duvidaTitulo) {
        DuvidaTitulo = duvidaTitulo;
    }

    public String getDuvidaConteudo() {
        return DuvidaConteudo;
    }
    public void setDuvidaConteudo(String duvidaConteudo) {
        DuvidaConteudo = duvidaConteudo;
    }
}