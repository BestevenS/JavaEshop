INSERT INTO customer (idcustomer, lastname, firstname, afm, telephone)
VALUES (NULL, 'Manolis', 'Manolis', 'ai123546', '696996966'),
(NULL, 'Stefanos', 'Stefanos', 'AE23445', '696996967'),
(NULL, 'Epitheto', 'Onoma', 'ai123546', '696996966'),
(NULL, 'Lastname', 'Firstname', 'AE23445', '696996967');

INSERT INTO inventory (idinv, category, description, price, quantity)
VALUES (NULL, 'Keyboard', 'ck-104', 40, 20),
(NULL, 'Laptop', 'MSI', 1500, 30),
(NULL, 'Mouse', 'Zeroground Soorin', 25, 30),
(NULL, 'Headset', 'Keiji', 30, 20);

INSERT INTO orders(idOrders, custid, invid, quantity, price)
VALUES(NULL, 3, 4, 30, 10),
(NULL, 2, 3, 30, 10);

USE inv;
SELECT * FROM customer;
SELECT * FROM inventory;
SELECT * FROM orders;