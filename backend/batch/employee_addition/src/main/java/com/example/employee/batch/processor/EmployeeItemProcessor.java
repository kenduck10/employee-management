package com.example.employee.batch.processor;

import com.example.employee.batch.dto.EmployeeDto;
import com.example.employee.common.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmployeeItemProcessor implements ItemProcessor<EmployeeDto, Employee> {

    @Override
    public Employee process(EmployeeDto employeeDto) throws Exception {
        log.info("Processing employee: {}", employeeDto.getEmployeeNumber());
        
        // Validate required fields
        if (employeeDto.getEmployeeNumber() == null || employeeDto.getEmployeeNumber().trim().isEmpty()) {
            log.warn("Skipping employee with empty employee number");
            return null;
        }
        
        if (employeeDto.getEmail() == null || employeeDto.getEmail().trim().isEmpty()) {
            log.warn("Skipping employee {} with empty email", employeeDto.getEmployeeNumber());
            return null;
        }
        
        // Convert DTO to Entity
        Employee employee = new Employee();
        employee.setEmployeeNumber(employeeDto.getEmployeeNumber());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhone(employeeDto.getPhone());
        employee.setHireDate(employeeDto.getHireDate());
        employee.setDepartmentId(employeeDto.getDepartmentId());
        employee.setPosition(employeeDto.getPosition());
        employee.setSalary(employeeDto.getSalary());
        employee.setStatus(employeeDto.getStatus() != null ? employeeDto.getStatus() : "ACTIVE");
        
        log.info("Successfully processed employee: {}", employee.getEmployeeNumber());
        return employee;
    }
}
