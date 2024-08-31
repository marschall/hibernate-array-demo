CREATE TABLE currency (
  number NUMERIC(3) NOT NULL,
  code   VARCHAR(3) NOT NULL,
  CONSTRAINT pk_currency PRIMARY KEY(number, code)
);
