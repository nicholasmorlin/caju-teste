CREATE SCHEMA caju;

CREATE TABLE IF NOT EXISTS caju.account (
                                id SERIAL PRIMARY KEY,
                                name VARCHAR(255) NOT NULL,
                                document VARCHAR(11) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS caju.category (
                                 id SERIAL PRIMARY KEY,
                                 type VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS caju.balance (
                                id SERIAL PRIMARY KEY,
                                account_id BIGINT NOT NULL,
                                category_id BIGINT NOT NULL,
                                balance DECIMAL(10, 2) NOT NULL,
                                FOREIGN KEY (account_id) REFERENCES caju.account(id),
                                FOREIGN KEY (category_id) REFERENCES caju.category(id)
);

CREATE TABLE IF NOT EXISTS caju.mcc (
                            id SERIAL PRIMARY KEY,
                            category_id BIGINT NOT NULL,
                            code VARCHAR(4) NOT NULL,
                            FOREIGN KEY (category_id) REFERENCES caju.category(id)
);

CREATE TABLE IF NOT EXISTS caju.payment_history (
                                        id SERIAL PRIMARY KEY,
                                        account_id BIGINT NOT NULL,
                                        mcc_id BIGINT NULL,
                                        category_id BIGINT NULL,
                                        amount DECIMAL(10, 2) NOT NULL,
                                        merchant VARCHAR(255) NOT NULL,
                                        creation_date TIMESTAMP NOT NULL,
                                        FOREIGN KEY (account_id) REFERENCES caju.account(id),
                                        FOREIGN KEY (mcc_id) REFERENCES caju.mcc(id),
                                        FOREIGN KEY (category_id) REFERENCES caju.category(id)
);

CREATE TABLE IF NOT EXISTS caju.merchant (
                                 id SERIAL PRIMARY KEY,
                                 name VARCHAR(255) NOT NULL,
                                 category_id BIGINT NOT NULL,
                                 FOREIGN KEY (category_id) REFERENCES caju.category(id)
);

INSERT INTO caju.account (id, name, document)
VALUES (1, 'Nicholas', '11291316612');

INSERT INTO caju.account (id, name, document)
VALUES (2, 'Caju', '99999999999');

INSERT INTO caju.category (id, type)
VALUES (1, 'FOOD');

INSERT INTO caju.category (id, type)
VALUES (2, 'MEAL');

INSERT INTO caju.category (id, type)
VALUES (3, 'CASH');

INSERT INTO caju.mcc (id, category_id, code)
VALUES (1, 1, '5411');

INSERT INTO caju.mcc (id, category_id, code)
VALUES (2, 1, '5412');

INSERT INTO caju.mcc (id, category_id, code)
VALUES (3, 2, '5811');

INSERT INTO caju.mcc (id, category_id, code)
VALUES (4, 2, '5812');

INSERT INTO caju.balance (account_id, category_id, balance)
VALUES (1, 1, 2000);

INSERT INTO caju.balance (account_id, category_id, balance)
VALUES (1, 2, 2000);

INSERT INTO caju.balance (account_id, category_id, balance)
VALUES (1, 3, 3000);

INSERT INTO caju.merchant (name, category_id)
VALUES ('PADARIA DO ZE               SAO PAULO BR', 1);

INSERT INTO caju.merchant (name, category_id)
VALUES ('UBER TRIP                   SAO PAULO BR', 2);

INSERT INTO caju.merchant (name, category_id)
VALUES ('UBER EATS                   SAO PAULO BR', 1);

INSERT INTO caju.merchant (name, category_id)
VALUES ('PAG*JoseDaSilva          RIO DE JANEI BR', 2);