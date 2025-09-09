-- Inizio CERTIFICATIONS.sql
insert into PUBLIC.CERTIFICATIONS (ID, NAME, DESCRIPTION, ISSUING_BODY, IS_ACTIVE)
values  (1, 'BIO', 'Certificazione biologica per prodotti alimentari', 'ICEA', true),
        (2, 'IGP', 'Indicazione Geografica Protetta', 'Consorzio Tutela IGP', true),
        (3, 'DOP', 'Denominazione di Origine Protetta', 'Consorzio Tutela DOP', true),
        (4, 'FAIR TRADE', 'Certificazione commercio equo e solidale', 'Fair Trade Italia', true),
        (5, 'MSC', 'Certificazione sostenibilità pesca', 'Marine Stewardship Council', true),
        (6, 'HACCP', 'Certificazione sicurezza alimentare', 'ISO', true),
        (7, 'ISO 22000', 'Sistema gestione sicurezza alimentare', 'ISO', true),
        (8, 'GLUTEN FREE', 'Certificazione prodotti senza glutine', 'AIC', true),
        (9, 'VEGAN', 'Certificazione prodotti vegani', 'VeganOK', true),
        (10, 'GMO FREE', 'Prodotti senza organismi geneticamente modificati', 'Non-GMO Project', true),
        (11, 'OLD CERT', 'Certificazione obsoleta', 'Ente Vecchio', false),
        (12, 'Certificazione di prova 2', 'Certificazione di prova descrizione 2', 'Issuing body di prova 2', false),
        (13, 'Certificazione di prova 3', 'Certificazione di prova descrizione 3', 'Issuing body di prova 3', false);
-- Fine CERTIFICATIONS.sql

-- Inizio COMPANIES.sql
insert into PUBLIC.COMPANIES (ID, OWNER_ID, NAME, FISCAL_CODE, LOCATION_ID, DESCRIPTION, WEBSITE_URL, IS_DELETED, COMPANY_TYPE_ID, CERTIFICATION_ID)
values  (1, 11, 'Azienda Agricola Rossi', 'RSSMRA85M01H501X', 1, 'Produzione di frutta e verdura biologica', 'https://www.rossiagricola.it', false, 1, 1),
        (2, 12, 'Trasformazioni Bianchi', 'BNCGPP78A12F205Y', 5, 'Trasformazione di prodotti agricoli in conserve', 'https://www.bianchitransformazioni.it', false, 2, 1),
        (3, 13, 'Distributore Tipicità Verdi', 'VRDCTP90B34H501Z', 2, 'Distribuzione di prodotti tipici locali', 'https://www.verditipici.it', true, 3, 1),
        (4, 14, 'Azienda Agricola Gialli', 'GLLMRA92C56D501W', 10, 'Produzione di oli e vini tipici', 'https://www.gialliagricola.it', false, 1, 1),
        (5, 11, 'Trasformazioni Blu', 'BLUMRA80D78F205X', 4, 'Laboratorio di trasformazione lattiero-casearia', 'https://www.blutransformazioni.it', true, 2, 1),
        (6, 12, 'Distributore Tipicità Neri', 'NRIMRA88E12H501Y', 3, 'Distribuzione prodotti biologici e tipici', 'https://www.neritipici.it', false, 3, 1),
        (7, 13, 'Azienda Agricola Viola', 'VLMRA91F34D501Z', 6, 'Produzione di ortaggi e frutta di stagione', 'https://www.violagricola.it', false, 1, 1),
        (8, 14, 'Trasformazioni Arancio', 'ARMRA87G56F205X', 7, 'Laboratorio di conserve e marmellate', 'https://www.aranciotransformazioni.it', true, 2, 1),
        (9, 55, 'Company di prova', 'fiscalcodediprova', 1, 'descrizione di prova', null, false, 1, 1);
-- Fine COMPANIES.sql

-- Inizio COMPANY_TYPES.sql
insert into PUBLIC.COMPANY_TYPES (ID, NAME, DESCRIPTION)
values  (1, 'Produttore', 'Azienda che produce materie prime o prodotti agricoli'),
        (2, 'Trasformatore', 'Azienda che trasforma le materie prime in prodotti finiti'),
        (3, 'Distributore di Tipicità', 'Azienda che distribuisce prodotti tipici locali');
-- Fine COMPANY_TYPES.sql

-- Inizio CULTIVATION_METHODS.sql
insert into PUBLIC.CULTIVATION_METHODS (ID, NAME, DESCRIPTION, IS_ACTIVE)
values  (1, 'Biologico', 'Metodo di coltivazione senza pesticidi chimici e fertilizzanti sintetici', true),
        (2, 'Convenzionale', 'Metodo di coltivazione tradizionale con uso di prodotti chimici', true),
        (3, 'Biodinamico', 'Metodo che integra pratiche agricole con principi olistici', true),
        (4, 'Permacultura', 'Sistema di progettazione agricola sostenibile e autosufficiente', true),
        (5, 'Lotta integrata', 'Controllo dei parassiti tramite tecniche naturali e chimiche limitate', true),
        (6, 'Agricoltura sinergica', 'Coltivazione che sfrutta le interazioni tra piante per aumentare la produttività', true),
        (7, 'Idroponica', 'Coltivazione senza suolo usando soluzioni nutritive', false),
        (8, 'Aeroponica', 'Coltivazione sospesa in aria con nebulizzazione di nutrienti', false),
        (9, 'Agroforestale', 'Integrazione di coltivazioni con alberi e arbusti', true),
        (10, 'Coltivazione tradizionale', 'Metodo storico locale con pratiche tradizionali', true);
