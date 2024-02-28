-- Products
INSERT INTO product (classification, price, id, name) VALUES (0, 20, 1, 'Tiramisu');
INSERT INTO product (classification, price, id, name) VALUES (1, 8, 2, 'Blooming onion');
INSERT INTO product (classification, price, id, name) VALUES (2, 50, 3, 'Pulled beef burger');
INSERT INTO product (classification, price, id, name) VALUES (3, 40, 4, 'Napoletana');
INSERT INTO product (classification, price, id, name) VALUES (4, 5.5, 5, 'Sparkling Water');
-- Waiters
INSERT INTO waiter (id, name) VALUES (1, 'John Smith');
INSERT INTO waiter (id, name) VALUES (2, 'Sandra Perez');
-- Tables
INSERT INTO table_detail (status, id, waiter_id) VALUES (0, 1, 1);
INSERT INTO table_detail (status, id, waiter_id) VALUES (1, 2, 1);
INSERT INTO table_detail (status, id, waiter_id) VALUES (2, 3, 1);
INSERT INTO table_detail (status, id, waiter_id) VALUES (0, 4, 2);
-- Orders
INSERT INTO order_detail (order_number, status, total, created_at, id, table_detail_id, note) VALUES (123, 0, 11, '2024-02-26 15:30:00', 1, 1, 'Nothing');
INSERT INTO order_detail (order_number, status, total, created_at, id, table_detail_id, note) VALUES (124, 0, 0, '2024-02-26 15:30:00', 2, 1, 'Nothing');
-- Product Orders
INSERT INTO product_order (count, id, product_id, order_detail_id, note) VALUES (2, 1, 5, 1, 'Cold water');
-- Sequence start for order_detail
ALTER SEQUENCE order_detail_id_seq RESTART WITH 3;
-- Sequence start for product_order
ALTER SEQUENCE product_order_id_seq RESTART WITH 2;