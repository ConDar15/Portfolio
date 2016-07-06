These commands are to be used using psql's \i command while in the monaco_db database on dbteach2.

	File		|			Use
---------------------------------------------------------------------------------
add_login.sql		| Adds the default values to the login table
add_rows.sql		| Adds the default values to the products table
create_tables.sql	| The table creation commands for login and products
refresh_login.sql	| Deletes all rows from login then calls add_login.sql
refresh_products.sql	| Deletes all rows from products then calls add_rows.sql
refresh.sql		| Calls both refresh_login.sql and refresh_products.sql
