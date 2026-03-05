# 🎬 FTTB - Streaming API (Back-end)

API robusta desenvolvida em **Java 21** com **Spring Boot 3**, projetada para gerenciar perfis de usuário, autenticação e consumo inteligente de metadados cinematográficos via TMDB.

## 🛠 Tecnologias e Dependências
- **Java 21** e **Spring Boot 3**: Núcleo da aplicação.
- **Spring Security**: Autenticação **Basic Auth** e proteção de rotas.
- **Spring Data JPA & H2 Database**: Persistência de usuários e favoritos em banco de dados em memória.
- **Spring Cloud OpenFeign**: Cliente HTTP declarativo para consumo da API do TMDB.
- **Lombok**: Redução de código boilerplate.
- **Jackson**: Processamento e mapeamento eficiente de JSON/DTOs.

## 🚀 Funcionalidades
- **Gestão de Usuários**: Registro de novos perfis e autenticação segura de login.
- **Sistema de Favoritos**: Persistência real de filmes e séries vinculados ao `USER_ID` do usuário logado.
- **Consumo TMDB**: Busca de títulos em alta, detalhes técnicos, elenco e recomendações.
- **Exploração de Conteúdo**: Detalhamento de episódios por temporada para séries.

## ⚙️ Configuração
Configure sua chave do TMDB e as propriedades do banco no arquivo `src/main/resources/application.properties`:

```properties
# TMDB API
tmdb.api.key=SUA_CHAVE_AQUI

# H2 Console & Database
spring.datasource.url=jdbc:h2:mem:streamingdb
spring.h2.console.enabled=true