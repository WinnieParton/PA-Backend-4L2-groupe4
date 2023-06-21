# Utilisez une image de base avec Java préinstallé
FROM openjdk:19-jdk-alpine

# Définissez le répertoire de travail
WORKDIR /app

# Copiez le fichier JAR de votre projet dans le conteneur
COPY target/pa-0.0.1-SNAPSHOT.jar app.jar

# Exposez le port sur lequel votre application Spring Boot écoute
EXPOSE 8080

# Commande de démarrage de l'application Spring Boot
CMD ["java", "-jar", "app.jar"]
