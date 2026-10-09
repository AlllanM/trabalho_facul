# 🥟 Baozi Store - API REST

**Disciplina:** Desenvolvimento Web Back-End  
**Instituição:** UNINTER  
**Aluno / Cliente do Estudo de Caso:** Allan Almeida4976216  
**Tecnologias:** Java, Spring Boot, Spring Data JPA, H2 Database, Maven, Postman  

---

## 1. Descrição do Projeto e Estudo de Caso

A **Baozi Store** é uma pequena loja especializada na venda de pãezinhos chineses (*baozi*). Para informatizar a gestão das vendas, foi desenvolvida esta **API REST** construída em Java com Spring Boot e Spring Data JPA, utilizando o banco de dados relacional em memória **H2 Database**.

### Situação Fictícia (Exigida para o Relatório Acadêmico)
> A **Baozi Store** é uma pequena loja que vende pão chinês. Para melhorar a organização do negócio, foi criado um sistema simples para controlar clientes, produtos e pedidos.  
> Um cliente chamado **Allan Almeida4976216** realizou seu cadastro no sistema.  
> O produto vendido pela loja chama-se **Baozi Tradicional** e é vendido por unidade (preço: R$ 8,50).  
> Em um determinado momento, o cliente realizou um pedido de **5 unidades** do produto.  
> O sistema registra o cliente, o produto comprado e a quantidade solicitada, facilitando o controle da loja.

---

## 2. Como Executar o Projeto no VS Code

1. Abra o terminal do VS Code na pasta do projeto:
   ```powershell
   cd baozi-store
   .\mvnw spring-boot:run
   ```

2. URL da API: `http://localhost:8080`
3. Console H2 Database: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:baozidb`, user: `sa`, password: em branco)

Para acessar a documentação detalhada com a especificação de todos os endpoints, diagrama de casos de uso e exemplos de JSON para o Postman, acesse o arquivo [`baozi-store/README.md`](file:///c:/Users/Allan/Documents/GitHub/trabalho_facul/baozi-store/README.md).
