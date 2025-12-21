DROP SCHEMA IF EXISTS finlet CASCADE;

CREATE SCHEMA finlet;

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

-- general --

CREATE TABLE finlet.general (
  name CHAR(32) NOT NULL PRIMARY KEY,
  value CHAR(128)
);

INSERT INTO finlet.general (name, value) VALUES ('content', 'Weekend Warehouse');
INSERT INTO finlet.general (name, value) VALUES ('version', '00:01');
INSERT INTO finlet.general (name, value) VALUES ('company', '3');
INSERT INTO finlet.general (name, value) VALUES ('address', '4');
INSERT INTO finlet.general (name, value) VALUES ('telephone', '5');
INSERT INTO finlet.general (name, value) VALUES ('email', '6');
INSERT INTO finlet.general (name, value) VALUES ('site', '7');
INSERT INTO finlet.general (name, value) VALUES ('reg_no', '8');
INSERT INTO finlet.general (name, value) VALUES ('town', '9');
INSERT INTO finlet.general (name, value) VALUES ('inn', '10');
INSERT INTO finlet.general (name, value) VALUES ('kpp', '11');
INSERT INTO finlet.general (name, value) VALUES ('okato', '12');
INSERT INTO finlet.general (name, value) VALUES ('okpo', '13');
INSERT INTO finlet.general (name, value) VALUES ('account', '14');
INSERT INTO finlet.general (name, value) VALUES ('bank', '15');
INSERT INTO finlet.general (name, value) VALUES ('bic', '16');
INSERT INTO finlet.general (name, value) VALUES ('corr_acc', '17');
INSERT INTO finlet.general (name, value) VALUES ('head', '18');
INSERT INTO finlet.general (name, value) VALUES ('chief_accountant', '19');
INSERT INTO finlet.general (name, value) VALUES ('accountant', '20');
INSERT INTO finlet.general (name, value) VALUES ('issued_by', '21');

-- warehouses --

CREATE TABLE finlet.warehouses (
  id SERIAL PRIMARY KEY,
  name CHAR(32) NOT NULL,
  full_name CHAR(128)
);

CREATE UNIQUE INDEX warehouses_idx_1 ON warehouses (name);

CREATE VIEW v_warehouses AS SELECT id, 0 AS no, name, full_name FROM warehouses;

