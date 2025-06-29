package com.kenduck10.common.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.kenduck10.common.models.Employee;

/**
 * EmployeeMapperのユニットテストクラス。
 * 
 * @see EmployeeMapper
 */
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DBRider
class EmployeeMapperTest {

  @Autowired
  private EmployeeMapper employeeMapper;

  /**
   * selectByNumbers メソッドのテストクラス。
   */
  @Nested
  @DataSet("datasets/mappers/EmployeeMapper_selectByNumbers.yml")
  class SelectByNumbersTest {

    /**
     * 単一の従業員番号で従業員を取得するテスト。 単一の従業員番号入力でメソッドが正しく動作することを検証する。
     */
    @Test
    void withSingleNumber_shouldReturnOneEmployee() {
      List<String> numbers = Arrays.asList("EMP001");
      List<Employee> result = employeeMapper.selectByNumbers(numbers).list();
      assertThat(result).hasSize(1);

      Employee employee = result.get(0);
      assertThat(employee.getNumber()).isEqualTo("EMP001");
      assertThat(employee.getFirstName()).isEqualTo("太郎");
      assertThat(employee.getLastName()).isEqualTo("田中");
      assertThat(employee.getEmail()).isEqualTo("tanaka.taro@company.com");
      assertThat(employee.getDepartmentCode()).isEqualTo("HR");
    }

    /**
     * 複数の従業員番号で複数従業員を取得するテスト。 複数の従業員番号を提供して、全従業員が正しい順序で返されることを検証する。
     */
    @Test
    void withMultipleNumbers_shouldReturnMultipleEmployees() {
      List<String> numbers = Arrays.asList("EMP001", "EMP003", "EMP005");
      List<Employee> result = employeeMapper.selectByNumbers(numbers).list();
      assertThat(result).hasSize(3);

      // 従業員が番号順（アルファベット順）で返されることを検証
      assertThat(result.get(0).getNumber()).isEqualTo("EMP001");
      assertThat(result.get(1).getNumber()).isEqualTo("EMP003");
      assertThat(result.get(2).getNumber()).isEqualTo("EMP005");
    }

    /**
     * 存在しない従業員番号を処理するテスト。 一致する従業員が見つからない場合に空のリストが返されることを検証する。
     */
    @Test
    void withNonExistentNumbers_shouldReturnEmptyList() {
      List<String> numbers = Arrays.asList("NONEXISTENT", "INVALID");
      List<Employee> result = employeeMapper.selectByNumbers(numbers).list();
      assertThat(result).isEmpty();
    }

    /**
     * 存在する番号と存在しない番号が混在する場合のテスト。 存在する従業員のみが返されることを検証する。
     */
    @Test
    void withMixedNumbers_shouldReturnOnlyExistingEmployees() {
      List<String> numbers = Arrays.asList("EMP001", "NONEXISTENT", "EMP003", "INVALID");
      List<Employee> result = employeeMapper.selectByNumbers(numbers).list();
      assertThat(result).hasSize(2);

      assertThat(result.get(0).getNumber()).isEqualTo("EMP001");
      assertThat(result.get(1).getNumber()).isEqualTo("EMP003");
    }

    /**
     * 入力リストに重複する番号が含まれる場合のテスト。 重複する番号が結果に重複した従業員を生成しないことを検証する。
     */
    @Test
    void withDuplicateNumbers_shouldReturnUniqueResults() {
      List<String> numbers = Arrays.asList("EMP001", "EMP003", "EMP001", "EMP003");
      List<Employee> result = employeeMapper.selectByNumbers(numbers).list();
      assertThat(result).hasSize(2);

      assertThat(result.get(0).getNumber()).isEqualTo("EMP001");
      assertThat(result.get(1).getNumber()).isEqualTo("EMP003");
    }

    /**
     * 結果の順序を検証するテスト。 入力の順序に関係なく、従業員が常に番号のアルファベット順で返されることを検証する。
     */
    @Test
    void withUnorderedInput_shouldReturnOrderedResults() {
      List<String> numbers = Arrays.asList("EMP005", "EMP001", "EMP003");
      List<Employee> result = employeeMapper.selectByNumbers(numbers).list();
      assertThat(result).hasSize(3);

      // 結果が番号のアルファベット順で並んでいることを検証
      assertThat(result.get(0).getNumber()).isEqualTo("EMP001");
      assertThat(result.get(1).getNumber()).isEqualTo("EMP003");
      assertThat(result.get(2).getNumber()).isEqualTo("EMP005");
    }

