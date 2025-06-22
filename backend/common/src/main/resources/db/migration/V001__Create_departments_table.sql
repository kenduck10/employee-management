CREATE TABLE departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO departments (name, description) VALUES
('Engineering', 'Software development and engineering'),
('Human Resources', 'Employee management and recruitment'),
('Finance', 'Financial planning and accounting'),
('Marketing', 'Product marketing and promotion');
