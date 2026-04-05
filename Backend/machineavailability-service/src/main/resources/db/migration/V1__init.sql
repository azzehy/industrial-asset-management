CREATE TABLE t_machine_availability (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    serial_number VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    current_location VARCHAR(255),
    last_inspection_date DATE,
    certified BOOLEAN DEFAULT FALSE
);