# Back End for NRS-School

API REST para gerenciamento escolar (salas de aula, alunos, matérias e usuários),
construída com Spring Boot, autenticação JWT e persistência via Spring Data JPA.

## Stack

| Categoria        | Tecnologia                                              |
| ---------------- | ------------------------------------------------------- |
| Linguagem        | Java 17                                                 |
| Framework        | Spring Boot 3.5.16 (Web, Security, Data JPA, Actuator)  |
| Autenticação     | Spring Security + JWT (jjwt 0.11.5)                     |
| Banco de dados   | PostgreSQL (produção/dev) e H2 (local/testes)           |
| Mapeamento       | ModelMapper                                             |
| Utilidades       | Lombok                                                  |
| Redis            | Spring Data Redis / Jedis (dependência declarada, sem uso no código) |
| Testes           | JUnit 5 + Cucumber 7 + Spring Boot Test                 |
| Qualidade        | Checkstyle (plugin Maven)                               |
| Build            | Maven Wrapper (`./mvnw`)                                |

## Estrutura do projeto

```
src/main/java/com/nrs/school/back
├── config/       # Security, JWT filter, CORS, ModelMapper, seeds locais
├── entities/     # Entidades JPA e DTOs (request/response)
├── enm/          # Enums de erro
├── exceptions/   # Exceções de domínio
├── repository/   # Repositórios Spring Data JPA
├── resource/     # Controllers REST
└── service/      # Regras de negócio

src/main/resources  # application.properties e perfis
src/test/java       # Steps do Cucumber, fixtures e configuração de teste
src/test/resources  # Features (.feature) do Cucumber
```

## Requisitos

- Java 17+
- Docker (opcional, para o PostgreSQL local)
- Um banco PostgreSQL acessível (ou use o perfil `local-h2`)

## Configuração por perfil

O perfil é definido pela variável de ambiente `SPRING_PROFILE_ACTIVE`
(veja `src/main/resources/application.properties`). A aplicação sobe na porta **8081**.

| Perfil           | Banco                          | Observações                                            |
| ---------------- | ------------------------------ | ------------------------------------------------------ |
| `local-h2`       | H2 em memória (`testdb`)       | Console H2 em `/h2-console`; JWT configurado           |
| `local-postgres` | PostgreSQL `localhost:5432`    | Banco `NRS_SCHOOL`, usuário `develop`                  |
| `dev`            | PostgreSQL via variáveis       | `ddl-auto=update`                                      |
| `prod`           | PostgreSQL via variáveis       | Requer credenciais e chave JWT por ambiente            |
| `test`           | H2 em memória                  | Usado automaticamente pelos testes                     |

### Variáveis de ambiente

| Variável                  | Perfil        | Descrição                             |
| ------------------------- | ------------- | ------------------------------------- |
| `SPRING_PROFILE_ACTIVE`   | todos         | Perfil ativo da aplicação             |
| `SPRING_DATASOURCE_URL`   | `dev`         | URL JDBC do banco                     |
| `SPRING_DATASOURCE_USERNAME` | `dev`      | Usuário do banco                      |
| `SPRING_DATASOURCE_PASSWORD` | `dev`      | Senha do banco                        |
| `DB_HOST`                 | `prod`        | Host do banco (ex.: `host:5432/db`)   |
| `DB_SCHEMA`               | `prod`        | Nome do schema/banco                  |
| `DB_USER`                 | `prod`        | Usuário do banco                      |
| `DB_PASSWORD`             | `prod`        | Senha do banco                        |
| `JWT_SECURITY_KEY`        | `prod`        | Chave secreta usada para assinar JWT  |

As propriedades `security.jwt.secret-key` e `security.jwt.expiration-time`
(1 hora por padrão) ficam nos arquivos `application-*.properties`.

## Como executar

### Com H2 (sem banco externo)

```bash
SPRING_PROFILE_ACTIVE=local-h2 ./mvnw spring-boot:run
```

### Com PostgreSQL local

```bash
make local-postgres-start                       # sobe o PostgreSQL via Docker
SPRING_PROFILE_ACTIVE=local-postgres ./mvnw spring-boot:run
make local-postgres-stop                        # derruba o container
```

### Build

```bash
./mvnw clean package
java -jar target/school.back-0.0.4-SNAPSHOT.jar
```

### Docker Compose (raiz)

O `docker-compose.yml` da raiz sobe:

