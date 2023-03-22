-- Skript for å opprete databasen og legge inn data
	-- Skjema = oblig3
		-- Tabeller = Ansatt, Avdeling, ProsjektDeltakelse, Prosjekt

DROP SCHEMA IF EXISTS oblig3 CASCADE;
CREATE SCHEMA oblig3;
SET search_path TO oblig3;
    
-- Opprette tabeller
CREATE TABLE Ansatt (
    AnsattId SERIAL,
    Brukernavn CHAR(3) UNIQUE NOT NULL,
    Fornavn VARCHAR(30) NOT NULL,
    Etternavn VARCHAR(50) NOT NULL,
    Ansettelsesdato DATE NOT NULL,
    Stilling VARCHAR(20) NOT NULL,
    Maanedslonn FLOAT NOT NULL,
    AvdelingId INTEGER NOT NULL,
    CONSTRAINT AnsattPK PRIMARY KEY(AnsattId)
);

CREATE TABLE Avdeling (
	AvdelingId SERIAL,
	Navn VARCHAR(50),
	Sjef INTEGER,
	CONSTRAINT AvdelingPK PRIMARY KEY(AvdelingId),
	CONSTRAINT SjefFK FOREIGN KEY(Sjef)
		REFERENCES Ansatt(AnsattId)
);

CREATE TABLE ProsjektDeltakelse (
	AnsattId Integer NOT NULL,
	ProsjektId Integer NOT NULL,
	Timer Integer,
	CONSTRAINT ProsjektDeltakelsePK PRIMARY KEY(AnsattId, ProsjektId),
	CONSTRAINT AnsattFK FOREIGN KEY(AnsattId)
		REFERENCES Ansatt(AnsattId),
	CONSTRAINT ProsjektFK FOREIGN KEY(ProsjektId)
		REFERENCES Prosjekt(ProsjektId)
);

CREATE TABLE Prosjekt (
	ProsjektId SERIAL,
	Navn VARCHAR(50) NOT NULL,
	Beskrivelse VARCHAR(300),
	CONSTRAINT ProsjektPK PRIMARY KEY(ProsjektId),
	CONSTRAINT ProsjektDeltakelseFK FOREIGN KEY(ProsjektId)
		REFERENCES ProsjektDeltakelse(ProsjektId)
);

-- Set in foreign keys etter oprettet tabeller fordi skript sliter med å kjøre i eclipseDTP
ALTER TABLE Ansatt (
    CONSTRAINT AvdelingFK FOREIGN KEY(AvdelingId) 
        REFERENCES Avdeling(AvdelingId),
    CONSTRAINT ProsjektDeltakelseFK FOREIGN KEY(AnsattId) 
        REFERENCES ProsjektDeltakelse(AnsattId)
);

-- Legg inn noe data
INSERT INTO 
	Ansatt(Brukernavn, Fornavn, Etternavn, Ansettelsesdato, Stilling, Maanedslonn, AvdelingId)
VALUES 
    ('abc', 'John', 'Doe', '2022-01-01', 'Sjef', 50000, 1),
    ('def', 'Jane', 'Doe', '2022-01-01', 'Selger', 40000, 3),
    ('ghi', 'Bob', 'Smith', '2022-01-01', 'Regnskapskonsulent', 45000, 4),
    ('jkl', 'Sara', 'Johnson', '2022-01-01', 'Utvikler', 55000, 2),
    ('mno', 'Tom', 'Williams', '2022-01-01', 'Utvikler', 60000, 2);
  
INSERT INTO 
	Avdeling(Navn, Sjef)
VALUES 
	('Administrasjon', 1),
    ('IT', 5),
    ('Salg', 2),
    ('Regnskap', 3);
	
INSERT INTO 
	ProsjektDeltakelse(AnsattId, ProsjektId, Timer)
VALUES 
    (1, 1, 120),
    (2, 2, 203),
    (2, 3, 130),
    (4, 2, 240),
    (5, 2, 350),
	(1, 2, 100),
    (1, 3, 2000),
    (3, 2, 300);
	
INSERT INTO 
	Prosjekt(Navn, Beskrivelse)
VALUES 
    ('Utbygging', 'Utbygging av bedrift'),
    ('Apputvikling', 'Utvikling av app for bedrift klienter'),
    ('Classified', 'Restriced');
	
-- ?? Sjekk at det funker ??
SELECT * FROM oblig3;
