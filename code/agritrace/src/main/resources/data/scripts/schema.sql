create table PUBLIC.CERTIFICATIONS
(
    ID           INTEGER auto_increment,
    NAME         CHARACTER VARYING(255) not null,
    DESCRIPTION  CHARACTER VARYING(255),
    ISSUING_BODY CHARACTER VARYING(255) not null,
    IS_ACTIVE    BOOLEAN default TRUE   not null,
    constraint PK_CERTIFICATIONS
        primary key (ID)
);

comment on column PUBLIC.CERTIFICATIONS.IS_ACTIVE is 'false, non attivo true, attivo, default value';

create table PUBLIC.COMPANY_TYPES
(
    ID          INTEGER auto_increment,
    NAME        CHARACTER VARYING(255) not null,
    DESCRIPTION CHARACTER VARYING(255),
    constraint PK_COMPANY_TYPES
        primary key (ID)
);

create table PUBLIC.CULTIVATION_METHODS
(
    ID          INTEGER auto_increment,
    NAME        CHARACTER VARYING(255) not null,
    DESCRIPTION CHARACTER VARYING(255),
    IS_ACTIVE   BOOLEAN default TRUE   not null,
    constraint PK_CULTIVATION_METHODS
        primary key (ID)
);

create table PUBLIC.DB_TABLES
(
    ID   INTEGER auto_increment,
    NAME CHARACTER VARYING(255) not null,
    constraint PK_TABLES
        primary key (ID)
);

create table PUBLIC.EVENT_TYPES
(
    ID          INTEGER auto_increment,
    NAME        CHARACTER VARYING(255) not null,
    DESCRIPTION CHARACTER VARYING(255),
    constraint PK_EVENT_TYPES
        primary key (ID)
);

create table PUBLIC.HARVEST_SEASONS
(
    ID          INTEGER auto_increment,
    NAME        CHARACTER VARYING(255) not null,
    MONTH_START SMALLINT               not null,
    MONTH_END   SMALLINT               not null,
    HEMISPHERE  CHARACTER VARYING(255) not null,
    DESCRIPTION CHARACTER VARYING(255),
    constraint PK_HARVEST_SEASONS
        primary key (ID)
);

comment on column PUBLIC.HARVEST_SEASONS.MONTH_START is 'Numero del mese in cui inizia la stagione, es: 03 ';

comment on column PUBLIC.HARVEST_SEASONS.MONTH_END is 'Numero del mese in cui termina la stagione, es: 03 ';

comment on column PUBLIC.HARVEST_SEASONS.HEMISPHERE is 'es. "Nord", "Sud"';

create table PUBLIC.LOCATIONS
(
    ID            INTEGER auto_increment,
    NAME          CHARACTER VARYING(255) not null,
    ADDRESS       CHARACTER VARYING(255) not null,
    STREET_NUMBER CHARACTER VARYING(255) not null,
    CITY          CHARACTER VARYING(255) not null,
    LATITUDE      NUMERIC(19),
    LONGITUDE     NUMERIC(19),
    LOCATION_TYPE CHARACTER VARYING(255),
    constraint PK_LOCATIONS
        primary key (ID)
);

comment on column PUBLIC.LOCATIONS.ADDRESS is 'Indirizzo completo';

comment on column PUBLIC.LOCATIONS.STREET_NUMBER is 'Numero della eventuale casa';

comment on column PUBLIC.LOCATIONS.LOCATION_TYPE is 'Tipo di luogo (es. “azienda”, “mercato”, “evento”, “magazzino”, ecc.)';

create table PUBLIC.PRODUCT_CATEGORIES
(
    ID          INTEGER auto_increment,
    NAME        CHARACTER VARYING(255) not null,
    DESCRIPTION CHARACTER VARYING(255),
    constraint PK_PRODUCTS_CATEGORIES
        primary key (ID)
);

create table PUBLIC.STATUS
(
    ID          INTEGER auto_increment,
    NAME        CHARACTER VARYING(255) not null,
    DESCRIPTION CHARACTER VARYING(255),
    constraint PK_STATUS
        primary key (ID)
);

