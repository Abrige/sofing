INSERT INTO "PUBLIC"."PRODUCTS" VALUES
(1, 'Pomodori Rossi', 'Pomodori freschi di stagione, ideali per insalate', 1, 1, 2, FALSE, 1),
(2, 'Insalata Lattuga', 'Foglie di lattuga fresca', 1, 2, 1, FALSE, 1),
(3, 'Mele Golden', 'Mele dolci e croccanti', 2, 1, 3, FALSE, 1),
(4, 'Formaggio Pecorino', 'Formaggio stagionato di latte di pecora', 3, 3, 3, FALSE, 1),
(5, 'Vino Chianti', 'Vino rosso tipico della Toscana', 5, 4, 2, FALSE, 1),
(6, 'Olio Extra Vergine', 'Olio di oliva spremuto a freddo', 6, 5, 4, FALSE, 1),
(7, 'Carote Bio', 'Carote biologiche, coltivate senza pesticidi', 1, 1, 2, TRUE, 1),
(8, 'Zucchine', 'Zucchine fresche di stagione', 1, 2, 2, FALSE, 1),
(9, 'Pane Integrale', 'Pane artigianale integrale', 8, 9, 1, FALSE, 1),
(10, 'Marmellata di Arance', 'Marmellata artigianale di arance', 7, 10, 4, TRUE, 1),
(11, 'Frutta Secca Mista', 'Mix di noci, mandorle e nocciole', 2, 3, 3, FALSE, 1),
(12, 'Birra Artigianale', 'Birra locale prodotta con metodo tradizionale', 10, 4, 2, FALSE, 1),
(13, 'Spinaci Freschi', 'Spinaci verdi e freschi', 1, 1, 1, FALSE, 1),
(14, 'Fichi Secchi', 'Fichi essiccati naturalmente', 2, 3, 4, FALSE, 1),
(15, 'Mozzarella di Bufala', 'Mozzarella fresca di bufala', 3, 5, 3, FALSE, 1);   

INSERT INTO "PUBLIC"."CERTIFICATIONS" VALUES
(1, 'BIO', 'Certificazione biologica per prodotti alimentari', 'ICEA', TRUE),
(2, 'IGP', 'Indicazione Geografica Protetta', 'Consorzio Tutela IGP', TRUE),
(3, 'DOP', 'Denominazione di Origine Protetta', 'Consorzio Tutela DOP', TRUE),
(4, 'FAIR TRADE', 'Certificazione commercio equo e solidale', 'Fair Trade Italia', TRUE),
(5, 'MSC', U&'Certificazione sostenibilit\00e0 pesca', 'Marine Stewardship Council', TRUE),
(6, 'HACCP', 'Certificazione sicurezza alimentare', 'ISO', TRUE),
(7, 'ISO 22000', 'Sistema gestione sicurezza alimentare', 'ISO', TRUE),
(8, 'GLUTEN FREE', 'Certificazione prodotti senza glutine', 'AIC', TRUE),
(9, 'VEGAN', 'Certificazione prodotti vegani', 'VeganOK', TRUE),
(10, 'GMO FREE', 'Prodotti senza organismi geneticamente modificati', 'Non-GMO Project', TRUE),
(11, 'OLD CERT', 'Certificazione obsoleta', 'Ente Vecchio', FALSE);          
    
