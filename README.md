# Repository Ufficiale di Ingegneria del Software 24/25

## 📌 Progetto: Piattaforma di Digitalizzazione e Valorizzazione della Filiera Agricola Locale

Il progetto mira a creare una piattaforma che permetta la **gestione**, **valorizzazione** e **tracciabilità** dei prodotti agricoli di un territorio comunale.

La piattaforma consentirà di:
- Caricare, visualizzare e condividere informazioni legate alla **filiera agricola**
- Tracciare ogni prodotto attraverso i diversi attori della filiera
- Visualizzare l’intero ciclo produttivo, dalla produzione alla vendita
- Promuovere il territorio e i suoi prodotti tipici
- Organizzare e pubblicizzare eventi locali, come fiere e visite guidate

I contenuti caricati potranno includere:
- Dati relativi a certificazioni
- Metodi di coltivazione e pratiche produttive
- Informazioni geolocalizzate visualizzabili su una **mappa interattiva**

---

## 👨‍💻 Autori

- Alessandroni Leonardo  
- Brizi Mattia  
- Profili Luca  

---

## 📁 Struttura del Repository

```text
agriTrace/
│
├── README.md               # Questo file: descrizione del progetto e struttura
├── .gitignore              # File per escludere cartelle e file non tracciati
│
├── model/                  # Modello concettuale e progettuale
│   └── visual-paradigm/    # Contiene il file .vpp e altri file generati/esportati
│       ├── agriTrace.vpp
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
│   ├── agriTrace-collection.json
│   └── agriTrace-environment.json
│
├── docs/                   # Documentazione tecnica e funzionale
│   ├── requisiti.md
│   ├── API-docs.md
│   └── schema-db.png
│
└── scripts/                # Script utili (es. per inizializzazione DB)
    ├── init-db.sql
    └── populate-sample-data.sql