# 🌾 Piattaforma Filiera Agricola Locale

> **Web application per la gestione, tracciabilità e valorizzazione dei prodotti agricoli territoriali**

Progetto sviluppato per il corso di **Ingegneria del Software – A.A. 2024/2025**

Questa piattaforma digitale nasce con l'obiettivo di supportare la **gestione**, la **tracciabilità** e la **valorizzazione** della filiera agricola di un territorio comunale. È progettata per fornire strumenti utili agli attori locali (produttori, trasformatori, distributori, curatori, organizzatori di eventi) e offrire ai cittadini un'esperienza informativa e interattiva.

La web application consente di caricare, consultare e condividere informazioni su prodotti locali, pratiche agricole, certificazioni di qualità, eventi e molto altro, il tutto geolocalizzato e integrato in una **mappa interattiva**. Inoltre, include funzionalità di marketplace e promozione territoriale.

## 🚜 Funzionalità principali

- 📦 Caricamento e gestione dei contenuti lungo la filiera agricola  
- 🔍 Tracciabilità completa del ciclo di vita dei prodotti  
- 🗺️ Visualizzazione geolocalizzata su una **mappa interattiva**  
- 🧾 Inserimento di **certificazioni**, **metodi di coltivazione** e **processi di trasformazione**  
- 🛒 Vendita diretta tramite un **marketplace**  
- 🎪 Organizzazione e promozione di eventi locali (fiere, tour, degustazioni)

## 🧑‍🌾 Attori coinvolti

- **Produttore**: carica dati sui propri prodotti e li mette in vendita  
- **Trasformatore**: descrive i processi di trasformazione e collega i prodotti ai produttori  
- **Distributore di Tipicità**: propone prodotti singoli o in pacchetti gastronomici  
- **Curatore**: verifica e approva i contenuti prima della pubblicazione  
- **Animatore della Filiera**: organizza fiere, visite aziendali, tour di degustazione  
- **Acquirente**: acquista prodotti, partecipa a eventi, consulta le informazioni  
- **Utente Generico**: accede liberamente per conoscere il territorio e i prodotti  
- **Gestore della Piattaforma**: gestisce aspetti amministrativi e autorizzazioni  
- **Sistemi Social**: permettono la condivisione dei contenuti creati  
- **Sistema OSM**: fornisce il supporto cartografico per la mappa della filiera
  
---

## 📁 Struttura del Repository

```text
sofing/
│
├── README.md               # Questo file: descrizione del progetto e struttura
├── .gitignore              # File per escludere cartelle e file non tracciati
│
├── model/                  # Modello concettuale e progettuale
│   └── visual-paradigm/    # Contiene il file .vpp e altri file generati/esportati
│       ├── ProvaPush.vpp
│       └── diagrammi/, immagini/, ecc.
│
├── code/                   # Codice applicativo completo
│   ├── backend/            # Backend realizzato con Spring Boot e Gradle
│   │   ├── build.gradle
│   │   └── src/...         # Codice Java e risorse
│   └── frontend/           # Frontend (React, Vue, o altro)
│       └── (placeholder iniziale o progetto completo)
│
├── postman/                # Collezioni Postman per testare le API
│   ├── sofing-collection.json
│   └── sofing-environment.json
│
├── docs/                   # Documentazione tecnica e funzionale
│   ├── requisiti.md
│   ├── API-docs.md
│   └── schema-db.png
│
└── scripts/                # Script utili (es. per inizializzazione DB)
    ├── init-db.sql
    └── populate-sample-data.sql
```

---

## 👨‍💻 Autori

- Alessandroni Leonardo  
- Brizi Mattia  
- Porfiri Luca  

---
