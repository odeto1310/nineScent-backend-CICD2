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
                         `role` VARCHAR(100) NOT NULL DEFAULT 'ROLE_USER',
                         PRIMARY KEY (`user_no`)
);

CREATE TABLE `item` (
                        `item_id` BIGINT NOT NULL AUTO_INCREMENT,
                        `sub_category_id` BIGINT NOT NULL,
                        `item_name` VARCHAR(200) NOT NULL,
                        `category_id` INT NOT NULL,
                        `item_size` VARCHAR(200) NULL,
                        `item_description` TEXT NULL,
                        `price` DECIMAL(10,2) NOT NULL,
                        `discount_rate` INT DEFAULT 0,
                        `discounted_price` DECIMAL(10,2) NULL,
                        `discount_start` DATE NULL,
                        `discount_end` DATE NULL,
                        `discount_description` VARCHAR(4000) NULL,
                        `stock` INT DEFAULT 0,
                        `main_photo` VARCHAR(4000) NULL,
                        `detail_photo` VARCHAR(4000) NULL,
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

INSERT INTO `item` (`sub_category_id`, `item_name`, `category_id`, `item_size`, `item_description`, `price`, `discount_rate`, `discounted_price`, `discount_start`, `discount_end`, `discount_description`, `stock`, `main_photo`, `detail_photo`)
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


-- =================== Shipment Table ===================

CREATE TABLE shipment (
                          shipment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          order_id BIGINT NOT NULL,
                          user_no BIGINT NOT NULL,
                          tracking_num VARCHAR(30),
                          tracking_date DATETIME,
                          delivery_status VARCHAR(20)
);

-- =================== Payment Table ===================

CREATE TABLE payment (
                         payment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         order_id BIGINT NOT NULL,
                         user_no BIGINT NOT NULL,
                         payment_status VARCHAR(20),
                         payment_method VARCHAR(20),
                         payment_date DATETIME,
                         total_amount BIGINT
);

-- =================== RefundChange Table ===================

CREATE TABLE refund_change (
                               refund_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               order_id BIGINT NOT NULL,
                               payment_id BIGINT NOT NULL,
                               shipment_id BIGINT NOT NULL,
                               delivery_refund_addr_id BIGINT NOT NULL,
                               delivery_addr_id BIGINT NOT NULL,
                               refund_change_type INT NOT NULL,  -- 1: 환불, 2: 교환
                               refund_method VARCHAR(50),
                               reason_category VARCHAR(50),
                               reason_text TEXT,
                               is_done BOOLEAN DEFAULT FALSE,
                               update_date DATETIME
);

-- =================== Sample Data for Shipment ===================

INSERT INTO shipment (shipment_id, order_id, user_no, tracking_num, tracking_date, delivery_status)
VALUES
    (1, 1, 1, 'TRK1234567890', '2024-01-15 10:30:00', '배송중'),
    (2, 2, 2, 'TRK0987654321', '2024-01-16 14:45:00', '배송완료');

-- =================== Sample Data for Payment ===================

INSERT INTO payment (payment_id, order_id, user_no, payment_status, payment_method, payment_date, total_amount)
VALUES
    (1, 1, 1, '결제완료', '카드', '2024-01-15 09:00:00', 150000),
    (2, 2, 2, '결제완료', '계좌이체', '2024-01-16 13:00:00', 200000);

-- =================== Sample Data for RefundChange ===================

INSERT INTO refund_change (refund_id, order_id, payment_id, shipment_id, delivery_refund_addr_id, delivery_addr_id, refund_change_type, refund_method, reason_category, reason_text, is_done, update_date)
VALUES
    (1, 1, 1, 1, 1, 3, 1, '카드 취소', '상품불량', '제품이 손상되어 도착했습니다.', false, '2024-01-17 08:00:00'),
    (2, 2, 2, 2, 2, 4, 2, '계좌 환불', '단순변심', '생각보다 크기가 큽니다.', false, '2024-01-18 09:30:00');

-- =================== Sample Data for Business Address (배송/환불 주소) ===================

CREATE TABLE business_address (
                                  business_address_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  business_address_name VARCHAR(100) NOT NULL,
                                  business_contact VARCHAR(20),
                                  business_zipcode Long,
                                  business_address VARCHAR(255),
                                  business_address_detail VARCHAR(255)
);

INSERT INTO business_address (business_address_name, business_contact, business_zipcode, business_address, business_address_detail)
VALUES
    ('서울 물류센터', '02-1234-5678', '04524', '서울특별시 중구 세종대로 110', 'A동 2층'),
    ('부산 물류센터', '051-9876-5432', '48941', '부산광역시 중구 중앙대로 26', 'B동 3층');

USE ninescent;
SELECT * FROM users;
SELECT * FROM orders;
SELECT * FROM order_items;
SELECT * FROM cart_item;
SELECT * FROM address;
