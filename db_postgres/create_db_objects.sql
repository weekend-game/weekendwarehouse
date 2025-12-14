DROP SCHEMA IF EXISTS finlet CASCADE;

CREATE SCHEMA finlet;

CREATE TABLE finlet.general (
  name CHAR(32) NOT NULL PRIMARY KEY,
  value CHAR(64)
);

INSERT INTO finlet.general (name, value) VALUES ('content', 'Weekend Warehouse');
INSERT INTO finlet.general (name, value) VALUES ('version', '00:01');

CREATE TABLE finlet.journal_titles (
  id SERIAL PRIMARY KEY,
  name CHAR(32) NOT NULL,
  title VARCHAR(64) NOT NULL,
  fromView CHAR(32) NOT NULL,
  orderBy SMALLINT NOT NULL,
  rightType SMALLINT NOT NULL
);

CREATE TABLE finlet.journal_columns (
  id SERIAL PRIMARY KEY,
  title_id INTEGER
    CONSTRAINT journal_columns_titles_rf REFERENCES finlet.journal_titles ON DELETE CASCADE,
  nono INTEGER,
  caption CHAR(64),
  source CHAR(64),
  width SMALLINT,
  sumup BOOLEAN NOT NULL
);

INSERT INTO journal_titles (name, title, fromView, orderBy, rightType) VALUES ('receipts', 'receipts', 'v_receipts', 2, 2);

INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (1,1,'no','no',80,'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (1,2,'number','doc_numb',250,'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (1,3,'date','doc_date',250,'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (1,4,'amount','amount',300,'true');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (1,4,'note','rem',1000,'true');

CREATE TABLE finlet.receipts (
  id SERIAL PRIMARY KEY,
  doc_numb CHAR(16) NOT NULL,
  doc_date DATE NOT NULL,
  amount DEC(16, 2) NOT NULL,
  rem CHAR(80)
);

INSERT INTO receipts (doc_numb, doc_date, amount, rem) VALUES ('001/A', '2025-06-23', 123.45, 'Некоторое примечание');
INSERT INTO receipts (doc_numb, doc_date, amount, rem) VALUES ('004/A', '2025-06-12', 432.01, 'Заметка');
INSERT INTO receipts (doc_numb, doc_date, amount, rem) VALUES ('002/A', '2025-06-30', 746.43, 'Комментарий');
INSERT INTO receipts (doc_numb, doc_date, amount, rem) VALUES ('003/A', '2025-06-05', 324.39, 'бла бла бла бла');

CREATE VIEW v_receipts AS SELECT id, 0 AS no, doc_numb, doc_date, amount, rem FROM receipts;

