--is_active TINYINT(1) DEFAULT 1,--bi khoa
--create_at CURRENT_TIMESTAMP,
--price FLOAT NOT NULL CHECK (price >=0),
--status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled') COMMENT 'Trạng thái đơn hàng',
-- CREATE TABLE posts(
--     id INT PRIMARY KEY AUTO_INCREMENT,
--     name VARCHAR(100) NOT NULL,
--     thumbnail VARCHAR(300) DEFAULT '',
--     create_at DATETIME,
--     update_at DATETIME,
--     success_at DATETIME,
--     cancelled_at DATETIME,
--     status ENUM('pending', 'success', 'cancelled') COMMENT 'Trạng thái bài đăng',
--     category_id INT,
--     FOREIGN KEY (category_id) REFERENCES categories(id),
--     user_id INT,
--     FOREIGN KEY (user_id) REFERENCES users(id)
-- )
--dat rang buoc cho post_images, neu post bi xoa thi post_images phai bi xoa theo
--CONSTRAINT fk_post_images_post_id FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
CREATE DATABASE webcang;
USE webcang;
CREATE TABLE roles(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL
);
CREATE TABLE users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(100) DEFAULT '',
    lastname VARCHAR(100) DEFAULT '',
    address VARCHAR(200) DEFAULT '',
    department VARCHAR(200) DEFAULT '',
    password VARCHAR(100) NOT NULL DEFAULT '',
    create_at DATETIME,
    update_at DATETIME,
    is_active TINYINT(1) DEFAULT 1,
    date_of_birth DATE,
    phone VARCHAR(10) DEFAULT '',
    facebook_account_id INT DEFAULT 0,
    google_account_id INT DEFAULT 0,
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);
CREATE TABLE tokens(
    id INT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(255) UNIQUE NOT NULL,
    token_type VARCHAR(50) NOT NULL,
    expiration_date DATETIME,
    revoked TINYINT(1) NOT NULL,
    expired TINYINT(1) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE social_accounts(
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `provider` VARCHAR(20) NOT NULL COMMENT 'Tên nhà social network',
    `provider_id` VARCHAR(50) NOT NULL,
    `email` VARCHAR(150) NOT NULL COMMENT 'Email tài khoản',
    `name` VARCHAR(100) NOT NULL COMMENT 'Tên người dùng',
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE departments(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(350) COMMENT 'Tên phòng',
    thumbnail VARCHAR(300) DEFAULT '',
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE categories(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL DEFAULT '' COMMENT 'Tên danh mục, vd: tt hoạt động, thời tiết,..'
);
CREATE TABLE posts(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    thumbnail VARCHAR(300) DEFAULT '',
    create_at DATETIME,
    update_at DATETIME,
    status ENUM('pending', 'success', 'cancelled') COMMENT 'Trạng thái bài đăng',
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(id),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE post_images(
    id INT PRIMARY KEY AUTO_INCREMENT,
    post_id INT,
--     FOREIGN KEY (post_id) REFERENCES posts(id),
    CONSTRAINT fk_post_images_post_id FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    image_url VARCHAR(300)
)
