# 🚀 Produto CRUD System – Desafio Técnico Java Web

**Sistema completo de gerenciamento de produtos com autenticação de usuários**, desenvolvido como portfólio para demonstrar proficiência em **Java Web (Servlets + JSP)**, **JPA/Hibernate**, **PostgreSQL** e boas práticas de mercado.

> ✅ Projeto pronto para produção, com foco em segurança, escalabilidade e código limpo.

---

## 👨‍💻 Sobre o Autor

**Desenvolvedor Java Junior** com forte base em:

- Programação orientada a objetos e princípios SOLID
- Persistência e otimização de consultas com JPA/Hibernate
- Segurança de aplicações web (BCrypt, sessões, prevenção OWASP)
- Performance para sistemas com múltiplos usuários simultâneos
- Versionamento com Git e boas práticas de commit

---

## 📌 Sobre o Projeto

Este projeto foi desenvolvido para demonstrar na prática conceitos fundamentais exigidos no mercado de desenvolvimento Java Web:

| Requisito | Solução Implementada |
|-----------|----------------------|
| Cadastro e login de usuários | BCrypt para hash de senhas + sessões HTTP |
| CRUD completo de produtos | Servlets + JPA/Hibernate + PostgreSQL |
| Controle de acesso | Filtro de autenticação (100% das rotas privadas) |
| Interface amigável | Bootstrap 5 + JSP + JSTL |
| Persistência segura | Prepared statements via JPA, transações controladas |
| Alta concorrência | Pool de conexões HikariCP, lazy loading, índices estratégicos |

---

## 🧠 Decisões Técnicas 

### 1. Arquitetura em Camadas

- Separação clara entre apresentação, controle, persistência e domínio.
- Facilita manutenção e testes.

### 2. Segurança Ofensiva e Defensiva

- ✅ BCrypt (salt 12 rounds) – senhas nunca em texto puro.
- ✅ Filtro de autenticação centralizado.
- ✅ Validação de entrada no backend.
- ✅ Proteção contra SQL Injection (JPA).
- ✅ XSS mitigado via JSTL `escapeXml`.
- ✅ Logout invalida sessão imediatamente.

### 3. Performance e Escalabilidade

- Pool de conexões HikariCP (20 máx, 5 min).
- Índices no PostgreSQL (`usuarios.email`, `produtos.usuario_id`).
- Lazy loading nas associações JPA (`FetchType.LAZY`).
- Timeout de sessão = 30 minutos.
- Preparado para cache de segundo nível (Hibernate).

### 4. Código Limpo e Manutenível

- Nomes significativos (classes, métodos, variáveis).
- Tratamento explícito de exceções com rollback de transações.
- DAOs coesos com responsabilidade única.
- Configuração externa no `persistence.xml`.

---

## 🛠️ Stack Tecnológica

| Categoria          | Tecnologia                         | Nível de Proficiência |
|--------------------|------------------------------------|------------------------|
| Linguagem          | Java 17                            | Avançado               |
| Web                | Servlets (Jakarta 10), JSP, JSTL   | Avançado               |
| ORM/Persistência   | JPA 3.1, Hibernate 6.4             | Avançado               |
| Banco de Dados     | PostgreSQL 15 (SQL, índices, joins)| Intermediário/Avançado |
| Segurança          | BCrypt, sessões HTTP, filtros      | Avançado               |
| Frontend           | Bootstrap 5, HTML5, CSS3           | Intermediário          |
| Build              | Maven                              | Avançado               |
| Servidor           | Apache Tomcat 10                   | Avançado               |
| Controle de Versão | Git                                | Avançado               |

---

## 🚀 Como Executar

### Pré-requisitos em 1 minuto

- Java 17
- Maven
- PostgreSQL 15+
- Git

### Passo a passo (copiar e colar)

```bash
# 1. Clone
git clone https://github.com/seu-usuario/produto-crud.git
cd produto-crud

# 2. Crie o banco
psql -U postgres -c "CREATE DATABASE produto_crud_db;"
psql -U postgres -d produto_crud_db -f database/schema.sql

# 3. Configure acesso no persistence.xml (usuário/senha)

# 4. Compile e rode
mvn clean package
cp target/produto-crud.war $TOMCAT_HOME/webapps/
$TOMCAT_HOME/bin/startup.sh
