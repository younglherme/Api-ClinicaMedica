CREATE TABLE consultas (
                           id BIGSERIAL PRIMARY KEY,
                           medico_id BIGINT NOT NULL,
                           paciente_id BIGINT NOT NULL,
                           data TIMESTAMP NOT NULL,
                           observacoes_medicas VARCHAR(350),
                           convenio VARCHAR(100),
                           CONSTRAINT fk_consultas_medico_id FOREIGN KEY (medico_id) REFERENCES medicos (id),
                           CONSTRAINT fk_consultas_paciente_id FOREIGN KEY (paciente_id) REFERENCES pacientes (id)
);

ALTER TABLE consultas ADD COLUMN motivo_cancelamento VARCHAR(100);