create table PUBLIC.USER_ROLES
(
    ID          INTEGER auto_increment,
    NAME        CHARACTER VARYING(255) not null,
    IS_ACTIVE   BOOLEAN default TRUE   not null,
    DESCRIPTION CHARACTER VARYING(255),
    constraint PK_USER_ROLES
        primary key (ID)
);

create table PUBLIC.USERS
(
    ID              INTEGER auto_increment,
    USERNAME        CHARACTER VARYING(255) not null,
    EMAIL           CHARACTER VARYING(255) not null
        constraint UQ_USERS_EMAIL
            unique,
    PASSWORD_HASHED CHARACTER VARYING(500) not null,
    FIRST_NAME      CHARACTER VARYING(255) not null,
    LAST_NAME       CHARACTER VARYING(255) not null,
    PHONE           CHARACTER VARYING(255),
    ROLE_ID         INTEGER                not null,
    IS_DELETED      BOOLEAN default FALSE  not null,
    constraint PK_USERS
        primary key (ID),
    constraint FKUSERS711135
        foreign key (ROLE_ID) references PUBLIC.USER_ROLES
);

create table PUBLIC.CHANGE_LOGS
(
    ID           INTEGER auto_increment,
    TABLE_ID     INTEGER                             not null,
    RECORD_ID    INTEGER                             not null,
    CHANGED_BY   INTEGER                             not null,
    CHANGED_AT   TIMESTAMP default CURRENT_TIMESTAMP not null,
    CHANGED_TYPE CHARACTER(255)                      not null,
    CHANGES      JSON                                not null,
    constraint PK_CHANGE_LOGS
        primary key (ID),
    constraint FKCHANGELOGS355938
        foreign key (TABLE_ID) references PUBLIC.DB_TABLES,
    constraint FKCHANGELOGS861214
        foreign key (CHANGED_BY) references PUBLIC.USERS,
    constraint CHK_CHANGED_TYPE
        check ("CHANGED_TYPE" IN ('i', 'u', 'd'))
);

comment on column PUBLIC.CHANGE_LOGS.RECORD_ID is 'Id della riga (nella tabella) che è stata cambiata';

comment on column PUBLIC.CHANGE_LOGS.CHANGED_BY is 'Chiave dell''user che ha cambiato qualcosa';

comment on column PUBLIC.CHANGE_LOGS.CHANGED_TYPE is '"i" --> insert "u" --> update "d" --> delete';

comment on column PUBLIC.CHANGE_LOGS.CHANGES is 'Il tipo è JSON ma in realtà viene trattato come un varchar, quindi va serializzato e deserializzato a mano da stringa';

create table PUBLIC.COMPANIES
(
    ID              INTEGER auto_increment,
    OWNER_ID        INTEGER                not null,
    NAME            CHARACTER VARYING(255) not null,
    FISCAL_CODE     CHARACTER VARYING(255) not null,
    LOCATION_ID     INTEGER                not null,
    DESCRIPTION     CHARACTER VARYING(255),
    WEBSITE_URL     CHARACTER VARYING(255),
    IS_DELETED      BOOLEAN default FALSE  not null,
    COMPANY_TYPE_ID INTEGER                not null,
    constraint PK_COMPANIES
        primary key (ID),
    constraint FKCOMPANIES236745
        foreign key (COMPANY_TYPE_ID) references PUBLIC.COMPANY_TYPES,
    constraint FKCOMPANIES238615
        foreign key (LOCATION_ID) references PUBLIC.LOCATIONS,
    constraint FKCOMPANIES657217
        foreign key (OWNER_ID) references PUBLIC.USERS
);

comment on column PUBLIC.COMPANIES.OWNER_ID is 'Chiave esterna del proprietario dell''azienda';

comment on column PUBLIC.COMPANIES.LOCATION_ID is 'Chiave esterna che punta alla tabella delle location per indicare dove si colloca nel mondo';

