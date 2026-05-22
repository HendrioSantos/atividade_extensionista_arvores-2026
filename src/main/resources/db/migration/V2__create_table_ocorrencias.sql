CREATE TABLE ocorrencias(
    id BIGSERIAL PRIMARY KEY,
    tipo_dano varchar(50) NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    endereco VARCHAR(255),
    url_foto VARCHAR(255) NOT NULL,
    descricao TEXT,
    data_registro TIMESTAMP NOT NULL,
    status_ocorrencia VARCHAR(50) NOT NULL,
    slug VARCHAR(255) NOT NULL,
    usuario_id BIGINT NOT NULL,

    CONSTRAINT fk_ocorrencias_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);