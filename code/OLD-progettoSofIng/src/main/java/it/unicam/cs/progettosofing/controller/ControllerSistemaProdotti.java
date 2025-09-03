package it.unicam.cs.progettosofing.controller;

import it.unicam.cs.progettosofing.model.business.Azienda;
import it.unicam.cs.progettosofing.model.entity.Prodotto;
import it.unicam.cs.progettosofing.model.service.SistemaProdotti;

import java.util.Collections;
import java.util.List;

public class ControllerSistemaProdotti {
    private final SistemaProdotti sistemaProdotti;
    private final Azienda azienda;

    public ControllerSistemaProdotti(SistemaProdotti sistemaProdotti, Azienda azienda) {
        this.sistemaProdotti = sistemaProdotti;
        this.azienda = azienda;
    }


    public List<Prodotto> visualizzaProdottiAzienda(){
        try{
            return this.sistemaProdotti.visualizzaProdotti(this.azienda);
        }
        catch (IllegalArgumentException e) {
            System.err.println("Errore: Argomenti non validi per la visualizzazione dei prodotti. " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println("Errore imprevisto durante la visualizzazione dei prodotti.");
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