INSERT INTO "PUBLIC"."PRODUCT_REVIEWS" VALUES
(1, 1, 1, 5, 'Prodotto eccellente, freschissimo e gustoso!'),
(2, 2, 2, 4, U&'Buona qualit\00e0, leggermente costoso ma ne vale la pena.'),
(3, 3, 3, 3, 'Nella media, niente di speciale.'),
(4, 4, 4, 5, 'Ottimo formaggio, sapore intenso e ben stagionato.'),
(5, 5, 5, 2, U&'Non soddisfatto, qualit\00e0 inferiore alle aspettative.'),
(6, 6, 8, 4, 'Prodotto valido, consegna puntuale.'),
(7, 8, 10, 5, 'Prodotto perfetto, lo consiglio a tutti.'),
(8, 9, 11, 3, U&'Buono ma potrebbe essere pi\00f9 fresco.'),
(9, 11, 12, 4, U&'Prodotto di qualit\00e0, prezzo giusto.'),
(10, 12, 13, 5, U&'Molto soddisfatto, ottimo rapporto qualit\00e0/prezzo.'),
(11, 13, 14, 2, 'Non mi ha convinto, sapore troppo delicato.'),
(12, 14, 1, 5, U&'Perfetto, torner\00f2 a comprare!'),
(13, 15, 2, 4, 'Buon prodotto, conforme alla descrizione.');         
            
INSERT INTO "PUBLIC"."EVENT_TYPES" VALUES
(1, 'Mercato', 'Mercato agricolo locale'),
(2, 'Workshop', 'Laboratorio formativo (es. potatura, semina)'),
(3, 'Degustazione', 'Degustazione di prodotti'),
(4, 'Conferenza', 'Evento informativo o convegno'),
(5, 'Fiera', 'Fiera di settore (es. prodotti biologici)'),
(6, 'Open day', U&'Giornata aperta presso un\2019azienda agricola'),
(7, 'Networking', 'Evento per far incontrare produttori/clienti'),
(8, 'Visita aziendale', U&'Visita aziendale presso l\2019azienda agricola'),
(9, 'Tour di degustazione', 'Tour di degustazione di prodotti');    
 
INSERT INTO "PUBLIC"."USERS" VALUES
(1, 'mattia.curatore', 'mattia.curatore@example.com', 'hashedpwd1', 'Mattia', 'Rossi', '3331112233', 1, FALSE),
(2, 'giulia.acquirente', 'giulia.acquirente@example.com', 'hashedpwd2', 'Giulia', 'Bianchi', '3331112234', 2, FALSE),
(3, 'andrea.gestore', 'andrea.gestore@example.com', 'hashedpwd3', 'Andrea', 'Verdi', '3331112235', 3, FALSE),
(4, 'lucia.animatore', 'lucia.animatore@example.com', 'hashedpwd4', 'Lucia', 'Neri', '3331112236', 4, FALSE),
(5, 'paolo.creatore', 'paolo.creatore@example.com', 'hashedpwd5', 'Paolo', 'Gialli', '3331112237', 5, FALSE),
(6, 'elena.curatore', 'elena.curatore@example.com', 'hashedpwd6', 'Elena', 'Arancio', '3331112240', 1, TRUE),
(7, 'federico.acquirente', 'federico.acquirente@example.com', 'hashedpwd7', 'Federico', 'Rosa', '3331112241', 2, TRUE),
(8, 'silvia.animatore', 'silvia.animatore@example.com', 'hashedpwd8', 'Silvia', 'Grigio', '3331112242', 4, FALSE),
(9, 'luca.creatore', 'luca.creatore@example.com', 'hashedpwd9', 'Luca', 'Blu', '3331112243', 5, TRUE),
(10, 'marco.gestore', 'marco.gestore@example.com', 'hashedpwd10', 'Marco', 'Viola', '3331112244', 3, FALSE),
(11, 'utente8_1', 'utente8_1@example.com', 'hashedpwd11', 'Utente1', 'Rossi', '3331112250', 8, FALSE),
(12, 'utente8_2', 'utente8_2@example.com', 'hashedpwd12', 'Utente2', 'Bianchi', '3331112251', 8, FALSE),
(13, 'utente8_3', 'utente8_3@example.com', 'hashedpwd13', 'Utente3', 'Verdi', '3331112252', 8, FALSE),
(14, 'utente8_4', 'utente8_4@example.com', 'hashedpwd14', 'Utente4', 'Neri', '3331112253', 8, FALSE);  

