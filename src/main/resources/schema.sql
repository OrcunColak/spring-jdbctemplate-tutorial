create TABLE IF NOT EXISTS stories
(
    -- BIGSERIAL returns Long SERIAL returns Integer
    id         BIGSERIAL  PRIMARY KEY,
    title      TEXT,
    body      TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);