ALTER TABLE `users`
    ADD COLUMN `iss`    VARCHAR(255) DEFAULT NULL,
    ADD COLUMN `sub`    VARCHAR(255) DEFAULT NULL,
    ADD CONSTRAINT unique_iss_sub UNIQUE (`iss`, `sub`);

ALTER TABLE `users`
    MODIFY COLUMN `phone` varchar(20) DEFAULT NULL;