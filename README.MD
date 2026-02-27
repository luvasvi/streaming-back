# 🎬 FTTB - Streaming API (Back-end)

API desenvolvida em **Java 21** com **Spring Boot 3** para consumo de dados cinematográficos.

### 🛠 Tecnologias e Dependências
- **Spring Web**: Criação de endpoints REST.
- **Spring Cloud OpenFeign**: Consumo simplificado da API externa do TMDB.
- **Lombok**: Redução de código boilerplate (Getters/Setters).
- **Jackson**: Mapeamento de JSON para Objetos (DTOs).

### 🚀 Funcionalidades
- Busca de filmes e séries em alta.
- Detalhes completos de títulos, incluindo elenco e recomendações.
- **[NOVO]** Busca de episódios detalhados por temporada.

### ⚙️ Configuração
É necessário configurar sua chave da API no `application.properties`:
`tmdb.api.key=SUA_CHAVE_AQUI`