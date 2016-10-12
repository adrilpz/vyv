-- ----------------------------------------------------------------------------
-- Model
-------------------------------------------------------------------------------

-- ---------- Table for validation queries from the connection pool -----------

DROP TABLE PingTable;
CREATE TABLE PingTable (foo CHAR(1));

-- ---------- Drop tables by the correct order -----------

DROP TABLE RESERVA;
DROP TABLE OFERTA;

-- ---------- Create table OFERTA -----------

CREATE TABLE OFERTA
 ( COD_OFERTA BIGINT NOT NULL AUTO_INCREMENT,
   NOMBRE VARCHAR(50) COLLATE latin1_bin NOT NULL,
   DESCRIPCION VARCHAR(1024) COLLATE latin1_bin NOT NULL,
   FECHA_LIMITE_OFERTA TIMESTAMP DEFAULT 0 NOT NULL,
   FECHA_LIMITE_RESERVA TIMESTAMP DEFAULT 0 NOT NULL,
   PRECIO_REAL FLOAT NOT NULL,
   PRECIO_DESCONTADO FLOAT NOT NULL,
   COMISION_VENTA FLOAT NOT NULL,
   VALIDA BIT NOT NULL,
   CONSTRAINT OFERTAPK PRIMARY KEY (COD_OFERTA),
   CONSTRAINT VALIDPRICE CHECK ( PRECIO_REAL >= 0),
   CONSTRAINT VALIDPRICE2 CHECK ( PRECIO_DESCONTADO >= 0),
   CONSTRAINT VALIDPRICE3 CHECK ( COMISION_VENTA >= 0)
 ) ENGINE = InnoDB;

-- ---------- Create table RESERVA -----------

CREATE TABLE RESERVA
 ( COD_RESERVA BIGINT NOT NULL AUTO_INCREMENT,
   COD_OFERTA BIGINT NOT NULL,
   EMAIL VARCHAR(30) NOT NULL,
   TARJETA_CREDITO VARCHAR(16) NOT NULL,
   ESTADO VARCHAR(9) NOT NULL,
   FECHA_RESERVA TIMESTAMP DEFAULT 0 NOT NULL,
   PRECIO_OFERTA FLOAT NOT NULL,
   CONSTRAINT RESERVAPK PRIMARY KEY(COD_RESERVA),
   CONSTRAINT OFERTAFK FOREIGN KEY(COD_OFERTA)
    REFERENCES OFERTA(COD_OFERTA) ON DELETE CASCADE
 ) ENGINE = InnoDB;