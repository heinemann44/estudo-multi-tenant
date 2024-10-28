CREATE SCHEMA IF NOT EXISTS "tenant-1";
CREATE SCHEMA IF NOT EXISTS "tenant-2";
CREATE SCHEMA IF NOT EXISTS "tenant-3";
CREATE SCHEMA IF NOT EXISTS "tenant-4";
CREATE SCHEMA IF NOT EXISTS "tenant-5";
CREATE SCHEMA IF NOT EXISTS "tenant-6";
CREATE SCHEMA IF NOT EXISTS "tenant-7";
CREATE SCHEMA IF NOT EXISTS "tenant-8";
CREATE SCHEMA IF NOT EXISTS "tenant-9";
CREATE SCHEMA IF NOT EXISTS "tenant-10";
CREATE SCHEMA IF NOT EXISTS "tenant-11";
CREATE SCHEMA IF NOT EXISTS "tenant-12";
CREATE SCHEMA IF NOT EXISTS "tenant-13";
CREATE SCHEMA IF NOT EXISTS "tenant-14";
CREATE SCHEMA IF NOT EXISTS "tenant-15";

CREATE EXTENSION PG_TRGM;

CREATE TABLE IF NOT EXISTS "tenant-1".PESSOAS (
    ID UUID CONSTRAINT ID_PK PRIMARY KEY,
    APELIDO VARCHAR(50) UNIQUE,
    NOME VARCHAR(100),
    NASCIMENTO CHAR(10),
    STACK TEXT,
    BUSCA_TRGM TEXT GENERATED ALWAYS AS ( LOWER(NOME || APELIDO || STACK) ) STORED
);

CREATE INDEX CONCURRENTLY IF NOT EXISTS IDX_PESSOAS_BUSCA_TGRM ON "tenant-1".PESSOAS USING GIST (BUSCA_TRGM GIST_TRGM_OPS(SIGLEN=64));

CREATE TABLE IF NOT EXISTS "tenant-2".PESSOAS (
    ID UUID CONSTRAINT ID_PK PRIMARY KEY,
    APELIDO VARCHAR(50) UNIQUE,
    NOME VARCHAR(100),
    NASCIMENTO CHAR(10),
    STACK TEXT,
    BUSCA_TRGM TEXT GENERATED ALWAYS AS ( LOWER(NOME || APELIDO || STACK) ) STORED
);

CREATE INDEX CONCURRENTLY IF NOT EXISTS IDX_PESSOAS_BUSCA_TGRM ON "tenant-2".PESSOAS USING GIST (BUSCA_TRGM GIST_TRGM_OPS(SIGLEN=64));

CREATE TABLE IF NOT EXISTS "tenant-3".PESSOAS (
    ID UUID CONSTRAINT ID_PK PRIMARY KEY,
    APELIDO VARCHAR(50) UNIQUE,
    NOME VARCHAR(100),
    NASCIMENTO CHAR(10),
    STACK TEXT,
    BUSCA_TRGM TEXT GENERATED ALWAYS AS ( LOWER(NOME || APELIDO || STACK) ) STORED
);

CREATE INDEX CONCURRENTLY IF NOT EXISTS IDX_PESSOAS_BUSCA_TGRM ON "tenant-3".PESSOAS USING GIST (BUSCA_TRGM GIST_TRGM_OPS(SIGLEN=64));

CREATE TABLE IF NOT EXISTS "tenant-4".PESSOAS (
    ID UUID CONSTRAINT ID_PK PRIMARY KEY,
    APELIDO VARCHAR(50) UNIQUE,
    NOME VARCHAR(100),
    NASCIMENTO CHAR(10),
    STACK TEXT,
    BUSCA_TRGM TEXT GENERATED ALWAYS AS ( LOWER(NOME || APELIDO || STACK) ) STORED
);

CREATE INDEX CONCURRENTLY IF NOT EXISTS IDX_PESSOAS_BUSCA_TGRM ON "tenant-4".PESSOAS USING GIST (BUSCA_TRGM GIST_TRGM_OPS(SIGLEN=64));

