Hibernate Array Demo
=====================

Demo for binding arrays in Hibernate. This can be used as an efficient way to implement IN-lists.

Currently only JPQL is tested, criteria API results in IN-lists.

Tested Databases
----------------

* H2
* HSQLDB
* Oracle
* PostgreSQL


SQL Generated
-------------

H2

```sql
SELECT c1_0.code,
       c1_0.num
  FROM currency c1_0
 WHERE array_contains(?, c1_0.num)
```

HSQLDB

```sql
SELECT c1_0.code,c1_0.num
  FROM currency c1_0
 WHERE EXISTS (SELECT 1
                 FROM unnest(cast(? AS integer array)) t(i)
                WHERE t.i = c1_0.num)
```

Postgres

(currently missing a cast for `NUMERIC`)

```sql
SELECT c1_0.code,
       c1_0.num
  FROM currency c1_0
 WHERE ? @> array[c1_0.num]
```

Oracle

(currently broken)

```sql
SELECT c1_0.code,
       c1_0.num
  FROM currency c1_0
 WHERE IntegerArray_position(?, c1_0.num) > 0
```