-- Fine CULTIVATION_METHODS.sql

-- Inizio DB_TABLES.sql
insert into PUBLIC.DB_TABLES (ID, NAME)
values  (1, 'CERTIFICATIONS'),
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
-- Fine DB_TABLES.sql

-- Inizio EVENTS.sql
insert into PUBLIC.EVENTS (ID, ORGANIZER_ID, TITLE, DESCRIPTION, EVENT_TYPE_ID, START_DATE, END_DATE, LOCATION_ID, IS_ACTIVE)
values  (1, 1, 'Mercato Primavera', 'Mercato agricolo con prodotti freschi di stagione', 1, '2025-03-20', '2025-03-21', 2, true),
        (2, 2, 'Workshop Potatura', 'Laboratorio pratico di potatura degli alberi da frutto', 2, '2025-04-05', '2025-04-06', 6, true),
        (3, 3, 'Degustazione Vini', 'Evento per degustare vini locali tipici', 3, '2025-05-10', '2025-05-10', 5, true),
        (4, 4, 'Conferenza Biologica', 'Seminario informativo su agricoltura biologica', 4, '2025-06-15', '2025-06-15', 3, true),
        (5, 5, 'Fiera dei Prodotti Bio', 'Fiera di settore con espositori e produttori', 5, '2025-07-01', '2025-07-03', 4, true),
        (6, 8, 'Open Day Azienda Rossi', 'Giornata aperta presso l’azienda agricola', 6, '2025-08-12', '2025-08-12', 1, false),
        (7, 10, 'Networking Produttori', 'Evento per creare connessioni tra produttori e clienti', 7, '2025-09-05', '2025-09-05', 7, true),
        (8, 11, 'Visita Aziendale Verde', 'Tour guidato per conoscere le pratiche agricole', 8, '2025-10-10', '2025-10-10', 8, false),
        (9, 12, 'Mercato Autunnale', 'Mercato locale con prodotti di stagione autunnale', 1, '2025-10-20', '2025-10-21', 9, true),
        (10, 13, 'Workshop Semina Invernale', 'Laboratorio formativo per semina invernale', 2, '2025-11-05', '2025-11-06', 10, true),
        (11, 14, 'Degustazione Formaggi', 'Evento di degustazione di formaggi tipici', 3, '2025-12-01', '2025-12-01', 2, false);
-- Fine EVENTS.sql

-- Inizio EVENT_PARTECIPANTS.sql
;
-- Fine EVENT_PARTECIPANTS.sql

-- Inizio EVENT_TYPES.sql
insert into PUBLIC.EVENT_TYPES (ID, NAME, DESCRIPTION)
values  (1, 'Mercato', 'Mercato agricolo locale'),
        (2, 'Workshop', 'Laboratorio formativo (es. potatura, semina)'),
        (3, 'Degustazione', 'Degustazione di prodotti'),
        (4, 'Conferenza', 'Evento informativo o convegno'),
        (5, 'Fiera', 'Fiera di settore (es. prodotti biologici)'),
        (6, 'Open day', 'Giornata aperta presso un’azienda agricola'),
        (7, 'Networking', 'Evento per far incontrare produttori/clienti'),
        (8, 'Visita aziendale', 'Visita aziendale presso l’azienda agricola'),
        (9, 'Tour di degustazione', 'Tour di degustazione di prodotti');
-- Fine EVENT_TYPES.sql

-- Inizio HARVEST_SEASONS.sql
insert into PUBLIC.HARVEST_SEASONS (ID, NAME, MONTH_START, MONTH_END, HEMISPHERE, DESCRIPTION)
values  (1, 'Primavera', 3, 5, 'Nord', 'Periodo di raccolta dalla fine di marzo a maggio, fiori e prime verdure di stagione'),
        (2, 'Estate', 6, 8, 'Nord', 'Raccolta estiva di frutta e ortaggi maturi'),
        (3, 'Autunno', 9, 11, 'Nord', 'Raccolta di frutta autunnale e ortaggi tardivi'),
        (4, 'Inverno', 12, 2, 'Nord', 'Periodo invernale con raccolte limitate, principalmente agrumi e prodotti invernali'),
        (5, 'Primavera', 9, 11, 'Sud', 'Periodo di raccolta primaverile per emisfero sud'),
        (6, 'Estate', 12, 2, 'Sud', 'Raccolta estiva nell’emisfero sud'),
        (7, 'Autunno', 3, 5, 'Sud', 'Raccolta autunnale nell’emisfero sud'),
        (8, 'Inverno', 6, 8, 'Sud', 'Raccolta invernale nell’emisfero sud');
-- Fine HARVEST_SEASONS.sql

