package it.unicam.cs.progettosofing.model.entity;

public class UtenteRegistratoDTO {
    private String id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    public String indirizzo;

    public UtenteRegistratoDTO(String id, String nome, String cognome, String email, String password, String indirizzo) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.indirizzo = indirizzo;
    }
}
