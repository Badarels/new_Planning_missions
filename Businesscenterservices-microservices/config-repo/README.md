# BCS Config Repository

Ce dépôt Git centralise les configurations des microservices Business Center Services.

Structure recommandée :

```
config-repo/
├── application.yml        # configuration commune (optionnelle)
├── api-gateway.yml        # configuration spécifique API Gateway
├── auth-service.yml       # configuration Auth
├── medecins-service.yml   # configuration Médecins
├── centres-service.yml    # configuration Centres
├── missions-service.yml   # configuration Missions
└── notifications-service.yml # configuration Notifications
```

Initialisez ce dossier comme un dépôt Git séparé (`git init`) puis commitez les fichiers.
Ensuite, pointez `spring.cloud.config.server.git.uri` vers ce dépôt.
