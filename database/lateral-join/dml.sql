CREATE TABLE product (
    SELECT   id,
             id * 10 * random() AS price,
             'product ' || id AS product
    FROM generate_series(1, 1000) AS id;

CREATE TABLE product (
    id              bigserial,
    price        text,
    desired_price   numeric
);

CREATE TABLE wishlist (
    id              bigserial,
    username        text,
    desired_price   numeric
);

INSERT INTO wishlist VALUES
    (1, 'hans', '450'),
    (2, 'joe', '60'),
    (3, 'jane', '1500')
;