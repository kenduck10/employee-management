package com.example.employee.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

@SpringBootApplication(scanBasePackages = {
    "com.example.employee.batch",
    "com.example.employee.common"
})
@EnableBatchProcessing
public class EmployeeAdditionBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeAdditionBatchApplication.class, args);
    }
}
