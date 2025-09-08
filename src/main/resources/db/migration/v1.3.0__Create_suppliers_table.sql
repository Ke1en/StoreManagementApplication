CREATE TABLE IF NOT EXISTS suppliers (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE ,
    phone TEXT,
    address TEXT,
    website TEXT,
    updated_at TIMESTAMP
);