-- Inizio LOCATIONS.sql
insert into PUBLIC.LOCATIONS (ID, NAME, ADDRESS, STREET_NUMBER, CITY, LATITUDE, LONGITUDE, LOCATION_TYPE)
values  (1, 'Azienda Agricola Rossi', 'Via del Fieno', '12', 'Milano', 45, 9, 'Azienda'),
        (2, 'Mercato Centrale', 'Piazza del Mercato', '5', 'Firenze', 44, 11, 'Mercato'),
        (3, 'Evento Degustazione Bio', 'Via delle Rose', '20', 'Bologna', 44, 11, 'Evento'),
        (4, 'Magazzino Nord', 'Via Industriali', '3', 'Torino', 45, 8, 'Magazzino'),
        (5, 'Open Day Azienda Verde', 'Via dei Campi', '7', 'Roma', 42, 12, 'Evento'),
        (6, 'Laboratorio Semina', 'Via dei Fiori', '10', 'Napoli', 41, 14, 'Evento'),
        (7, 'Mercato Contadino', 'Piazza San Giovanni', '2', 'Palermo', 38, 13, 'Mercato'),
        (8, 'Magazzino Sud', 'Via delle Industrie', '15', 'Bari', 41, 17, 'Magazzino'),
        (9, 'Fiera Biologica', 'Viale della Fiera', '8', 'Verona', 45, 11, 'Evento'),
        (10, 'Azienda Agricola Bianchi', 'Strada dei Vigneti', '4', 'Parma', 45, 10, 'Azienda');
-- Fine LOCATIONS.sql

-- Inizio ORDERS.sql
insert into PUBLIC.ORDERS (ID, BUYER_ID, TOTAL_AMOUNT, STATUS_ID, ORDERED_AT, DELIVERY_DATE, DELIVERY_LOCATION_ID)
values  (1, 1, 50, 6, '2025-08-19 13:59:32.993143', '2025-08-25', 2),
        (2, 2, 31, 1, '2025-08-19 13:59:32.993143', '2025-08-26', 3),
        (3, 3, 75, 1, '2025-08-19 13:59:32.993143', '2025-08-27', 4),
        (4, 4, 120, 1, '2025-08-19 13:59:32.993143', '2025-08-28', 5),
        (5, 5, 60, 1, '2025-08-19 13:59:32.993143', '2025-08-29', 6),
        (6, 8, 46, 1, '2025-08-19 13:59:32.993143', '2025-08-30', 7),
        (7, 10, 90, 1, '2025-08-19 13:59:32.993143', '2025-09-01', 8),
        (8, 11, 110, 1, '2025-08-19 13:59:32.993143', '2025-09-02', 9),
        (9, 12, 35, 1, '2025-08-19 13:59:32.993143', '2025-09-03', 10),
        (10, 13, 80, 1, '2025-08-19 13:59:32.993143', '2025-09-04', 1),
        (11, 14, 95, 1, '2025-08-19 13:59:32.993143', '2025-09-05', 2);
-- Fine ORDERS.sql

-- Inizio ORDER_ITEMS.sql
insert into PUBLIC.ORDER_ITEMS (ID, ORDER_ID, PRODUCT_LISTING_ID, QUANTITY, UNIT_PRICE, TOTAL_PRICE, TYPICAL_PACKAGE_ID)
values  (1, 1, 1, 2, 3, 5, null),
        (2, 1, 2, 3, 2, 5, null),
        (3, 2, 3, 1, 12, 12, null),
        (4, 2, 4, 2, 15, 30, null),
        (5, 3, 5, 4, 9, 34, null),
        (6, 3, 6, 2, 3, 6, null),
        (7, 4, 7, 1, 9, 9, null),
        (8, 4, 8, 2, 8, 15, null),
        (9, 5, 1, 3, 3, 8, null),
        (10, 5, 9, 1, 5, 5, null),
        (11, 6, 10, 2, 4, 7, null),
        (12, 7, 11, 1, 11, 11, null),
        (13, 7, 12, 1, 15, 15, null),
        (14, 8, 13, 2, 7, 14, null),
        (15, 9, 1, 1, 3, 3, null),
        (16, 9, 2, 2, 2, 4, null),
        (17, 10, 3, 1, 12, 12, null),
        (18, 10, 4, 1, 15, 15, null),
        (19, 11, 5, 3, 9, 26, null),
        (20, 11, 6, 1, 3, 3, null);
-- Fine ORDER_ITEMS.sql

-- Inizio PRODUCTION_STEPS.sql
insert into PUBLIC.PRODUCTION_STEPS (ID, INPUT_PRODUCT_ID, STEP_TYPE, DESCRIPTION, STEP_DATE, LOCATION_ID, OUTPUT_PRODUCT_ID)
values  (1, 1, 'produzione', 'Raccolta pomodori e selezione qualità', '2025-03-20', 2, null),
        (2, 2, 'produzione', 'Raccolta lattuga fresca', '2025-03-21', 3, null),
        (3, 3, 'trasformazione', 'Produzione salsa di pomodoro', '2025-04-01', 5, 8),
        (4, 4, 'trasformazione', 'Produzione formaggio stagionato', '2025-04-10', 6, 12),
        (5, 5, 'produzione', 'Raccolta uva per vino Chianti', '2025-09-01', 4, null),
        (6, 6, 'trasformazione', 'Olio spremuto a freddo', '2025-10-05', 7, 9),
        (7, 8, 'trasformazione', 'Produzione marmellata di arance', '2025-12-01', 8, 11),
        (8, 9, 'produzione', 'Raccolta frutta secca', '2025-09-15', 9, null),
        (9, 11, 'trasformazione', 'Produzione pane integrale', '2025-08-20', 10, 13),
        (10, 12, 'produzione', 'Raccolta spinaci freschi', '2025-04-15', 1, null),
        (11, 13, 'trasformazione', 'Preparazione formaggi freschi', '2025-05-10', 3, 14),
        (12, 14, 'produzione', 'Raccolta fichi secchi', '2025-09-20', 2, null),
        (13, 15, 'trasformazione', 'Produzione mozzarella di bufala', '2025-06-01', 5, 15),
        (14, 1, 'trasformato', 'Pomodori Rossi', '2025-08-29', null, 20),
        (16, 15, 'trasformato', 'Mozzarella', '2025-08-29', null, 20),
        (46, 1, 'trasformato', 'descrizione di prova', '2025-09-08', null, 20);
