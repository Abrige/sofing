package it.unicam.cs.progettosofing.model.entity;

import it.unicam.cs.progettosofing.model.AccessoMarketplaceUtenteRegistrato;

public class UtenteRegistrato implements AccessoMarketplaceUtenteRegistrato {
    private String id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    public String indirizzo;

    public UtenteRegistrato(String id, String nome, String cognome, String email, String password, String indirizzo) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.indirizzo = indirizzo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
}