- `db`: PostgreSQL 16 (`NRS_SCHOOL`, usuário `develop`);
- `nrs-school-java`: imagem `neresdev/nrs-school-java`, perfil `dev`, exposta em `8081`.

O `scripts/init-database.sql` cria as tabelas, sequências e FKs (compatível com
PostgreSQL/Neon) e pode ser usado em bancos gerenciados.

## Dados de exemplo

Nos perfis `local-h2`, `local-postgres` e `test`, a classe `LocalConfig` limpa o
banco e insere dados iniciais:

- usuário `admin@admin.com` com senha `admin`;
- matérias: Matemática, Português, Ciências, História;
- salas: `4°B`, `3°C`, `7°D`, `2°A`;
- alunos: Diego Stretz (`m423af1`) e Bruno Silva (`m34m1en`).

## Autenticação

Todas as rotas, exceto `/auth/**`, exigem o header:

```
Authorization: Bearer <token>
```

O token é obtido em `POST /auth/login`. Requisições sem token retornam `401`;
tokens inválidos/expirados retornam `403`. As respostas de erro são JSON com
`timestamp`, `path`, `status` e `error` (e `errorCode` em erros de domínio).

## Endpoints

### Auth (públicos)

| Método | Rota           | Body/Descrição                                        |
| ------ | -------------- | ----------------------------------------------------- |
| POST   | `/auth/signup` | `{ "fullName", "email", "password" }`                 |
| POST   | `/auth/login`  | `{ "email", "password" }` → `{ "token", "expiresIn" }`|

### Classrooms

| Método | Rota                                             | Descrição                              |
| ------ | ------------------------------------------------ | -------------------------------------- |
| GET    | `/classrooms`                                    | Lista todas as salas                   |
| GET    | `/classrooms/{classroomReferenceCode}`           | Busca sala pelo código de referência   |
| GET    | `/classrooms/classroom-students/{refCode}`       | Lista alunos da sala                   |
| POST   | `/classrooms/create`                             | Cria uma sala                          |

Corpo de criação de sala: `classroomName`, `capacity`, `teacher`, `shift`,
`classNumber`, `classroomReferenceCode`.

### Students

| Método | Rota                                       | Descrição                                |
| ------ | ------------------------------------------ | ---------------------------------------- |
| GET    | `/students`                                | Lista todos os alunos                    |
| GET    | `/students/{registration}`                 | Busca aluno pela matrícula               |
| GET    | `/students/classroom/{refCode}`            | Lista alunos pela referência da sala     |
| POST   | `/students`                                | Cria aluno                               |
| PUT    | `/students`                                | Atualiza aluno pela matrícula            |
| DELETE | `/students/{registration}`                 | Remove aluno pela matrícula              |

Corpo de aluno: `studentName`, `studentEmail`, `classroomName` (opcional),
`registration` (7 caracteres). A criação retorna `201` com o header `Location`.

### Subjects

| Método | Rota         | Descrição               |
| ------ | ------------ | ----------------------- |
| GET    | `/subjects`  | Lista todas as matérias |

### Users

| Método | Rota          | Descrição                    |
| ------ | ------------- | ---------------------------- |
| GET    | `/users/me`   | Usuário autenticado atual    |
| GET    | `/users/`     | Lista todos os usuários      |

## Modelo de dados

- `USERS`: usuários da aplicação (login por e-mail, senha com BCrypt).
- `CLASSROOMS`: salas (nome, capacidade, professor, turno, número, ref code UUID).
- `STUDENTS`: alunos (nome, e-mail, matrícula, `IDT_CLASSROOM`, ref code UUID).
- `SUBJECT`: matérias.
- `STUDENTS_RECOVERY`: relação aluno × matéria de recuperação.

## Testes

Os testes de integração utilizam Cucumber com H2 e perfil `test`:

```bash
./mvnw test
```

As features ficam em `src/test/resources/features`:

- `authentication.feature` — cadastro e login;
- `student.feature` — CRUD de alunos;
- `classroom.feature` — listagem de salas e alunos por sala;
- `subject.feature` — listagem de matérias.

## Qualidade de código

O Checkstyle roda na fase `validate` (incluindo fontes de teste) e falha o build
em caso de violação (`checkstyle.xml`):

```bash
./mvnw validate
```

## Coleções de requisição

- `collection.json`: coleção Insomnia com as rotas principais e ambientes
  `local` (`http://localhost:8081`) e `prod`.
- `requests/restclient/request.http`: exemplos antigos de requisições HTTP.
