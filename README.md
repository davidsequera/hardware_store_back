# hardware_store_back
A web hardware store project made in springboot and graphql

## Introduction

This project is a web hardware store made in springboot and graphql.
It has 3 microservices:
- user
- auth
- tools

## How to run

Create application.properties files in each microservice with the following content:

```properties
#application.properties
spring.data.mongodb.uri=mongodb+srv://<user>:<password>@the-cluster.0nyix.gcp.mongodb.net/?retryWrites=true&w=majority
spring.data.mongodb.database=<databasename>
spring.graphql.graphiql.enabled=true
spring.graphql.graphiql.path=/graphiql
```

Additionally, you need to add to auth microservice a file called "config.properties" in the same path as the "application.properties" file with the following content:

```properties
#config.properties
JWT_SECRET="your_jwt_secret"
JWT_SECRET_REFRESH = "a_different_jwt_secret"
```

