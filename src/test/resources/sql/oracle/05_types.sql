
ALTER SESSION SET CONTAINER = FREEPDB1;
ALTER SESSION SET CURRENT_SCHEMA = jdbc;

BEGIN
  -- array of scalar values
  EXECUTE IMMEDIATE 'CREATE OR REPLACE TYPE INTEGERARRAY IS TABLE OF NUMBER';
  EXECUTE IMMEDIATE 'CREATE OR REPLACE TYPE STRINGARRAY  IS TABLE OF VARCHAR2(255)';

END;
/
