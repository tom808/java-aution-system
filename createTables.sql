USE AuctionHouse;

CREATE TABLE items
(
itemID int AUTO_INCREMENT,
name varchar(50),
startPrice double,
PRIMARY KEY (itemID)
);

CREATE TABLE transactions
(
itemID int,
action varchar(50)
);

CREATE TABLE users
(
user_name varchar(20),
password varchar(20),
email varchar(20)
);

INSERT INTO items
(name,
startPrice)
VALUES
('old clock',1.99),
('piano',56.00),
('picture',70.00),
('classic car',100.50);