create table PUBLIC.COMPANY_CERTIFICATION
(
    ID                 INTEGER auto_increment,
    COMPANY_ID         INTEGER                not null,
    CERTIFICATION_ID   INTEGER                not null,
    CERTIFICATE_NUMBER CHARACTER VARYING(255) not null,
    ISSUE_DATE         DATE                   not null,
    EXPIRY_DATE        DATE                   not null,
    constraint PK_COMPANIES_CERTIFICATIONS
        primary key (ID),
    constraint FKCOMPANIESC522106
        foreign key (COMPANY_ID) references PUBLIC.COMPANIES,
    constraint FKCOMPANIESC863487
        foreign key (CERTIFICATION_ID) references PUBLIC.CERTIFICATIONS
);

comment on column PUBLIC.COMPANY_CERTIFICATION.CERTIFICATE_NUMBER is 'Numero identificativo per quella certificazione';

create table PUBLIC.EVENTS
(
    ID            INTEGER auto_increment,
    ORGANIZER_ID  INTEGER                not null,
    TITLE         CHARACTER VARYING(255) not null,
    DESCRIPTION   CHARACTER VARYING(255),
    EVENT_TYPE_ID INTEGER                not null,
    START_DATE    DATE                   not null,
    END_DATE      DATE                   not null,
    LOCATION_ID   INTEGER                not null,
    IS_ACTIVE     BOOLEAN default TRUE   not null,
    constraint PK_EVENTS
        primary key (ID),
    constraint FKEVENTS27698
        foreign key (EVENT_TYPE_ID) references PUBLIC.EVENT_TYPES,
    constraint FKEVENTS361433
        foreign key (LOCATION_ID) references PUBLIC.LOCATIONS,
    constraint FKEVENTS362719
        foreign key (ORGANIZER_ID) references PUBLIC.USERS
);

create table PUBLIC.EVENT_PARTECIPANTS
(
    ID             INTEGER auto_increment,
    EVENT_ID       INTEGER                             not null,
    CREATED_AT     TIMESTAMP default CURRENT_TIMESTAMP not null,
    PARTECIPANT_ID INTEGER                             not null,
    INVITER_ID     INTEGER                             not null,
    STATUS_ID      INTEGER   default 2                 not null,
    RESPONDED_AT   TIMESTAMP,
    constraint PK_EVENT_PARTECIPANTS
        primary key (ID),
    constraint FKEVENTPARTE208364
        foreign key (STATUS_ID) references PUBLIC.STATUS,
    constraint FKEVENTPARTE362867
        foreign key (INVITER_ID) references PUBLIC.USERS,
    constraint FKEVENTPARTE450221
        foreign key (EVENT_ID) references PUBLIC.EVENTS,
    constraint FKEVENTPARTE896734
        foreign key (PARTECIPANT_ID) references PUBLIC.USERS
);

create table PUBLIC.ORDERS
(
    ID                   INTEGER auto_increment,
    BUYER_ID             INTEGER                             not null,
    SELLER_ID            INTEGER                             not null,
    TOTAL_AMOUNT         NUMERIC(19)                         not null,
    STATUS_ID            INTEGER   default 1                 not null,
    ORDERED_AT           TIMESTAMP default CURRENT_TIMESTAMP not null,
    DELIVERY_DATE        DATE,
    DELIVERY_LOCATION_ID INTEGER                             not null,
    constraint PK_ORDERS
        primary key (ID),
    constraint FKORDERS278345
        foreign key (STATUS_ID) references PUBLIC.STATUS,
    constraint FKORDERS311009
        foreign key (DELIVERY_LOCATION_ID) references PUBLIC.LOCATIONS,
    constraint FKORDERS420253
        foreign key (SELLER_ID) references PUBLIC.COMPANIES,
    constraint FKORDERS457904
        foreign key (BUYER_ID) references PUBLIC.USERS
);

comment on column PUBLIC.ORDERS.TOTAL_AMOUNT is 'Totale ordine (calcolato dalla somma degli item)';

