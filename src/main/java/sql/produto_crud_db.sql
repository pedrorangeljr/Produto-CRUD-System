
-- Recriar usuários com BIGINT
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,  -- Isso cria BIGINT
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE
);

-- Recriar produtos com BIGINT
CREATE TABLE produtos (
    id BIGSERIAL PRIMARY KEY,  -- BIGINT aqui também
    nome VARCHAR(150) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10,2) NOT NULL CHECK (preco >= 0),
    quantidade_estoque INTEGER NOT NULL DEFAULT 0,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_id BIGINT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Índices (performance)
CREATE INDEX idx_produtos_usuario_id ON produtos(usuario_id);
CREATE INDEX idx_produtos_nome ON produtos(nome);
CREATE INDEX idx_usuarios_email ON usuarios(email);

-- Verificar tipos (deve mostrar bigint)
SELECT column_name, data_type 
FROM information_schema.columns 
WHERE table_name IN ('usuarios', 'produtos') 
AND column_name = 'id';