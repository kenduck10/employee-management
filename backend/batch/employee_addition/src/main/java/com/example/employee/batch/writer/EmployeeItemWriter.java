package com.example.employee.batch.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmployeeItemWriter implements ItemWriter<Employee> {

  @Autowired
  private EmployeeMapper employeeMapper;

  @Override
  public void write(Chunk<? extends Employee> chunk) throws Exception {
    log.info("Writing {} employees to database", chunk.size());

    for (Employee employee : chunk) {
      try {
        employeeMapper.insert(employee);
        log.info("Successfully inserted employee: {}", employee.getEmployeeNumber());
      } catch (Exception e) {
        log.error("Failed to insert employee: {}", employee.getEmployeeNumber(), e);
        throw e;
      }
    }

    log.info("Completed writing {} employees", chunk.size());
  }
}