    /**
     * 空のリストが渡された場合の例外処理テスト。 空のリストが渡された場合に適切な例外がスローされることを検証する。
     */
    @Test
    void withEmptyList_shouldThrowException() {
      List<String> numbers = Arrays.asList();
      assertThatThrownBy(() -> employeeMapper.selectByNumbers(numbers))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Employee number list cannot be empty");
    }

    /**
     * nullが渡された場合の例外処理テスト。 nullが渡された場合に適切な例外がスローされることを検証する。
     */
    @Test
    void withNullList_shouldThrowException() {
      assertThatThrownBy(() -> employeeMapper.selectByNumbers(null))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Employee number list cannot be empty");
    }
  }

  /**
   * selectByDepartmentCode メソッドのテストクラス。
   */
  @Nested
  @DataSet("datasets/mappers/EmployeeMapper_selectByDepartmentCode.yml")
  class SelectByDepartmentCodeTest {

    /**
     * 有効な部署コードで従業員を取得するテスト。 指定された部署に所属する従業員が正しく取得されることを検証する。
     */
    @Test
    void withValidDepartmentCode_shouldReturnEmployeesInDepartment() {
      List<Employee> result = employeeMapper.selectByDepartmentCode("IT").list();
      assertThat(result).hasSize(2);

      // 全ての従業員がIT部署に所属していることを検証
      assertThat(result).allMatch(emp -> "IT".equals(emp.getDepartmentCode()));

      // 番号順で並んでいることを検証
      assertThat(result.get(0).getNumber()).isEqualTo("EMP002");
      assertThat(result.get(1).getNumber()).isEqualTo("EMP011");
    }

    /**
     * 存在しない部署コードを処理するテスト。 存在しない部署コードが指定された場合に空のリストが返されることを検証する。
     */
    @Test
    void withNonExistentDepartmentCode_shouldReturnEmptyList() {
      List<Employee> result = employeeMapper.selectByDepartmentCode("NONEXISTENT").list();
      assertThat(result).isEmpty();
    }

    /**
     * nullの部署コードが渡された場合の例外処理テスト。 nullが渡された場合に適切な例外がスローされることを検証する。
     */
    @Test
    void withNullDepartmentCode_shouldThrowException() {
      assertThatThrownBy(() -> employeeMapper.selectByDepartmentCode(null))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Department code cannot be null or empty");
    }

    /**
     * 空文字列の部署コードが渡された場合の例外処理テスト。 空文字列が渡された場合に適切な例外がスローされることを検証する。
     */
    @Test
    void withEmptyDepartmentCode_shouldThrowException() {
      assertThatThrownBy(() -> employeeMapper.selectByDepartmentCode(""))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Department code cannot be null or empty");
    }

    /**
     * 空白文字のみの部署コードが渡された場合の例外処理テスト。 空白文字のみが渡された場合に適切な例外がスローされることを検証する。
     */
    @Test
    void withWhitespaceDepartmentCode_shouldThrowException() {
      assertThatThrownBy(() -> employeeMapper.selectByDepartmentCode("   "))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Department code cannot be null or empty");
    }
  }

  /**
   * selectAll メソッドのテストクラス。
   */
  @Nested
  @DataSet("datasets/mappers/EmployeeMapper_selectAll.yml")
  class SelectAllTest {

    /**
     * 全従業員取得のテスト。 全従業員が番号順で取得されることを検証する。
     */
    @Test
    void shouldReturnAllEmployeesOrderedByNumber() {
      List<Employee> result = employeeMapper.selectAll().list();
      assertThat(result).hasSize(5);

      // 番号順で並んでいることを検証
      assertThat(result.get(0).getNumber()).isEqualTo("EMP001");
      assertThat(result.get(1).getNumber()).isEqualTo("EMP002");
      assertThat(result.get(2).getNumber()).isEqualTo("EMP003");
      assertThat(result.get(3).getNumber()).isEqualTo("EMP004");
      assertThat(result.get(4).getNumber()).isEqualTo("EMP005");
    }
  }
}
