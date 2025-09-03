package it.unicam.cs.progettosofing.model.entity;

public class UtenteRegistratoDTO {
    private final String id;
    private final String nome;
    private final String cognome;
    private final String email;
    private final String password;
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