-- Fine PRODUCTION_STEPS.sql

-- Inizio PRODUCTS.sql
insert into PUBLIC.PRODUCTS (ID, NAME, DESCRIPTION, CATEGORY_ID, CULTIVATION_METHOD_ID, HARVEST_SEASON_ID, IS_DELETED, PRODUCER_ID)
values  (1, 'Pomodori Rossi', 'Pomodori freschi di stagione, ideali per insalate', 1, 1, 2, false, 1),
        (2, 'prova update product', 'Foglie di lattuga fresca', 1, 2, 1, false, 1),
        (3, 'Mele Golden', 'Mele dolci e croccanti', 2, 1, 3, false, 1),
        (4, 'Formaggio Pecorino', 'Formaggio stagionato di latte di pecora', 3, 3, 3, false, 1),
        (5, 'Vino Chianti', 'Vino rosso tipico della Toscana', 5, 4, 2, false, 1),
        (6, 'Olio Extra Vergine', 'Olio di oliva spremuto a freddo', 6, 5, 4, false, 1),
        (7, 'Carote Bio', 'Carote biologiche, coltivate senza pesticidi', 1, 1, 2, true, 1),
        (8, 'Zucchine', 'Zucchine fresche di stagione', 1, 2, 2, false, 1),
        (9, 'Pane Integrale', 'Pane artigianale integrale', 8, 9, 1, false, 1),
        (10, 'Marmellata di Arance', 'Marmellata artigianale di arance', 7, 10, 4, true, 1),
        (11, 'Frutta Secca Mista', 'Mix di noci, mandorle e nocciole', 2, 3, 3, false, 1),
        (12, 'Birra Artigianale', 'Birra locale prodotta con metodo tradizionale', 10, 4, 2, false, 1),
        (13, 'Spinaci Freschi', 'Spinaci verdi e freschi', 1, 1, 1, false, 1),
        (14, 'Fichi Secchi', 'Fichi essiccati naturalmente', 2, 3, 4, false, 1),
        (15, 'Mozzarella di Bufala', 'Mozzarella fresca di bufala', 3, 5, 3, false, 1),
        (16, 'Ciliegina', 'Una ciliegia molto buonissima', 3, 2, 2, false, 4),
        (17, 'Ciliegina', 'Una ciliegia molto buonissima', 3, 2, 2, false, 4),
        (18, 'Pollo Impanato', 'Una pollo succoso', 3, 2, 2, false, 4),
        (19, 'Pollo Impanato', 'Una pollo succoso', 3, 2, 2, false, 4),
        (20, 'Insalatona', 'Fatta da pomodori, mozzarella e insalata', 1, 3, 1, false, 1),
        (51, 'vino chianti', 'sas', 5, 4, 2, false, 1);
-- Fine PRODUCTS.sql

-- Inizio PRODUCT_CATEGORIES.sql
insert into PUBLIC.PRODUCT_CATEGORIES (ID, NAME, DESCRIPTION)
values  (1, 'Verdure', 'Ortaggi freschi di stagione'),
        (2, 'Frutta', 'Frutta fresca e di stagione'),
        (3, 'Formaggi', 'Formaggi locali e nazionali'),
        (4, 'Carne', 'Carni fresche e lavorate'),
        (5, 'Vini', 'Vini tipici e regionali'),
        (6, 'Oli', 'Oli extravergine di oliva e semi'),
        (7, 'Conserve', 'Conserve alimentari e marmellate'),
        (8, 'Pane e prodotti da forno', 'Pane, dolci e prodotti da forno artigianali'),
        (9, 'Prodotti biologici', 'Prodotti certificati biologici'),
        (10, 'Bevande', 'Succhi, birre artigianali e altre bevande');
-- Fine PRODUCT_CATEGORIES.sql

-- Inizio PRODUCT_CERTIFICATION.sql
insert into PUBLIC.PRODUCT_CERTIFICATION (ID, PRODUCT_ID, CERTIFICATION_ID, CERTIFICATE_NUMBER, ISSUE_DATE, EXPIRY_DATE)
values  (1, 1, 1, 'BIO-P001', '2023-01-10', '2025-01-09'),
        (2, 2, 2, 'IGP-P002', '2023-02-15', '2025-02-14'),
        (3, 3, 3, 'DOP-P003', '2023-03-05', '2025-03-04'),
        (4, 4, 4, 'FAIR-P004', '2023-04-12', '2025-04-11'),
        (5, 5, 5, 'MSC-P005', '2023-05-20', '2025-05-19'),
        (6, 6, 6, 'HACCP-P006', '2023-06-08', '2025-06-07'),
        (7, 8, 7, 'ISO-P007', '2023-07-01', '2025-06-30'),
        (8, 9, 8, 'GLUTEN-P008', '2023-08-15', '2025-08-14'),
        (9, 11, 9, 'VEGAN-P009', '2023-09-10', '2025-09-09'),
        (10, 12, 10, 'GMO-P010', '2023-10-05', '2025-10-04'),
        (11, 13, 1, 'BIO-P011', '2023-11-12', '2025-11-11'),
        (12, 14, 2, 'IGP-P012', '2023-12-01', '2025-11-30'),
        (13, 15, 3, 'DOP-P013', '2023-12-20', '2025-12-19');