CREATE TABLE IF NOT EXISTS "tenant-5".PESSOAS (
    ID UUID CONSTRAINT ID_PK PRIMARY KEY,
    APELIDO VARCHAR(50) UNIQUE,
    NOME VARCHAR(100),
    NASCIMENTO CHAR(10),
    STACK TEXT,
    BUSCA_TRGM TEXT GENERATED ALWAYS AS ( LOWER(NOME || APELIDO || STACK) ) STORED
);

CREATE INDEX CONCURRENTLY IF NOT EXISTS IDX_PESSOAS_BUSCA_TGRM ON "tenant-5".PESSOAS USING GIST (BUSCA_TRGM GIST_TRGM_OPS(SIGLEN=64));

CREATE TABLE IF NOT EXISTS "tenant-6".PESSOAS (
    ID UUID CONSTRAINT ID_PK PRIMARY KEY,
    APELIDO VARCHAR(50) UNIQUE,
    NOME VARCHAR(100),
    NASCIMENTO CHAR(10),
    STACK TEXT,
    BUSCA_TRGM TEXT GENERATED ALWAYS AS ( LOWER(NOME || APELIDO || STACK) ) STORED
);

CREATE INDEX CONCURRENTLY IF NOT EXISTS IDX_PESSOAS_BUSCA_TGRM ON "tenant-6".PESSOAS USING GIST (BUSCA_TRGM GIST_TRGM_OPS(SIGLEN=64));

CREATE TABLE IF NOT EXISTS "tenant-7".PESSOAS (
    ID UUID CONSTRAINT ID_PK PRIMARY KEY,
    APELIDO VARCHAR(50) UNIQUE,
    NOME VARCHAR(100),
    NASCIMENTO CHAR(10),
    STACK TEXT,
    BUSCA_TRGM TEXT GENERATED ALWAYS AS ( LOWER(NOME || APELIDO || STACK) ) STORED
);

CREATE INDEX CONCURRENTLY IF NOT EXISTS IDX_PESSOAS_BUSCA_TGRM ON "tenant-7".PESSOAS USING GIST (BUSCA_TRGM GIST_TRGM_OPS(SIGLEN=64));

CREATE TABLE IF NOT EXISTS "tenant-8".PESSOAS (
    ID UUID CONSTRAINT ID_PK PRIMARY KEY,
    APELIDO VARCHAR(50) UNIQUE,
    NOME VARCHAR(100),
    NASCIMENTO CHAR(10),
    STACK TEXT,
    BUSCA_TRGM TEXT GENERATED ALWAYS AS ( LOWER(NOME || APELIDO || STACK) ) STORED
);

CREATE INDEX CONCURRENTLY IF NOT EXISTS IDX_PESSOAS_BUSCA_TGRM ON "tenant-8".PESSOAS USING GIST (BUSCA_TRGM GIST_TRGM_OPS(SIGLEN=64));

CREATE TABLE IF NOT EXISTS "tenant-9".PESSOAS (
    ID UUID CONSTRAINT ID_PK PRIMARY KEY,
    APELIDO VARCHAR(50) UNIQUE,
    NOME VARCHAR(100),
    NASCIMENTO CHAR(10),
    STACK TEXT,
    BUSCA_TRGM TEXT GENERATED ALWAYS AS ( LOWER(NOME || APELIDO || STACK) ) STORED
);

CREATE INDEX CONCURRENTLY IF NOT EXISTS IDX_PESSOAS_BUSCA_TGRM ON "tenant-9".PESSOAS USING GIST (BUSCA_TRGM GIST_TRGM_OPS(SIGLEN=64));

CREATE TABLE IF NOT EXISTS "tenant-10".PESSOAS (
    ID UUID CONSTRAINT ID_PK PRIMARY KEY,
    APELIDO VARCHAR(50) UNIQUE,
    NOME VARCHAR(100),
    NASCIMENTO CHAR(10),
    STACK TEXT,
    BUSCA_TRGM TEXT GENERATED ALWAYS AS ( LOWER(NOME || APELIDO || STACK) ) STORED
);

