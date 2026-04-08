# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Stack

- Java 17
- Spring Boot 3.2.4
- Maven
- MySQL 8.0 (database: `ccproject`, porta `3306`)
- Spring Data JPA + Hibernate
- Spring Validation
- Spring Security + JWT (jjwt 0.12.6)
- SpringDoc OpenAPI 2.5.0 (Swagger UI)
- Spring Boot DevTools (hot reload — apenas no perfil `dev`)

## Commands

```bash
# Rodar a aplicação (perfil dev por padrão)
mvn spring-boot:run

# Rodar em outro perfil
APP_ENV=hml mvn spring-boot:run
APP_ENV=prd mvn spring-boot:run

# Compilar
mvn compile

# Rodar testes
mvn test

# Rodar um teste específico
mvn test -Dtest=NomeDaClasseTest
```

## Estrutura de pacotes

```
com.example.claudecodeproject
├── ClaudeCodeProjectApplication.java   # Entry point
├── controller/                         # REST controllers
├── service/                            # Regras de negócio
├── repository/                         # Interfaces JPA
├── model/                              # Entidades JPA
├── dto/                                # Objetos de transferência (padrão Request/Response)
├── security/                           # JWT, filtros e configuração do Spring Security
└── exception/                          # Exceções customizadas
```

## Banco de dados

- URL: `jdbc:mysql://localhost:3306/ccproject`
- Usuário: `root`
- `ddl-auto=update` em todos os perfis — o Hibernate atualiza o schema automaticamente com base nos modelos JPA

## Perfis

Controlado pela variável de ambiente `APP_ENV` (padrão: `dev`).

| Config         | dev    | hml    | prd    |
|----------------|--------|--------|--------|
| `ddl-auto`     | update | update | update |
| `show-sql`     | true   | false  | false  |
| `useSSL`       | false  | false  | true   |
| DevTools       | ativo  | off    | off    |

## Segurança

- Autenticação via JWT — token obtido em `POST /auth`
- Endpoints públicos configurados em `src/main/resources/security.properties`
- Todos os demais endpoints exigem header `Authorization: Bearer <token>`
- Expiração padrão do token: `3600000ms` (1 hora), configurável via `jwt.expiration`
- Credencial padrão (in-memory): `api` / `api123`

## Endpoints

| Método | Path        | Auth | Descrição                       |
|--------|-------------|------|---------------------------------|
| POST   | `/auth`     | Não  | Gera token JWT                  |
| GET    | `/`         | Não  | Health check da API             |
| GET    | `/health`   | Não  | Health check da API             |
| GET    | `/swagger`  | Não  | Interface Swagger UI no browser |
| GET    | `/api-docs` | Não  | JSON da especificação OpenAPI   |
