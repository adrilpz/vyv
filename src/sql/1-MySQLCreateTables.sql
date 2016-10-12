-- Indexes for primary keys have been explicitly created.

-- ---------- Table for validation queries from the connection pool. ----------

DROP TABLE PingTable;
CREATE TABLE PingTable (foo CHAR(1));

--------------------------------------------------------------------------------
DROP TABLE Apuesta;
DROP TABLE OpcionApuesta;
DROP TABLE TipoApuesta;
DROP TABLE Evento;
DROP TABLE Categoria;
DROP TABLE UserProfile;
-- ------------------------------ UserProfile ----------------------------------

CREATE TABLE UserProfile (
    usrId BIGINT NOT NULL AUTO_INCREMENT,
    loginName VARCHAR(30) COLLATE latin1_bin NOT NULL,
    enPassword VARCHAR(13) NOT NULL, 
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(40) NOT NULL, email VARCHAR(60) NOT NULL,
    CONSTRAINT UserProfile_PK PRIMARY KEY (usrId),
    CONSTRAINT LoginNameUniqueKey UNIQUE (loginName)) 
    ENGINE = InnoDB;

CREATE INDEX UserProfileIndexByLoginName ON UserProfile (loginName);

-- ------------------------------- Categoria -----------------------------------

CREATE TABLE Categoria ( 
	nombre VARCHAR(30) COLLATE latin1_bin NOT NULL,
	codCategoria BIGINT NOT NULL AUTO_INCREMENT,
	CONSTRAINT CategoriaPK PRIMARY KEY(codCategoria))ENGINE = InnoDB;

-- -------------------------------- Evento -------------------------------------

CREATE TABLE Evento ( 
	nombre VARCHAR(30) COLLATE latin1_bin NOT NULL,
	fecha TIMESTAMP NOT NULL,
	codCategoria BIGINT NOT NULL,
	codEvento BIGINT NOT NULL AUTO_INCREMENT,
	CONSTRAINT EventoPK PRIMARY KEY(codEvento),
 	CONSTRAINT EventoCodCategoriaFK FOREIGN KEY(codCategoria)
        	REFERENCES Categoria (codCategoria))ENGINE = InnoDB;

-- ------------------------------ TipoApuesta ----------------------------------

CREATE TABLE TipoApuesta ( 
	pregunta VARCHAR(30) COLLATE latin1_bin NOT NULL,
	multiple BIT NOT NULL,
	codTipoApuesta BIGINT NOT NULL AUTO_INCREMENT,
	codEvento BIGINT NOT NULL, 
	CONSTRAINT TipoApuestaPK PRIMARY KEY(codTipoApuesta),
	CONSTRAINT TipoApuestaCodEventoFK FOREIGN KEY(codEvento)
		REFERENCES Evento (codEvento)) ENGINE = InnoDB;

-- ----------------------------- OpcionApuesta ---------------------------------

CREATE TABLE OpcionApuesta ( 
	resultado VARCHAR(30) NOT NULL,
	cuota FLOAT NOT NULL,
	codOpcionApuesta BIGINT NOT NULL AUTO_INCREMENT,
	ganadora BIT,
	codTipoApuesta BIGINT NOT NULL,
	CONSTRAINT OpcionApuestaPK PRIMARY KEY(codOpcionApuesta),
	CONSTRAINT OpcionApuestaCodTipoApuestaFK FOREIGN KEY(codTipoApuesta)
		REFERENCES TipoApuesta (codTipoApuesta),
	CONSTRAINT VALIDcuota CHECK ( cuota >= 0)) ENGINE = InnoDB;

-- -------------------------------- Apuesta ------------------------------------

CREATE TABLE Apuesta ( 
	codOpcionApuesta BIGINT NOT NULL,
	cantidad FLOAT NOT NULL,
	codApuesta BIGINT NOT NULL AUTO_INCREMENT,
	usrId BIGINT NOT NULL,
    	fechaApuesta TIMESTAMP NOT NULL,
	CONSTRAINT ApuestaPK PRIMARY KEY(codApuesta),
	CONSTRAINT ApuestaCodOpcionApuestaFK FOREIGN KEY(codOpcionApuesta)
		REFERENCES OpcionApuesta (codOpcionApuesta),
	CONSTRAINT ApuestaUsrIdFK FOREIGN KEY(usrId)
		REFERENCES UserProfile (usrId),
	CONSTRAINT VALIDCantidad CHECK ( cantidad >= 0)) ENGINE = InnoDB;

