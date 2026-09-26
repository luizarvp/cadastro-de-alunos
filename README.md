# Sistema de Cadastro de Alunos

Projeto acadêmico desenvolvido em Java 25, com interface gráfica Swing e persistência em banco SQLite.

## Funcionalidades

- Cadastrar aluno com nome, matrícula, curso e e-mail.
- Listar os alunos cadastrados.
- Selecionar um registro para editar.
- Excluir um aluno após confirmação.
- Pesquisar por nome, matrícula ou curso.
- Validar campos obrigatórios e evitar matrícula duplicada.

## Como executar

1. Instale o JDK 25 ou superior e o Maven.
2. Na pasta do projeto, execute:

```bash
mvn clean package
java -jar target/cadastro-alunos-1.0.0.jar
```

O arquivo `cadastro-alunos.db` é criado automaticamente na pasta do projeto.

## Estrutura

- `model/Aluno.java`: entidade do sistema.
- `dao/AlunoDAO.java`: operações de inclusão, consulta, alteração e exclusão.
- `database/Database.java`: conexão e criação da tabela SQLite.
- `view/MainFrame.java`: interface gráfica principal.
- `App.java`: ponto de entrada da aplicação.
