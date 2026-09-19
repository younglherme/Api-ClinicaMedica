# API Clínica Médica

API REST desenvolvida em Spring Boot para gestão de uma clínica médica (TCC). Oferece cadastro de pacientes e médicos, agendamento de consultas, cancelamentos e envio de notificações por e-mail.

## Tecnologias

- Java 17
- Spring Boot 3.1
- Spring Data JPA
- PostgreSQL
- Flyway (migrations)
- Spring Mail + Thymeleaf (templates de e-mail)
- Lombok
- Maven

## Pré-requisitos

- JDK 17+
- Maven 3.8+
- PostgreSQL em execução

## Configuração

1. Crie o banco de dados (ex.: `consultas`).
2. Ajuste `src/main/resources/application.properties` com usuário, senha e URL do PostgreSQL.
3. Para envio de e-mail, configure as propriedades `spring.mail.*` (SMTP).

As migrations do Flyway em `src/main/resources/db/migration` são aplicadas na subida da aplicação.

## Executando

Na pasta do projeto (`api`):

```bash
mvn spring-boot:run
```

Ou, após compilar:

```bash
mvn clean package
java -jar target/api-0.0.1-SNAPSHOT.jar
```

Por padrão a API fica disponível em `http://localhost:8080`.

## Endpoints principais

| Recurso        | Base path      |
|----------------|----------------|
| Consultas      | `/consultas`   |
| Pacientes      | `/pacientes`   |
| Médicos        | `/medicos`     |
| Usuários       | `/usuarios`    |
| Notificações   | `/notificacoes`|
| Teste de e-mail| `/teste-email` |

## Testes

```bash
mvn test
```

## Licença

Projeto acadêmico (TCC).
