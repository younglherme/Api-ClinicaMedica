-- V7: Normaliza valores legados do campo motivo_cancelamento para nomes de enum
BEGIN;

-- Mapear variações conhecidas para o nome do enum
UPDATE consultas
SET motivo_cancelamento = 'PACIENTE_DESISTIU'
WHERE motivo_cancelamento IN (
  'Paciente não compareceu',
  'Paciente nao compareceu',
  'Paciente desistiu'
);

UPDATE consultas
SET motivo_cancelamento = 'MEDICO_CANCELOU'
WHERE motivo_cancelamento IN (
  'Médico cancelou',
  'Medico cancelou'
);

-- Marcar quaisquer valores óbvios não mapeados como OUTROS (opcional)
UPDATE consultas
SET motivo_cancelamento = 'OUTROS'
WHERE motivo_cancelamento IS NOT NULL
  AND motivo_cancelamento NOT IN (
    'PACIENTE_DESISTIU',
    'MEDICO_CANCELOU',
    'DESASTRES_NATURAIS',
    'FERIADO',
    'PROBLEMAS_ESTRUTURAIS',
    'OUTROS',
    'AGENDADO'
  )
  AND motivo_cancelamento ~* '^[A-Za-zÀ-ú \-]+'; -- protege nulos e entradas já corretas

COMMIT;
