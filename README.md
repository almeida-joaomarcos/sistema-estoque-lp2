# Sistema de Controle de Estoque (Mercado) - LP2

Trabalho prático da disciplina de **Linguagem de Programação II (LP2)**.
Aplicação desktop desenvolvida em **Java Swing** com foco em Orientação a Objetos, contemplando as abordagens de interface **SDI** e **MDI**, operações completas de CRUD e persistência via JDBC com banco de dados PostgreSQL.

---

## Integrantes da Equipe
* João Marcos Almeida
* Luciano Farias
* Rodrigo Barreto
* Marlon Silva

---

## Cenário Escolhido
* **Cenário 4:** Sistema de Controle de Produtos (Loja / Mercado).
* **Entidade de Domínio:** `Produto`
  * **Atributos:**
    * `codigo` (Inteiro, chave primária auto-gerada)
    * `nome` (Texto, identificador comercial do item)
    * `descricao` (Texto, detalhes complementares)
    * `categoria` (Texto, grupo do item: Alimentos, Limpeza, etc.)
    * `preco` (Decimal com precisão, valor unitário)
    * `quantidadeEstoque` (Inteiro, saldo em estoque)
    * `situacao` (Texto, status do item: Ativo / Inativo)

---

## Arquiteturas de Interface Implementadas

### 1. SDI (Single Document Interface)
* Telas organizadas em janelas independentes herdando de `JFrame`.
* Cada funcionalidade (Menu, Cadastro e Consulta) possui ciclo de vida e controle de janela próprio no sistema operacional.

### 2. MDI (Multiple Document Interface)
* Janela mestre utilizando `JFrame` com um painel de desktop virtual `JDesktopPane`.
* As funcionalidades de formulário e consulta abrem como janelas internas filhas utilizando `JInternalFrame`.

---

## Funcionalidades Implementadas (CRUD)

* **Inserir (Create):** Cadastro de novos produtos através de formulário com campos organizados via `GridLayout`.
* **Consultar / Listar (Read):** Exibição em grade com `JTable` envelopada em `JScrollPane`. Suporta busca dinâmica por:
  * Todos os registros
  * Código
  * Nome
  * Categoria
* **Alterar (Update):** Carregamento do produto selecionado na tabela diretamente nos campos do formulário para edição.
* **Excluir (Delete):** Remoção de itens com diálogo modal de confirmação obrigatória via `JOptionPane`.

---

## Estrutura de Pacotes

* `com.estoque.model`: Entidade de domínio (`Produto`) encapsulada com getters, setters e construtores.
* `com.estoque.dao`: Padrão Data Access Object (`ProdutoDAO`) para isolar a persistência das regras visuais.
* `com.estoque.conexao`: Gerenciador de conexão JDBC com o PostgreSQL.
* `com.estoque.view.sdi`: Telas baseadas em múltiplos `JFrame` (`MenuSDI`, `FormProdutoSDI`, `ConsultaProdutoSDI`).
* `com.estoque.view.mdi`: Telas baseadas em `JDesktopPane` e `JInternalFrame` (`MenuMDI`, `FormProdutoMDI`, `ConsultaProdutoMDI`).

---

## Requisitos do Ambiente
* **Java JDK:** 17 ou superior.
* **IDE:** Apache NetBeans.
* **SGBD:** PostgreSQL 14 ou superior.
* **Driver:** `postgresql-42.x.x.jar` adicionado às bibliotecas do projeto.

---

## Configuração do Banco de Dados (PostgreSQL)

Antes de executar com a persistência definitiva, execute o script SQL abaixo:

```sql
CREATE DATABASE mercado_db;

\c mercado_db;

CREATE TABLE produto (
    codigo SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    categoria VARCHAR(50) NOT NULL,
    preco NUMERIC(10, 2) NOT NULL,

---------

## Instruções de Execução
Abra o Apache NetBeans.

Vá em File > Open Project e selecione a pasta raiz SistemaEstoque.

Para testar a Versão SDI:
- Navegue até o pacote com.estoque.view.sdi.
- Clique com o botão direito em MenuSDI.java e selecione Run File (ou aperte Shift + F6).

Para testar a Versão MDI:
- Navegue até o pacote com.estoque.view.mdi.
- Clique com o botão direito em MenuMDI.java e selecione Run File (ou aperte Shift + F6).
    quantidade_estoque INT NOT NULL,
    situacao VARCHAR(20) NOT NULL
);
