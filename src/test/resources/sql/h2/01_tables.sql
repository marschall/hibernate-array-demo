CREATE TABLE currency (
  number NUMERIC(3) NOT NULL,
  code   VARCHAR(3) NOT NULL,
  PRIMARY KEY (number, code)
);
