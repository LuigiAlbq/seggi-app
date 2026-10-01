# Seggi App (seggi-app)

Microsserviço backend Seggi para controle de estoque, gestão de usuários, fornecedores, promotores, produtos e distribuição aos interessados.

---

- **Java 21 LTS** (Eclipse Temurin / OpenJDK)
- **Spring Boot 4.1.x**
- **Spring Framework 7**
- **Jakarta EE 11** (`jakarta.persistence`, `jakarta.validation`)
- **Spring Data JPA** & **Hibernate 7** (com `ddl-auto=validate`)
- **Flyway** (via `spring-boot-starter-flyway` — scripts em `src/main/resources/db/migration`)
- **PostgreSQL 16** (banco de dados relacional principal)
- **Testcontainers 2.x** (testes de integração com instância real do PostgreSQL 16 em container)
- **OpenAPI Generator 7.21.0** (com `useSpringBoot4` e `useJackson3` / Jackson 3) — Design de API *Contract-First*
- **SpringDoc OpenAPI 3.1.x** (Swagger UI)
- **Spring Boot Actuator** (monitoramento de saúde e integridade com validação do banco)
- **Lombok**
- **Apache Maven 3.9+**
- **Docker & Docker Compose**

---

## 📋 Pré-requisitos

- **JDK 21 LTS** instalado e configurado no `JAVA_HOME`.
- **Apache Maven 3.9+** instalado.
- **Docker** e **Docker Compose** ativos (necessário para subir o banco via Compose ou rodar testes via Testcontainers). Caso utilize uma instalação do PostgreSQL local fora do Docker, crie manualmente o banco de dados `seggidb` na porta `5432` antes de iniciar a aplicação.

---

## 🐳 Inicializando o Banco de Dados (PostgreSQL)

Para subir a instância do **PostgreSQL 16** com volume persistente via Docker Compose:

```bash
docker compose up -d
```

> ⚠️ **Atenção:** As credenciais abaixo são exclusivas para desenvolvimento local:
> - **Host:** `localhost:5432`
> - **Database:** `seggidb`
> - **User:** `postgres`
> - **Password:** `postgres`
> 
> Em outros ambientes (staging/produção), configure obrigatoriamente as credenciais através de variáveis de ambiente:  
> `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`.

---

## 🛠️ Como Executar o Projeto

### 1. Compilação e Geração de Modelos (OpenAPI)
O projeto adota o padrão **Contract-First**. A especificação OpenAPI está localizada em `src/main/resources/openapi/api-spec.yaml`. Ao compilar, o plugin gera automaticamente os DTOs e contratos de interface em `target/generated-sources/openapi`:

```bash
mvn clean compile
```

### 2. Executar os Testes Automatizados
Os testes de integração utilizam **Testcontainers** (PostgreSQL 16 real em container). É necessário ter o Docker em execução:

```bash
mvn clean verify
```

> ℹ️ **Nota:** A primeira execução baixa a imagem Docker `postgres:16-alpine` e por isso pode demorar um pouco mais.

### 3. Executar a Aplicação Localmente
Com o banco PostgreSQL ativo:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`. Na inicialização, o **Flyway** executa automaticamente as migrações presentes em `src/main/resources/db/migration/` e o Hibernate valida o schema das entidades (`ddl-auto=validate`).

---

## 📖 Documentação, Monitoramento e Swagger UI

Com a aplicação em execução, acesse os endpoints disponíveis:

- **Swagger UI (Documentação Interativa):**  
  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

- **Especificação OpenAPI JSON:**  
  [http://localhost:8080/api-docs](http://localhost:8080/api-docs) *(ou o padrão `/v3/api-docs`)*

- **Health Check da Aplicação (Actuator):**  
  [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)  
  *(Valida o status do microsserviço e a conectividade com o banco PostgreSQL — `db: UP`)*

---

## 🤝 Contribuição e Boas Práticas

1. Mantenha o padrão de código e as anotações do Jakarta EE (`jakarta.*`).
2. Qualquer novo contrato de API deve ser versionado no arquivo `src/main/resources/openapi/api-spec.yaml`.
3. Garanta que `mvn clean verify` passe (build + testes com Testcontainers) antes de abrir Pull Requests.
4. Qualquer alteração estrutural no banco de dados deve ser realizada via script versionado no Flyway (`V{X}__{descricao}.sql` em `src/main/resources/db/migration`).
