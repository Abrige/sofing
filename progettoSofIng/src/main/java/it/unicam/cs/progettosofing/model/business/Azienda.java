package it.unicam.cs.progettosofing.model.business;

import it.unicam.cs.progettosofing.controller.ControllerSistemaProdotti;

/**
 * Classe astratta che rappresenta una generica azienda all'interno del sistema.
 * Ogni azienda ha un identificativo univoco, un nome e un riferimento al controller
 * che gestisce il sistema di prodotti.
 * <p>
 * Questa classe funge da base per specifiche tipologie di aziende che operano
 * nel sistema, fornendo attributi comuni come l'ID e il nome.
 * </p>
 *
 * @author lucaporfiri, brizimattia, leonardoalessandroni
 *
 * @see Trasformatore
 * @see Produttore
 */
public abstract class Azienda {
    private String ID;
    private String nome;
    private ControllerSistemaProdotti controllerSistemaProdotti;

    public Azienda(String ID, String nome, ControllerSistemaProdotti controller){
        this.ID = ID;
        this.nome = nome;
        this.controllerSistemaProdotti = controller;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ControllerSistemaProdotti getControllerSistemaProdotti() {
        return controllerSistemaProdotti;
    }

    public void setControllerSistemaProdotti(ControllerSistemaProdotti controllerSistemaProdotti) {
        this.controllerSistemaProdotti = controllerSistemaProdotti;
    }
}
