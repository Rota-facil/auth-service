CREATE TABLE IF NOT EXISTS prefectures_tb (
    prefecture_id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    region VARCHAR(50) NOT NULL
);


INSERT INTO prefectures_tb VALUES (
    gen_random_uuid(),
    'São Paulo',
    'SOUTHEAST'
);