-- Fine PRODUCT_CERTIFICATION.sql

-- Inizio PRODUCT_LISTINGS.sql
insert into PUBLIC.PRODUCT_LISTINGS (ID, PRODUCT_ID, SELLER_ID, PRICE, QUANTITY_AVAILABLE, UNIT_OF_MEASURE, IS_ACTIVE)
values  (1, 1, 1, 3, 100, 'kg', true),
        (2, 2, 2, 2, 50, 'kg', true),
        (3, 3, 4, 12, 20, 'pezzi', true),
        (4, 4, 6, 15, 10, 'pezzi', true),
        (5, 5, 7, 9, 40, 'kg', true),
        (6, 6, 1, 3, 80, 'kg', true),
        (7, 8, 2, 9, 25, 'kg', true),
        (8, 9, 4, 8, 30, 'kg', true),
        (9, 11, 6, 5, 60, 'kg', true),
        (10, 12, 7, 4, 70, 'kg', true),
        (11, 13, 1, 11, 15, 'pezzi', true),
        (12, 14, 2, 15, 12, 'pezzi', true),
        (13, 15, 4, 7, 50, 'kg', true),
        (14, 5, 6, 9, 40, 'kg', false),
        (15, 6, 7, 3, 80, 'kg', false),
        (16, 9, 1, 8, 30, 'kg', false);
-- Fine PRODUCT_LISTINGS.sql

-- Inizio PRODUCT_REVIEWS.sql
insert into PUBLIC.PRODUCT_REVIEWS (ID, PRODUCT_ID, USER_ID, RATING, COMMENT)
values  (1, 1, 1, 5, 'Prodotto eccellente, freschissimo e gustoso!'),
        (2, 2, 2, 4, 'Buona qualità, leggermente costoso ma ne vale la pena.'),
        (3, 3, 3, 3, 'Nella media, niente di speciale.'),
        (4, 4, 4, 5, 'Ottimo formaggio, sapore intenso e ben stagionato.'),
        (5, 5, 5, 2, 'Non soddisfatto, qualità inferiore alle aspettative.'),
        (6, 6, 8, 4, 'Prodotto valido, consegna puntuale.'),
        (7, 8, 10, 5, 'Prodotto perfetto, lo consiglio a tutti.'),
        (8, 9, 11, 3, 'Buono ma potrebbe essere più fresco.'),
        (9, 11, 12, 4, 'Prodotto di qualità, prezzo giusto.'),
        (10, 12, 13, 5, 'Molto soddisfatto, ottimo rapporto qualità/prezzo.'),
        (11, 13, 14, 2, 'Non mi ha convinto, sapore troppo delicato.'),
        (12, 14, 1, 5, 'Perfetto, tornerò a comprare!'),
        (13, 15, 2, 4, 'Buon prodotto, conforme alla descrizione.');
-- Fine PRODUCT_REVIEWS.sql

