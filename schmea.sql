Drop database airline_management_system;

CREATE DATABASE airline_management_system;
USE airline_management_system;



CREATE TABLE `aircraft` (
                            `aircraft_id` varchar(20) NOT NULL,
                            `model` varchar(100) NOT NULL,
                            `capacity` int NOT NULL,
                            `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
                            `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            `is_deleted` tinyint(1) DEFAULT '0',
                            PRIMARY KEY (`aircraft_id`),
                            CONSTRAINT `aircraft_chk_1` CHECK ((`capacity` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `airport` (
                           `airport_code` char(3) NOT NULL,
                           `airport_name` varchar(100) NOT NULL,
                           `city` varchar(100) NOT NULL,
                           `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
                           `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           `is_deleted` tinyint(1) DEFAULT '0',
                           PRIMARY KEY (`airport_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;






CREATE TABLE `flight` (
                          `flight_id` varchar(20) NOT NULL,
                          `flight_code` varchar(10) NOT NULL,
                          `source_airport` char(3) NOT NULL,
                          `destination_airport` char(3) NOT NULL,
                          `departure_time` datetime NOT NULL,
                          `arrival_time` datetime NOT NULL,
                          `aircraft_id` varchar(20) NOT NULL,
                          `base_fare` decimal(12,2) NOT NULL,
                          `status` varchar(30) NOT NULL,
                          `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
                          `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          `is_deleted` tinyint(1) DEFAULT '0',
                          PRIMARY KEY (`flight_id`),
                          UNIQUE KEY `flight_code` (`flight_code`),
                          KEY `fk_flight_source` (`source_airport`),
                          KEY `fk_flight_destination` (`destination_airport`),
                          KEY `fk_flight_aircraft` (`aircraft_id`),
                          CONSTRAINT `fk_flight_aircraft` FOREIGN KEY (`aircraft_id`) REFERENCES `aircraft` (`aircraft_id`),
                          CONSTRAINT `fk_flight_destination` FOREIGN KEY (`destination_airport`) REFERENCES `airport` (`airport_code`),
                          CONSTRAINT `fk_flight_source` FOREIGN KEY (`source_airport`) REFERENCES `airport` (`airport_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `user` (
                        `id` varchar(20) NOT NULL,
                        `first_name` varchar(50) NOT NULL,
                        `last_name` varchar(50) NOT NULL,
                        `date_of_birth` date NOT NULL,
                        `gender` varchar(20) NOT NULL,
                        `email` varchar(100) NOT NULL,
                        `phone_number` varchar(15) NOT NULL,
                        `password_hash` varchar(255) NOT NULL,
                        `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
                        `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        `is_deleted` tinyint(1) DEFAULT '0',
                        `role` varchar(20) NOT NULL DEFAULT 'USER',
                        `last_login_at` datetime DEFAULT CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




CREATE TABLE `upi` (
                       `upi_id` varchar(100) NOT NULL,
                       `upi_password` varchar(255) NOT NULL,
                       `bank_name` varchar(100) NOT NULL,
                       `bank_account_number` varchar(30) NOT NULL,
                       `balance` decimal(12,2) DEFAULT '0.00',
                       `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
                       `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       `is_deleted` tinyint(1) DEFAULT '0',
                       PRIMARY KEY (`upi_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `wallet` (
                          `wallet_id` varchar(20) NOT NULL,
                          `user_id` varchar(20) NOT NULL,
                          `balance` decimal(12,2) NOT NULL DEFAULT '0.00',
                          `currency` varchar(5) NOT NULL DEFAULT 'INR',
                          `status` varchar(20) NOT NULL,
                          `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
                          `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          `is_deleted` tinyint(1) DEFAULT '0',
                          PRIMARY KEY (`wallet_id`),
                          UNIQUE KEY `user_id` (`user_id`),
                          CONSTRAINT `fk_wallet_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user_upi` (
                            `user_id` varchar(20) NOT NULL,
                            `upi_id` varchar(100) NOT NULL,
                            `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
                            `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            `is_deleted` tinyint(1) DEFAULT '0',
                            PRIMARY KEY (`user_id`,`upi_id`),
                            KEY `fk_user_upi_upi` (`upi_id`),
                            CONSTRAINT `fk_user_upi_upi` FOREIGN KEY (`upi_id`) REFERENCES `upi` (`upi_id`),
                            CONSTRAINT `fk_user_upi_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `booking` (
                           `booking_id` varchar(20) NOT NULL,
                           `flight_id` varchar(20) NOT NULL,
                           `booking_datetime` datetime NOT NULL,
                           `seat_class` varchar(30) NOT NULL,
                           `amount` decimal(12,2) NOT NULL,
                           `booking_status` varchar(30) NOT NULL,
                           `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
                           `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           `is_deleted` tinyint(1) DEFAULT '0',
                           `user_id` varchar(20) DEFAULT NULL,
                           PRIMARY KEY (`booking_id`),
                           KEY `fk_booking_flight` (`flight_id`),
                           KEY `fk_booking_user` (`user_id`),
                           CONSTRAINT `fk_booking_flight` FOREIGN KEY (`flight_id`) REFERENCES `flight` (`flight_id`),
                           CONSTRAINT `fk_booking_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;





CREATE TABLE `loyalty_account` (
                                   `loyalty_account_id` varchar(20) NOT NULL,
                                   `user_id` varchar(20) NOT NULL,
                                   `points` int NOT NULL DEFAULT '0',
                                   `tier` varchar(20) NOT NULL,
                                   `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
                                   `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   `is_deleted` tinyint(1) DEFAULT '0',
                                   PRIMARY KEY (`loyalty_account_id`),
                                   UNIQUE KEY `user_id` (`user_id`),
                                   CONSTRAINT `fk_loyalty_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `passenger` (
                             `passenger_id` varchar(20) NOT NULL,
                             `user_id` varchar(20) DEFAULT NULL,
                             `booking_id` varchar(20) NOT NULL,
                             `first_name` varchar(50) NOT NULL,
                             `last_name` varchar(50) NOT NULL,
                             `date_of_birth` date NOT NULL,
                             `gender` varchar(20) NOT NULL,
                             `email` varchar(100) DEFAULT NULL,
                             `phone_number` varchar(15) DEFAULT NULL,
                             `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
                             `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             `is_deleted` tinyint(1) DEFAULT '0',
                             `is_cancelled` tinyint(1) DEFAULT '0',
                             PRIMARY KEY (`passenger_id`),
                             KEY `fk_passenger_user` (`user_id`),
                             KEY `fk_passenger_booking` (`booking_id`),
                             CONSTRAINT `fk_passenger_booking` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`),
                             CONSTRAINT `fk_passenger_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `ticket` (
                          `ticket_id` varchar(20) NOT NULL,
                          `booking_id` varchar(20) NOT NULL,
                          `passenger_id` varchar(20) NOT NULL,
                          `fare` decimal(12,2) NOT NULL,
                          `seat_class` varchar(30) NOT NULL,
                          `seat_number` varchar(10) NOT NULL,
                          `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
                          `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          `is_deleted` tinyint(1) DEFAULT '0',
                          PRIMARY KEY (`ticket_id`),
                          KEY `fk_ticket_booking` (`booking_id`),
                          CONSTRAINT `fk_ticket_booking` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `transaction` (
                               `transaction_id` varchar(20) NOT NULL,
                               `sender_user_id` varchar(20) NOT NULL,
                               `receiver_user_id` varchar(20) NOT NULL,
                               `from_payment_method` varchar(20) NOT NULL,
                               `to_payment_method` varchar(20) NOT NULL,
                               `sender_upi` varchar(100) DEFAULT NULL,
                               `receiver_upi` varchar(100) DEFAULT NULL,
                               `amount` decimal(12,2) NOT NULL,
                               `status` varchar(20) NOT NULL,
                               `transaction_time` datetime DEFAULT CURRENT_TIMESTAMP,
                               `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
                               `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               `is_deleted` tinyint(1) DEFAULT '0',
                               PRIMARY KEY (`transaction_id`),
                               KEY `fk_transaction_sender` (`sender_user_id`),
                               KEY `fk_transaction_receiver` (`receiver_user_id`),
                               CONSTRAINT `fk_transaction_receiver` FOREIGN KEY (`receiver_user_id`) REFERENCES `user` (`id`),
                               CONSTRAINT `fk_transaction_sender` FOREIGN KEY (`sender_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `payment` (
                           `payment_id` varchar(20) NOT NULL,
                           `booking_id` varchar(20) NOT NULL,
                           `transaction_id` varchar(20) DEFAULT NULL,
                           `amount` decimal(12,2) NOT NULL,
                           `paid` tinyint(1) DEFAULT '0',
                           `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
                           `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           `is_deleted` tinyint(1) DEFAULT '0',
                           PRIMARY KEY (`payment_id`),
                           UNIQUE KEY `booking_id` (`booking_id`),
                           KEY `fk_payment_transaction` (`transaction_id`),
                           CONSTRAINT `fk_payment_booking` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`),
                           CONSTRAINT `fk_payment_transaction` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `refund` (
                          `refund_id` varchar(20) NOT NULL,
                          `booking_id` varchar(20) NOT NULL,
                          `transaction_id` varchar(20) DEFAULT NULL,
                          `amount` decimal(12,2) NOT NULL,
                          `status` varchar(20) NOT NULL,
                          `reason` varchar(255) DEFAULT NULL,
                          `refund_time` datetime DEFAULT CURRENT_TIMESTAMP,
                          `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
                          `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          `is_deleted` tinyint(1) DEFAULT '0',
                          PRIMARY KEY (`refund_id`),
                          KEY `fk_refund_booking` (`booking_id`),
                          KEY `fk_refund_transaction` (`transaction_id`),
                          CONSTRAINT `fk_refund_booking` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`),
                          CONSTRAINT `fk_refund_transaction` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;