create table PUBLIC.PRODUCTS
(
    ID                    INTEGER auto_increment,
    NAME                  CHARACTER VARYING(255) not null,
    DESCRIPTION           CHARACTER VARYING(255),
    CATEGORY_ID           INTEGER                not null,
    CULTIVATION_METHOD_ID INTEGER                not null,
    HARVEST_SEASON_ID     INTEGER                not null,
    IS_DELETED            BOOLEAN default FALSE  not null,
    PRODUCER_ID           INTEGER                not null,
    constraint PK_PRODUCTS
        primary key (ID),
    constraint FKPRODUCTS333592
        foreign key (HARVEST_SEASON_ID) references PUBLIC.HARVEST_SEASONS,
    constraint FKPRODUCTS372854
        foreign key (PRODUCER_ID) references PUBLIC.COMPANIES,
    constraint FKPRODUCTS832433
        foreign key (CATEGORY_ID) references PUBLIC.PRODUCT_CATEGORIES,
    constraint FKPRODUCTS967807
        foreign key (CULTIVATION_METHOD_ID) references PUBLIC.CULTIVATION_METHODS
);

create table PUBLIC.PRODUCTION_STEPS
(
    ID                INTEGER auto_increment,
    INPUT_PRODUCT_ID  INTEGER                not null,
    COMPANY_ID        INTEGER                not null,
    STEP_TYPE         CHARACTER VARYING(255) not null,
    DESCRIPTION       CHARACTER VARYING(255),
    STEP_DATE         DATE,
    LOCATION_ID       INTEGER,
    OUTPUT_PRODUCT_ID INTEGER,
    constraint PK_PRODUCTION_STEPS
        primary key (ID),
    constraint FKPRODUCTION159907
        foreign key (COMPANY_ID) references PUBLIC.COMPANIES,
    constraint FKPRODUCTION372810
        foreign key (OUTPUT_PRODUCT_ID) references PUBLIC.PRODUCTS,
    constraint FKPRODUCTION590696
        foreign key (INPUT_PRODUCT_ID) references PUBLIC.PRODUCTS,
    constraint FKPRODUCTION797821
        foreign key (LOCATION_ID) references PUBLIC.LOCATIONS
);

comment on column PUBLIC.PRODUCTION_STEPS.STEP_TYPE is 'Tipo di passaggio (es. “produzione”, “trasformazione”, "confezionamento", "vendita")';

comment on column PUBLIC.PRODUCTION_STEPS.LOCATION_ID is 'Dove è stato fatto, può essere omesso';

create table PUBLIC.PRODUCT_CERTIFICATION
(
    ID                 INTEGER auto_increment,
    PRODUCT_ID         INTEGER not null,
    CERTIFICATION_ID   INTEGER not null,
    CERTIFICATE_NUMBER CHARACTER VARYING(255),
    ISSUE_DATE         DATE    not null,
    EXPIRY_DATE        DATE,
    constraint PK_PRODUCTS_CERTIFICATIONS
        primary key (ID),
    constraint FKPRODUCTSCE881021
        foreign key (CERTIFICATION_ID) references PUBLIC.CERTIFICATIONS,
    constraint FKPRODUCTSCE935361
        foreign key (PRODUCT_ID) references PUBLIC.PRODUCTS
);

create table PUBLIC.PRODUCT_LISTINGS
(
    ID                 INTEGER auto_increment,
    PRODUCT_ID         INTEGER              not null,
    SELLER_ID          INTEGER              not null,
    PRICE              NUMERIC(19)          not null,
    QUANTITY_AVAILABLE INTEGER default 0    not null,
    UNIT_OF_MEASURE    CHARACTER VARYING(255),
    IS_ACTIVE          BOOLEAN default TRUE not null,
    constraint PK_PRODUCTS_LISTING
        primary key (ID),
    constraint FKPRODUCTSLI165062
        foreign key (SELLER_ID) references PUBLIC.COMPANIES,
    constraint FKPRODUCTSLI376923
        foreign key (PRODUCT_ID) references PUBLIC.PRODUCTS
);

comment on column PUBLIC.PRODUCT_LISTINGS.UNIT_OF_MEASURE is 'Unità di misura di quel prodotto Es. kg, litri, pezzi';

create table PUBLIC.ORDER_ITEMS
(
    ID                 INTEGER auto_increment,
    ORDER_ID           INTEGER     not null,
    PRODUCT_LISTING_ID INTEGER     not null,
    QUANTITY           INTEGER     not null,
    UNIT_PRICE         NUMERIC(19) not null,
    TOTAL_PRICE        NUMERIC(19) not null,
    constraint PK_ORDER_ITEMS
        primary key (ID),
    constraint FKORDERITEMS729798
        foreign key (PRODUCT_LISTING_ID) references PUBLIC.PRODUCT_LISTINGS,
    constraint FKORDERITEMS960372
        foreign key (ORDER_ID) references PUBLIC.ORDERS
);

