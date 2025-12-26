CREATE TABLE usuarios (
                          id BIGSERIAL PRIMARY KEY,
                          login VARCHAR(100),
                          senha VARCHAR(255),
                          medico BOOLEAN NOT NULL,
                          admin BOOLEAN NOT NULL DEFAULT FALSE
);