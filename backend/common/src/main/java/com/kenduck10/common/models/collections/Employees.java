package com.kenduck10.common.models.collections;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import com.kenduck10.common.collections.FirstClassCollection;
import com.kenduck10.common.models.Employee;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * 従業員（Employee）のファーストクラスコレクション。
 * 
 * @see Employee
 * @see FirstClassCollection
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Employees extends FirstClassCollection<Employee> {

  /**
   * 従業員のリストから Employees インスタンスを作成します。
   * 
   * @param employees 従業員のリスト
   */
  public Employees(List<Employee> employees) {
    list = employees;
  }

  /**
   * 可変長引数から Employees インスタンスを作成します。
   * 
   * @param employees 従業員の可変長引数
   */
  public Employees(Employee... employees) {
    list = List.of(employees);
  }

  /**
   * 指定された部署コードに所属する従業員のみをフィルタリングして返します。
   * 
   * @param departmentCode フィルタリングする部署コード
   * @return 指定された部署に所属する従業員のコレクション
   */
  public Employees filterByDepartment(String departmentCode) {
    if (StringUtils.isBlank(departmentCode)) {
      return new Employees(List.of());
    }

    List<Employee> filtered = list.stream()
        .filter(employee -> employee.belongsToDepartment(departmentCode))
        .collect(Collectors.toList());

    return new Employees(filtered);
  }

  /**
   * 指定された従業員番号のリストに含まれる従業員のみをフィルタリングして返します。
   * 
   * @param numbers フィルタリングする従業員番号のリスト
   * @return 指定された従業員番号を持つ従業員のコレクション
   */
  public Employees filterByNumbers(List<String> numbers) {
    if (CollectionUtils.isEmpty(numbers)) {
      return new Employees(List.of());
    }

    List<Employee> filtered = list.stream()
        .filter(employee -> numbers.contains(employee.getNumber()))
        .collect(Collectors.toList());

    return new Employees(filtered);
  }

  /**
   * 全従業員のフルネームのリストを取得します。
   * 
   * @return フルネームのリスト
   */
  public List<String> getFullNames() {
    return list.stream()
        .map(Employee::getFullName)
        .collect(Collectors.toList());
  }

  /**
   * 全従業員の従業員番号のリストを取得します。
   * 
   * @return 従業員番号のリスト
   */
  public List<String> getNumbers() {
    return list.stream()
        .map(Employee::getNumber)
        .collect(Collectors.toList());
  }
}
