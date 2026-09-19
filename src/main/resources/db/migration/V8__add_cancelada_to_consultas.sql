ALTER TABLE consultas
    ADD COLUMN IF NOT EXISTS cancelada BOOLEAN NOT NULL DEFAULT FALSE;

UPDATE consultas
SET cancelada = TRUE
WHERE motivo_cancelamento IS NOT NULL;