INSERT INTO "PUBLIC"."TYPICAL_PACKAGE_ITEMS" VALUES
(1, 1, 1, 2),
(2, 1, 2, 3),
(3, 1, 3, 1),
(4, 2, 2, 2),
(5, 2, 4, 1),
(6, 2, 5, 1),
(7, 3, 3, 2),
(8, 3, 6, 1),
(9, 3, 8, 2),
(10, 4, 4, 1),
(11, 4, 11, 1),
(12, 4, 12, 2),
(13, 5, 5, 2),
(14, 5, 13, 1),
(15, 5, 14, 1),
(16, 6, 1, 1),
(17, 6, 7, 2),
(18, 6, 8, 1),
(19, 7, 2, 2),
(20, 7, 9, 1),
(21, 8, 3, 1),
(22, 8, 10, 1),
(23, 8, 12, 2);      

INSERT INTO "PUBLIC"."STATUS" VALUES
(1, 'new', 'Stato iniziale di un ordine: new'),
(2, 'pending', 'Ordine in attesa di approvazione o conferma: pending'),
(3, 'shipped', 'Ordine spedito al cliente: shipped'),
(4, 'delivered', 'Ordine consegnato al cliente: delivered'),
(5, 'cancelled', 'Ordine annullato: cancelled'),
(6, 'rejected', U&'Ordine rifiutato dal sistema o dall\2019utente: rejected'),
(7, 'declined', 'Ordine declinato per problemi o errori: declined');           
         
INSERT INTO "PUBLIC"."DB_TABLES" VALUES
(1, 'CERTIFICATIONS'),
(2, 'CHANGE_LOGS'),
(3, 'COMPANIES'),
(4, 'COMPANY_CERTIFICATION'),
(5, 'COMPANY_TYPES'),
(6, 'CULTIVATION_METHODS'),
(7, 'DB_TABLES'),
(8, 'EVENT_PARTECIPANTS'),
(9, 'EVENT_TYPES'),
(10, 'EVENTS'),
(11, 'HARVEST_SEASONS'),
(12, 'LOCATIONS'),
(13, 'ORDER_ITEMS'),
(14, 'ORDERS'),
(15, 'PRODUCT_CATEGORIES'),
(16, 'PRODUCT_CERTIFICATION'),
(17, 'PRODUCT_LISTINGS'),
(18, 'PRODUCT_REVIEWS'),
(19, 'PRODUCTION_STEPS'),
(20, 'PRODUCTS'),
(21, 'REQUESTS'),
(22, 'STATUS'),
(23, 'TYPICAL_PACKAGES'),
(24, 'TYPICAL_PACKAGES_ITEMS'),
(25, 'USER_COMPANY'),
(26, 'USER_ROLES'),
(27, 'USERS');          

INSERT INTO "PUBLIC"."CULTIVATION_METHODS" VALUES
(1, 'Biologico', 'Metodo di coltivazione senza pesticidi chimici e fertilizzanti sintetici', TRUE),
(2, 'Convenzionale', 'Metodo di coltivazione tradizionale con uso di prodotti chimici', TRUE),
(3, 'Biodinamico', 'Metodo che integra pratiche agricole con principi olistici', TRUE),
(4, 'Permacultura', 'Sistema di progettazione agricola sostenibile e autosufficiente', TRUE),
(5, 'Lotta integrata', 'Controllo dei parassiti tramite tecniche naturali e chimiche limitate', TRUE),
(6, 'Agricoltura sinergica', U&'Coltivazione che sfrutta le interazioni tra piante per aumentare la produttivit\00e0', TRUE),
(7, 'Idroponica', 'Coltivazione senza suolo usando soluzioni nutritive', FALSE),
(8, 'Aeroponica', 'Coltivazione sospesa in aria con nebulizzazione di nutrienti', FALSE),
(9, 'Agroforestale', 'Integrazione di coltivazioni con alberi e arbusti', TRUE),
(10, 'Coltivazione tradizionale', 'Metodo storico locale con pratiche tradizionali', TRUE);        
     
