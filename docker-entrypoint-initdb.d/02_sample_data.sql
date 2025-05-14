-- Sample data for Item table
USE inventory_db;

-- Clear existing items first for a clean start
DELETE FROM items;

-- Reset auto-increment
ALTER TABLE items AUTO_INCREMENT = 1;

-- Make sure columns match JPA entity types
ALTER TABLE items MODIFY id BIGINT AUTO_INCREMENT;
ALTER TABLE items MODIFY price DOUBLE NOT NULL;
ALTER TABLE items MODIFY expiration_date DATE NOT NULL;

-- Insert sample items with various descriptions, prices, expiration dates, and quantities
INSERT INTO items (description, price, expiration_date, quantity) VALUES 
('Organic Apples - Red Delicious', 3.99, '2025-06-15', 150),
('Premium Orange Juice 1L', 5.49, '2025-07-20', 75),
('Whole Wheat Bread', 2.99, '2025-05-30', 45),
('Rice - Basmati 5kg', 12.99, '2026-01-10', 30),
('Pasta - Spaghetti 500g', 1.99, '2025-12-31', 120),
('Extra Virgin Olive Oil 750ml', 15.99, '2026-03-15', 40),
('Canned Tuna in Water 4-pack', 6.99, '2026-09-20', 85),
('Organic Eggs - Dozen', 4.99, '2025-06-05', 50),
('Ground Coffee - Dark Roast 340g', 8.99, '2025-08-25', 65),
('Greek Yogurt - Plain 500g', 3.99, '2025-05-25', 55),
('Frozen Mixed Vegetables 1kg', 4.49, '2025-11-15', 70),
('Chocolate Chip Cookies 300g', 3.79, '2025-07-10', 90),
('Tomato Soup - Condensed 295ml', 1.89, '2026-05-18', 110),
('Honey - Pure 500g', 9.99, '2027-01-01', 35),
('Almond Milk - Unsweetened 1L', 3.29, '2025-06-22', 60),
('Potato Chips - Original 180g', 2.99, '2025-09-12', 100),
('Chicken Stock 946ml', 3.49, '2026-02-28', 80),
('Salsa - Medium 450g', 4.29, '2025-08-30', 65),
('Granola Bars - Variety Pack 8ct', 5.99, '2025-10-15', 75),
('Frozen Pizza - Pepperoni 400g', 6.49, '2025-07-05', 40);
