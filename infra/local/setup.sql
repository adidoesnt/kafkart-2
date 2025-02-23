-- Teardown

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS product_views;

-- Setup

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255),
    address VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(255),
    zip VARCHAR(255),
    country VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    short_description VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    image VARCHAR(255),
    stock INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    confirmed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE IF NOT EXISTS order_items (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE IF NOT EXISTS payments (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE IF NOT EXISTS product_views (
    id SERIAL PRIMARY KEY,
    product_id INT NOT NULL,
    user_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Seed

INSERT INTO users (first_name, middle_name, last_name, email, phone, address, city, state, zip, country, created_at, updated_at) VALUES
    ('Liam', 'Xavier', 'Anderson', 'liam.anderson@example.com', '555-123-4567', '742 Evergreen Terrace', 'Springfield', 'IL', '62704', 'USA', NOW(), NOW()),
    ('Olivia', NULL, 'Brown', 'olivia.brown@example.com', '555-234-5678', '221B Baker St.', 'London', 'LDN', 'NW1 6XE', 'UK', NOW(), NOW()),
    ('Noah', 'Zachary', 'Clark', 'noah.clark@example.com', '555-345-6789', '12 Grimmauld Place', 'London', 'LDN', 'WC1N 3XX', 'UK', NOW(), NOW()),
    ('Emma', NULL, 'Davis', 'emma.davis@example.com', '555-456-7890', '1600 Pennsylvania Ave NW', 'Washington', 'DC', '20500', 'USA', NOW(), NOW()),
    ('Mason', 'Vincent', 'Evans', 'mason.evans@example.com', '555-567-8901', '4 Privet Drive', 'Little Whinging', 'SRY', 'CR3 0AA', 'UK', NOW(), NOW()),
    ('Ava', NULL, 'Foster', 'ava.foster@example.com', '555-678-9012', '10 Downing St.', 'London', 'LDN', 'SW1A 2AA', 'UK', NOW(), NOW()),
    ('Ethan', 'Gabriel', 'Garcia', 'ethan.garcia@example.com', '555-789-0123', '31 Spooner St.', 'Quahog', 'RI', '02920', 'USA', NOW(), NOW()),
    ('Sophia', NULL, 'Harris', 'sophia.harris@example.com', '555-890-1234', '742 Evergreen Terrace', 'Springfield', 'IL', '62704', 'USA', NOW(), NOW());


INSERT INTO products (name, short_description, description, price, image, stock, created_at, updated_at) VALUES
    ('Standard King-sized Bed', 'Luxurious and comfortable', 'A luxurious bed with a sturdy frame and a comfortable mattress.', 2000, 'https://kafkart-bucket.s3.eu-west-2.amazonaws.com/product-images/bed.jpeg', 4, NOW(), NOW()),
    ('Green Bedsheet Set', 'Elegant olive green bedsheets', 'A set of bedsheets in a beautiful olive green color.', 200, 'https://kafkart-bucket.s3.eu-west-2.amazonaws.com/product-images/bedsheets.jpeg', 20, NOW(), NOW()),
    ('Soy-based Bedside Candle', 'Natural soy wax candle', 'A candle made from soy wax and essential oils.', 40, 'https://kafkart-bucket.s3.eu-west-2.amazonaws.com/product-images/candle.jpeg', 0, NOW(), NOW()),
    ('White Duvet', 'Cozy weighted duvet', 'A soft, weighted duvet that provides a cozy and comfortable sleeping experience.', 300, 'https://kafkart-bucket.s3.eu-west-2.amazonaws.com/product-images/duvet.jpeg', 40, NOW(), NOW()),
    ('Wooden Desk Chair', 'Stylish oak desk chair', 'A comfortable, stylish and affordable desk chair made of the finest oak wood.', 100, 'https://kafkart-bucket.s3.eu-west-2.amazonaws.com/product-images/chair.jpeg', 50, NOW(), NOW()),
    ('Bedside Lamp', 'Elegant bedroom lamp', 'A stylish and functional lamp that adds a touch of elegance to your bedroom.', 35, 'https://kafkart-bucket.s3.eu-west-2.amazonaws.com/product-images/lamp.jpeg', 60, NOW(), NOW()),
    ('Pillows', 'Soft and fluffy pillows', 'A set of soft and fluffy pillows that provide a comfortable and cozy sleeping experience.', 50, 'https://kafkart-bucket.s3.eu-west-2.amazonaws.com/product-images/pillows.jpeg', 70, NOW(), NOW()),
    ('Bedside Table', 'Stylish and functional', 'A stylish and functional table that adds a touch of elegance to your bedroom.', 150, 'https://kafkart-bucket.s3.eu-west-2.amazonaws.com/product-images/side-table.jpeg', 80, NOW(), NOW()),
    ('Wooden Desk', 'Durable workspace table', 'A sturdy and durable table that provides a comfortable and functional workspace.', 250, 'https://kafkart-bucket.s3.eu-west-2.amazonaws.com/product-images/table.jpeg', 90, NOW(), NOW());
