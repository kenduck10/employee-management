package com.kenduck10.common.mappers;

import static com.kenduck10.common.mappers.generated.GeneratedEmployeeDynamicSqlSupport.number;
import static org.mybatis.dynamic.sql.SqlBuilder.isIn;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import com.kenduck10.common.mappers.generated.GeneratedEmployeeMapper;
import com.kenduck10.common.models.Employee;
import com.kenduck10.common.models.collections.Employees;

/**
 * 従業員マッパークラス。
 * 
 * @see GeneratedEmployeeMapper
 * @see Employee
 * @see Employees
 */
@Mapper
public interface EmployeeMapper extends GeneratedEmployeeMapper {

  /**
   * 指定された従業員番号リストに含まれる番号を持つ従業員を取得します。
   * 
   * @param numbers 従業員番号のリスト（null不可、空不可）
   * @return 指定された従業員番号リストに含まれる番号を持つ従業員のコレクション
   * 
   * @see Employees
   */
  default Employees selectByNumbers(List<String> numbers) {
    if (CollectionUtils.isEmpty(numbers)) {
      throw new IllegalArgumentException("Employee number list cannot be empty.");
    }
    return new Employees(
        select(c -> c.where(number, isIn(numbers)).orderBy(number))
            .stream()
            .map(generatedEmployee -> {
              Employee employee = new Employee();
              BeanUtils.copyProperties(generatedEmployee, employee);
              return employee;
            })
            .toList());
  }

  /**
   * 指定された部署コードに所属する全従業員を取得します。
   * 
   * @param departmentCode 部署コード（null不可、空不可）
   * @return 指定された部署コードに所属する従業員のコレクション
   * 
   * @see Employees
   */
  default Employees selectByDepartmentCode(String departmentCode) {
    if (StringUtils.isBlank(departmentCode)) {
      throw new IllegalArgumentException("Department code cannot be null or empty.");
    }
    return new Employees(
        select(c -> c.where(
            com.kenduck10.common.mappers.generated.GeneratedEmployeeDynamicSqlSupport.departmentCode,
            org.mybatis.dynamic.sql.SqlBuilder.isEqualTo(departmentCode))
            .orderBy(number))
                .stream()
                .map(generatedEmployee -> {
                  Employee employee = new Employee();
                  BeanUtils.copyProperties(generatedEmployee, employee);
                  return employee;
                })
                .toList());
  }

  /**
   * 全従業員を取得します。
   * 
   * @return 全従業員のコレクション
   * 
   * @see Employees
   */
  default Employees selectAll() {
    return new Employees(
        select(c -> c.orderBy(number))
            .stream()
            .map(generatedEmployee -> {
              Employee employee = new Employee();
              BeanUtils.copyProperties(generatedEmployee, employee);
              return employee;
            })
            .toList());
  }
}
