CREATE TABLE `users`
(
    `id`         int unsigned NOT NULL AUTO_INCREMENT,
--     `code`       varchar(50)  NOT NULL COMMENT 'Mã người dùng duy nhất',
    `phone`      varchar(20)  NOT NULL,
    `email`      varchar(255)          DEFAULT NULL,
    `name`       varchar(255)          DEFAULT NULL,
    `address`    varchar(255)          DEFAULT NULL,
    `password`   varchar(255)          DEFAULT NULL COMMENT 'Mật khẩu của người dùng',
--     `birthday`   date                  DEFAULT NULL,
--     `gender`     int                   DEFAULT NULL,
--     `company_id` int unsigned NOT NULL COMMENT 'Khóa ngoại tham chiếu đến công ty mà người dùng thuộc về',
--     `role_id`    int unsigned          DEFAULT NULL COMMENT 'Khóa ngoại tham chiếu đến vai trò của người dùng',
--     `avatar_id`  int unsigned          DEFAULT NULL COMMENT 'Khóa ngoại tham chiếu đến hình đại diện của người dùng',
--     `status`     int          NOT NULL COMMENT 'Trạng thái hoạt động của người dùng (0: Không hoạt động, 1: Hoạt động)',
    `deleted`    bit(1)       NOT NULL DEFAULT 0,
    `created_at` datetime     NOT NULL,
    `updated_at` datetime     NOT NULL,
    PRIMARY KEY (`id`)
--     UNIQUE KEY (`code`),
--     UNIQUE KEY (`phone`),
--     FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`),
--     FOREIGN KEY (`avatar_id`) REFERENCES `upload_files` (`id`),
--     FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE = InnoDB COMMENT ='Quan ly tai khoan nguoi dung truy cap dich vu'
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;