package com.TriNote.Usuario;

import java.io.Serializable;

public class AdapterUsuario implements Serializable {
    String idUsuario;
    String nomeUsuario;
    String loginUsuario;
    String emailUsuario;
    String telefoneUsuario;
    byte[] fotoUsuario;
    byte[] capaUsuario;

    public String getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }
    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }
    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getTelefoneUsuario() {
        return telefoneUsuario;
    }
    public void setTelefoneUsuario(String telefoneUsuario) {
        this.telefoneUsuario = telefoneUsuario;
    }

    public byte[] getFotoUsuario() {
        return fotoUsuario;
    }
    public void setFotoUsuario(byte[] fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }

    public byte[] getCapaUsuario() {
        return capaUsuario;
    }
    public void setCapaUsuario(byte[] capaUsuario) {
        this.capaUsuario = capaUsuario;
    }
}