INSERT INTO journal_titles (name, title, fromView, orderBy, rightType) VALUES ('warehouses', 'warehouses', 'v_warehouses', 1, 2);
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (1, 1, 'no', 'no', 30, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (1, 2, 'name', 'name', 160, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (1, 3, 'full_name', 'full_name', 512, 'false');

INSERT INTO warehouses (name, full_name) VALUES ('Главный', 'Главный склад компании');
INSERT INTO warehouses (name, full_name) VALUES ('Снежный', 'Склад на Снежной улице');
INSERT INTO warehouses (name, full_name) VALUES ('Липовый', 'Склад на Липовой улице');
INSERT INTO warehouses (name, full_name) VALUES ('Пром', 'Склад в промышленной зоне');

-- companies_groups --

CREATE TABLE finlet.companies_groups (
  id SERIAL PRIMARY KEY,
  name CHAR(32) NOT NULL
);

CREATE UNIQUE INDEX companies_groups_idx_1 ON companies_groups (name);

CREATE VIEW v_companies_groups AS SELECT id, 0 AS no, name FROM companies_groups;

INSERT INTO journal_titles (name, title, fromView, orderBy, rightType) VALUES ('companies_groups', 'groups_of_companies', 'v_companies_groups', 1, 2);
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (2, 1, 'no', 'no', 30, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (2, 2, 'name', 'name', 256, 'false');

INSERT INTO companies_groups (name) VALUES ('Группа компаний один');
INSERT INTO companies_groups (name) VALUES ('Группа компаний два');
INSERT INTO companies_groups (name) VALUES ('Группа компаний три');
INSERT INTO companies_groups (name) VALUES ('Группа компаний четыре');

-- companies --

CREATE TABLE finlet.companies (
  id SERIAL PRIMARY KEY,
  group_id INT NOT NULL REFERENCES companies_groups (id),
  name CHAR(32) NOT NULL,
  full_name CHAR(128),
  address CHAR(128),
  telephone CHAR(32),
  email CHAR(32),
  site CHAR(128),
  reg_no CHAR(32),
  town CHAR(64),
  inn CHAR(32),
  kpp CHAR(32),
  okato CHAR(32),
  okpo CHAR(32),
  account CHAR(32),
  bank CHAR(128),
  bic CHAR(32),
  corr_acc CHAR(32),
  head CHAR(64),
  chief_accountant CHAR(64),
  accountant CHAR(64),
  issued_by CHAR(64),
  rem1 CHAR(96),
  rem2 CHAR(96),
  customer BOOLEAN,
  supplier BOOLEAN
);

CREATE INDEX companies_idx_1 ON companies (group_id);
CREATE UNIQUE INDEX companies_idx_2 ON companies (name);

CREATE VIEW v_companies AS SELECT c.id, 0 AS no, c.group_id, cg.name AS group, c.name, c.full_name,
  c.address, c.telephone, c.email, c.site, c.reg_no, c.town, c.inn, c.kpp, c.okato, c.okpo,
  c.account, c.bank, c.bic, c.corr_acc, c.head, c.chief_accountant, c.accountant, c.issued_by,
  c.rem1, c.rem2, c.customer, c.supplier
  FROM companies c
  LEFT OUTER JOIN companies_groups cg ON cg.id = c.group_id;

INSERT INTO journal_titles (name, title, fromView, orderBy, rightType) VALUES ('companies', 'companies', 'v_companies', 1, 2);
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 1, 'no', 'no', 32, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 2, 'name', 'name', 128, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 3, 'full_name', 'full_name', 194, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 4, 'group', 'group', 128, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 5, 'address', 'address', 194, 'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 0, 'telephone', 'telephone', 128, 'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 0, 'email', 'email', 194, 'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 0, 'site', 'site', 194, 'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 0, 'reg_no', 'reg_no', 194, 'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 0, 'town', 'town', 194, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 6, 'inn', 'inn', 64, 'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 0, 'kpp', 'kpp', 194, 'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 0, 'okato', 'okato', 194, 'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 0, 'okpo', 'okpo', 194, 'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 0, 'account', 'account', 194, 'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 0, 'bank', 'bank', 194, 'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 0, 'bic', 'bic', 194, 'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 0, 'corr_acc', 'corr_acc', 194, 'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 0, 'head', 'head', 194, 'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 0, 'chief_accountant', 'chief_accountant', 194, 'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 0, 'accountant', 'accountant', 194, 'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 0, 'issued_by', 'issued_by', 194, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 7, 'rem1', 'rem1', 128, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 8, 'rem2', 'rem2', 128, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 9, 'customer', 'customer', 48, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (3, 10, 'supplier', 'supplier', 48, 'false');

INSERT INTO companies (group_id, name, full_name,
  address, telephone, email, site, reg_no, town, inn, kpp, okato, okpo,
  account, bank, bic, corr_acc, head, chief_accountant, accountant, issued_by,
  rem1, rem2, customer, supplier
  ) VALUES (
  1, 'name 1', 'full_name 1',
  'address 1', 'telephone 1', 'email 1', 'site 1', 'reg_no 1', 'town 1', 'inn 1', 'kpp 1', 'okato 1', 'okpo 1',
  'account 1', 'bank 1', 'bic 1', 'corr_acc 1', 'head 1', 'chief_accountant 1', 'accountant 1', 'issued_by 1',
  'rem1 1', 'rem2 1', 'true', 'true'
  );

INSERT INTO companies (group_id, name, full_name,
  address, telephone, email, site, reg_no, town, inn, kpp, okato, okpo,
  account, bank, bic, corr_acc, head, chief_accountant, accountant, issued_by,
  rem1, rem2, customer, supplier
  ) VALUES (
  1, 'name 2', 'full_name 2',
  'address 2', 'telephone 2', 'email 2', 'site 2', 'reg_no 2', 'town 2', 'inn 2', 'kpp 2', 'okato 2', 'okpo 2',
  'account 2', 'bank 2', 'bic 2', 'corr_acc 2', 'head 2', 'chief_accountant 2', 'accountant 2', 'issued_by 2',
  'rem1 2', 'rem2 2', 'true', 'false'
  );

INSERT INTO companies (group_id, name, full_name,
  address, telephone, email, site, reg_no, town, inn, kpp, okato, okpo,
  account, bank, bic, corr_acc, head, chief_accountant, accountant, issued_by,
  rem1, rem2, customer, supplier
  ) VALUES (
  2, 'name 3', 'full_name 3',
  'address 3', 'telephone 3', 'email 3', 'site 3', 'reg_no 3', 'town 3', 'inn 3', 'kpp 3', 'okato 3', 'okpo 3',
  'account 3', 'bank 3', 'bic 3', 'corr_acc 3', 'head 3', 'chief_accountant 3', 'accountant 3', 'issued_by 3',
  'rem1 3', 'rem2 3', 'false', 'true'
  );

INSERT INTO companies (group_id, name, full_name,
  address, telephone, email, site, reg_no, town, inn, kpp, okato, okpo,
  account, bank, bic, corr_acc, head, chief_accountant, accountant, issued_by,
  rem1, rem2, customer, supplier
  ) VALUES (
  3, 'name 4', 'full_name 4',
  'address 4', 'telephone 4', 'email 4', 'site 4', 'reg_no 4', 'town 4', 'inn 4', 'kpp 4', 'okato 4', 'okpo 4',
  'account 4', 'bank 4', 'bic 4', 'corr_acc 4', 'head 4', 'chief_accountant 4', 'accountant 4', 'issued_by 4',
  'rem1 4', 'rem2 4', 'false', 'true'
  );

-- products_groups --

CREATE TABLE finlet.products_groups (
  id SERIAL PRIMARY KEY,
  name CHAR(32) NOT NULL
);

CREATE UNIQUE INDEX products_groups_idx_1 ON products_groups (name);

CREATE VIEW v_products_groups AS SELECT id, 0 AS no, name FROM products_groups;

INSERT INTO journal_titles (name, title, fromView, orderBy, rightType) VALUES ('products_groups', 'groups_of_products', 'v_products_groups', 1, 2);
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (4, 1, 'no', 'no', 30, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (4, 2, 'name', 'name', 256, 'false');

INSERT INTO products_groups (name) VALUES ('Группа товаров один');
INSERT INTO products_groups (name) VALUES ('Группа товаров два');
INSERT INTO products_groups (name) VALUES ('Группа товаров три');
INSERT INTO products_groups (name) VALUES ('Группа товаров четыре');

-- products --

CREATE TABLE finlet.products (
  id SERIAL PRIMARY KEY,
  group_id INT NOT NULL REFERENCES products_groups (id),
  article CHAR(32) NOT NULL,
  name CHAR(128),
  unit CHAR(16),
  vat NUMERIC(15, 2)
);

CREATE INDEX products_idx_1 ON products (group_id);
CREATE UNIQUE INDEX products_idx_2 ON products (article);

CREATE VIEW v_products AS SELECT p.id, 0 AS no, p.group_id, pg.name AS group,
      p.article, p.name, p.unit, p.vat
  FROM products p
  LEFT OUTER JOIN products_groups pg ON pg.id = p.group_id;

INSERT INTO journal_titles (name, title, fromView, orderBy, rightType) VALUES ('products', 'products', 'v_products', 1, 2);
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (5, 1, 'no', 'no', 24, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (5, 2, 'article', 'article', 64, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (5, 3, 'name', 'name', 196, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (5, 4, 'group', 'group', 128, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (5, 5, 'unit', 'unit', 24, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (5, 6, 'vat', 'vat', 24, 'false');

INSERT INTO products (
   group_id, article, name, unit, vat
) VALUES (
   1, 'article 1', 'Товар 1', 'unit 1', 20
);

INSERT INTO products (
   group_id, article, name, unit, vat
) VALUES (
   1, 'article 2', 'Товар 2', 'unit 2', 20
);

INSERT INTO products (
   group_id, article, name, unit, vat
) VALUES (
   2, 'article 3', 'Товар 3', 'unit 3', 20
);

INSERT INTO products (
   group_id, article, name, unit, vat
) VALUES (
   3, 'article 4', 'Товар 4', 'unit 4', 20
);

-- autonumberer --

CREATE TABLE finlet.autonumberer (
  id SERIAL PRIMARY KEY,
  document CHAR(16) NOT NULL,
  counter INT,
  first_value INT,
  last_value INT,
  prefix CHAR(8),
  suffix CHAR(8)
);

CREATE UNIQUE INDEX autonumberer_idx_1 ON autonumberer (document);

CREATE VIEW v_autonumberer AS SELECT id, 0 AS no, document,
      counter, first_value, last_value, prefix, suffix
  FROM autonumberer;

INSERT INTO journal_titles (name, title, fromView, orderBy, rightType) VALUES ('autonumberer', 'autonumberer', 'v_autonumberer', 1, 2);
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (6, 1, 'no', 'no', 24, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (6, 2, 'document', 'document', 64, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (6, 3, 'counter', 'counter', 24, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (6, 4, 'first_value', 'first_value', 24, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (6, 5, 'last_value', 'last_value', 24, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (6, 6, 'prefix', 'prefix', 24, 'false');
INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (6, 6, 'suffix', 'suffix', 24, 'false');

INSERT INTO autonumberer (
   document, counter, first_value, last_value, prefix, suffix
) VALUES (
   'Receipt', 1, 1, 999, 'R-', '-2026'
);

INSERT INTO autonumberer (
   document, counter, first_value, last_value, prefix, suffix
) VALUES (
   'Issue', 1, 1, 999, 'I', null
);

-- receipts --

CREATE TABLE finlet.receipts (
  id SERIAL PRIMARY KEY,
  doc_numb CHAR(16) NOT NULL,
  doc_date DATE,
  amount DEC(16, 2) NOT NULL,
  rem CHAR(80)
);

CREATE VIEW v_receipts AS SELECT id, 0 AS no, doc_numb, doc_date, amount, rem FROM receipts;

--INSERT INTO journal_titles (name, title, fromView, orderBy, rightType) VALUES ('receipts', 'receipts', 'v_receipts', 2, 2);

--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (7,1,'no','no',80,'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (7,2,'number','doc_numb',250,'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (7,3,'date','doc_date',250,'false');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (7,4,'amount','amount',300,'true');
--INSERT INTO journal_columns (title_id, nono, caption, source, width, sumup) VALUES (7,4,'note','rem',1000,'true');

INSERT INTO receipts (doc_numb, doc_date, amount, rem) VALUES ('001/A', '2025-06-23', 123.45, 'Некоторое примечание');
INSERT INTO receipts (doc_numb, doc_date, amount, rem) VALUES ('004/A', '2025-06-12', 432.01, 'Заметка');
INSERT INTO receipts (doc_numb, doc_date, amount, rem) VALUES ('002/A', '2025-06-30', 746.43, 'Комментарий');
INSERT INTO receipts (doc_numb, doc_date, amount, rem) VALUES ('003/A', '2025-06-05', 324.39, 'бла бла бла бла');
