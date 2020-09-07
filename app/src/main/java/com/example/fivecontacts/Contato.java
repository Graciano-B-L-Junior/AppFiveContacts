package com.example.fivecontacts;

import java.io.Serializable;

public class Contato implements Serializable {
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String nome;
    public String numero;
}

