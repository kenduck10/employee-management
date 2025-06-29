package com.example.employee.batch.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class EmployeeDto {
  private String employeeNumber;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private LocalDate hireDate;
  private Long departmentId;
  private String position;
  private BigDecimal salary;
  private String status;
}