-- Inizio REQUESTS.sql
insert into PUBLIC.REQUESTS (ID, REQUESTER_ID, TARGET_TABLE_ID, TARGET_ID, PAYLOAD, CURATOR_ID, DECISION_NOTES, CREATED_AT, REVIEWED_AT, STATUS_ID, REQUEST_TYPE_ID)
values  (37, 1, 23, null, '''7B227061636B6167655F6964223A6E756C6C2C226E616D65223A2250616363686574746F2070617A7A6573636F222C226465736372697074696F6E223A2270617A7A75726F642071756573746F20C3A8203330222C227072696365223A33392C2270726F64756365725F6964223A342C226974656D73223A5B7B2270726F64756374223A6E756C6C2C227175616E74697479223A337D2C7B2270726F64756374223A6E756C6C2C227175616E74697479223A327D2C7B2270726F64756374223A6E756C6C2C227175616E74697479223A327D5D7D''', null, null, '2025-09-05 10:32:28.455885', null, 2, 2),
        (38, 1, 23, null, '''7B226E616D65223A2250616363686574746F2070617A7A6573636F222C226465736372697074696F6E223A2270617A7A75726F642071756573746F20C3A8203330222C227072696365223A33392C2270726F64756365725F6964223A342C226974656D73223A5B7B2270726F647563745F6964223A312C227175616E74697479223A337D2C7B2270726F647563745F6964223A322C227175616E74697479223A327D2C7B2270726F647563745F6964223A312C227175616E74697479223A327D5D7D''', null, null, '2025-09-05 11:01:17.456283', null, 2, 2),
        (39, 1, 23, null, '''7B226E616D65223A2250616363686574746F2070617A7A6573636F222C226465736372697074696F6E223A2270617A7A75726F642071756573746F20C3A8203330222C227072696365223A33392C2270726F64756365725F6964223A342C226974656D73223A5B7B2270726F647563745F6964223A372C227175616E74697479223A337D2C7B2270726F647563745F6964223A322C227175616E74697479223A327D2C7B2270726F647563745F6964223A312C227175616E74697479223A327D5D7D''', null, null, '2025-09-05 11:01:49.659358', null, 2, 2),
        (40, 1, 23, 11, '''7B226E616D65223A2250616363686574746F2070617A7A6573636F222C226465736372697074696F6E223A2270617A7A75726F642071756573746F20C3A8203330222C227072696365223A33392C2270726F64756365725F6964223A342C226974656D73223A5B7B2270726F647563745F6964223A312C227175616E74697479223A337D2C7B2270726F647563745F6964223A322C227175616E74697479223A327D2C7B2270726F647563745F6964223A342C227175616E74697479223A327D5D7D''', 1, null, '2025-09-05 11:07:41.950529', '2025-09-05 11:09:33.986445', 8, 2),
        (41, 1, 23, 12, '''7B226E616D65223A2250726F766120646F706F2063616D6269616D656E746F222C226465736372697074696F6E223A2270726F76612070726F7661222C227072696365223A312C2270726F64756365725F6964223A312C226974656D73223A5B7B2270726F647563745F6964223A312C227175616E74697479223A317D2C7B2270726F647563745F6964223A322C227175616E74697479223A327D2C7B2270726F647563745F6964223A332C227175616E74697479223A337D5D7D''', 1, null, '2025-09-05 11:10:21.221317', '2025-09-05 11:10:29.894721', 8, 2),
        (42, 1, 1, null, '''7B2263657274696669636174696F6E5F6964223A302C226E616D65223A224365727469666963617A696F6E652064692070726F7661222C226465736372697074696F6E223A224365727469666963617A696F6E652064692070726F7661206465736372697A696F6E65222C2269737375696E675F626F6479223A2249737375696E6720626F64792064692070726F7661227D''', null, null, '2025-09-05 17:04:42.179040', null, 2, 7),
        (43, 1, 1, null, '''7B2263657274696669636174696F6E5F6964223A302C226E616D65223A224365727469666963617A696F6E652064692070726F7661222C226465736372697074696F6E223A224365727469666963617A696F6E652064692070726F7661206465736372697A696F6E65222C2269737375696E675F626F6479223A2249737375696E6720626F64792064692070726F7661227D''', null, null, '2025-09-05 17:07:06.989092', null, 2, 7),
        (44, 1, 1, null, '''7B2263657274696669636174696F6E5F6964223A302C226E616D65223A224365727469666963617A696F6E652064692070726F7661222C226465736372697074696F6E223A224365727469666963617A696F6E652064692070726F7661206465736372697A696F6E65222C2269737375696E675F626F6479223A2249737375696E6720626F64792064692070726F7661227D''', null, null, '2025-09-05 17:07:41.824134', null, 2, 7),
        (45, 1, 1, 12, '''7B226E616D65223A224365727469666963617A696F6E652064692070726F76612032222C226465736372697074696F6E223A224365727469666963617A696F6E652064692070726F7661206465736372697A696F6E652032222C2269737375696E675F626F6479223A2249737375696E6720626F64792064692070726F76612032227D''', 1, null, '2025-09-05 17:08:02.780883', '2025-09-05 17:08:43.217553', 8, 7),
        (46, 1, 1, 13, '''7B226E616D65223A224365727469666963617A696F6E652064692070726F76612033222C226465736372697074696F6E223A224365727469666963617A696F6E652064692070726F7661206465736372697A696F6E652033222C2269737375696E675F626F6479223A2249737375696E6720626F64792064692070726F76612033227D''', 1, null, '2025-09-05 17:09:52.135762', '2025-09-05 17:10:00.017309', 8, 7),
        (47, 1, 1, null, '''7B227461726765745F6964223A31337D''', null, null, '2025-09-05 17:13:00.228471', null, 2, 8),
        (48, 1, 1, 13, '''7B227461726765745F6964223A31337D''', 1, null, '2025-09-05 17:13:10.809365', '2025-09-05 17:13:21.661745', 8, 8),
        (49, 18, 23, null, '''7B226E616D65223A2250726F766120636F6E207574656E7465222C226465736372697074696F6E223A2270726F7661207574656E7465222C227072696365223A312C2270726F64756365725F6964223A312C226974656D73223A5B7B2270726F647563745F6964223A312C227175616E74697479223A317D2C7B2270726F647563745F6964223A322C227175616E74697479223A327D2C7B2270726F647563745F6964223A332C227175616E74697479223A337D5D7D''', null, null, '2025-09-05 18:05:42.912501', null, 2, 2),
        (50, 23, 20, 51, '''7B2270726F647563745F6964223A6E756C6C2C226E616D65223A2276696E6F20636869616E7469222C226465736372697074696F6E223A22736173222C2263617465676F72795F6964223A352C2263756C7469766174696F6E5F6D6574686F645F6964223A342C22686172766573745F736561736F6E5F6964223A322C2270726F64756365725F6964223A317D''', 17, null, '2025-09-05 18:42:48.620641', '2025-09-05 18:46:09.987350', 8, 1),
        (82, 55, 3, 9, '''7B226E616D65223A22436F6D70616E792064692070726F7661222C2266697363616C5F636F6465223A2266697363616C636F6465646970726F7661222C226C6F636174696F6E5F6964223A312C226465736372697074696F6E223A226465736372697A696F6E652064692070726F7661222C22636F6D70616E795F747970655F6964223A317D''', 19, null, '2025-09-06 18:31:15.660046', '2025-09-06 18:36:05.385241', 8, 9);
-- Fine REQUESTS.sql