INSERT INTO "PUBLIC"."USER_ROLES" VALUES
(1, 'CURATORE', TRUE, U&'Ruolo responsabile della gestione dei contenuti e della qualit\00e0'),
(2, 'ACQUIRENTE', TRUE, U&'Ruolo che pu\00f2 acquistare prodotti o servizi sulla piattaforma'),
(3, 'GESTORE DELLA PIATTAFORMA', TRUE, 'Ruolo amministrativo con permessi completi sulla piattaforma'),
(4, 'ANIMATORE DELLA FILIERA', TRUE, U&'Ruolo che promuove e coordina attivit\00e0 tra i partecipanti della filiera'),
(5, 'CREATORE DI CONTENUTI', TRUE, 'Ruolo che produce contenuti digitali o materiali per la piattaforma'),
(6, 'DIPENDENTE', FALSE, 'Ruolo obsoleto precedentemente usato'),
(7, 'MODERATORE', FALSE, U&'Ruolo non pi\00f9 attivo per moderazione dei contenuti'),
(8, 'UTENTE', TRUE, 'Ruolo che identifica un utente generico nella piattaforma');   

INSERT INTO "PUBLIC"."COMPANIES" VALUES
(1, 11, 'Azienda Agricola Rossi', 'RSSMRA85M01H501X', 1, 'Produzione di frutta e verdura biologica', 'https://www.rossiagricola.it', FALSE, 1),
(2, 12, 'Trasformazioni Bianchi', 'BNCGPP78A12F205Y', 5, 'Trasformazione di prodotti agricoli in conserve', 'https://www.bianchitransformazioni.it', FALSE, 2),
(3, 13, U&'Distributore Tipicit\00e0 Verdi', 'VRDCTP90B34H501Z', 2, 'Distribuzione di prodotti tipici locali', 'https://www.verditipici.it', TRUE, 3),
(4, 14, 'Azienda Agricola Gialli', 'GLLMRA92C56D501W', 10, 'Produzione di oli e vini tipici', 'https://www.gialliagricola.it', FALSE, 1),
(5, 11, 'Trasformazioni Blu', 'BLUMRA80D78F205X', 4, 'Laboratorio di trasformazione lattiero-casearia', 'https://www.blutransformazioni.it', TRUE, 2),
(6, 12, U&'Distributore Tipicit\00e0 Neri', 'NRIMRA88E12H501Y', 3, 'Distribuzione prodotti biologici e tipici', 'https://www.neritipici.it', FALSE, 3),
(7, 13, 'Azienda Agricola Viola', 'VLMRA91F34D501Z', 6, 'Produzione di ortaggi e frutta di stagione', 'https://www.violagricola.it', FALSE, 1),
(8, 14, 'Trasformazioni Arancio', 'ARMRA87G56F205X', 7, 'Laboratorio di conserve e marmellate', 'https://www.aranciotransformazioni.it', TRUE, 2);     

INSERT INTO "PUBLIC"."EVENTS" VALUES
(1, 1, 'Mercato Primavera', 'Mercato agricolo con prodotti freschi di stagione', 1, DATE '2025-03-20', DATE '2025-03-21', 2, TRUE),
(2, 2, 'Workshop Potatura', 'Laboratorio pratico di potatura degli alberi da frutto', 2, DATE '2025-04-05', DATE '2025-04-06', 6, TRUE),
(3, 3, 'Degustazione Vini', 'Evento per degustare vini locali tipici', 3, DATE '2025-05-10', DATE '2025-05-10', 5, TRUE),
(4, 4, 'Conferenza Biologica', 'Seminario informativo su agricoltura biologica', 4, DATE '2025-06-15', DATE '2025-06-15', 3, TRUE),
(5, 5, 'Fiera dei Prodotti Bio', 'Fiera di settore con espositori e produttori', 5, DATE '2025-07-01', DATE '2025-07-03', 4, TRUE),
(6, 8, 'Open Day Azienda Rossi', U&'Giornata aperta presso l\2019azienda agricola', 6, DATE '2025-08-12', DATE '2025-08-12', 1, FALSE),
(7, 10, 'Networking Produttori', 'Evento per creare connessioni tra produttori e clienti', 7, DATE '2025-09-05', DATE '2025-09-05', 7, TRUE),
(8, 11, 'Visita Aziendale Verde', 'Tour guidato per conoscere le pratiche agricole', 8, DATE '2025-10-10', DATE '2025-10-10', 8, FALSE),
(9, 12, 'Mercato Autunnale', 'Mercato locale con prodotti di stagione autunnale', 1, DATE '2025-10-20', DATE '2025-10-21', 9, TRUE),
(10, 13, 'Workshop Semina Invernale', 'Laboratorio formativo per semina invernale', 2, DATE '2025-11-05', DATE '2025-11-06', 10, TRUE),
(11, 14, 'Degustazione Formaggi', 'Evento di degustazione di formaggi tipici', 3, DATE '2025-12-01', DATE '2025-12-01', 2, FALSE);             
   
