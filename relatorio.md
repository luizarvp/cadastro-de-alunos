# SISTEMA DE CADASTRO DE ALUNOS

## Integrantes

| Nome | Matrícula |
|---|---|
| Luiza Ribeiro Ventura Pires | 1250100226 |
| Maria Eduarda Pires Gonçalves Medrado | 1250101767 |
| Diogo Gualberto Martins Prudencio | 1250112212 |
| Bernardo de Freitas Aguiar | 1250111320 |

## 1. Introdução

Este trabalho apresenta o desenvolvimento de um sistema desktop para cadastro de alunos.
O sistema foi criado em Java utilizando a biblioteca Swing para a interface gráfica e
SQLite para armazenar os dados em um arquivo local. A proposta é demonstrar, de forma
prática, a construção de uma aplicação com entrada de dados, validação, persistência e
consulta de registros.

## 2. Contexto e objetivo

Em uma instituição de ensino, manter os dados dos alunos organizados facilita a consulta
de informações acadêmicas e reduz o uso de controles manuais. O objetivo do sistema é
permitir que um usuário cadastre, pesquise, altere e exclua alunos de maneira simples.

Cada registro possui nome, matrícula, curso e e-mail. A matrícula é única, evitando
duplicidade no banco de dados.

## 3. Tecnologias utilizadas

- **Java 17:** linguagem utilizada no desenvolvimento.
- **Java Swing:** criação da interface gráfica.
- **SQLite:** banco de dados local, armazenado no arquivo `cadastro-alunos.db`.
- **JDBC:** comunicação entre o programa Java e o banco.
- **Maven:** gerenciamento da dependência do driver SQLite e empacotamento do projeto.

## 4. Funcionamento do sistema

Ao iniciar, a aplicação verifica se o banco de dados e a tabela `alunos` existem. Caso
não existam, eles são criados automaticamente. A tela principal apresenta um formulário
para preenchimento dos dados, botões de ação, campo de pesquisa e uma tabela com os
registros cadastrados.

O botão **Salvar** realiza a inclusão de um novo aluno. Quando uma linha da tabela é
selecionada, os dados são carregados no formulário e o mesmo botão passa a atualizar o
registro. O botão **Excluir selecionado** remove o registro após uma confirmação do
usuário. A pesquisa permite localizar alunos pelo nome, matrícula ou curso.

## 5. Organização do código

O projeto utiliza uma separação simples de responsabilidades:

- `model/Aluno.java`: representa os dados de um aluno.
- `database/Database.java`: cria a conexão e a tabela do SQLite.
- `dao/AlunoDAO.java`: concentra as operações de banco de dados.
- `view/MainFrame.java`: implementa a tela e os eventos dos botões.
- `App.java`: inicializa o banco e abre a janela principal.

Essa divisão melhora a manutenção, pois a interface não precisa conhecer os detalhes
dos comandos SQL.

## 6. Trechos comentados

### Criação da tabela

O método de inicialização utiliza `CREATE TABLE IF NOT EXISTS`. Assim, a tabela só é
criada quando ainda não existe, preservando os dados nas próximas execuções.

```java
CREATE TABLE IF NOT EXISTS alunos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    matricula TEXT NOT NULL UNIQUE,
    curso TEXT NOT NULL,
    email TEXT NOT NULL
)
```

### Persistência com PreparedStatement

As operações usam `PreparedStatement`, que separa os valores do comando SQL e evita
problemas causados pela concatenação direta de textos.

```java
PreparedStatement statement = connection.prepareStatement(sql);
statement.setString(1, aluno.getNome());
statement.setString(2, aluno.getMatricula());
statement.executeUpdate();
```

### Validação do formulário

Antes de salvar, o sistema verifica se todos os campos foram preenchidos. Caso exista
algum campo vazio, uma mensagem é exibida e a gravação não é realizada.

## 7. Modelagem UML opcional

### Caso de uso

**Ator:** Usuário do sistema.

**Casos de uso:** Cadastrar aluno, pesquisar aluno, editar aluno e excluir aluno.

### Classes

- `Aluno`
- `AlunoDAO`
- `Database`
- `MainFrame`

O usuário interage com `MainFrame`, que utiliza `AlunoDAO` para manipular objetos
`Aluno`. O `AlunoDAO` utiliza `Database` para obter a conexão com o SQLite.

## 8. Prints da aplicação

> Inserir aqui os prints da tela executando. Abaixo está a sugestão de legenda:

**Figura 1 – Tela principal do cadastro de alunos.**  
*Inserir print da tela com o formulário e a tabela.*

**Figura 2 – Inclusão de um novo aluno.**  
*Inserir print com os campos preenchidos antes de salvar.*

**Figura 3 – Pesquisa de aluno.**  
*Inserir print mostrando um filtro aplicado.*

**Figura 4 – Registro salvo na tabela.**  
*Inserir print após a gravação.*

## 9. Conclusão

O desenvolvimento do sistema permitiu aplicar conceitos de programação orientada a
objetos, criação de interfaces gráficas, validação de dados, acesso a banco de dados e
organização de código. O resultado atende à proposta de um cadastro com interface
gráfica e gravação persistente em arquivo de banco de dados.

Como melhoria futura, podem ser adicionados login de usuários, níveis de acesso,
exportação dos alunos para PDF, filtros avançados e integração com um banco de dados
servidor.
