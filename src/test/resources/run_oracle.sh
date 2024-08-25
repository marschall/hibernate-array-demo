#!/bin/bash
# https://github.com/oracle/docker-images/blob/master/OracleDatabase/SingleInstance/README.md#running-oracle-database-enterprise-and-standard-edition-2-in-a-docker-container
DIRECTORY=`dirname $0`
DIRECTORY=$(realpath $DIRECTORY)

docker run --name oracle-hibernate-array-6 \
 -p 1521:1521 -p 5500:5500 \
 --shm-size=1g \
 -v ${DIRECTORY}/sql/oracle:/docker-entrypoint-initdb.d/setup \
 -d oracle/database:23.4.0-free