INSERT INTO "PUBLIC"."HARVEST_SEASONS" VALUES
(1, 'Primavera', 3, 5, 'Nord', 'Periodo di raccolta dalla fine di marzo a maggio, fiori e prime verdure di stagione'),
(2, 'Estate', 6, 8, 'Nord', 'Raccolta estiva di frutta e ortaggi maturi'),
(3, 'Autunno', 9, 11, 'Nord', 'Raccolta di frutta autunnale e ortaggi tardivi'),
(4, 'Inverno', 12, 2, 'Nord', 'Periodo invernale con raccolte limitate, principalmente agrumi e prodotti invernali'),
(5, 'Primavera', 9, 11, 'Sud', 'Periodo di raccolta primaverile per emisfero sud'),
(6, 'Estate', 12, 2, 'Sud', U&'Raccolta estiva nell\2019emisfero sud'),
(7, 'Autunno', 3, 5, 'Sud', U&'Raccolta autunnale nell\2019emisfero sud'),
(8, 'Inverno', 6, 8, 'Sud', U&'Raccolta invernale nell\2019emisfero sud');       
      
INSERT INTO "PUBLIC"."COMPANY_TYPES" VALUES
(1, 'Produttore', 'Azienda che produce materie prime o prodotti agricoli'),
(2, 'Trasformatore', 'Azienda che trasforma le materie prime in prodotti finiti'),
(3, U&'Distributore di Tipicit\00e0', 'Azienda che distribuisce prodotti tipici locali');           
    
INSERT INTO "PUBLIC"."PRODUCTION_STEPS" VALUES
(1, 1, 1, 'produzione', U&'Raccolta pomodori e selezione qualit\00e0', DATE '2025-03-20', 2, NULL),
(2, 2, 2, 'produzione', 'Raccolta lattuga fresca', DATE '2025-03-21', 3, NULL),
(3, 3, 4, 'trasformazione', 'Produzione salsa di pomodoro', DATE '2025-04-01', 5, 8),
(4, 4, 6, 'trasformazione', 'Produzione formaggio stagionato', DATE '2025-04-10', 6, 12),
(5, 5, 7, 'produzione', 'Raccolta uva per vino Chianti', DATE '2025-09-01', 4, NULL),
(6, 6, 1, 'trasformazione', 'Olio spremuto a freddo', DATE '2025-10-05', 7, 9),
(7, 8, 2, 'trasformazione', 'Produzione marmellata di arance', DATE '2025-12-01', 8, 11),
(8, 9, 4, 'produzione', 'Raccolta frutta secca', DATE '2025-09-15', 9, NULL),
(9, 11, 6, 'trasformazione', 'Produzione pane integrale', DATE '2025-08-20', 10, 13),
(10, 12, 7, 'produzione', 'Raccolta spinaci freschi', DATE '2025-04-15', 1, NULL),
(11, 13, 1, 'trasformazione', 'Preparazione formaggi freschi', DATE '2025-05-10', 3, 14),
(12, 14, 2, 'produzione', 'Raccolta fichi secchi', DATE '2025-09-20', 2, NULL),
(13, 15, 4, 'trasformazione', 'Produzione mozzarella di bufala', DATE '2025-06-01', 5, 15);
  
