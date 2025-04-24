# Système de Gestion d'Inventaire

Ce projet est une application web simple pour la gestion d'inventaire, développée avec Java Servlets, JSP et MySQL. Elle permet aux utilisateurs de gérer des articles, de se connecter et de visualiser des données d'inventaire.

## Composants Principaux

1. **Java Servlets** : Gèrent la logique métier et les requêtes HTTP. Par exemple, `LoginServlet` pour l'authentification et `ItemsServlet` pour la gestion des articles.
2. **JSP (JavaServer Pages)** : Fournissent l'interface utilisateur dynamique. Les fichiers JSP comme `login.jsp` et `items.jsp` affichent les pages web.
3. **MySQL** : Base de données relationnelle utilisée pour stocker les informations des utilisateurs et des articles.
4. **Tomcat** : Serveur d'applications utilisé pour déployer et exécuter l'application web.
5. **Docker** : Conteneurise l'application et la base de données pour une exécution simplifiée.

## Fonctionnement

- Les utilisateurs accèdent à l'application via un navigateur web.
- Les requêtes HTTP sont traitées par des servlets qui interagissent avec la base de données MySQL.
- Les réponses sont rendues dynamiquement via des pages JSP.
- Docker orchestre les conteneurs pour Tomcat et MySQL, simplifiant le déploiement.

## Déploiement

- L'application est packagée en un fichier WAR et déployée sur Tomcat.
- La base de données est initialisée avec un script SQL au démarrage du conteneur MySQL.