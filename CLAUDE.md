# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Stack

- Java 17
- Spring Boot 3.2.4
- Maven
- MySQL 8.0 (database: `ccproject`, porta `3306`)
- Spring Data JPA + Hibernate
- Spring Validation

## Commands

```bash
# Rodar a aplicação
mvn spring-boot:run

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
├── dto/                                # Objetos de transferência
└── exception/                          # Exceções customizadas
```

## Banco de dados

Conexão configurada em `src/main/resources/application.properties`:

- URL: `jdbc:mysql://localhost:3306/ccproject`
- Usuário: `root`
- `ddl-auto=update` — o Hibernate atualiza automaticamente o schema com base nos modelos JPA

## Endpoints

| Método | Path      | Descrição              |
|--------|-----------|------------------------|
| GET    | `/`       | Health check da API    |
| GET    | `/health` | Health check da API    |
