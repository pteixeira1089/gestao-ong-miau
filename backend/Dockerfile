# --- Estágio 1: Build ---
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia apenas o pom.xml primeiro para baixar dependências (cache layer)
COPY pom.xml .
# Baixa as dependências (se o pom não mudou, o docker usa o cache)
RUN mvn dependency:go-offline

# Copia o código fonte
COPY src ./src

# Compila o projeto e gera o JAR (pula testes para agilizar deploy, mas idealmente rodaria)
RUN mvn clean package -DskipTests

# --- Estágio 2: Runtime ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Cria um usuário não-root para segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia o JAR gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta (apenas documentação, o Railway injeta a variável PORT)
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
