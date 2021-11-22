SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE orders RESTART IDENTITY;
TRUNCATE TABLE order_line_item RESTART IDENTITY;
TRUNCATE TABLE menu RESTART IDENTITY;
TRUNCATE TABLE menu_group RESTART IDENTITY;
TRUNCATE TABLE menu_product RESTART IDENTITY;
TRUNCATE TABLE order_table RESTART IDENTITY;
TRUNCATE TABLE table_group RESTART IDENTITY;
TRUNCATE TABLE product RESTART IDENTITY;
SET REFERENTIAL_INTEGRITY TRUE;

ALTER TABLE orders ALTER COLUMN id RESTART WITH 1;
ALTER TABLE order_line_item ALTER COLUMN seq RESTART WITH 1;
ALTER TABLE menu ALTER COLUMN id RESTART WITH 1;
ALTER TABLE menu_group ALTER COLUMN id RESTART WITH 1;
ALTER TABLE menu_product ALTER COLUMN seq RESTART WITH 1;
ALTER TABLE order_table ALTER COLUMN id RESTART WITH 1;
ALTER TABLE table_group ALTER COLUMN id RESTART WITH 1;
ALTER TABLE product ALTER COLUMN id RESTART WITH 1;