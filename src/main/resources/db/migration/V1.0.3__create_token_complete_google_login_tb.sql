CREATE TABLE IF NOT EXISTS token_complete_google_login_tb (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    pending_token UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expiration TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT u_pending_token UNIQUE(pending_token),
    CONSTRAINT fk_token_complete_gogle_login_user FOREIGN KEY (user_id) REFERENCES users_tb(user_id) ON DELETE CASCADE
)