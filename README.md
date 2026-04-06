# TodoApp

TodoApp est une API REST Spring Boot de gestion de tâches, pensée pour une utilisation simple, lisible et facilement testable.

Le projet expose une API documentée avec Swagger, une console H2 en développement, une couche service testée, et une suite de tests WebMvc pour le contrôleur REST.

## Objectif du projet

- Créer, consulter, modifier et supprimer des tâches.
- Valider les données reçues côté API.
- Fournir une documentation interactive via Swagger.
- Pouvoir lancer le backend localement ou dans un conteneur Docker.

## Stack technique

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Hibernate
- H2 en mémoire
- Springdoc OpenAPI / Swagger UI
- Maven Wrapper
- Mockito et JUnit 5 pour les tests

## Fonctionnalités

- Liste de toutes les tâches.
- Création d'une tâche.
- Mise à jour d'une tâche existante.
- Suppression d'une tâche.
- Gestion des erreurs de validation et des réponses HTTP.
- Documentation OpenAPI automatique.

## Prérequis

- Java 21 installé.
- Aucun serveur de base de données requis en local, la base H2 est embarquée.
- Docker installé uniquement si tu veux lancer l'application en conteneur.

## Lancer le projet en local

```bash
./mvnw spring-boot:run
```

L'application démarre par défaut sur : http://localhost:8080

## Documentation API

- Swagger UI : http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON : http://localhost:8080/v3/api-docs

## Console H2

La console H2 est disponible en développement pour inspecter les données en mémoire.

- URL : http://localhost:8080/h2-console
- JDBC URL : jdbc:h2:mem:testdb
- User : sa
- Mot de passe : vide

## Lancer les tests

```bash
./mvnw test
```

Tests présents dans le projet :

- Tests unitaires du service avec Mockito.
- Test de chargement du contexte Spring.
- Tests WebMvc du contrôleur REST avec MockMvc.

## Lancer avec Docker

### Construire l'image

```bash
docker build -t todoapp-backend .
```

### Démarrer le conteneur

```bash
docker run --rm -p 8080:8080 todoapp-backend
```

L'application est alors disponible sur : http://localhost:8080

## Structure du projet

```text
src/
	main/
		java/com/vacheronalyssa/todoapp/
			config/
			controller/
			dto/
			entity/
			exception/
			service/
		resources/
	test/
		java/com/vacheronalyssa/todoapp/
			controller/
			service/
```

## Endpoints principaux

- GET /tasks : récupérer toutes les tâches
- POST /tasks : créer une tâche
- PUT /tasks/{id} : mettre à jour une tâche
- DELETE /tasks/{id} : supprimer une tâche

## Notes techniques

- Les données sont stockées en mémoire avec H2 pour simplifier le lancement local.
- Le backend est documenté avec Springdoc OpenAPI.
- Les réponses d'erreur sont centralisées via un handler global.