-- Inizio REQUEST_TYPES.sql
insert into PUBLIC.REQUEST_TYPES (ID, NAME, DESCRIPTION)
values  (1, 'ADD_PRODUCT', 'Creazione di un nuovo prodotto'),
        (2, 'ADD_PACKAGE', 'Creazione di un nuovo pacchetto'),
        (3, 'UPDATE_PRODUCT', 'Aggiornamento di un prodotto esistente'),
        (4, 'UPDATE_PACKAGE', 'Aggiornamento di un pacchetto esistente'),
        (5, 'DELETE_PRODUCT', 'Eliminazione (soft) di un prodotto'),
        (6, 'DELETE_PACKAGE', 'Eliminazione (soft) di un pacchetto'),
        (7, 'ADD_CERTIFICATION', 'Creazione di una nuova certificazione'),
        (8, 'DELETE_CERTIFICATION', 'Eliminazione (soft) di una certificazione'),
        (9, 'ADD_COMPANY', 'Creazione di una nuova azienda');
-- Fine REQUEST_TYPES.sql

-- Inizio SHOPPING_CART.sql
insert into PUBLIC.SHOPPING_CART (ID, USER_ID)
values  (1, 1),
        (4, 18);
-- Fine SHOPPING_CART.sql

-- Inizio SHOPPING_CART_ITEM.sql
insert into PUBLIC.SHOPPING_CART_ITEM (ID, QUANTITY, CART_ID, PRODUCT_ID, PRODUCT_LISTING_ID, TYPICAL_PACKAGE_ID)
values  (2, 2, 1, 12, null, null);
-- Fine SHOPPING_CART_ITEM.sql

-- Inizio STATUS.sql
insert into PUBLIC.STATUS (ID, NAME, DESCRIPTION)
values  (1, 'new', 'Stato iniziale di un ordine: new'),
        (2, 'pending', 'Stato di attesa di approvazione o conferma: pending'),
        (3, 'shipped', 'Ordine spedito al cliente: shipped'),
        (4, 'delivered', 'Ordine consegnato al cliente: delivered'),
        (5, 'cancelled', 'Ordine annullato: cancelled'),
        (6, 'rejected', 'Ordine rifiutato dal sistema o dall’utente: rejected'),
        (7, 'declined', 'Ordine declinato per problemi o errori: declined'),
        (8, 'accepted', 'Accettato');
-- Fine STATUS.sql

-- Inizio TYPICAL_PACKAGES.sql
insert into PUBLIC.TYPICAL_PACKAGES (ID, NAME, DESCRIPTION, PRICE, PRODUCER_ID, IS_ACTIVE)
values  (1, 'Pacchetto updated', 'Il miglior pacchetto del mondo', 100, 2, false),
        (2, 'Jonny corporation', 'Confezione di verdure biologiche miste', 30, 2, true),
        (3, 'Pacco Frutta Mista', 'Frutta di stagione selezionata', 29, 4, true),
        (4, 'Pacco Formaggi Locali', 'Selezione di formaggi tipici locali', 40, 6, true),
        (5, 'Pacco Dolci Artigianali', 'Dolci e confetture locali', 35, 7, true),
        (6, 'Pacco Gourmet', 'Prodotti gourmet e specialità regionali', 50, 1, true),
        (7, 'Pacco Essenziale', 'Prodotti base per uso quotidiano', 20, 2, true),
        (8, 'Pacco Degustazione', 'Piccolo assortimento per degustazione', 15, 4, true),
        (9, 'Pacco Storico', 'Prodotti tradizionali storici', 45, 6, false),
        (10, 'Pacco Vintage', 'Selezione di prodotti stagionali non più in produzione', 60, 7, false),
        (11, 'Pacchetto pazzesco', 'pazzurod questo è 30', 39, 4, true),
        (12, 'Prova dopo cambiamento', 'prova prova', 1, 1, true);
-- Fine TYPICAL_PACKAGES.sql

-- Inizio TYPICAL_PACKAGE_ITEMS.sql
insert into PUBLIC.TYPICAL_PACKAGE_ITEMS (ID, TYPICAL_PACKAGE_ID, PRODUCT_ID, QUANTITY)
values  (4, 2, 2, 2),
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
        (23, 8, 12, 2),
        (27, 1, 1, 3),
        (28, 1, 2, 4),
        (29, 1, 5, 2),
        (56, 11, 2, 2),
        (57, 11, 1, 3),
        (58, 11, 4, 2),
        (59, 12, 2, 2),
        (60, 12, 3, 3),
        (61, 12, 1, 1);
-- Fine TYPICAL_PACKAGE_ITEMS.sql

