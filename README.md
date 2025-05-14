# Système de Gestion d'Inventaire

Application web de gestion d'inventaire utilisant Java Servlets, JSP et MySQL.

## Prérequis

- JDK 17
- Maven 3.9
- Docker et Docker Compose

## Configuration rapide (Debian/Ubuntu)

### 1. Installer JDK 17 (si nécessaire)
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

### 2. Configurer Maven pour utiliser JDK 17
```bash
JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 mvn clean install
```

### 3. Vérifier la configuration
```bash
mvn -v
```
Confirmez que la sortie indique "Java version: 17.x.x"

## Utilisation

### Construction et démarrage
```bash
# Construire l'application avec JDK 17
JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 mvn clean install

# Démarrer les conteneurs
docker-compose up -d
```

### Accès à l'application
- URL : http://localhost:8080
- Identifiants : admin / admin123