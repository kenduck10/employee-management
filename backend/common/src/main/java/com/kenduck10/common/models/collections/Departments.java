package com.kenduck10.common.models.collections;

import java.util.List;
import com.kenduck10.common.collections.FirstClassCollection;
import com.kenduck10.common.models.Department;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * 部門（Department）のファーストクラスコレクション。
 *
 * @see Department
 * @see FirstClassCollection
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Departments extends FirstClassCollection<Department> {

  /**
   * 部門のリストから Departments インスタンスを作成します。
   *
   * @param departments 部門のリスト（null不可）
   */
  public Departments(List<Department> departments) {
    list = departments;
  }

  /**
   * 可変長引数から Departments インスタンスを作成します。
   *
   * @param departments 部門の可変長引数（null要素は不可）
   */
  public Departments(Department... departments) {
    list = List.of(departments);
  }
}
