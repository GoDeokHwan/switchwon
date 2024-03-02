CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS merchants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS policy_fees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fees DECIMAL(10, 2) NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS payment_accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    users_id INT NOT NULL,
    balance DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (users_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    users_id INT NOT NULL,
    payment_accounts_id INT NOT NULL,
    merchants_id INT,
    status VARCHAR(10) NOT NULL,
    type VARCHAR(10) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    approve_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (users_id) REFERENCES users(id),
    FOREIGN KEY (payment_accounts_id) REFERENCES payment_accounts(id)
);

CREATE TABLE IF NOT EXISTS payment_methods (
    id INT AUTO_INCREMENT PRIMARY KEY,
    users_id INT NOT NULL,
    payment_method VARCHAR(15) NOT NULL,
    card_number VARCHAR(30) NOT NULL,
    expiry_date VARCHAR(5) NOT NULL,
    cvv INT NOT NULL,
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (users_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS payment_logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    payments_id INT NOT NULL,
    merchants_id INT NOT NULL,
    payment_methods_id INT NOT NULL,
    status VARCHAR(10) NOT NULL,
    type VARCHAR(10) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (payments_id) REFERENCES payments(id),
    FOREIGN KEY (merchants_id) REFERENCES merchants(id),
    FOREIGN KEY (payment_methods_id) REFERENCES payment_methods(id)
);

CREATE TABLE IF NOT EXISTS payment_estimate_logs (
        id INT AUTO_INCREMENT PRIMARY KEY,
        users_id INT NOT NULL,
        merchants_id INT NOT NULL,
        policy_fees_id INT NOT NULL,
        amount DECIMAL(10, 2) NOT NULL,
        currency VARCHAR(10) NOT NULL,
        fees DECIMAL(10, 2) NOT NULL,
        estimated_total DECIMAL(10, 2) NOT NULL,
        create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        modify_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (merchants_id) REFERENCES merchants(id),
        FOREIGN KEY (policy_fees_id) REFERENCES policy_fees(id),
        FOREIGN KEY (users_id) REFERENCES users(id)
);
