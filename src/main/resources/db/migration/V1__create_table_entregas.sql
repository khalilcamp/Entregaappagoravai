CREATE TABLE entregas (
    id                  BIGSERIAL PRIMARY KEY,
    codigo_rastreamento VARCHAR(50)    NOT NULL UNIQUE,
    data_envio          DATE           NOT NULL,
    data_entrega        DATE,
    valor_frete         DECIMAL(10, 2) NOT NULL,
    destinatario        VARCHAR(100)   NOT NULL,
    endereco            VARCHAR(255)   NOT NULL,
    status              VARCHAR(30)    NOT NULL
);