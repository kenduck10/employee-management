package com.kenduck10.common.models;

import com.kenduck10.common.models.generated.GeneratedDepartment;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Department extends GeneratedDepartment {

  public static final int CODE_MAX_LENGTH = 10;
  public static final int NAME_MAX_LENGTH = 100;

}
