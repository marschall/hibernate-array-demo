CREATE TABLE currency (
  num  NUMERIC(3) NOT NULL,
  code VARCHAR(3) NOT NULL,
  CONSTRAINT pk_currency PRIMARY KEY (num, code)
);
