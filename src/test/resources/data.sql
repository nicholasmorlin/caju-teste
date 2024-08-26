INSERT INTO account (id, name, document)
VALUES (1, 'Nicholas', '11291316612');

INSERT INTO account (id, name, document)
VALUES (2, 'Caju', '99999999999');

INSERT INTO category (id, type)
VALUES (1, 'FOOD');

INSERT INTO category (id, type)
VALUES (2, 'MEAL');

INSERT INTO category (id, type)
VALUES (3, 'CASH');

INSERT INTO mcc (id, category_id, code)
VALUES (1, 1, '5411');

INSERT INTO mcc (id, category_id, code)
VALUES (2, 1, '5412');

INSERT INTO mcc (id, category_id, code)
VALUES (3, 2, '5811');

INSERT INTO mcc (id, category_id, code)
VALUES (4, 2, '5812');

INSERT INTO balance (account_id, category_id, balance)
VALUES (1, 1, 2000);

INSERT INTO balance (account_id, category_id, balance)
VALUES (1, 2, 2000);

INSERT INTO balance (account_id, category_id, balance)
VALUES (1, 3, 3000);

INSERT INTO merchant (name, category_id)
VALUES ('PADARIA DO ZE               SAO PAULO BR', 1);

INSERT INTO merchant (name, category_id)
VALUES ('UBER TRIP                   SAO PAULO BR', 2);

INSERT INTO merchant (name, category_id)
VALUES ('UBER EATS                   SAO PAULO BR', 1);

INSERT INTO merchant (name, category_id)
VALUES ('PAG*JoseDaSilva          RIO DE JANEI BR', 2);