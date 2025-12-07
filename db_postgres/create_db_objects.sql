DROP SCHEMA IF EXISTS finlet CASCADE;

CREATE SCHEMA finlet;

CREATE TABLE finlet.general (
  name CHAR(32) NOT NULL PRIMARY KEY,
  value CHAR(64)
);

INSERT INTO finlet.general (name, value) VALUES ('content', 'Weekend Warehouse');
INSERT INTO finlet.general (name, value) VALUES ('version', '00:01');

