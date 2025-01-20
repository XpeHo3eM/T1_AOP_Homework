ALTER TABLE client
ALTER COLUMN id SET DEFAULT nextval('client_seq');

ALTER TABLE account
ALTER COLUMN id SET DEFAULT nextval('account_seq');

ALTER TABLE transaction
ALTER COLUMN id SET DEFAULT nextval('transaction_seq');

ALTER TABLE data_source_error_log
ALTER COLUMN id SET DEFAULT nextval('data_source_error_log_seq');