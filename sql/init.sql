CREATE SCHEMA testdb;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE score (
    id uuid DEFAULT uuid_generate_v4(),
    player VARCHAR NOT NULL,
    score SMALLINT NOT NULL,
    "time" timestamp NOT NULL,
    PRIMARY KEY (id)
);