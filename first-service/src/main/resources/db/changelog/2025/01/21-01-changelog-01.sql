CREATE TABLE client_new (
    id          BIGSERIAL    PRIMARY KEY,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255)
);

INSERT INTO client_new
SELECT * FROM client;

DROP TABLE client CASCADE;

ALTER TABLE client_new RENAME TO client;

DROP SEQUENCE IF EXISTS client_seq;

ALTER TABLE account
ADD CONSTRAINT fk_client_id
FOREIGN KEY (client_id)
REFERENCES client(id);