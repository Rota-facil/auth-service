CREATE TABLE IF NOT EXISTS users_tb (
    user_id UUID PRIMARY KEY,
    prefecture_id UUID,
    name VARCHAR(120) NOT NULL,
    email VARCHAR (120) NOT NULL,
    cpf VARCHAR(14),
    password VARCHAR(120),
    google_id TEXT,
    role VARCHAR(10) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_users_prefectures FOREIGN KEY (prefecture_id) REFERENCES prefectures_tb(prefecture_id),
    CONSTRAINT u_email UNIQUE(email),
    CONSTRAINT u_cpf UNIQUE(cpf)
);