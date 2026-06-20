CREATE DATABASE IF NOT EXISTS mydb;
USE mydb;

CREATE TABLE IF NOT EXISTS tb_ninja (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        nome VARCHAR(100) NOT NULL,
    vila VARCHAR(100) NOT NULL,
    cla VARCHAR(100) NOT NULL,
    rank_ninja VARCHAR(50) NOT NULL,
    natureza_chakra VARCHAR(50) NOT NULL,
    status BOOLEAN
    );

CREATE TABLE IF NOT EXISTS tb_missao (
                                         id INT AUTO_INCREMENT PRIMARY KEY,
                                         titulo VARCHAR(100) NOT NULL,
    descricao VARCHAR(150) NOT NULL,
    rank_missao VARCHAR(1) NOT NULL,
    vila_origem VARCHAR(100) NOT NULL,
    status VARCHAR(30) NOT NULL
    );

CREATE TABLE IF NOT EXISTS tb_ninja_missao (
                                               id INT AUTO_INCREMENT PRIMARY KEY,
                                               id_ninja INT,
                                               id_missao INT,
                                               funcao VARCHAR(45),
    data_participacao DATE,
    CONSTRAINT fk_ninja FOREIGN KEY (id_ninja) REFERENCES tb_ninja(id),
    CONSTRAINT fk_missao FOREIGN KEY (id_missao) REFERENCES tb_missao(id),
    CONSTRAINT uk_ninja_missao UNIQUE (id_ninja, id_missao)
    );

CREATE TABLE IF NOT EXISTS tb_totalizador_ninja (
                                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                                    descricao VARCHAR(150),
    quantidade INT,
    data_geracao DATE
    );

CREATE OR REPLACE VIEW vw_ninja_missoes AS
SELECT
    n.nome AS nome_ninja,
    n.vila,
    n.cla,
    n.rank_ninja,
    m.titulo AS titulo_missao,
    m.rank_missao,
    nm.funcao,
    m.status AS status_missao
FROM tb_ninja_missao nm
         INNER JOIN tb_ninja n ON nm.id_ninja = n.id
         INNER JOIN tb_missao m ON nm.id_missao = m.id;

CREATE OR REPLACE VIEW vw_total_ninjas_por_vila AS
SELECT
    vila,
    COUNT(*) AS quantidade_ninjas
FROM tb_ninja
GROUP BY vila;

CREATE OR REPLACE VIEW vw_total_missoes_por_rank AS
SELECT
    rank_missao,
    COUNT(*) AS quantidade_missoes
FROM tb_missao
GROUP BY rank_missao;

CREATE OR REPLACE VIEW vw_missoes_sem_ninjas AS
SELECT
    m.id AS id_missao,
    m.titulo,
    m.rank_missao,
    m.vila_origem,
    m.status
FROM tb_missao m
         LEFT JOIN tb_ninja_missao nm ON m.id = nm.id_missao
WHERE nm.id_missao IS NULL;