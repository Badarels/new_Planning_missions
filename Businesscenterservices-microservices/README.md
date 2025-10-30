# Business Center Services - Architecture Microservices

## Structure générale

```
Businesscenterservices-microservices/
├── pom.xml (parent)
├── config-server/
├── discovery-server/
├── api-gateway/
├── auth-service/
├── medecins-service/
├── centres-service/
├── missions-service/
└── notifications-service/
```

Chaque module est un projet Spring Boot autonome (Java 17, Spring Boot 3.2.5) packagé en jar et géré via Maven multi-modules.

## Services

| Service | Port par défaut | Rôle | Points clefs |
| --- | --- | --- | --- |
| `config-server` | 8888 | Fournit la configuration centralisée (profil `native` par défaut) | Protégé par user/mot de passe simple (`config-user/config-pass`). |
| `discovery-server` | 8761 | Registre Eureka pour la découverte de services | Authentification basique (`eureka-user/eureka-pass`). |
| `api-gateway` | 8080 | Entrée unique (Spring Cloud Gateway) | Découverte dynamique auprès d’Eureka, sécurité basic à compléter par JWT. |
| `auth-service` | 9001 | Gestion des utilisateurs, rôles, authentification JWT et emails | Base PostgreSQL dédiée (`bcs_auth`). |
| `medecins-service` | 9002 | CRUD médecins et disponibilité | Base PostgreSQL `bcs_medecins`. |
| `centres-service` | 9003 | Gestion des centres hospitaliers | Base PostgreSQL `bcs_centres`. |
| `missions-service` | 9004 | Missions de remplacement, production d’événements | Base PostgreSQL `bcs_missions`, Kafka (`missions.events`). |
| `notifications-service` | 9005 | Envoi emails/SMS, consommation Kafka | SMTP via variables d’environnement, consomme `missions.events`. |

### Communication
- **Synchrone** : Gateway → services (REST). Les routes sont découvertes automatiquement (Service ID en minuscules).
- **Asynchrone** : `missions-service` publie sur Kafka, `notifications-service` consomme (topic configurable via `application.yml`).

### Sécurité
- Authentification centralisée dans `auth-service` (JWT à intégrer à la Gateway).
- Config Server et Eureka protégés par Basic Auth configurable.
- Secrets (DB, SMTP, JWT) exposés en variables d’environnement.

## Prérequis
- Java 17
- Maven 3.9+
- PostgreSQL (bases : `bcs_auth`, `bcs_medecins`, `bcs_centres`, `bcs_missions`)
- Kafka + Zookeeper (pour les événements missions/notifications)

## Commandes Maven
Depuis la racine `Businesscenterservices-microservices/` :

```bash
mvn clean install       # build de tous les modules
mvn -pl config-server spring-boot:run
mvn -pl discovery-server spring-boot:run
mvn -pl api-gateway spring-boot:run
mvn -pl auth-service spring-boot:run
# etc.
```

Pour lancer un service spécifique : `mvn -pl <module> spring-boot:run` ou `mvn -pl <module> package` puis `java -jar`.

## Prochaines étapes recommandées
1. **Configurer le Config Server** pour pointer sur un dépôt Git centralisé et y placer les `application.yml` de chaque service.
2. **Implémenter les domaines** : copier progressivement entités/DTO/services du monolithe vers les microservices correspondants.
3. **MIse en place JWT** côté Gateway (filtre global pour vérifier les tokens émis par `auth-service`).
4. **Configurer Kafka** (topics, producteurs/consommateurs) et finaliser la logique de notifications.
5. **Containerisation** : créer Dockerfile/docker-compose pour orchestrer config, discovery, gateway et microservices.
6. **Observabilité** : ajouter Spring Boot Actuator + Prometheus/Grafana, logs centralisés, tracing distribué (Spring Cloud Sleuth/Zipkin).
7. **Tests & CI/CD** : réactiver tests (unitaires, rest assured), pipelines d’intégration.

## Notes
- Les mots de passe par défaut sont à remplacer par des secrets sécurisés (Vault, variables d’environnement).
- Ajuster les ports/URLs pour s’adapter à votre infrastructure.
- Pour la migration, prévoir un plan étape par étape (strangler pattern) pour isoler chaque domaine depuis le monolithe actuel.
