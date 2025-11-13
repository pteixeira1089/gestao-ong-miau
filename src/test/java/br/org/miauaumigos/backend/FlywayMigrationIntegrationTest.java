package br.org.miauaumigos.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class FlywayMigrationIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.flyway.enabled", () -> "true"); // Garante que o Flyway esteja ativo para o teste
    }

    @Test
    void contextLoadsAndMigrationsRunSuccessfully() {
        // Se este teste passar, significa que:
        // 1. O container do PostgreSQL foi iniciado com sucesso.
        // 2. O Spring Boot conseguiu se conectar a ele.
        // 3. O Flyway executou TODAS as migrações no banco de dados limpo do container.
        // 4. O Hibernate (JPA) validou que as entidades (@Entity) correspondem ao schema criado pelo Flyway.
        // Se qualquer um desses passos falhar (ex: um erro de sintaxe no seu SQL), o teste irá quebrar.
    }
}
