ALTER TABLE order_item
DROP
CONSTRAINT fk_order_item_on_order;

ALTER TABLE "order"
DROP
CONSTRAINT fk_order_on_customer;

ALTER TABLE "order"
DROP
CONSTRAINT fk_order_on_payment;

ALTER TABLE payment
DROP
CONSTRAINT fk_payment_on_order;

CREATE TABLE orders
(
    id           UUID             NOT NULL,
    date         date             NOT NULL,
    status       VARCHAR(255)     NOT NULL,
    total_amount DOUBLE PRECISION NOT NULL,
    payment_id   UUID,
    customer_id  UUID             NOT NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_PAYMENT FOREIGN KEY (payment_id) REFERENCES payment (id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDER_ITEM_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE payment
    ADD CONSTRAINT FK_PAYMENT_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

DROP TABLE "order" CASCADE;