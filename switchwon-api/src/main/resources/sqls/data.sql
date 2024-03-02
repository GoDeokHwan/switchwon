-- user
INSERT INTO users(name) VALUES ('John');
INSERT INTO users(name) VALUES ('Benjamin');
INSERT INTO users(name) VALUES ('Oliver');
INSERT INTO users(name) VALUES ('Theodore');
INSERT INTO users(name) VALUES ('Alexander');
INSERT INTO users(name) VALUES ('Jameson');

-- merchant
INSERT INTO merchants(name) VALUES ('성수카페');
INSERT INTO merchants(name) VALUES ('오그가게');
INSERT INTO merchants(name) VALUES ('소녀방앗간');
INSERT INTO merchants(name) VALUES ('골목길다방');

-- policy
INSERT INTO policy_fees(fees, start_date, end_date) VALUES (0.05, '1999-01-01T00:00:00', '2999-12-31T23:59:59');
INSERT INTO policy_fees(fees, start_date, end_date) VALUES (0.03, '2024-02-01T00:00:00', '2024-03-31T23:59:59');

-- payment_accounts
INSERT INTO payment_accounts(users_id, balance, currency) VALUES (1, 1000.0, 'USD');
INSERT INTO payment_accounts(users_id, balance, currency) VALUES (3, 1000.0, 'USD');

-- payment_methods
INSERT INTO payment_methods(users_id, payment_method, card_number, expiry_date, cvv) VALUES (1, 'CREDIT_CARD', '1234-5678-9123-4567', '12/24', '123');
INSERT INTO payment_methods(users_id, payment_method, card_number, expiry_date, cvv) VALUES (3, 'CREDIT_CARD', '1234-5678-9123-4567', '12/24', '123');

-- payments
INSERT INTO payments(users_id, payment_accounts_id, merchants_id, status, type, amount, currency, timestamp, approve_timestamp) VALUES (1, 1, null, 'APPROVAL', 'CHARGE', 1000.0, 'USD', '2024-03-01T00:00:00', '2024-03-01T00:00:02');
INSERT INTO payments(users_id, payment_accounts_id, merchants_id, status, type, amount, currency, timestamp, approve_timestamp) VALUES (2, 2, null, 'APPROVAL', 'CHARGE', 1000.0, 'USD', '2024-03-01T00:00:00', '2024-03-01T00:00:02');

-- payment_logs
INSERT INTO payment_logs(payments_id, merchants_id, payment_methods_id, status, type, amount, currency) VALUES (1, 1, 1, 'APPROVAL', 'CHARGE', 1000.0, 'USD');
INSERT INTO payment_logs(payments_id, merchants_id, payment_methods_id, status, type, amount, currency) VALUES (2, 1, 2, 'APPROVAL', 'CHARGE', 1000.0, 'USD');

