CREATE TABLE category
(
    id          UUID        NOT NULL,
    name        VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE customer
(
    id         UUID         NOT NULL,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    email      VARCHAR(100) NOT NULL,
    password   VARCHAR(50)  NOT NULL,
    address    VARCHAR(100) NOT NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE "order"
(
    id           UUID             NOT NULL,
    date         date             NOT NULL,
    status       VARCHAR(255)     NOT NULL,
    total_amount DOUBLE PRECISION NOT NULL,
    payment_id   UUID,
    customer_id  UUID             NOT NULL,
    CONSTRAINT pk_order PRIMARY KEY (id)
);

CREATE TABLE order_item
(
    id         UUID             NOT NULL,
    quantity   INTEGER          NOT NULL,
    unit_price DOUBLE PRECISION NOT NULL,
    order_id   UUID             NOT NULL,
    product_id UUID             NOT NULL,
    CONSTRAINT pk_order_item PRIMARY KEY (id)
);

CREATE TABLE payment
(
    id       UUID             NOT NULL,
    amount   DOUBLE PRECISION NOT NULL,
    method   VARCHAR(50)      NOT NULL,
    date     date             NOT NULL,
    order_id UUID             NOT NULL,
    CONSTRAINT pk_payment PRIMARY KEY (id)
);

CREATE TABLE products
(
    id             UUID             NOT NULL,
    name           VARCHAR(100)     NOT NULL,
    description    VARCHAR(255),
    price          DOUBLE PRECISION NOT NULL,
    stock_quantity INTEGER          NOT NULL,
    category_id    UUID             NOT NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

ALTER TABLE customer
    ADD CONSTRAINT uc_customer_email UNIQUE (email);

ALTER TABLE payment
    ADD CONSTRAINT uc_payment_order UNIQUE (order_id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDER_ITEM_ON_ORDER FOREIGN KEY (order_id) REFERENCES "order" (id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDER_ITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE "order"
    ADD CONSTRAINT FK_ORDER_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE "order"
    ADD CONSTRAINT FK_ORDER_ON_PAYMENT FOREIGN KEY (payment_id) REFERENCES payment (id);

ALTER TABLE payment
    ADD CONSTRAINT FK_PAYMENT_ON_ORDER FOREIGN KEY (order_id) REFERENCES "order" (id);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);