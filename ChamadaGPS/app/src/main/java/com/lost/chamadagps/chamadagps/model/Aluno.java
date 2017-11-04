package com.lost.chamadagps.chamadagps.model;

/**
 * Created by gustavo on 02/11/2017.
 */

public class Aluno {

    private String key;
    private String nome;
    private double latitude;
    private double longitude;

    public Aluno(){

    }

    public Aluno(String nome, double latitude, double longitude) {
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Aluno(String key, String nome, double latitude, double longitude) {
        this.key = key;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
