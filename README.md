# Atividade Extensionista - Plataforma de Monitoramento Ambiental

Este é o back-end de uma aplicação inclusiva desenvolvida em **Java + Spring Boot** para reportar danos ambientais (como queimadas e desmatamentos) e gerenciar ativos ecológicos.

## 🚀 Funcionalidades Concluídas
- **Registro de Ocorrências**: Cadastro de denúncias com upload de fotos e dados geográficos.
- **Geocoding Inteligente**: Fallback automático para coordenadas geográficas a partir de endereços de texto utilizando API gratuita (OpenStreetMap).
- **URLs Amigáveis**: Geração automática de `slugs` baseados no tipo de dano e na data do registro.
- **Arquitetura SOLID**: Padrão de projeto estruturado para transição inteligente de estados das ocorrências (`StatusOcorrencia`).
- **Autenticação Avançada**: Gerenciamento de perfis de acesso divididos entre Administradores (Fiscais) e usuários anônimos (Guests via Device ID).

## 🛠️ Tecnologias Utilizadas
- Java 17.0.2
- Spring Boot 3.5.14
- Spring Data JPA
- Spring Security
- Spring Web
- Spring Dev Tools
- Lombok
- Banco de Dados (MySQL / PostgreSQL)
- Flyway Migration
- Maven