comment on column PUBLIC.ORDER_ITEMS.UNIT_PRICE is 'Prezzo unitario AL MOMENTO DELL''ORDINE (che potrebbe essere diverso da quello attuale)';

comment on column PUBLIC.ORDER_ITEMS.TOTAL_PRICE is 'Prezzo totale = quantity * unit_price';

create table PUBLIC.PRODUCT_REVIEWS
(
    ID         INTEGER auto_increment,
    PRODUCT_ID INTEGER not null,
    USER_ID    INTEGER not null,
    RATING     INTEGER not null,
    COMMENT    CHARACTER VARYING(255),
    constraint PK_PRODUCTS_REVIEWS
        primary key (ID),
    constraint FKPRODUCTSRE355336
        foreign key (PRODUCT_ID) references PUBLIC.PRODUCTS,
    constraint FKPRODUCTSRE355937
        foreign key (USER_ID) references PUBLIC.USERS
);

comment on column PUBLIC.PRODUCT_REVIEWS.RATING is 'Voto del prodotto dell''utente ad esempio 0-5 stelle o 0/10.';

create table PUBLIC.REQUESTS
(
    ID              INTEGER auto_increment,
    REQUESTER_ID    INTEGER                             not null,
    TARGET_TABLE_ID INTEGER                             not null,
    TARGET_ID       INTEGER,
    REQUEST_TYPE    CHARACTER                           not null,
    PAYLOAD         JSON                                not null,
    CURATOR_ID      INTEGER,
    DECISION_NOTES  CHARACTER VARYING(255),
    CREATED_AT      TIMESTAMP default CURRENT_TIMESTAMP not null,
    REVIEWED_AT     TIMESTAMP,
    STATUS_ID       INTEGER   default 2                 not null,
    constraint PK_REQUESTS
        primary key (ID),
    constraint FKREQUESTS161355
        foreign key (REQUESTER_ID) references PUBLIC.USERS,
    constraint FKREQUESTS283647
        foreign key (STATUS_ID) references PUBLIC.STATUS,
    constraint FKREQUESTS52014
        foreign key (CURATOR_ID) references PUBLIC.USERS,
    constraint FKREQUESTS941478
        foreign key (TARGET_TABLE_ID) references PUBLIC.DB_TABLES,
    constraint CHK_REQUEST_TYPE
        check ("REQUEST_TYPE" IN ('u', 'c', 'd'))
);

comment on column PUBLIC.REQUESTS.TARGET_ID is 'Id del record della tabella che deve essere modificato';

comment on column PUBLIC.REQUESTS.DECISION_NOTES is 'Descrizione del perchè di quell''azione per quella richiesta, motivazioni, commenti, ecc..';

create table PUBLIC.TYPICAL_PACKAGES
(
    ID          INTEGER auto_increment,
    NAME        CHARACTER VARYING(255) not null,
    DESCRIPTION CHARACTER VARYING(255),
    PRICE       NUMERIC(19)            not null,
    PRODUCER_ID INTEGER                not null,
    IS_ACTIVE   BOOLEAN default TRUE   not null,
    constraint PK_TYPICAL_PACKAGES
        primary key (ID),
    constraint FKTYPICALPAC633778
        foreign key (PRODUCER_ID) references PUBLIC.COMPANIES
);

create table PUBLIC.TYPICAL_PACKAGE_ITEMS
(
    ID                 INTEGER auto_increment,
    TYPICAL_PACKAGE_ID INTEGER not null,
    PRODUCT_ID         INTEGER not null,
    QUANTITY           INTEGER not null,
    constraint PK_TYPICAL_PACKAGES_ITEMS
        primary key (ID),
    constraint FKTYPICALPAC412048
        foreign key (TYPICAL_PACKAGE_ID) references PUBLIC.TYPICAL_PACKAGES,
    constraint FKTYPICALPAC820231
        foreign key (PRODUCT_ID) references PUBLIC.PRODUCTS
);

