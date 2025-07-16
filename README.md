# Miau Aumigos - Backend

## 🛠️ Pré-requisitos

- Java 17
- PostgreSQL 16+
- Maven 3.9+

## 🐘 Configuração do Banco de Dados

Antes de executar a aplicação, **crie manualmente o banco de dados** seguindo estes passos:

### Via PgAdmin (GUI):
1. Abra o PgAdmin
2. Conecte-se ao servidor PostgreSQL
3. Clique com o botão direito em **Databases** → **Create** → **Database**
4. Preencha:
    - Name: `miauaumigos`
    - Owner: `postgres` (ou outro usuário com privilégios)

### Via PSQL (Terminal):
```sql
CREATE DATABASE miauaumigos 
WITH 
OWNER = postgres
ENCODING = 'UTF8'
CONNECTION LIMIT = -1;
```

### Permissões (opcional, se usar outro usuário):
```sql
GRANT ALL PRIVILEGES ON DATABASE miauaumigos TO seu_usuario;
```

## ⚙️ Configuração da Aplicação

Crie/atualize o arquivo `src/main/resources/application.properties` com:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/miauaumigos
spring.datasource.username=postgres
spring.datasource.password=sua_senha_aqui

# Flyway (migrations)
spring.flyway.locations=classpath:db/migration
```

## 🚀 Executando a Aplicação

```bash
mvn spring-boot:run
```

## 📦 Estrutura do Banco

O Flyway criará automaticamente as tabelas através dos arquivos em:
`src/main/resources/db/migration/`

**Importante:** Não altere arquivos de migration já aplicados!