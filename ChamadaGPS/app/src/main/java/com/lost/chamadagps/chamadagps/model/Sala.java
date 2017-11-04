package com.lost.chamadagps.chamadagps.model;

/**
 * Created by gustavo on 24/10/2017.
 */

public class Sala {

    private String colegio;
    private String nome;
    private double latitude;
    private double longitude;
    private int raio;
    private String key;
    private String prof;


    public Sala() {
    }

    public Sala(String colegio, String nome, double latitude, double longitude) {
        this.colegio = colegio;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Sala(String colegio, String nome, double latitude, double longitude, int raio, String key) {
        this.colegio = colegio;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
        this.raio = raio;
        this.key = key;
    }

    public Sala(String colegio, String nome, double latitude, double longitude, int raio, String key, String prof) {
        this.colegio = colegio;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
        this.raio = raio;
        this.key = key;
        this.prof = prof;
    }

    public Sala(String colegio, String nome) {
        this.colegio = colegio;
        this.nome = nome;
    }

    public Sala(String colegio, String nome, double latitude, double longitude, int raio) {
        this.colegio = colegio;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
        this.raio = raio;
    }

    public String getColegio() {
        return colegio;
    }

    public void setColegio(String colegio) {
        this.colegio = colegio;
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

    public int getRaio() {
        return raio;
    }

    public void setRaio(int raio) {
        this.raio = raio;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }
}
