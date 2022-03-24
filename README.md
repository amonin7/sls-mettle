#SLS-mettle

## Description
This is a simple Spring Boot application that hosts a REST web service and persists data in a database.

## Installation guide

```shell
git clone https://github.com/amonin7/sls-mettle.git

cd sls-mettle/

./gradlew clean build -x test && docker-compose up --build
```

## Prerequisites

1. [Java 14+](https://www.java.com/)
2. [Gradle 7.4.1](https://gradle.org/releases/)
3. [Spring framework 2.6.4](https://spring.io/)
4. [PostgreSQL 14](https://www.postgresql.org/)
5. [Flyway migration](https://flywaydb.org/)
6. [Docker](https://www.docker.com/)

## Required Tasks

1. Create a spring boot application named sls-mettle.
2. Create a REST webservice that manages items. (Create, Read, Update, Delete)
    ```json
    {
      "id": "26937741-15a2-435b-82b0-39cd0539ed5e",  //uuid
      "name": "Item Name",                           //string (0-20)
      "description": "Item description",             //string (0-200)
      "type": "hockey_pads",                         //enum["hockey_pads","hockey_skates","hockey_stick"]
      "cost": 20.00,                                 //double (>0.00)
      "created_at": "2022-03-10T14:46:55.372283Z",   //timestamp (iso8601)
      "updated_at": "2022-03-10T14:46:55.372283Z",   //timestamp (iso8601)
      "deleted_at": null                             //timestamp (iso8601)
    }
    ```
3. Instantiate the database schema for storing this information using FlywayDB. Use PostgreSQL or MySQL for the database.
4. Add appropriate logging statements and error handling.