-- a senha eh admin

INSERT INTO users_tb (user_id, prefecture_id, name, email, password, cpf, role) VALUES (
    gen_random_uuid(),
    (SELECT p.prefecture_id FROM prefectures_tb p WHERE p.name = 'São Paulo' ),
    'tulipa',
    'tulipa@gmail.com',
    '$2a$10$CmSHeq5j1Skg3m/s9kuVFe/HByL/iKI3NqRpVTlWIJN9/N.StLvC6',
    '000.000.000-00',
    'SUPERUSER'
)