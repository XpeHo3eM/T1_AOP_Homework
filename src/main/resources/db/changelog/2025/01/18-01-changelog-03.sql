CREATE SEQUENCE IF NOT EXISTS data_source_error_log_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS data_source_error_log (
    id          BIGINT       PRIMARY KEY,
    stack_trace VARCHAR(255) NOT NULL,
    message     VARCHAR(255) NOT NULL,
    signature   VARCHAR(255) NOT NULL
);