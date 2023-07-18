CREATE TABLE conta
(
    id_conta IDENTITY NOT NULL PRIMARY KEY,
    nome_responsavel VARCHAR(50) NOT NULL
);


CREATE TABLE transferencia
(
    id IDENTITY NOT NULL PRIMARY KEY,
    data_transferencia TIMESTAMP WITH TIME ZONE NOT NULL,
    valor NUMERIC (20,2) NOT NULL,
    tipo VARCHAR(15) NOT NULL,
    nome_operador_transacao VARCHAR (50),
    conta_id INT NOT NULL,

        CONSTRAINT FK_CONTA
        FOREIGN KEY (conta_id)
        REFERENCES conta(id_conta)
);

INSERT INTO conta (id_conta, nome_responsavel) VALUES (1,'Fulano');
INSERT INTO conta (id_conta, nome_responsavel) VALUES (2,'Sicrano');

INSERT INTO transferencia (id,data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (1,'2019-01-01 12:00:00+03',30895.46,'DEPOSITO', null, 1);
INSERT INTO transferencia (id,data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (2,'2019-02-03 09:53:27+03',12.24,'DEPOSITO', null,2);
INSERT INTO transferencia (id,data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (3,'2019-05-04 08:12:45+03',-500.50,'SAQUE', null,1);
INSERT INTO transferencia (id,data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (4,'2019-08-07 08:12:45+03',-530.50,'SAQUE', null,2);
INSERT INTO transferencia (id,data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (5,'2020-06-08 10:15:01+03',3241.23,'TRANSFERENCIA', 'Beltrano',1);
INSERT INTO transferencia (id,data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (6,'2021-04-01 12:12:04+03',25173.09,'TRANSFERENCIA', 'Ronnyscley',2);


INSERT INTO conta (id_conta, nome_responsavel) VALUES (3, 'John Doe');
INSERT INTO conta (id_conta, nome_responsavel) VALUES (4, 'Jane Smith');
INSERT INTO conta (id_conta, nome_responsavel) VALUES (5, 'Michael Johnson');

INSERT INTO transferencia (id, data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (7, '2022-01-01 09:30:00+03', 1000.00, 'DEPOSITO', NULL, 3);
INSERT INTO transferencia (id, data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (8, '2022-02-02 14:45:00+03', 150.75, 'DEPOSITO', NULL, 4);
INSERT INTO transferencia (id, data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (9, '2022-03-03 11:15:00+03', -50.50, 'SAQUE', NULL, 3);
INSERT INTO transferencia (id, data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (10, '2022-04-04 16:30:00+03', -70.25, 'SAQUE', NULL, 4);
INSERT INTO transferencia (id, data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (11, '2022-05-05 13:45:00+03', 500.00, 'TRANSFERENCIA', 'Alice', 3);
INSERT INTO transferencia (id, data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (12, '2022-06-06 10:00:00+03', 2000.00, 'TRANSFERENCIA', 'Bob', 4);
INSERT INTO transferencia (id, data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (13, '2022-07-07 11:30:00+03', 800.50, 'DEPOSITO', NULL, 5);
INSERT INTO transferencia (id, data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (14, '2022-08-08 15:45:00+03', -100.25, 'SAQUE', NULL, 5);
INSERT INTO transferencia (id, data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (15, '2022-09-09 12:15:00+03', 300.00, 'TRANSFERENCIA', 'Carol', 5);
INSERT INTO transferencia (id, data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (16, '2022-02-02 12:15:00+03', 30.00, 'TRANSFERENCIA', 'Carol', 5);
INSERT INTO transferencia (id, data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (17, '2022-09-10 12:15:00+03', 5600.00, 'TRANSFERENCIA', 'Carol', 5);
INSERT INTO transferencia (id, data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (18, '2022-09-20 12:15:00+03', 400.00, 'TRANSFERENCIA', 'Carol', 5);
