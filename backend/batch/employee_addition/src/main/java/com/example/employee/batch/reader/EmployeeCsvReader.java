package com.example.employee.batch.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import com.example.employee.batch.dto.EmployeeDto;

@Component
public class EmployeeCsvReader extends FlatFileItemReader<EmployeeDto> {

  public EmployeeCsvReader() {
    setName("employeeCsvReader");
    setResource(new ClassPathResource("data/employees.csv"));
    setLinesToSkip(1); // Skip header line

    DefaultLineMapper<EmployeeDto> lineMapper = new DefaultLineMapper<>();

    DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
    tokenizer.setNames("employeeNumber", "firstName", "lastName", "email",
        "phone", "hireDate", "departmentId", "position", "salary", "status");
    tokenizer.setDelimiter(",");

    BeanWrapperFieldSetMapper<EmployeeDto> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
    fieldSetMapper.setTargetType(EmployeeDto.class);

    lineMapper.setLineTokenizer(tokenizer);
    lineMapper.setFieldSetMapper(fieldSetMapper);

    setLineMapper(lineMapper);
  }
}
