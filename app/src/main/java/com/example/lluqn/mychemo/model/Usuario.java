package com.example.lluqn.mychemo.model;

public class Usuario {

    private String email;
    private String senha;

    private String image_perfil;
    private String image_fundo;

    private String nome;
    private String dataNasc;
    private String sexo;
    private double peso;
    private double altura;

    public Usuario() {
    }

    public Usuario(String email, String senha, String image, String image_fundo,
                   String nome, String dataNasc, String sexo, double peso, double altura) {

        this.email = email;
        this.senha = senha;
        this.image_perfil = image;
        this.image_fundo = image_fundo;
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.sexo = sexo;
        this.peso = peso;
        this.altura = altura;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getImage() {
        return image_perfil;
    }

    public void setImage(String image_perfil) {
        this.image_perfil = image_perfil;
    }

    public String getImage_fundo() {
        return image_fundo;
    }

    public void setImage_fundo(String image_fundo) {
        this.image_fundo = image_fundo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }
}