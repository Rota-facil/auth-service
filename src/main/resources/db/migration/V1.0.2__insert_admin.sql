-- a senha eh senha

INSERT INTO users_tb (user_id, prefecture_id, name, email, password, cpf, role) VALUES (
    gen_random_uuid(),
    (SELECT p.prefecture_id FROM prefectures_tb p WHERE p.name = 'São Paulo' ),
    'tulipa',
    'tulipa@gmail.com',
    '$2a$10$xi8X1eELxbfKvWH26z2aKO.liFbyp.Iatyoh/wm6NHYd./nLAQVQG',
    '000.000.000-00',
    'SUPERUSER'
);

INSERT INTO users_tb (user_id, prefecture_id, name, email, password, cpf, role) VALUES (
    gen_random_uuid(),
    (SELECT p.prefecture_id FROM prefectures_tb p WHERE p.name = 'São Paulo' ),
    'admin_sao_paulo',
    'admin_sao_paulo@gmail.com',
    '$2a$10$xi8X1eELxbfKvWH26z2aKO.liFbyp.Iatyoh/wm6NHYd./nLAQVQG',
    '000.000.000-01',
    'ADMIN'
);