CREATE INDEX CONCURRENTLY IF NOT EXISTS IDX_PESSOAS_BUSCA_TGRM ON "tenant-10".PESSOAS USING GIST (BUSCA_TRGM GIST_TRGM_OPS(SIGLEN=64));

CREATE TABLE IF NOT EXISTS "tenant-11".PESSOAS (
    ID UUID CONSTRAINT ID_PK PRIMARY KEY,
    APELIDO VARCHAR(50) UNIQUE,
    NOME VARCHAR(100),
    NASCIMENTO CHAR(10),
    STACK TEXT,
    BUSCA_TRGM TEXT GENERATED ALWAYS AS ( LOWER(NOME || APELIDO || STACK) ) STORED
);

CREATE INDEX CONCURRENTLY IF NOT EXISTS IDX_PESSOAS_BUSCA_TGRM ON "tenant-11".PESSOAS USING GIST (BUSCA_TRGM GIST_TRGM_OPS(SIGLEN=64));

CREATE TABLE IF NOT EXISTS "tenant-12".PESSOAS (
    ID UUID CONSTRAINT ID_PK PRIMARY KEY,
    APELIDO VARCHAR(50) UNIQUE,
    NOME VARCHAR(100),
    NASCIMENTO CHAR(10),
    STACK TEXT,
    BUSCA_TRGM TEXT GENERATED ALWAYS AS ( LOWER(NOME || APELIDO || STACK) ) STORED
);

CREATE INDEX CONCURRENTLY IF NOT EXISTS IDX_PESSOAS_BUSCA_TGRM ON "tenant-12".PESSOAS USING GIST (BUSCA_TRGM GIST_TRGM_OPS(SIGLEN=64));

CREATE TABLE IF NOT EXISTS "tenant-13".PESSOAS (
    ID UUID CONSTRAINT ID_PK PRIMARY KEY,
    APELIDO VARCHAR(50) UNIQUE,
    NOME VARCHAR(100),
    NASCIMENTO CHAR(10),
    STACK TEXT,
    BUSCA_TRGM TEXT GENERATED ALWAYS AS ( LOWER(NOME || APELIDO || STACK) ) STORED
);

CREATE INDEX CONCURRENTLY IF NOT EXISTS IDX_PESSOAS_BUSCA_TGRM ON "tenant-13".PESSOAS USING GIST (BUSCA_TRGM GIST_TRGM_OPS(SIGLEN=64));

CREATE TABLE IF NOT EXISTS "tenant-14".PESSOAS (
    ID UUID CONSTRAINT ID_PK PRIMARY KEY,
    APELIDO VARCHAR(50) UNIQUE,
    NOME VARCHAR(100),
    NASCIMENTO CHAR(10),
    STACK TEXT,
    BUSCA_TRGM TEXT GENERATED ALWAYS AS ( LOWER(NOME || APELIDO || STACK) ) STORED
);

CREATE INDEX CONCURRENTLY IF NOT EXISTS IDX_PESSOAS_BUSCA_TGRM ON "tenant-14".PESSOAS USING GIST (BUSCA_TRGM GIST_TRGM_OPS(SIGLEN=64));

CREATE TABLE IF NOT EXISTS "tenant-15".PESSOAS (
    ID UUID CONSTRAINT ID_PK PRIMARY KEY,
    APELIDO VARCHAR(50) UNIQUE,
    NOME VARCHAR(100),
    NASCIMENTO CHAR(10),
    STACK TEXT,
    BUSCA_TRGM TEXT GENERATED ALWAYS AS ( LOWER(NOME || APELIDO || STACK) ) STORED
);

CREATE INDEX CONCURRENTLY IF NOT EXISTS IDX_PESSOAS_BUSCA_TGRM ON "tenant-15".PESSOAS USING GIST (BUSCA_TRGM GIST_TRGM_OPS(SIGLEN=64));