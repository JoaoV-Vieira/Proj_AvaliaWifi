CREATE DATABASE avaliawifi;

CREATE TABLE residencia (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(200) NOT NULL
);

CREATE TABLE comodo (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    residencia_id INTEGER NOT NULL,
    FOREIGN KEY (residencia_id) REFERENCES residencia(id)
);

CREATE TABLE medicao (
    id SERIAL PRIMARY KEY,
    data_hora TIMESTAMP NOT NULL,
    nivel_sinal INT NOT NULL,
    velocidade DOUBLE PRECISION NOT NULL,
    interferencia VARCHAR(100),
    comodo_id INTEGER NOT NULL,
    residencia_id INTEGER NOT NULL,
    FOREIGN KEY (comodo_id) REFERENCES comodo(id),
    FOREIGN KEY (residencia_id) REFERENCES residencia(id)
);