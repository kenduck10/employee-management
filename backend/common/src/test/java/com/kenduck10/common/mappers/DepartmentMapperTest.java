package com.kenduck10.common.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.kenduck10.common.models.Department;

/**
 * DepartmentMapperのユニットテストクラス
 */
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DBRider
class DepartmentMapperTest {

  @Autowired
  private DepartmentMapper departmentMapper;

  @Nested
  class SelectByCodesTest {

    @Test
    @DataSet("datasets/mappers/DepartmentMapper_selectByCodes.yml")
    void selectByCodes_正常系_複数のコードで部署を取得() {
      List<String> codes = Arrays.asList("IT", "HR");
      List<Department> result = departmentMapper.selectByCodes(codes);
      assertThat(result).hasSize(2);

      Department department1 = result.get(0);
      assertThat(department1.getCode()).isEqualTo("HR");
      assertThat(department1.getName()).isEqualTo("人事部");
      assertThat(department1.getDescription()).isEqualTo("人材採用と人事管理を担当");

      Department department2 = result.get(1);
      assertThat(department2.getCode()).isEqualTo("IT");
      assertThat(department2.getName()).isEqualTo("情報技術部");
      assertThat(department2.getDescription()).isEqualTo("システム開発とIT運用を担当");
    }
  }


  // @Test
  // @DisplayName("単一のコードで部署を正常に取得できること")
  // @DataSet("datasets/departments.yml")
  // void selectByCodes_正常系_単一のコードで部署を取得() {
  // // Given
  // List<String> codes = Arrays.asList("SALES");

  // // When
  // List<Department> result = departmentMapper.selectByCodes(codes);

  // // Then
  // assertThat(result).hasSize(1);
  // Department salesDepartment = result.get(0);
  // assertThat(salesDepartment.getCode()).isEqualTo("SALES");
  // assertThat(salesDepartment.getName()).isEqualTo("営業部");
  // assertThat(salesDepartment.getDescription()).isEqualTo("営業活動と顧客管理を担当");
  // }

  // @Test
  // @DisplayName("存在しないコードを指定した場合、空のリストが返されること")
  // @DataSet("datasets/departments.yml")
  // void selectByCodes_正常系_存在しないコードで空のリストを取得() {
  // // Given
  // List<String> codes = Arrays.asList("NONEXISTENT");

  // // When
  // List<Department> result = departmentMapper.selectByCodes(codes);

  // // Then
  // assertThat(result).isEmpty();
  // }

  // @Test
  // @DisplayName("存在するコードと存在しないコードが混在している場合、存在するもののみ取得できること")
  // @DataSet("datasets/departments.yml")
  // void selectByCodes_正常系_存在するコードと存在しないコードの混在() {
  // // Given
  // List<String> codes = Arrays.asList("IT", "NONEXISTENT", "FINANCE");

  // // When
  // List<Department> result = departmentMapper.selectByCodes(codes);

  // // Then
  // assertThat(result).hasSize(2);
  // assertThat(result)
  // .extracting(Department::getCode)
  // .containsExactlyInAnyOrder("IT", "FINANCE");
  // }

  // @Test
  // @DisplayName("空のコードリストを指定した場合、IllegalArgumentExceptionが発生すること")
  // void selectByCodes_異常系_空のコードリスト() {
  // // Given
  // List<String> codes = Collections.emptyList();

  // // When & Then
  // assertThatThrownBy(() -> departmentMapper.selectByCodes(codes))
  // .isInstanceOf(IllegalArgumentException.class)
  // .hasMessage("Code list cannot be empty");
  // }

  // @Test
  // @DisplayName("nullのコードリストを指定した場合、IllegalArgumentExceptionが発生すること")
  // void selectByCodes_異常系_nullのコードリスト() {
  // // Given
  // List<String> codes = null;

  // // When & Then
  // assertThatThrownBy(() -> departmentMapper.selectByCodes(codes))
  // .isInstanceOf(IllegalArgumentException.class)
  // .hasMessage("Code list cannot be empty");
  // }

  // @Test
  // @DisplayName("全ての部署コードを指定した場合、全ての部署が取得できること")
  // @DataSet("datasets/departments.yml")
  // void selectByCodes_正常系_全ての部署コードを指定() {
  // // Given
  // List<String> codes = Arrays.asList("IT", "HR", "SALES", "FINANCE");

  // // When
  // List<Department> result = departmentMapper.selectByCodes(codes);

  // // Then
  // assertThat(result).hasSize(4);
  // assertThat(result)
  // .extracting(Department::getCode)
  // .containsExactlyInAnyOrder("IT", "HR", "SALES", "FINANCE");
  // }

  // @Test
  // @DisplayName("重複するコードを指定した場合、重複なしで部署が取得できること")
  // @DataSet("datasets/departments.yml")
  // void selectByCodes_正常系_重複するコードを指定() {
  // // Given
  // List<String> codes = Arrays.asList("IT", "IT", "HR", "HR");

  // // When
  // List<Department> result = departmentMapper.selectByCodes(codes);

  // // Then
  // assertThat(result).hasSize(2);
  // assertThat(result)
  // .extracting(Department::getCode)
  // .containsExactlyInAnyOrder("IT", "HR");
  // }
}
