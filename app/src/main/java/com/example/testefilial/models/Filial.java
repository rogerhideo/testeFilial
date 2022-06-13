package com.example.testefilial.models;

public class Filial
{
    private int id;
    private String nome;
    private String cidade;
    private String latitude;
    private String longitude;

    public Filial( int id, String nome, String cidade, String latitude, String longitude ) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId( ) {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome( ) {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade( ) {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    public String getLatitude( ) {
        return latitude;
    }
    public void setLatitude( String latitude ) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }
    public void setLongitude( String longitude ) {
        this.longitude = longitude;
    }
}