FROM eclipse-temurin:21-jdk-alpine

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR da aplicação para o contêiner
COPY target/*.jar app.jar

# Expõe a porta usada pela aplicação (ajuste se necessário)
EXPOSE 8505

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]