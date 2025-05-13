
CREATE TABLE `batteries` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `name` varchar(100) NOT NULL,
                             `postcode` varchar(20) NOT NULL,
                             `watt_capacity` int NOT NULL,
                             `createdAt` bigint DEFAULT NULL,
                             `updatedAt` bigint DEFAULT NULL,
                             PRIMARY KEY (`id`)
)