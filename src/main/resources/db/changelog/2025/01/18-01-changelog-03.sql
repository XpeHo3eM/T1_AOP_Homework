CREATE TABLE IF NOT EXISTS data_source_error_log (
    id          BIGSERIAL    PRIMARY KEY,
    stack_trace VARCHAR(255) NOT NULL,
    message     VARCHAR(255) NOT NULL,
    signature   VARCHAR(255) NOT NULL
);