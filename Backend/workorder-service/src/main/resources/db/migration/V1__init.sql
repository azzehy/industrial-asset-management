CREATE TABLE t_work_orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_order_number VARCHAR(255) NOT NULL,
    serial_number VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    scheduled_date DATE,
    completion_date DATE,
    technician_email VARCHAR(255),
    technician_first_name VARCHAR(255),
    technician_last_name VARCHAR(255)
);