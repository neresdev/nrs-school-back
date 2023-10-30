-- Crie um novo usuário (substitua 'usuario' e 'senha' pelos valores desejados)
CREATE USER 'usuario'@'%' IDENTIFIED BY 'senha';

-- Conceda permissões para o banco de dados
GRANT ALL PRIVILEGES ON escola.* TO 'usuario'@'%';

-- Use o banco de dados escola
USE escola;

-- Crie a tabela "alunos" com uma coluna "nome" do tipo VARCHAR(100)
CREATE TABLE alunos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100)
);