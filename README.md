# 📱 MyStudent - Sistema de Gestão Escolar

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white" alt="Android Platform">
  <img src="https://img.shields.io/badge/Language-Java-ED8B00?logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Database-SQLite-003B57?logo=sqlite&logoColor=white" alt="SQLite">
</p>

O **MyStudent** é um aplicativo Android nativo desenvolvido para facilitar o gerenciamento de alunos, cursos e usuários acadêmicos. O projeto conta com um sistema completo de autenticação e armazenamento de dados local utilizando o banco de dados SQLite.

---

## 🎯 Funcionalidades Principais

* **🔒 Sistema de Autenticação:** Tela de login e cadastro de novos usuários com validação de campos em tempo real.
* **📚 CRUD de Cursos:** Cadastro, listagem, edição popup e exclusão em tempo real de cursos (com campos de Nome, Duração e Turno).
* **👨‍🎓 CRUD de Alunos:** Cadastro completo de alunos integrado com os cursos disponíveis.
* **⚡ Gerador de Matrícula:** Botão automatizado com lógica aleatória para geração instantânea do número de matrícula do aluno.
* **🔄 Interface Reativa:** Listagem dinâmica via Adapters customizados que atualizam a tela imediatamente após qualquer alteração (Update/Delete).

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Descrição |
| :--- | :--- |
| **Java** | Linguagem de desenvolvimento nativa do Android |
| **SQLite** | Banco de dados relacional embutido para persistência local |
| **Android SDK** | Conjunto de ferramentas de desenvolvimento do sistema Android |
| **ListView & Adapters** | Renderização dinâmica e otimizada de listas de dados |

---

## 🗄️ Estrutura do Banco de Dados (SQLite)

O aplicativo gerencia silenciosamente um banco de dados local chamado `mystudent` contendo três tabelas principais de forma unificada:

```sql
-- Tabela de Usuários para Login
CREATE TABLE usuarios (
    id INTEGER PRIMARY KEY AUTOINCREMENT, 
    nome TEXT, 
    email TEXT, 
    senha TEXT
);

-- Tabela de Cursos
CREATE TABLE cursos (
    id INTEGER PRIMARY KEY AUTOINCREMENT, 
    nome TEXT, 
    duracao TEXT, 
    turno TEXT
);

-- Tabela de Alunos (Relacionada ao Curso)
CREATE TABLE alunos (
    id INTEGER PRIMARY KEY AUTOINCREMENT, 
    nome TEXT, 
    email TEXT, 
    data_nasc TEXT, 
    matricula TEXT, 
    curso TEXT
);
