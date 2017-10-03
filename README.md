# TodoList REST Service

## Description
Service provides REST API to operate on lists of todo items. Each item has its description and can be marked as done.
User can the following roles:
 - USER: can create/edit/view own lists
 - ADMIN: have the same privileges as a regular USER but in addition can operate on any todo list and view registered users

## Running
Running with **default** profile (embedded H2 database located in /opt folder)
```
mvn spring-boot:run
```
Running with **postgres** profile (remote PostgreSQL database, may take some time to start)
```
mvn spring-boot:run -Dspring.profiles.active=postgres
```
Running war package with embedded Tomcat application server
```
java -jar todolist.war
```

## Authorization
Obtaining oAuth token
```
curl -X POST --user 'clientId:secret' -d 'grant_type=password&username=admin&password=admin' http://localhost:8089/oauth/token
```
Calling REST API
```
GET /api/** HTTP/1.1 Host: localhost:8089 Content-Type: application/json Authorization: Bearer 3ef68ea7-77a7-446e-bbdd-8d26ea252c81
```

## REST API
Swagger API documentation available from index page or on the following address http://localhost:8089/swagger-ui.html
