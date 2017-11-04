package com.lost.chamadagps.chamadagps.model;

/**
 * Created by gustavo on 26/10/2017.
 */

public class User {
    private String nome;

    public User(String nome) {
        this.nome = nome;
    }

    public User() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
