package com.example.contactlistproj;

public class Contato {
    private final String nome;
    private final String numero;
    private final String email;
    // private final String birth;

    public Contato(String nome, String numero, String email) {
        this.nome = nome;
        this.numero = numero;
        this.email = email;
        // this.birth=birth;
    }


    public String getNome() {
        return nome;
    }

    public String getNumero() {
        return numero;
    }

    public String getEmail() {
        return email;
    }
}


