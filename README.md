# hardware_store_back
Project is about the webpage of a hardware store, the web list the products of the commerce with some atributes like name and description, the cities in which every product is available, and the brand of every product.

## Introduction

This project is a web hardware store made in springboot and graphql.
It has 3 microservices:
- user
- auth
- tools


### Stack

- Using springboot with graphql with a mongodb database
- Meant to have an angular client
- Auth with JWT
- Code in Java and Kotlin

## Code structure 

There are 3 microservices every one of them is separated using clean architecture with a presentation folder for controllers, a domain folder for servies and a persitence 

## How to run

Create application.properties files in each microservice with the following content:

```properties
#application.properties
spring.data.mongodb.uri=mongodb+srv://<user>:<password>@the-cluster.0nyix.gcp.mongodb.net/?retryWrites=true&w=majority
spring.data.mongodb.database=<databasename>
spring.graphql.graphiql.enabled=true
spring.graphql.graphiql.path=/graphiql
```

Additionally, you need to add to auth microservice a file called "config.properties" in the root path in order to be accesible to every microservice file with the following content:

```properties
#config.properties
JWT_SECRET="your_jwt_secret"
JWT_SECRET_REFRESH = "a_different_jwt_secret"
```

