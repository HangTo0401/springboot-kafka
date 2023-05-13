DROP TABLE IF EXISTS sales;

--CREATE TABLE gender (
--  id UUID PRIMARY KEY,
--  label TEXT
--);
--
--CREATE TABLE country (
--  id UUID PRIMARY KEY,
--  code TEXT UNIQUE
--);
--
--CREATE TABLE "user" (
--  id BIGINT PRIMARY KEY,
--  first_name TEXT,
--  last_name TEXT,
--  email TEXT UNIQUE,
--  phone TEXT,
--  gender_id UUID REFERENCES gender(id),
--  country_id UUID REFERENCES country(id)
--);
--
--CREATE TABLE orders (
--  id BIGINT PRIMARY KEY,
--  order_date TIMESTAMP,
--  customer_id BIGINT REFERENCES user(id)
--);
--
--CREATE TABLE order_detail (
--  id BIGINT PRIMARY KEY,
--  qty INT,
--  unit TEXT,
--  price BIGINT,
--  order_id BIGINT REFERENCES orders(id),
--  product_id BIGINT REFERENCES product(id)
--);
--
--CREATE TABLE product (
--  id BIGINT PRIMARY KEY,
--  name TEXT,
--  description TEXT
--);

CREATE TABLE IF NOT EXISTS `sales` (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product VARCHAR(255) NOT NULL,
    order_date DATE NOT NULL,
    price BIGINT NOT NULL,
    sale INT NOT NULL,
    unit VARCHAR(255),
    store VARCHAR(255),
    address VARCHAR(255)
 )ENGINE=InnoDB DEFAULT CHARSET=UTF8;