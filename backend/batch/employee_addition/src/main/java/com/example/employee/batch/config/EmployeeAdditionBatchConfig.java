package com.example.employee.batch.config;

import com.example.employee.batch.dto.EmployeeDto;
import com.example.employee.batch.processor.EmployeeItemProcessor;
import com.example.employee.batch.reader.EmployeeCsvReader;
import com.example.employee.batch.writer.EmployeeItemWriter;
import com.example.employee.common.model.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class EmployeeAdditionBatchConfig {

    @Bean
    public ItemReader<EmployeeDto> employeeReader() {
        return new EmployeeCsvReader();
    }

    @Bean
    public ItemProcessor<EmployeeDto, Employee> employeeProcessor() {
        return new EmployeeItemProcessor();
    }

    @Bean
    public ItemWriter<Employee> employeeWriter() {
        return new EmployeeItemWriter();
    }

    @Bean
    public Step employeeAdditionStep(JobRepository jobRepository,
                                   PlatformTransactionManager transactionManager,
                                   ItemReader<EmployeeDto> reader,
                                   ItemProcessor<EmployeeDto, Employee> processor,
                                   ItemWriter<Employee> writer) {
        return new StepBuilder("employeeAdditionStep", jobRepository)
                .<EmployeeDto, Employee>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job employeeAdditionJob(JobRepository jobRepository, Step employeeAdditionStep) {
        return new JobBuilder("employeeAdditionJob", jobRepository)
                .start(employeeAdditionStep)
                .build();
    }
}
