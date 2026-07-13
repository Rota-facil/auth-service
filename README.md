# auth-service

Serviço de identidade do Rota Fácil. Cadastra e autentica usuários, administra prefeituras, emite JWT e publica eventos para sincronização dos demais serviços.

## Porta e base path

- Porta: `8084`
- Context path: `/auth`
- Via gateway: `http://localhost:8080/auth`

## Perfis

`STUDENT`, `DRIVER`, `ADMIN` e `SUPERUSER`.

## Endpoints de usuário

- `POST /auth/register`: cadastro público de estudante.
- `POST /auth/user/login`: login com e-mail e senha.
- `POST /auth/logout`: invalida o token atual por evento.
- `POST /auth/google/complete-registration?pendingToken={uuid}`: conclui cadastro Google.
- `GET /auth/me`: retorna o usuário autenticado.
- `PUT /auth/update`: atualiza a própria conta.
- `PATCH /auth/deactivate`: desativa a própria conta.
- `DELETE /auth`: remove a própria conta.
- `PATCH /auth/user/prefecture/{prefectureId}/change`: estudante troca de prefeitura.
- `GET /auth/students?page=0&size=20`: lista estudantes da prefeitura para `ADMIN/SUPERUSER`.
- `POST /auth/driver/register`: admin cadastra motorista.
- `PUT /auth/driver/{driverId}/update`: admin atualiza motorista.
- `DELETE /auth/driver/{driverId}/delete`: admin desativa motorista.
- `POST /auth/user/prefecture/register`: cria usuário administrativo; a primeira regra aplicável no gateway exige `SUPERUSER`.

## Endpoints de prefeitura

- `POST /auth/prefectures`
- `GET /auth/prefectures`
- `GET /auth/prefectures/{prefectureId}`
- `PUT /auth/prefectures/{prefectureId}`
- `DELETE /auth/prefectures/{prefectureId}`

Os GETs são públicos no gateway; mutações exigem `SUPERUSER`.

Infra: `GET /auth/health-check`, `/auth/v3/api-docs` e `/auth/swagger-ui.html`.

## JWT e segurança

O serviço assina tokens com `SECURITY_PRIVATE_KEY`. O gateway valida com a chave pública e injeta a identidade nos serviços internos. Senhas e chaves devem vir de variáveis de ambiente.

## Eventos

Publica em `auth.events`:

- `user.created`, `user.updated`, `driver.admin.updated`, `user.deleted`, `user.email.changed`, `user.deactivate`, `user.logout`.
- `prefecture.created`, `prefecture.updated`, `prefecture.deleted`.

O cadastro por admin preenche os campos `actor*` para preservar o ator na auditoria. O fluxo atual de `registerUserPrefecture` não publica `user.created`.

Consome de `transport.events`:

- `user.feedback`: atualiza score.
- `trip.completed`: incrementa viagens concluídas.
- `user.trips.increased` e `user.trips.decreased`: ajustam o total de viagens associado ao usuário.

## Persistência

- Banco: `jdbc:postgresql://localhost:5434/auth_database`
- Usuário padrão: `rota-facil`
- Migrations: `src/main/resources/db/migration`
- Hibernate: `ddl-auto=validate`

## Como rodar

```bash
cd auth-service
./mvnw spring-boot:run
```

Requer Java 21, PostgreSQL, Eureka e RabbitMQ. Para OAuth, configure `GOOGLE_CLIENT_ID` e `GOOGLE_CLIENT_SECRET`; para JWT, configure `SECURITY_PRIVATE_KEY`.
