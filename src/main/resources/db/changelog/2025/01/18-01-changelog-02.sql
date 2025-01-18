CREATE TABLE IF NOT EXISTS transaction (
    id         BIGSERIAL      PRIMARY KEY,
    account_id BIGINT         NOT NULL REFERENCES account(id) ON DELETE CASCADE,
    amount     NUMERIC(15, 2) NOT NULL,
    create_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);