-- Inizio USERS.sql
insert into PUBLIC.USERS (ID, USERNAME, EMAIL, PASSWORD_HASHED, FIRST_NAME, LAST_NAME, PHONE, ROLE_ID, IS_DELETED, LOCATION_ID)
values  (1, 'mattia.curatore', 'mattia.curatore@example.com', 'hashedpwd1', 'Mattia', 'Rossi', '3331112233', 1, false, 1),
        (2, 'giulia.acquirente', 'giulia.acquirente@example.com', 'hashedpwd2', 'Giulia', 'Bianchi', '3331112234', 2, false, 1),
        (3, 'andrea.gestore', 'andrea.gestore@example.com', 'hashedpwd3', 'Andrea', 'Verdi', '3331112235', 3, false, 1),
        (4, 'lucia.animatore', 'lucia.animatore@example.com', 'hashedpwd4', 'Lucia', 'Neri', '3331112236', 4, false, 1),
        (5, 'paolo.creatore', 'paolo.creatore@example.com', 'hashedpwd5', 'Paolo', 'Gialli', '3331112237', 5, false, 1),
        (6, 'elena.curatore', 'elena.curatore@example.com', 'hashedpwd6', 'Elena', 'Arancio', '3331112240', 1, true, 1),
        (7, 'federico.acquirente', 'federico.acquirente@example.com', 'hashedpwd7', 'Federico', 'Rosa', '3331112241', 2, true, 1),
        (8, 'silvia.animatore', 'silvia.animatore@example.com', 'hashedpwd8', 'Silvia', 'Grigio', '3331112242', 4, false, 1),
        (9, 'luca.creatore', 'luca.creatore@example.com', 'hashedpwd9', 'Luca', 'Blu', '3331112243', 5, true, 1),
        (10, 'marco.gestore', 'marco.gestore@example.com', 'hashedpwd10', 'Marco', 'Viola', '3331112244', 3, false, 1),
        (11, 'utente8_1', 'utente8_1@example.com', 'hashedpwd11', 'Utente1', 'Rossi', '3331112250', 8, false, 1),
        (12, 'utente8_2', 'utente8_2@example.com', 'hashedpwd12', 'Utente2', 'Bianchi', '3331112251', 8, false, 1),
        (13, 'utente8_3', 'utente8_3@example.com', 'hashedpwd13', 'Utente3', 'Verdi', '3331112252', 8, false, 1),
        (14, 'utente8_4', 'utente8_4@example.com', 'hashedpwd14', 'Utente4', 'Neri', '3331112253', 8, false, 1),
        (15, 'MarcelloMarachello', 'secondaprova@gmail.it', '$2a$10$wnjaNjBfia25aCYEj6BIuOUqmWFfL6pk9hmgu0yfXaBOJE.LZkoIa', 'Marcello', 'Marachello', null, 1, false, 1),
        (17, 'Curatore', 'curatore@gmail.it', '$2a$10$DLGFkHjbHbuN/S4A1VOyhe9jBDh8H9PYbjjyUl/RfNOvaeEFUhfWy', 'Mario', 'Curatore', '12345678900', 1, false, 1),
        (18, 'Acquirente', 'acquirente@gmail.it', '$2a$10$SJ8SIsDNNJaxNFa3.nRWI.wXKPNP5UZG15Nz9DalrYPg0/Le5tCBG', 'Mario', 'Acquirente', '12345678900', 2, false, 1),
        (19, 'Gestore della Piattaforma', 'gestore@gmail.it', '$2a$10$O.sUfi8.b1UmnkIDtTV4buvUEIvQosAfbasFmcmodVuqyqovZsf0G', 'Mario', 'Gestore', '12345678900', 3, false, 1),
        (20, 'Animatore della Filiera', 'animaotre@gmail.it', '$2a$10$oPU057lpxunGo47b3mlK8e0Uo/pF6I2sTUBa.aW5d8fAu.SCE4/WW', 'Mario', 'Animatore', '12345678900', 4, false, 1),
        (21, 'Utente', 'utente@gmail.it', '$2a$10$ZegHyBDMzsFxbcLBJTGQgeLK8M0xtxLD.vXirY4CHCUjzLyC3jPyG', 'Mario', 'Utente', '12345678900', 8, false, 1),
        (22, 'Distributor di Tipicita', 'distributore@gmail.it', '$2a$10$39bvQITZW.NgNLcX6Bc8pOGN4K431gmcQO8MyqBDThwV06IrHMXvu', 'Mario', 'Distributore', '12345678900', 9, false, 1),
        (23, 'Fornitore', 'fornitore@gmail.it', '$2a$10$.PnXaVXuc5K0WNMLLCXlpemMpqnF4viKsOaJQUECY/teNpz0mUipu', 'Mario', 'Fornitore', '12345678900', 10, false, 1),
        (55, 'Produttore', 'produttore@gmail.it', '$2a$10$gRLuUoqBm1rH7Il7Lq4jeuK9.c/3WTKeJxnBGqSdF5neZttlKNnjO', 'Mario', 'Produttore', '12345678900', 10, false, 1),
        (56, 'Trasformatore', 'trasformatore@gmail.it', '$2a$10$232qcb8rcYwJv6XaEJMmPuTwI4TNO0Ii8ROrWeaTSUd10Xh2ATRX2', 'Mario', 'Trasformatore', '12345678900', 11, false, 1);
-- Fine USERS.sql

-- Inizio USER_ROLES.sql
insert into PUBLIC.USER_ROLES (ID, NAME, IS_ACTIVE, DESCRIPTION)
values  (1, 'CURATORE', true, 'Ruolo responsabile della gestione dei contenuti e della qualità'),
        (2, 'ACQUIRENTE', true, 'Ruolo che può acquistare prodotti o servizi sulla piattaforma'),
        (3, 'GESTORE_DELLA_PIATTAFORMA', true, 'Ruolo amministrativo con permessi completi sulla piattaforma'),
        (4, 'ANIMATORE_DELLA_FILIERA', true, 'Ruolo che promuove e coordina attività tra i partecipanti della filiera'),
        (5, 'CREATORE_DI_CONTENUTI', true, 'Ruolo che produce contenuti digitali o materiali per la piattaforma'),
        (6, 'DIPENDENTE', false, 'Ruolo obsoleto precedentemente usato'),
        (7, 'MODERATORE', false, 'Ruolo non più attivo per moderazione dei contenuti'),
        (8, 'UTENTE', true, 'Ruolo che identifica un utente generico nella piattaforma'),
        (9, 'DISTRIBUTORE_DI_TIPICITA', true, null),
        (10, 'PRODUTTORE', true, 'Chi fa il produttore'),
        (11, 'TRASFORMATORE', true, 'Chi fa il trasformatore');
-- Fine USER_ROLES.sql

