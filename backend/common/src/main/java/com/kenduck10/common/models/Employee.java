package com.kenduck10.common.models;

import org.apache.commons.lang3.StringUtils;
import com.kenduck10.common.models.generated.GeneratedEmployee;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 従業員エンティティを表すモデルクラス。
 * 
 * @see GeneratedEmployee
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Employee extends GeneratedEmployee {

  /**
   * 従業員番号の最大許可長。
   */
  public static final int NUMBER_MAX_LENGTH = 10;

  /**
   * 名の最大許可長。
   */
  public static final int FIRST_NAME_MAX_LENGTH = 50;

  /**
   * 姓の最大許可長。
   */
  public static final int LAST_NAME_MAX_LENGTH = 50;

  /**
   * メールアドレスの最大許可長。
   */
  public static final int EMAIL_MAX_LENGTH = 100;

  /**
   * 電話番号の最大許可長。
   */
  public static final int PHONE_MAX_LENGTH = 20;

  /**
   * 部署コードの最大許可長。
   */
  public static final int DEPARTMENT_CODE_MAX_LENGTH = 10;

  /**
   * フルネームを取得します。
   * 
   * @return 姓と名を結合したフルネーム
   */
  public String getFullName() {
    if (StringUtils.isBlank(getLastName()) && StringUtils.isBlank(getFirstName())) {
      return "";
    }
    if (StringUtils.isBlank(getLastName())) {
      return getFirstName();
    }
    if (StringUtils.isBlank(getFirstName())) {
      return getLastName();
    }
    return getLastName() + " " + getFirstName();
  }

  /**
   * 従業員が特定の部署に所属しているかどうかを判定します。
   * 
   * @param departmentCode 確認する部署コード
   * @return 指定された部署に所属している場合は {@code true}、そうでなければ {@code false}
   */
  public boolean belongsToDepartment(String departmentCode) {
    return getDepartmentCode() != null && getDepartmentCode().equals(departmentCode);
  }
}
