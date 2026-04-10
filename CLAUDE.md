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
├── controller/
│   ├── AuthController.java             # POST /auth
│   ├── HealthController.java           # GET /, GET /health
│   ├── CategoryController.java         # CRUD /categories
│   ├── PriceController.java            # GET e PUT /prices
│   ├── MediaSetController.java         # CRUD /media-sets
│   └── ProductController.java          # CRUD /products
├── service/
│   ├── CategoryService.java
│   ├── PriceService.java
│   ├── MediaSetService.java
│   └── ProductService.java
├── repository/
│   ├── CategoryRepository.java
│   ├── PriceRepository.java            # findByIdFetched, findByProductId
│   ├── MediaSetRepository.java
│   └── ProductRepository.java          # findAllProjected, findByIdFetched
├── model/
│   ├── Category.java                   # id, name, description
│   ├── Price.java                      # id, product(inverso), salePrice, listPrice
│   ├── MediaSet.java                   # id, thumbnail, medium, large
│   └── Product.java                    # id, category, price, mediaSet, name, description
├── dto/
│   ├── AuthRequest.java / AuthResponse.java
│   ├── CategoryRequest.java / CategoryResponse.java
│   ├── PriceRequest.java / PriceResponse.java
│   ├── MediaSetRequest.java / MediaSetResponse.java
│   └── ProductRequest.java / ProductResponse.java / ProductListResponse.java
├── security/
│   ├── SecurityConfig.java             # Regras de segurança, beans de usuário e PasswordEncoder
│   ├── PublicEndpointsProperties.java  # Lê a lista de endpoints públicos do security.properties
│   ├── JwtUtil.java                    # Geração e validação de tokens JWT
│   └── JwtAuthFilter.java             # Filtro que autentica cada requisição via token
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
- Endpoints públicos configurados em `src/main/resources/security.properties` — adicione um por linha sem alterar o código
- Todos os demais endpoints exigem header `Authorization: Bearer <token>`
- Expiração padrão do token: `3600000ms` (1 hora), configurável via `jwt.expiration` em `application.properties`
- Credencial padrão (in-memory, provisória): `api` / `api123` — substituir por autenticação via banco futuramente

## Modelos

| Entidade  | Tabela      | Relacionamentos                                                        |
|-----------|-------------|------------------------------------------------------------------------|
| Product   | `product`   | `@ManyToOne` Category, `@OneToOne` Price (dono), `@OneToOne` MediaSet |
| Category  | `category`  | —                                                                      |
| Price     | `price`     | `@OneToOne(mappedBy="price")` Product (inverso, `@JsonIgnore`)         |
| MediaSet  | `media_set` | —                                                                      |

- Todos os modelos possuem `createdAt` e `modifiedAt` populados automaticamente pelo Hibernate
- Preços (`salePrice`, `listPrice`) em `BigDecimal` com `precision=10, scale=2`
- Campo `description` do produto como `LONGTEXT` (suporta rich text, HTML, etc.)
- `Price` é sempre criado junto com o `Product` — não existe endpoint de criação avulsa de preço
- Relacionamento `Product → Price`: cascade ALL + orphanRemoval. FK `price_id` fica na tabela `product`

## Paginação

- Todos os endpoints de listagem aceitam `page` (default `1`) e `size` (default `10`)
- A paginação é **1-based** na API — `page=1` retorna a primeira página
- Resposta envolve os dados em `PageResponse<T>` com os campos:
  - `content` — lista de itens da página
  - `page` — página atual (começa em 1)
  - `totalPages` — total de páginas
  - `totalItems` — total de registros

## Hibernate — boas práticas

- Todos os relacionamentos usam `FetchType.LAZY` por padrão — nunca alterar para EAGER sem motivo
- Usar `JOIN FETCH` no JPQL ao carregar entidades relacionadas (evita N+1)
- Usar projeções DTO (`SELECT new ...`) em listagens — nunca buscar `LONGTEXT` em queries de listagem
- Usar `@NativeQuery` em queries críticas onde o Hibernate gerar SQL desnecessariamente complexo
- Verificar o SQL gerado via `show-sql=true` (ativo no perfil `dev`) ao escrever novas queries

## Endpoints

### Públicos

| Método | Path        | Descrição                       |
|--------|-------------|---------------------------------|
| POST   | `/auth`     | Gera token JWT                  |
| GET    | `/`         | Health check da API             |
| GET    | `/health`   | Health check da API             |
| GET    | `/swagger`  | Interface Swagger UI no browser |
| GET    | `/api-docs` | JSON da especificação OpenAPI   |

### Autenticados (`Authorization: Bearer <token>`)

| Método | Path                        | Descrição                                      |
|--------|-----------------------------|------------------------------------------------|
| GET    | `/categories?page=1&size=10`      | Lista categorias paginada                      |
| GET    | `/categories/{id}`                | Busca categoria por ID                         |
| POST   | `/categories`                     | Cria categoria                                 |
| PUT    | `/categories/{id}`                | Atualiza categoria                             |
| GET    | `/prices/{id}`                    | Busca preço por ID                             |
| GET    | `/prices/product/{id}`            | Busca preço pelo ID do produto                 |
| PUT    | `/prices/{id}`                    | Atualiza preço                                 |
| GET    | `/media-sets?page=1&size=10`      | Lista media sets paginada                      |
| GET    | `/media-sets/{id}`                | Busca media set por ID                         |
| POST   | `/media-sets`                     | Cria media set                                 |
| PUT    | `/media-sets/{id}`                | Atualiza media set                             |
| GET    | `/products?page=1&size=10`        | Lista produtos paginada (sem `description`)    |
| GET    | `/products/{id}`                  | Busca produto completo por ID                  |
| POST   | `/products`                       | Cria produto (com price e mediaSet inline)     |
| PUT    | `/products/{id}`                  | Atualiza produto (com price e mediaSet inline) |
