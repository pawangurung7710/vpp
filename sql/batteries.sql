
CREATE DATABASE IF NOT EXISTS vpp;


USE vpp;


CREATE TABLE IF NOT EXISTS `batteries` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `name` varchar(100) NOT NULL,
                             `postcode` int NOT NULL,
                             `watt_capacity` int NOT NULL,
                             `created_at` bigint DEFAULT NULL,
                             `updated_at` bigint DEFAULT NULL,
                             PRIMARY KEY (`id`)
)