INSERT INTO "PUBLIC"."TYPICAL_PACKAGES" VALUES
(1, 'Pacco Primavera', 'Selezione di prodotti freschi di primavera', 25, 1, TRUE),
(2, 'Pacco Verdure Bio', 'Confezione di verdure biologiche miste', 30, 2, TRUE),
(3, 'Pacco Frutta Mista', 'Frutta di stagione selezionata', 29, 4, TRUE),
(4, 'Pacco Formaggi Locali', 'Selezione di formaggi tipici locali', 40, 6, TRUE),
(5, 'Pacco Dolci Artigianali', 'Dolci e confetture locali', 35, 7, TRUE),
(6, 'Pacco Gourmet', U&'Prodotti gourmet e specialit\00e0 regionali', 50, 1, TRUE),
(7, 'Pacco Essenziale', 'Prodotti base per uso quotidiano', 20, 2, TRUE),
(8, 'Pacco Degustazione', 'Piccolo assortimento per degustazione', 15, 4, TRUE),
(9, 'Pacco Storico', 'Prodotti tradizionali storici', 45, 6, FALSE),
(10, 'Pacco Vintage', U&'Selezione di prodotti stagionali non pi\00f9 in produzione', 60, 7, FALSE);              
      
INSERT INTO "PUBLIC"."ORDER_ITEMS" VALUES
(1, 1, 1, 2, 3, 5),
(2, 1, 2, 3, 2, 5),
(3, 2, 3, 1, 12, 12),
(4, 2, 4, 2, 15, 30),
(5, 3, 5, 4, 9, 34),
(6, 3, 6, 2, 3, 6),
(7, 4, 7, 1, 9, 9),
(8, 4, 8, 2, 8, 15),
(9, 5, 1, 3, 3, 8),
(10, 5, 9, 1, 5, 5),
(11, 6, 10, 2, 4, 7),
(12, 7, 11, 1, 11, 11),
(13, 7, 12, 1, 15, 15),
(14, 8, 13, 2, 7, 14),
(15, 9, 1, 1, 3, 3),
(16, 9, 2, 2, 2, 4),
(17, 10, 3, 1, 12, 12),
(18, 10, 4, 1, 15, 15),
(19, 11, 5, 3, 9, 26),
(20, 11, 6, 1, 3, 3);   

INSERT INTO "PUBLIC"."PRODUCT_CERTIFICATION" VALUES
(1, 1, 1, 'BIO-P001', DATE '2023-01-10', DATE '2025-01-09'),
(2, 2, 2, 'IGP-P002', DATE '2023-02-15', DATE '2025-02-14'),
(3, 3, 3, 'DOP-P003', DATE '2023-03-05', DATE '2025-03-04'),
(4, 4, 4, 'FAIR-P004', DATE '2023-04-12', DATE '2025-04-11'),
(5, 5, 5, 'MSC-P005', DATE '2023-05-20', DATE '2025-05-19'),
(6, 6, 6, 'HACCP-P006', DATE '2023-06-08', DATE '2025-06-07'),
(7, 8, 7, 'ISO-P007', DATE '2023-07-01', DATE '2025-06-30'),
(8, 9, 8, 'GLUTEN-P008', DATE '2023-08-15', DATE '2025-08-14'),
(9, 11, 9, 'VEGAN-P009', DATE '2023-09-10', DATE '2025-09-09'),
(10, 12, 10, 'GMO-P010', DATE '2023-10-05', DATE '2025-10-04'),
(11, 13, 1, 'BIO-P011', DATE '2023-11-12', DATE '2025-11-11'),
(12, 14, 2, 'IGP-P012', DATE '2023-12-01', DATE '2025-11-30'),
(13, 15, 3, 'DOP-P013', DATE '2023-12-20', DATE '2025-12-19'); 

