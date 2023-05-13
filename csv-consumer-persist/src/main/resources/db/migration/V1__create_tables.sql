DROP TABLE IF EXISTS preference;
DROP TABLE IF EXISTS activity;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS country;
DROP TABLE IF EXISTS gender;

CREATE TABLE gender (
  id UUID PRIMARY KEY,
  label TEXT
);

CREATE TABLE country (
  id UUID PRIMARY KEY,
  code TEXT UNIQUE
);

CREATE TABLE "user" (
  id BIGINT PRIMARY KEY,
  first_name TEXT,
  last_name TEXT,
  email TEXT UNIQUE,
  gender_id UUID REFERENCES gender(id),
  country_id UUID REFERENCES country(id)
);

CREATE TABLE activity (
  id UUID PRIMARY KEY,
  ip_address TEXT,
  last_login TIMESTAMP WITH TIME ZONE,
  user_id BIGINT REFERENCES "user" (id)
);

CREATE TABLE preference (
  id UUID PRIMARY KEY,
  user_id BIGINT REFERENCES "user"(id),
  favorite_color TEXT
);

CREATE TABLE account (
  id UUID PRIMARY KEY,
  user_id BIGINT REFERENCES "user"(id),
  balance NUMERIC
);
