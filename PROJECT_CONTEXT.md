# Contexto do projeto: Café Marangoni

## Visão geral
Este repositório é uma aplicação web em Spring Boot para uma cafeteria chamada Café Marangoni. O objetivo principal do projeto é apresentar uma landing page e um cardápio interativo para clientes, com opções de navegação, pedidos em demonstração e cadastro/login em front-end.

O projeto está em andamento como uma versão de protótipo/front-end, com foco em apresentação visual e experiência de usuário. Há uso de Thymeleaf para renderização de templates HTML, Bootstrap para layout e JavaScript para o catálogo dinâmico e manipulação do carrinho local.

## Stack tecnológica
- Java 21
- Spring Boot 4.1.1
- Thymeleaf
- Maven
- Bootstrap 5
- HTML/CSS/JavaScript
- LocalStorage no navegador para simular carrinho e pedidos

## Estrutura principal do projeto

```text
Cafeteria-Marangoni/
├── pom.xml
├── README.md
├── PROJECT_CONTEXT.md
├── mvnw / mvnw.cmd
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── senai499/com/br/Cafe_Marangoni/
│   │   │       ├── CafeMarangoniApplication.java
│   │   │       └── HomeController.java
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   ├── static/
│   │   │   │   ├── css/
│   │   │   │   │   └── style.css
│   │   │   │   ├── img/
│   │   │   │   ├── js/
│   │   │   │   │   └── script.js
│   │   │   │   └── video/
│   │   │   └── templates/
│   │   │       ├── index.html
│   │   │       ├── produtos.html
│   │   │       ├── pedidos.html
│   │   │       ├── login.html
│   │   │       ├── cadastro.html
│   │   │       ├── admin.html
│   │   │       └── fragments/
│   │   │           └── navbar.html
│   └── test/
│       └── java/
│           └── senai499/com/br/Cafe_Marangoni/
│               └── CafeMarangoniApplicationTests.java
```

## Arquitetura e comportamento
### 1) Backend
O backend é mínimo e foi usado principalmente para iniciar o app Spring Boot e servir as páginas HTML.

Arquivo principal:
- `src/main/java/senai499/com/br/Cafe_Marangoni/CafeMarangoniApplication.java`

Controlador principal:
- `src/main/java/senai499/com/br/Cafe_Marangoni/HomeController.java`

Hoje o projeto mapeia apenas a rota inicial:
- `GET /` → retorna a página `index`

Ou seja, a maioria das outras páginas são tratadas como templates estáticos HTML, sem um controller específico para cada rota.

### 2) Front-end
A camada visual está em:
- `src/main/resources/templates/*.html`
- `src/main/resources/static/css/style.css`
- `src/main/resources/static/js/script.js`

A navegação e o design da interface estão em HTML + CSS. O catálogo de produtos, filtros, carrinho e pedidos de demonstração são controlados no JavaScript.

### 3) Dados do catálogo
O catálogo principal está no arquivo:
- `src/main/resources/static/js/script.js`

No início do arquivo existe a constante `catalog`, que estrutura os produtos por categoria:
- bebidas quentes
- bebidas geladas
- padaria e confeitaria
- lanches salgados
- sobremesas
- opções saudáveis
- produtos para venda

Cada categoria contém:
- `id`
- `title`
- `description`
- `image` (imagem padrão da categoria)
- `products` (lista de itens)

Cada produto contém:
- `id`
- `name`
- `description`
- `price`
- `image` (imagem específica do produto)

Importante: o sistema usa a imagem do produto quando existe; caso contrário cai para a imagem da categoria.

### 4) Renderização do catálogo
A função `renderCatalog()` monta o HTML dos grupos e dos produtos dinamicamente. Ela:
- lê `catalog`
- gera os filtros de categoria
- monta a coleção de produtos na página
- usa `src="${productImageRoot}${productImage}"` para carregar a imagem

Isso significa que, para trocar a imagem de um item, a alteração mais correta é feita no JavaScript, no objeto do produto específico.

