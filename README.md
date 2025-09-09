# 🌿 Agritrace

> **Web application per la gestione, tracciabilità e valorizzazione dei prodotti agricoli territoriali**

![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen?logo=spring)
![Gradle](https://img.shields.io/badge/Gradle-8.3-blue?logo=gradle)
![H2 Database](https://img.shields.io/badge/Database-H2-lightgrey?logo=h2)
![Hibernate](https://img.shields.io/badge/Hibernate-6.6.18-orange?logo=hibernate)
![Lombok](https://img.shields.io/badge/Lombok-1.18.38-pink?logo=lombok)
![MapStruct](https://img.shields.io/badge/MapStruct-1.5.5-blueviolet?logo=mapstruct)
![SpringDoc OpenAPI](https://img.shields.io/badge/SpringDoc%20OpenAPI-2.8.12-green?logo=swagger)
![License](https://img.shields.io/badge/License-MIT-yellow)

Progetto sviluppato per il corso di **Ingegneria del Software – A.A. 2024/2025**

Questa piattaforma digitale nasce con l'obiettivo di supportare la **gestione**, la **tracciabilità** e la **valorizzazione** della filiera agricola di un territorio comunale. È progettata per fornire strumenti utili agli attori locali (produttori, trasformatori, distributori, curatori, organizzatori di eventi) e offrire ai cittadini un'esperienza informativa e interattiva.

La web application consente di caricare, consultare e condividere informazioni su prodotti locali, pratiche agricole, certificazioni di qualità, eventi e molto altro, il tutto geolocalizzato e integrato in una **mappa interattiva**. Inoltre, include funzionalità di marketplace e promozione territoriale.

## 🚜 Funzionalità principali

- 📦 Caricamento e gestione dei contenuti lungo la filiera agricola  
- 🔍 Tracciabilità completa del ciclo di vita dei prodotti   
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
├── README.md               # Documentazione principale del progetto (descrizione e guida)
├── LICENSE                 # Licenza del progetto
│
├── code/                   # Codice sorgente dell'applicazione
│   └── agritrace/          # Implementazione principale (backend)
│
├── docs/                   # Documentazione tecnica e report
│   └── testo_progetto.pdf  # Documento descrittivo del progetto
│
├── model/                  # Modelli e diagrammi di progettazione
│   └── visual-paradigm/    # Diagrammi UML e modelli creati con Visual Paradigm
│
├── postman/                # Collezioni Postman per testare e documentare le API
│
└── scripts/                # Script SQL per il database
    ├── schema.sql          # Definizione delle tabelle e vincoli del DB
    └── data.sql            # Dati iniziali per popolare il DB
```

---

## 👨‍💻 Autori

- Alessandroni Leonardo  
- Brizi Mattia  
- Porfiri Luca  

---
