CREATE TABLE
    employees (
        number VARCHAR(10) PRIMARY KEY,
        first_name VARCHAR(50) NOT NULL,
        last_name VARCHAR(50) NOT NULL,
        email VARCHAR(100) NOT NULL UNIQUE,
        phone VARCHAR(20),
        hire_date DATE NOT NULL,
        department_code VARCHAR(10),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (department_code) REFERENCES departments (code)
    );

CREATE TRIGGER update_employees_updated_at BEFORE
UPDATE ON employees FOR EACH ROW EXECUTE FUNCTION update_updated_at_column ();