INSERT INTO "PUBLIC"."PRODUCT_LISTINGS" VALUES
(1, 1, 1, 3, 100, 'kg', TRUE),
(2, 2, 2, 2, 50, 'kg', TRUE),
(3, 3, 4, 12, 20, 'pezzi', TRUE),
(4, 4, 6, 15, 10, 'pezzi', TRUE),
(5, 5, 7, 9, 40, 'kg', TRUE),
(6, 6, 1, 3, 80, 'kg', TRUE),
(7, 8, 2, 9, 25, 'kg', TRUE),
(8, 9, 4, 8, 30, 'kg', TRUE),
(9, 11, 6, 5, 60, 'kg', TRUE),
(10, 12, 7, 4, 70, 'kg', TRUE),
(11, 13, 1, 11, 15, 'pezzi', TRUE),
(12, 14, 2, 15, 12, 'pezzi', TRUE),
(13, 15, 4, 7, 50, 'kg', TRUE),
(14, 5, 6, 9, 40, 'kg', FALSE),
(15, 6, 7, 3, 80, 'kg', FALSE),
(16, 9, 1, 8, 30, 'kg', FALSE); 

INSERT INTO "PUBLIC"."COMPANY_CERTIFICATION" VALUES
(1, 1, 1, 'BIO-001', DATE '2023-01-15', DATE '2025-01-14'),
(2, 1, 3, 'DOP-015', DATE '2022-06-10', DATE '2024-06-09'),
(3, 2, 2, 'IGP-022', DATE '2023-03-20', DATE '2025-03-19'),
(4, 2, 4, 'FAIR-007', DATE '2022-11-01', DATE '2024-10-31'),
(5, 3, 5, 'MSC-010', DATE '2023-02-05', DATE '2025-02-04'),
(6, 4, 1, 'BIO-020', DATE '2023-04-12', DATE '2025-04-11'),
(7, 4, 6, 'HACCP-003', DATE '2022-08-15', DATE '2024-08-14'),
(8, 5, 2, 'IGP-030', DATE '2023-05-10', DATE '2025-05-09'),
(9, 6, 3, 'DOP-045', DATE '2022-09-22', DATE '2024-09-21'),
(10, 6, 7, 'ISO22000-001', DATE '2023-01-01', DATE '2025-01-01'),
(11, 7, 8, 'GLUTENFREE-005', DATE '2022-12-12', DATE '2024-12-11'),
(12, 7, 9, 'VEGAN-002', DATE '2023-06-15', DATE '2025-06-14'),
(13, 8, 10, 'GMOFREE-001', DATE '2023-03-30', DATE '2025-03-29'),
(14, 8, 1, 'BIO-025', DATE '2023-07-20', DATE '2025-07-19');         
  
INSERT INTO "PUBLIC"."LOCATIONS" VALUES
(1, 'Azienda Agricola Rossi', 'Via del Fieno', '12', 'Milano', 45, 9, 'Azienda'),
(2, 'Mercato Centrale', 'Piazza del Mercato', '5', 'Firenze', 44, 11, 'Mercato'),
(3, 'Evento Degustazione Bio', 'Via delle Rose', '20', 'Bologna', 44, 11, 'Evento'),
(4, 'Magazzino Nord', 'Via Industriali', '3', 'Torino', 45, 8, 'Magazzino'),
(5, 'Open Day Azienda Verde', 'Via dei Campi', '7', 'Roma', 42, 12, 'Evento'),
(6, 'Laboratorio Semina', 'Via dei Fiori', '10', 'Napoli', 41, 14, 'Evento'),
(7, 'Mercato Contadino', 'Piazza San Giovanni', '2', 'Palermo', 38, 13, 'Mercato'),
(8, 'Magazzino Sud', 'Via delle Industrie', '15', 'Bari', 41, 17, 'Magazzino'),
(9, 'Fiera Biologica', 'Viale della Fiera', '8', 'Verona', 45, 11, 'Evento'),
(10, 'Azienda Agricola Bianchi', 'Strada dei Vigneti', '4', 'Parma', 45, 10, 'Azienda');          

