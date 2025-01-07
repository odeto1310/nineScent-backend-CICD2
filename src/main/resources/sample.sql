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
                         `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                         `birth` DATE NULL,
                         `role` VARCHAR(100) NOT NULL DEFAULT 'USER',
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

CREATE TABLE stock_log (
   `log_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
   `item_id` BIGINT NOT NULL,
   `change_type` VARCHAR(100) NOT NULL, -- 예: 'REDUCE', 'RESTORE'
   `quantity` INT NOT NULL,
   `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
   FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE CASCADE
);

CREATE TABLE address (
                         addr_no	bigInt	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         user_no	BIGINT	NOT NULL,
                         addr_name	varchar(4000)	NOT NULL,
                         addr_contact	varchar(20)	NOT NULL,
                         addr_zipcode	int	NOT NULL,
                         addr_address	varchar(100)	NOT NULL,
                         addr_detail	varchar(100)	NOT NULL,
                         addr_request	varchar(255)	NOT NULL,
                         is_default	boolean	NOT NULL,
                         is_liked	boolean	NOT NULL,
                         is_extra_fee BOOLEAN DEFAULT FALSE,
                         last_used	DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE shipment_extra (
                                extra_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                extra_zipcode BIGINT NOT NULL,
                                extra_address VARCHAR(255) NOT NULL,
                                extra_fee INT DEFAULT 5000
);

CREATE TABLE `orders` (
                          `order_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          `user_no` BIGINT NOT NULL,
                          `addr_no` BIGINT NOT NULL,
                          `order_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
                          `delivery_done` BOOLEAN DEFAULT FALSE,
                          `payment_done` BOOLEAN DEFAULT FALSE,
                          `refund_change_done` BOOLEAN DEFAULT FALSE
);
CREATE TABLE `order_items` (
                               `order_item_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                               `order_id` BIGINT NOT NULL,
                               `item_id` BIGINT NOT NULL,
                               `quantity` INT NOT NULL,
                               `original_price` LONG NOT NULL,
                               `discounted_price` LONG NOT NULL,
                               FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE,
                               FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE CASCADE
);

INSERT INTO `users` (`name`, `user_id`, `password`, `email`, `address`, `phone`, `created_at`, `birth`, `role`)
VALUES
    ('Kim Minji', 'minji001', 'hashed_password_1', 'minji001@example.com', 'Seoul, Korea', '010-1234-5678', '2024-06-01 10:00:00', '1995-03-15', default),
    ('Park Jaehyun', 'jaehyun89', 'hashed_password_2', 'jaehyun89@example.com', 'Busan, Korea', '010-9876-5432', '2024-06-05 12:30:00', '1989-07-22', default),
    ('Lee Sohyun', 'sohyun_admin', 'hashed_password_3', 'sohyun_admin@example.com', 'Incheon, Korea', '010-5678-1234', '2024-06-10 15:45:00', '1992-11-05', default);

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

INSERT INTO `stock_log` (`item_id`, `change_type`, `quantity`)
VALUES
    (1, 'REDUCE', 2),  -- Minji가 Classic T-Shirt 2개 주문
    (2, 'REDUCE', 1),  -- Jaehyun이 Running Shoes 1개 주문
    (3, 'RESTORE', 3); -- Sohyun이 Denim Jacket 주문 취소

INSERT INTO `address` (`user_no`, `addr_name`, `addr_contact`, `addr_zipcode`, `addr_address`, `addr_detail`, `addr_request`, `is_default`, `is_liked`)
VALUES
    (1, 'Home', '010-1234-5678', 15654, 'Seoul, Korea', '101동 202호', 'Leave at the front door', TRUE, TRUE), -- Minji의 기본 주소
    (2, 'Office', '010-9876-5432', 23008, 'Busan, Korea', 'Tech Tower 5층', 'Ring the bell', TRUE, FALSE);    -- Jaehyun의 기본 주소

INSERT INTO `shipment_extra` (`extra_zipcode`, `extra_address`)
VALUES
    (23008, '인천광역시 강화군 서도면 주문도길243번길'),  -- 도서 지역
    (15654, '경기도 안산시 단원구 풍도동 풍도2길 20'),  -- 산간 지역
    (63000, '제주특별자치도 제주시 추자면 횡간도길');  -- 제주 지역

INSERT INTO `orders` (`user_no`, `addr_no`, `order_date`, `delivery_done`, `payment_done`, `refund_change_done`)
VALUES
    (1, 1, '2024-06-20 10:00:00', FALSE, TRUE, FALSE), -- Minji의 주문

    (2, 2, '2024-06-21 11:30:00', TRUE, TRUE, FALSE);  -- Jaehyun의 주문

INSERT INTO `order_items` (`order_id`, `item_id`, `quantity`, `original_price`, `discounted_price`)
VALUES
    (1, 1, 2, 15000.00, 13500.00),  -- Minji의 주문: Classic T-Shirt 2개
    (2, 2, 1, 85000.00, 72250.00);  -- Jaehyun의 주문: Running Shoes 1개

SHOW TABLES;
SELECT * FROM order_items;
SELECT * FROM orders;
SELECT * FROM stock_log;
SELECT * FROM cart;
SELECT * FROM cart_item;
SELECT * FROM item;
SELECT * FROM address;