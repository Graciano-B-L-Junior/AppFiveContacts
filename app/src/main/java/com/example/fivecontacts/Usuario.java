package com.example.fivecontacts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Usuario implements Serializable {
    public String nome;
    public String login;
    public String senha;
    public String email;
    public int QtdContatos;
    public boolean logarDireto;
    public HashSet<Contato> contato = new HashSet<Contato>();
    public Usuario(String nome, String login, String password, String email) {
        this.nome = nome;
        this.login=login;
        this.senha=password;
        this.email=email;
        QtdContatos = 0;
    }
}
