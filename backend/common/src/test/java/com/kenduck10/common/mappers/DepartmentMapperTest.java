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
  @DataSet("datasets/mappers/DepartmentMapper_selectByCodes.yml")
  class SelectByCodesTest {

    /**
     * 単一のコードで部署を取得するテスト。 単一のコード入力でメソッドが正しく動作することを検証する。
     */
    @Test
    void withSingleCode_shouldReturnOneDepartment() {
      List<String> codes = Arrays.asList("SALES");
      List<Department> result = departmentMapper.selectByCodes(codes);
      assertThat(result).hasSize(1);

      Department department = result.get(0);
      assertThat(department.getCode()).isEqualTo("SALES");
      assertThat(department.getName()).isEqualTo("営業部");
      assertThat(department.getDescription()).isEqualTo("営業活動と顧客管理を担当");
    }

    /**
     * 全てのコードで全部署を取得するテスト。 利用可能な全てのコードを提供して、全部署が正しい順序で返されることを検証する。
     */
    @Test
    void withAllCodes_shouldReturnAllDepartments() {
      List<String> codes = Arrays.asList("IT", "HR", "SALES", "FINANCE");
      List<Department> result = departmentMapper.selectByCodes(codes);
      assertThat(result).hasSize(4);

      // 部署がコード順（アルファベット順）で返されることを検証
      assertThat(result.get(0).getCode()).isEqualTo("FINANCE");
      assertThat(result.get(1).getCode()).isEqualTo("HR");
      assertThat(result.get(2).getCode()).isEqualTo("IT");
      assertThat(result.get(3).getCode()).isEqualTo("SALES");
    }

    /**
     * 存在しない部署コードを処理するテスト。 一致する部署が見つからない場合に空のリストが返されることを検証する。
     */
    @Test
    void withNonExistentCodes_shouldReturnEmptyList() {
      List<String> codes = Arrays.asList("NONEXISTENT", "INVALID");
      List<Department> result = departmentMapper.selectByCodes(codes);
      assertThat(result).isEmpty();
    }

    /**
     * 存在するコードと存在しないコードが混在する場合のテスト。 存在する部署のみが返されることを検証する。
     */
    @Test
    void withMixedCodes_shouldReturnOnlyExistingDepartments() {
      List<String> codes = Arrays.asList("IT", "NONEXISTENT", "HR", "INVALID");
      List<Department> result = departmentMapper.selectByCodes(codes);
      assertThat(result).hasSize(2);

      assertThat(result.get(0).getCode()).isEqualTo("HR");
      assertThat(result.get(1).getCode()).isEqualTo("IT");
    }

    /**
     * 入力リストに重複するコードが含まれる場合のテスト。 重複するコードが結果に重複した部署を生成しないことを検証する。
     */
    @Test
    void withDuplicateCodes_shouldReturnUniqueResults() {
      List<String> codes = Arrays.asList("IT", "HR", "IT", "HR");
      List<Department> result = departmentMapper.selectByCodes(codes);
      assertThat(result).hasSize(2);

      assertThat(result.get(0).getCode()).isEqualTo("HR");
      assertThat(result.get(1).getCode()).isEqualTo("IT");
    }

    /**
     * 結果の順序を検証するテスト。 入力の順序に関係なく、部署が常にコードのアルファベット順で返されることを検証する。
     */
    @Test
    void withUnorderedInput_shouldReturnOrderedResults() {
      List<String> codes = Arrays.asList("SALES", "FINANCE", "IT", "HR");
      List<Department> result = departmentMapper.selectByCodes(codes);
      assertThat(result).hasSize(4);

      // 結果がコードのアルファベット順で並んでいることを検証
      assertThat(result.get(0).getCode()).isEqualTo("FINANCE");
      assertThat(result.get(1).getCode()).isEqualTo("HR");
      assertThat(result.get(2).getCode()).isEqualTo("IT");
      assertThat(result.get(3).getCode()).isEqualTo("SALES");
    }
  }
}
