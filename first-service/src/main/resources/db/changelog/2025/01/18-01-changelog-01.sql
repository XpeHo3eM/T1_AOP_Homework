CREATE TABLE IF NOT EXISTS account (
    id        BIGSERIAL      PRIMARY KEY,
    client_id BIGINT         NOT NULL REFERENCES client(id) ON DELETE CASCADE,
    type      VARCHAR(255)   NOT NULL,
    balance   NUMERIC(15, 2) NOT NULL DEFAULT 0.00
);