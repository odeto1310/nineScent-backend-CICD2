DROP DATABASE IF EXISTS ninescent;
CREATE DATABASE ninescent;
USE ninescent;
CREATE TABLE `users` (
                         `user_no` BIGINT NOT NULL AUTO_INCREMENT,
                         `name` VARCHAR(100) NULL,
                         `user_id` VARCHAR(100) NOT NULL UNIQUE,
                         `password` VARCHAR(255) NOT NULL,
                         `email` VARCHAR(100) NOT NULL UNIQUE,
                         `address` VARCHAR(4000) NULL,
                         `phone` VARCHAR(15) NULL,
                         `create_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                         `birth` DATE NULL,
                         `role` VARCHAR(100) NULL,
                         PRIMARY KEY (`user_no`)
);

CREATE TABLE `item` (
                        `item_id` BIGINT NOT NULL AUTO_INCREMENT,
                        `sub_category_id` BIGINT NOT NULL,
                        `item_name` VARCHAR(200) NOT NULL,
                        `category_id` INT NOT NULL,
                        `size` VARCHAR(200) NULL,
                        `description` TEXT NULL,
                        `price` DECIMAL(10,2) NOT NULL,
                        `discount_rate` INT DEFAULT 0,
                        `discounted_price` DECIMAL(10,2) NULL,
                        `discount_start` DATE NULL,
                        `discount_end` DATE NULL,
                        `discount_desc` VARCHAR(4000) NULL,
                        `stock` INT DEFAULT 0,
                        `photo` VARCHAR(4000) NULL,
                        `detail` VARCHAR(4000) NULL,
                        PRIMARY KEY (`item_id`)
);

CREATE TABLE `cart` (
                        `cart_id` BIGINT NOT NULL AUTO_INCREMENT,
                        `user_no` BIGINT NOT NULL,
                        `total_count` INT DEFAULT 0,
                        PRIMARY KEY (`cart_id`),
                        FOREIGN KEY (`user_no`) REFERENCES `users` (`user_no`) ON DELETE CASCADE
);

CREATE TABLE `cart_item` (
                             `cart_item_id` BIGINT NOT NULL AUTO_INCREMENT,
                             `cart_id` BIGINT NOT NULL,
                             `item_id` BIGINT NOT NULL,
                             `is_selected` BOOLEAN DEFAULT TRUE,
                             `quantity` INT NOT NULL,
                             `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                             PRIMARY KEY (`cart_item_id`),
                             FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cart_id`) ON DELETE CASCADE,
                             FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE CASCADE
);
SELECT * FROM cart_item WHERE cart_id = 1;
INSERT INTO `users` (`name`, `user_id`, `password`, `email`, `address`, `phone`, `create_date`, `birth`, `role`)
VALUES
    ('Kim Minji', 'minji001', 'hashed_password_1', 'minji001@example.com', 'Seoul, Korea', '010-1234-5678', '2024-06-01 10:00:00', '1995-03-15', 'customer'),
    ('Park Jaehyun', 'jaehyun89', 'hashed_password_2', 'jaehyun89@example.com', 'Busan, Korea', '010-9876-5432', '2024-06-05 12:30:00', '1989-07-22', 'customer'),
    ('Lee Sohyun', 'sohyun_admin', 'hashed_password_3', 'sohyun_admin@example.com', 'Incheon, Korea', '010-5678-1234', '2024-06-10 15:45:00', '1992-11-05', 'admin');

INSERT INTO `item` (`sub_category_id`, `item_name`, `category_id`, `size`, `description`, `price`, `discount_rate`, `discounted_price`, `discount_start`, `discount_end`, `discount_desc`, `stock`, `photo`, `detail`)
VALUES
    (1, 'Classic T-Shirt', 10, 'M', 'Comfortable cotton T-shirt', 15000.00, 10, 13500.00, '2024-06-01', '2024-06-15', 'Summer Sale', 100, 'classic_tshirt.jpg', 'Available in multiple colors'),
    (2, 'Running Shoes', 20, '270mm', 'Lightweight running shoes', 85000.00, 15, 72250.00, '2024-06-05', '2024-06-20', 'Limited Time Discount', 50, 'running_shoes.jpg', 'Perfect for jogging and exercise'),
    (3, 'Denim Jacket', 30, 'L', 'Stylish denim jacket', 120000.00, 0, NULL, NULL, NULL, NULL, 30, 'denim_jacket.jpg', 'Classic design with a modern twist');

INSERT INTO `cart` (`user_no`, `total_count`)
VALUES
    (1, 2),
    (2, 1),
    (3, 3);

INSERT INTO `cart_item` (`cart_id`, `item_id`, `is_selected`, `quantity`, `created_date`)
VALUES
    (1, 1, TRUE, 2, '2024-06-15 11:00:00'),    -- cart_id 1에 Classic T-Shirt 2개
    (2, 2, TRUE, 1, '2024-06-16 14:30:00'),    -- cart_id 2에 Running Shoes 1개
    (3, 3, FALSE, 3, '2024-06-17 17:45:00');    -- cart_id 3에 Denim Jacket 3개 (is_selected = 0)
