# Projeto Ambiental API 🌿

Esta é uma API desenvolvida em **Spring Boot** conectada a um banco de dados **PostgreSQL**. O projeto está totalmente dockerizado e a imagem oficial está publicada no Docker Hub, permitindo que a aplicação seja executada em qualquer máquina sem a necessidade de instalar o Java ou o PostgreSQL localmente.

---

## 🛠️ Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3.x** (Spring Data JPA, Spring Security)
* **PostgreSQL 15**
* **Docker & Docker Compose**
* **Maven**

---

## 🚀 Como Executar o Projeto com Docker (Recomendado)

Siga os passos abaixo para baixar e rodar a aplicação completa na sua máquina utilizando contêineres.

### Pré-requisitos
Você precisa ter apenas o **Docker** e o **Docker Compose** instalados no seu computador.
* [Download Docker Desktop](https://docker.com)

### Passo a Passo

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/HendrioSantos/atividade_extensionista_arvores-2026
   cd nome-do-repositorio
   ```

2. **Configure as variáveis de ambiente:**
   Duplique o arquivo `.env.example` na raiz do projeto e renomeie a cópia para `.env`.
   ```bash
   # No Windows (PowerShell):
   cp .env.example .env
   ```
   *(Você pode abrir o arquivo `.env` gerado e alterar as senhas se desejar, mas os valores padrão já funcionam automaticamente).*

3. **Suba os contêineres:**
   Execute o comando abaixo para baixar a imagem da API direto do Docker Hub e inicializar o banco de dados:
   ```bash
   docker compose up -d
   ```
   *O parâmetro `-d` roda o projeto em segundo plano, liberando o seu terminal.*

4. **Acesse a aplicação:**
   A API estará disponível e pronta para receber requisições em:
   👉 `http://localhost:8080`

---

## ⚙️ Variáveis de Ambiente Necessárias (`.env`)

O projeto utiliza as seguintes variáveis para garantir a segurança dos dados (nunca envie o arquivo `.env` para o Git):


| Variável | Descrição | Valor Padrão |
| :--- | :--- | :--- |
| `DB_NAME` | Nome do banco de dados PostgreSQL | `bd_atividade_extensionista` |
| `DB_USER` | Usuário do banco de dados | `postgres` |
| `DB_PASS` | Senha do banco de dados | `postgres` |

---

## 🛑 Como Parar a Aplicação

Para desligar os contêineres da API e do banco de dados salvando as informações, execute:
```bash
docker compose down
```

Se você modificar o código-fonte Java e quiser gerar uma nova imagem local para testar, use:
```bash
docker compose up --build
```

---

## 🐳 Imagem Oficial no Docker Hub
A imagem oficial compilada deste projeto pode ser encontrada em:
* [hendriohns/projeto-ambiental-api](https://docker.com)