### 5) Carrinho e pedidos de demonstração
A lógica de pedido e armazenamento local também está em `script.js`.

Usa `localStorage` para guardar:
- carrinho
- histórico de pedidos
- conta de demonstração
- cliente logado

Constantes relevantes:
- `storageKeys.cart`
- `storageKeys.orders`
- `storageKeys.account`
- `storageKeys.customer`

Esses dados ficam no navegador e servem apenas para demonstração local, sem integração com banco de dados real.

## Como alterar imagens do cardápio
### Opção correta
Se a intenção é trocar a imagem de um item específico do catálogo, edite o campo `image` do produto em `script.js`.

Exemplo:

```js
{ id: "espresso", name: "Café expresso", price: 8.9, image: "imagem_representacao_Cafe_expresso.jpg" }
```

A imagem deve estar em:
- `src/main/resources/static/img/`

### Exemplo de fluxo
1. Coloque a imagem nova na pasta `static/img`
2. Renomeie o arquivo de forma organizada
3. Atualize o valor do campo `image` no item correspondente em `script.js`
4. Recarregue a página de produtos

## Como alterar o cardápio em geral
Para adicionar ou remover produtos:
1. Abra `src/main/resources/static/js/script.js`
2. Localize a constante `catalog`
3. Ajuste a categoria e os itens listados
4. Mantenha a estrutura correta do objeto

Estrutura mínima de um item:

```js
{
  id: "nome-do-item",
  name: "Nome do produto",
  description: "Descrição do produto",
  price: 19.9,
  image: "arquivo_da_imagem.jpg"
}
```

## Templates importantes
### `index.html`
Página inicial da cafeteria. Conta com hero section, sobre, destaques e contato.

### `produtos.html`
Página do cardápio. É a principal tela em que o catálogo é exibido.

### `pedidos.html`
Página para apresentar o carrinho e o processo de pedido em demonstração.

### `login.html`
Tela de login de demonstração usando `localStorage`.

### `cadastro.html`
Tela de cadastro de conta demo.

### `admin.html`
Tela de administração para visualizar pedidos em demonstração.

### `fragments/navbar.html`
Fragmento reutilizado para a navegação do site.

## Configuração do projeto
Arquivo:
- `src/main/resources/application.properties`

Configurações principais:
- nome da aplicação: `Cafe-Marangoni`
- desativa auto-configuração de JPA/Datasource
- template prefix/suffix do Thymeleaf
- porta do servidor: `8080`

## Como rodar o projeto
No terminal da raiz do projeto:

```bash
./mvnw spring-boot:run
```

Ou, no Windows:

```bash
mvnw.cmd spring-boot:run
```

Em seguida, abrir:
- `http://localhost:8080`

## Observações importantes para IA ou manutenção
- O projeto ainda não está conectado a banco de dados real.
- O catálogo e pedidos são atuais em front-end, não persistentes no backend.
- Há páginas com links estáticos e também templates Thymeleaf.
- A alteração mais segura para o catálogo é feita em `script.js`, e não diretamente em `produtos.html`, salvo para elementos fixos da página.
- O projeto usa `localStorage` para simulação, então qualquer mudança no carrinho ou histórico ocorre apenas no navegador do usuário.
- A pasta `static/img` é a fonte principal das imagens do projeto.

## Resumo para outra IA
Esse projeto é um protótipo de site de cafeteria com backend mínimo em Spring Boot e interface rica em HTML/CSS/JS. O coração do sistema é um catálogo dinâmico em JavaScript que simula pedidos e navegação. O principal arquivo de manutenção para o cardápio e imagens é `src/main/resources/static/js/script.js`, enquanto as páginas visuais ficam em `src/main/resources/templates`.

Se uma IA for modificar o catálogo, a regra principal é: alterar `catalog`, `products`, e `image` dos itens no JavaScript; se for modificar um layout visual fixo, mexer na template correspondente e no CSS relacionado.
