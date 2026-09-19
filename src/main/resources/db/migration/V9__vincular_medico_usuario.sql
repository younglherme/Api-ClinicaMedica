WITH novos_usuarios AS (
    INSERT INTO usuarios (login, senha, medico, admin)
    SELECT
        (COALESCE(NULLIF(m.email, ''), 'medico') || '_' || m.id) AS login,
        COALESCE(NULLIF(m.crm, ''), 'senha_' || m.id) AS senha,
        TRUE AS medico,
        FALSE AS admin
    FROM medicos m
    WHERE m.idusuario IS NULL
    RETURNING id, login
)
UPDATE medicos m
SET idusuario = nu.id
FROM novos_usuarios nu
WHERE m.idusuario IS NULL
  AND nu.login = (COALESCE(NULLIF(m.email, ''), 'medico') || '_' || m.id);

UPDATE usuarios u
SET medico = TRUE
WHERE EXISTS (
    SELECT 1
    FROM medicos m
    WHERE m.idusuario = u.id
);

ALTER TABLE medicos
ALTER COLUMN idusuario SET NOT NULL;

ALTER TABLE medicos
ADD CONSTRAINT fk_medicos_usuarios
FOREIGN KEY (idusuario) REFERENCES usuarios(id);

ALTER TABLE medicos
ADD CONSTRAINT uk_medicos_idusuario
UNIQUE (idusuario);
