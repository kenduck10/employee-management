package com.kenduck10.common.mappers;

import static com.kenduck10.common.mappers.generated.GeneratedDepartmentDynamicSqlSupport.code;
import static org.mybatis.dynamic.sql.SqlBuilder.isIn;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import com.kenduck10.common.mappers.generated.GeneratedDepartmentMapper;
import com.kenduck10.common.models.Department;

@Mapper
public interface DepartmentMapper extends GeneratedDepartmentMapper {

  /**
   * 指定されたコードリストに含まれるコードを持つ部署を取得します。
   *
   * @param codes 部署コードのリスト
   * @return 指定されたコードリストに含まれるコードを持つ部署のリスト
   * @throws IllegalArgumentException コードリストが空の場合
   */
  default List<Department> selectByCodes(List<String> codes) {
    if (CollectionUtils.isEmpty(codes)) {
      throw new IllegalArgumentException("Code list cannot be empty");
    }
    return select(c -> c.where(code, isIn(codes)).orderBy(code))
        .stream()
        .map(generatedDepartment -> {
          Department department = new Department();
          BeanUtils.copyProperties(generatedDepartment, department);
          return department;
        })
        .toList();
  }
}
