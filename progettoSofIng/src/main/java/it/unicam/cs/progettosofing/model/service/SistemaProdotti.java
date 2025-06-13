package it.unicam.cs.progettosofing.model.service;

import it.unicam.cs.progettosofing.exception.ProdottoException;
import it.unicam.cs.progettosofing.model.business.Azienda;
import it.unicam.cs.progettosofing.model.entity.Prodotto;

import java.util.List;
import java.util.Map;

public class SistemaProdotti {
    private Map<Azienda, List<Prodotto>> prodottiAzienda;

    public List<Prodotto> visualizzaProdotti() throws ProdottoException {
        //TODO
        return null;
    }

    public List<Prodotto> visualizzaProdotti(Azienda azienda) throws ProdottoException {
        //TODO
        return null;
    }


    public void inserisciProdotti(Prodotto prodotto, Azienda azienda) throws ProdottoException {
        //TODO
    }

    public void cancellaProdotti(Prodotto prodotto, Azienda azienda) throws ProdottoException {
        //TODO
    }

    public void cancellaProdotti(Azienda azienda) throws ProdottoException {
        //TODO
    }

    public void modificaProdotti(Prodotto prodotto, Azienda azienda) throws ProdottoException {
        //TODO
    }


}