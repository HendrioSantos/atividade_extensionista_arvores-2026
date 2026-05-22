CREATE TABLE usuarios(
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(255) UNIQUE,
    senha VARCHAR(255),
    identificador_guest VARCHAR(255) UNIQUE,
    perfil VARCHAR(30) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);