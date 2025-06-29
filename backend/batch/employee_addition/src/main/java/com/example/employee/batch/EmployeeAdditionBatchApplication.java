package com.example.employee.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "com.kenduck10.employee.batch",
    "com.kenduck10.employee.common"
})
@EnableBatchProcessing
public class EmployeeAdditionBatchApplication {
  public static void main(String[] args) {
    SpringApplication.run(EmployeeAdditionBatchApplication.class, args);
  }
}
