# auth-service

Servico de autenticacao e administracao de usuarios/prefeituras do Rota Facil. Ele emite JWT, cadastra contas, autentica usuarios e publica eventos para os demais microservicos manterem suas copias locais sincronizadas.

## Para que serve

- Registro de estudantes, motoristas, administradores e usuarios de prefeitura.
- Login local por email/senha.
- Fluxo OAuth2 com Google e conclusao de cadastro.
- Gestao de prefeituras.
- Emissao de JWT assinado com chave privada.
- Publicacao de eventos de usuarios e prefeituras no RabbitMQ.

## Porta e base path

- Aplicacao: `auth-service`
- Porta: `8084`
- Context path: `/auth`
- Via gateway: `http://localhost:8080/auth`

## Endpoints principais

Usuarios:

- `POST /auth/register`: cria conta publica, com perfil `STUDENT`.
- `POST /auth/driver/register`: cria conta de motorista. Exige `ADMIN` no gateway.
- `POST /auth/user/prefecture/register`: cria conta vinculada a prefeitura. Exige `SUPERUSER`.
- `POST /auth/google/complete-registration?pendingToken={uuid}`: conclui cadastro iniciado por Google.
- `GET /auth/user/login`: realiza login com body `email` e `password`.
- `GET /auth/me`: retorna usuario autenticado.
- `PUT /auth/update`: atualiza usuario autenticado.
- `PATCH /auth/deactivate`: desativa conta autenticada.
- `DELETE /auth`: remove conta autenticada.

Prefeituras:

- `POST /auth/prefectures`: cria prefeitura e usuario admin inicial.
- `GET /auth/prefectures`: lista prefeituras.
- `GET /auth/prefectures/{prefectureId}`: busca prefeitura.
- `PUT /auth/prefectures/{prefectureId}`: atualiza prefeitura.
- `DELETE /auth/prefectures/{prefectureId}`: remove prefeitura.

Infra:

- `GET /auth/health-check`
- `/auth/v3/api-docs`
- `/auth/swagger-ui.html`

## Perfis

- `STUDENT`
- `DRIVER`
- `ADMIN`
- `SUPERUSER`

## Eventos publicados

Exchange: `auth.events`

- `user.created`
- `user.updated`
- `driver.admin.updated`
- `user.deleted`
- `user.email.changed`
- `user.deactivate`
- `prefecture.created`
- `prefecture.updated`
- `prefecture.deleted`

Consumidores importantes:

- `transport-service`: replica usuarios para rotas/viagens.
- `file-service`: remove arquivos de usuarios/prefeituras apagados.
- `audit-service`: registra auditoria.
- `notification-service`: envia email de conta criada/removida.
- `gateway-service`: invalida tokens quando usuario e removido ou troca email.

## Banco de dados

- Default: `jdbc:postgresql://localhost:5434/auth_database`
- Usuario default: `rota-facil`
- Senha default: `admin`
- Migrations: `src/main/resources/db/migration`

## Variaveis relevantes

- `AUTH_DATASOURCE_URL`
- `DATASOURCE_USERNAME`, `DATASOURCE_PASSWORD`
- `EUREKA_URL`
- `RABBITMQ_HOST`, `RABBITMQ_PORT`, `RABBITMQ_USER`, `RABBITMQ_PASSWORD`
- `SECURITY_PRIVATE_KEY`
- `GOOGLE_CLIENT_ID`, `GOOGLE_CLIENT_SECRET`

## Como rodar

Pre-requisitos:

- Java 21.
- PostgreSQL com banco `auth_database`.
- Eureka.
- RabbitMQ.

Comando:

```bash
cd auth-service
./mvnw spring-boot:run
```

## Especializacao

Este servico e a fonte principal de identidade, perfis, prefeituras e tokens. Outros servicos nao devem criar usuarios diretamente; devem consumir eventos do `auth-service` quando precisarem de uma copia local.
