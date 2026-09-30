-- V1__init_schema.sql
-- Initial schema migration for Your Supplier Application

CREATE TABLE IF NOT EXISTS users (
    id_user BIGSERIAL PRIMARY KEY,
    user_name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    card_status SMALLINT
);

CREATE TABLE IF NOT EXISTS customer (
    id_user BIGINT PRIMARY KEY,
    CONSTRAINT fk_customer_users FOREIGN KEY (id_user) REFERENCES users (id_user)
);

CREATE TABLE IF NOT EXISTS suppliers (
    id_user BIGINT PRIMARY KEY,
    CONSTRAINT fk_suppliers_users FOREIGN KEY (id_user) REFERENCES users (id_user)
);

CREATE TABLE IF NOT EXISTS promoters (
    id_user BIGINT PRIMARY KEY,
    CONSTRAINT fk_promoters_users FOREIGN KEY (id_user) REFERENCES users (id_user)
);

CREATE TABLE IF NOT EXISTS roles (
    id_role BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS groups (
    id_group BIGSERIAL PRIMARY KEY,
    group_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_groups (
    user_id BIGINT NOT NULL,
    group_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, group_id),
    CONSTRAINT fk_user_groups_users FOREIGN KEY (user_id) REFERENCES users (id_user),
    CONSTRAINT fk_user_groups_groups FOREIGN KEY (group_id) REFERENCES groups (id_group)
);

CREATE TABLE IF NOT EXISTS group_roles (
    group_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (group_id, role_id),
    CONSTRAINT fk_group_roles_groups FOREIGN KEY (group_id) REFERENCES groups (id_group),
    CONSTRAINT fk_group_roles_roles FOREIGN KEY (role_id) REFERENCES roles (id_role)
);

CREATE TABLE IF NOT EXISTS warehouses (
    id_warehouse BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    capacity INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS orders (
    id_order BIGSERIAL PRIMARY KEY,
    customer_entity_id_user BIGINT,
    supplier_entity_id_user BIGINT,
    promoter_entity_id_user BIGINT,
    CONSTRAINT fk_orders_customer FOREIGN KEY (customer_entity_id_user) REFERENCES customer (id_user),
    CONSTRAINT fk_orders_suppliers FOREIGN KEY (supplier_entity_id_user) REFERENCES suppliers (id_user),
    CONSTRAINT fk_orders_promoters FOREIGN KEY (promoter_entity_id_user) REFERENCES promoters (id_user)
);

CREATE TABLE IF NOT EXISTS products (
    id_product BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    price DOUBLE PRECISION NOT NULL,
    warehouse_id BIGINT,
    order_id BIGINT,
    CONSTRAINT fk_products_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses (id_warehouse),
    CONSTRAINT fk_products_order FOREIGN KEY (order_id) REFERENCES orders (id_order)
);

CREATE TABLE IF NOT EXISTS payments (
    id_payment BIGSERIAL PRIMARY KEY,
    payment_constant VARCHAR(50),
    num_card VARCHAR(255),
    cvv VARCHAR(255),
    expiration_date VARCHAR(255),
    payment_value DOUBLE PRECISION,
    id_order BIGINT UNIQUE,
    CONSTRAINT fk_payments_orders FOREIGN KEY (id_order) REFERENCES orders (id_order)
);
