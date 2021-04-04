# Transactional Api

This project aims to develop a rest api about transactions routine. This API is responsible for creating/querying a customer account and also register customer's transactions.

This project is written in Java 11 under Spring Boot web framework.

In this context You'll see the following in action:
* Layered Architecture
* API rest
* Spring Boot
* Java 11
* Postgres
* Unit and Integration tests

## Layered Architecture
The layers are a logical artifact, and are not related to the deployment of the service. They exist to help developers manage the complexity in the code.

### Domain Model Layer
Responsible for representing concepts of the business, information about the business situation, and business rules. State that reflects the business situation is controlled and used here, even though the technical details of storing it are delegated to the infrastructure. This layer is the heart of business software.

### Application Layer
Defines the jobs the software is supposed to do and directs the expressive domain objects to work out problems. The tasks this layer is responsible for are meaningful to the business or necessary for interaction with the application layers of other systems.

### Infrastructure Layer
That is how the data that is initially held in domain entities is persisted in databases or another persistent store.

In accordance with Persistence and Infrastructure Ignorance principles, the infrastructure layer must not “contaminate” the domain model layer. You must keep the domain model entity classes agnostic from the infrastructure that you use to persist data.

### API Layer
To wrap your flows with a web layer, you must turn to Spring MVC. Thanks to Spring Boot, there is little in infrastructure to code. In this layer there are our controllers which contain our endpoints.

## Postgres as database
In this project I decided to go with a SQL Database because our data is structured and organized, so it is very efficient to query this data with a SQL database. Postgres was used as database for this project. Postgresxw is an advanced open-source object-relational system which applies SQL language. Postgres allows you to store large and sophisticated data safely. It helps developers to build the most complex applications, run administrative tasks and create integral environments.

## Running Transactional API with docker
This project contains a docker-compose file. Thus, you just need to execute the command below to run the Transactional API.

```bash
docker-compose up
```

You should be able to make requests to http:localhost:8000 against the Transactional API.

## Running Transactional API

We can run the application without the Docker container (that is, in the host OS). In this project I am using Maven as manager dependencies.
Before you run Transactional API locally, you need to spin up the Postgres through the command below on the root folder:

```
docker-compose up db
```

Following, type the following:
```
./mvnw package && java -jar target/transactional-api-0.0.1-SNAPSHOT.jar
```
