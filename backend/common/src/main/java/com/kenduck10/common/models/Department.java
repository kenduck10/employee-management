package com.kenduck10.common.models;

import com.kenduck10.common.models.generated.GeneratedDepartment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部署エンティティを表すモデルクラス。
 * 
 * このクラスは自動生成された {@link GeneratedDepartment} クラスを継承し、 部署エンティティに対する追加のロジックと定数を提供します。
 * 部署には部署コード、部署名、説明などの情報が含まれます。
 * 
 * @see GeneratedDepartment
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Department extends GeneratedDepartment {

  /**
   * 部署コードの最大許可長。
   */
  public static final int CODE_MAX_LENGTH = 10;

  /**
   * 部署名の最大許可長。
   */
  public static final int NAME_MAX_LENGTH = 100;

}
