CREATE TABLE products (
	id 		SERIAL 	PRIMARY KEY,
	name 		TEXT 	NOT NULL,
	price		INTEGER NOT NULL CHECK (price > 0),
	pack_quantity 	INTEGER NOT NULL CHECK (pack_quantity > 0),
	quantity 	INTEGER NOT NULL CHECK (quantity >= 0),
	vat 		BOOLEAN NOT NULL,
	order_date 	DATE,
	expiry_date 	DATE);

CREATE TABLE login (
	usr 	TEXT PRIMARY KEY,
	pass 	TEXT NOT NULL);