INSERT INTO "PUBLIC"."EVENT_PARTECIPANTS" VALUES
(1, 1, TIMESTAMP '2025-08-01 10:15:00', 2, 1, 2, NULL),
(2, 1, TIMESTAMP '2025-08-01 11:20:00', 3, 1, 2, NULL),
(3, 2, TIMESTAMP '2025-08-02 09:30:00', 4, 2, 2, NULL),
(4, 2, TIMESTAMP '2025-08-02 10:45:00', 5, 2, 2, NULL),
(5, 3, TIMESTAMP '2025-08-03 14:00:00', 8, 3, 2, NULL),
(6, 4, TIMESTAMP '2025-08-04 16:30:00', 10, 4, 2, NULL),
(7, 4, TIMESTAMP '2025-08-04 17:15:00', 11, 4, 2, NULL),
(8, 5, TIMESTAMP '2025-08-05 12:00:00', 12, 5, 2, NULL),
(9, 7, TIMESTAMP '2025-08-06 13:45:00', 13, 8, 2, NULL),
(10, 9, TIMESTAMP '2025-08-07 15:30:00', 14, 10, 2, NULL),
(11, 10, TIMESTAMP '2025-08-08 09:00:00', 1, 11, 2, NULL);             

INSERT INTO "PUBLIC"."PRODUCT_CATEGORIES" VALUES
(1, 'Verdure', 'Ortaggi freschi di stagione'),
(2, 'Frutta', 'Frutta fresca e di stagione'),
(3, 'Formaggi', 'Formaggi locali e nazionali'),
(4, 'Carne', 'Carni fresche e lavorate'),
(5, 'Vini', 'Vini tipici e regionali'),
(6, 'Oli', 'Oli extravergine di oliva e semi'),
(7, 'Conserve', 'Conserve alimentari e marmellate'),
(8, 'Pane e prodotti da forno', 'Pane, dolci e prodotti da forno artigianali'),
(9, 'Prodotti biologici', 'Prodotti certificati biologici'),
(10, 'Bevande', 'Succhi, birre artigianali e altre bevande');

INSERT INTO "PUBLIC"."ORDERS" VALUES
(1, 1, 1, 50, 1, TIMESTAMP '2025-08-19 13:59:32.993143', DATE '2025-08-25', 2),
(2, 2, 2, 31, 1, TIMESTAMP '2025-08-19 13:59:32.993143', DATE '2025-08-26', 3),
(3, 3, 3, 75, 1, TIMESTAMP '2025-08-19 13:59:32.993143', DATE '2025-08-27', 4),
(4, 4, 4, 120, 1, TIMESTAMP '2025-08-19 13:59:32.993143', DATE '2025-08-28', 5),
(5, 5, 5, 60, 1, TIMESTAMP '2025-08-19 13:59:32.993143', DATE '2025-08-29', 6),
(6, 8, 6, 46, 1, TIMESTAMP '2025-08-19 13:59:32.993143', DATE '2025-08-30', 7),
(7, 10, 7, 90, 1, TIMESTAMP '2025-08-19 13:59:32.993143', DATE '2025-09-01', 8),
(8, 11, 8, 110, 1, TIMESTAMP '2025-08-19 13:59:32.993143', DATE '2025-09-02', 9),
(9, 12, 1, 35, 1, TIMESTAMP '2025-08-19 13:59:32.993143', DATE '2025-09-03', 10),
(10, 13, 2, 80, 1, TIMESTAMP '2025-08-19 13:59:32.993143', DATE '2025-09-04', 1),
(11, 14, 3, 95, 1, TIMESTAMP '2025-08-19 13:59:32.993143', DATE '2025-09-05', 2); 