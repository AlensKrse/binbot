# README #

Application Stack:
Java 17, Lombok,
Spring Boot Web application, Spring Boot Cloud
Spring Boot Security, JWT Tokenization (OAuth2), QR Code Two-factor authentication,
Spring Boot JPA, Liquibase, Postgres, Hibernate,
Spring Boot Test, Junit 5, Mockito, H2 Integration Tests
GCP Logging,
GCP Pub/sub (queue),
Maven, GIT, Docker, Gitlab Pipeline, GCP Cloud Run,
Thymeleaf, HTML, CSS, Angular.

Todo list:
Add possibility to receive all functionality of project by api and by UI.
Implement Angular UI as alternative.
Add two-factor authentication.
Add jwt tokens or auth service like OAuth.
Add filter by username in User list (Thymeleaf).
Add filters to Daemon list (Thymeleaf).
Add telegram chat id and credentials to user details (Every user might have own telegram bot that sends notifications
and trade requests).
Implement rates changes notifications.
Implement receive confirmation or decline from telegram bot on rate change to sell or buy asset.
Add configurable UI and api of trade daemons.
Add system metric (Actuator?).
Add charts of profit and balance.
Create docker compose file with own application and deploy it on cloud run.
Buy domain name and apply it.
Configure SSL certificate.
Configure cors.
Create binance api integration on its own microservice.