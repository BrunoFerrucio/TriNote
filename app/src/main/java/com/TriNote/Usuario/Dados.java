package com.TriNote.Usuario;

public class Dados {
    private static String DadoId;
    private static String DadoNome;
    private static String DadoEmail;
    private static String DadoTelefone;
    private static String DadoLogin;
    private static byte[] DadoFoto;
    private static String DadoDescricao;
    private static byte[] DadoCapa;

    public static String getDadoId() {
        return DadoId;
    }
    public static void setDadoId(String dadoId) {
        DadoId = dadoId;
    }

    public static String getDadoNome() {
        return DadoNome;
    }
    public static void setDadoNome(String dadoNome) {
        DadoNome = dadoNome;
    }

    public static String getDadoEmail() {
        return DadoEmail;
    }
    public static void setDadoEmail(String dadoEmail) {
        DadoEmail = dadoEmail;
    }

    public static String getDadoTelefone() {
        return DadoTelefone;
    }
    public static void setDadoTelefone(String dadoTelefone) {
        DadoTelefone = dadoTelefone;
    }

    public static String getDadoLogin() {
        return DadoLogin;
    }
    public static void setDadoLogin(String dadoLogin) {
        DadoLogin = dadoLogin;
    }

    public static byte[] getDadoFoto() {
        return DadoFoto;
    }
    public static void setDadoFoto(byte[] dadoFoto) {
        DadoFoto = dadoFoto;
    }

    public static String getDadoDescricao() {
        return DadoDescricao;
    }
    public static void setDadoDescricao(String dadoDescricao) {
        DadoDescricao = dadoDescricao;
    }

    public static byte[] getDadoCapa() {
        return DadoCapa;
    }
    public static void setDadoCapa(byte[] dadoCapa) {
        DadoCapa = dadoCapa;
    }
}