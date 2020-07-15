package com.TriNote.Adapters;

import java.io.Serializable;

public class AdapterAnotacoes implements Serializable {
    String idAnotacao;
    String usuarioCria;
    String usuarioAltera;
    String titulo;
    String conteudo;
    String Font;
    byte[] Imagem;

    public String getIdAnotacao() {
        return idAnotacao;
    }
    public void setIdAnotacao(String idAnotacao) {
        this.idAnotacao = idAnotacao;
    }

    public String getUsuarioCria() {
        return usuarioCria;
    }
    public void setUsuarioCria(String usuarioCria) {
        this.usuarioCria = usuarioCria;
    }

    public String getUsuarioAltera() {
        return usuarioAltera;
    }
    public void setUsuarioAltera(String usuarioAltera) {
        this.usuarioAltera = usuarioAltera;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }
    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getFont() {
        return Font;
    }
    public void setFont(String font) {
        Font = font;
    }

    public byte[] getImagem() {
        return Imagem;
    }
    public void setImagem(byte[] Imagem) {
        this.Imagem = Imagem;
    }
}