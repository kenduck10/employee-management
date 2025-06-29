package com.kenduck10.common.models.collections;

import java.util.List;
import com.kenduck10.common.collections.FirstClassCollection;
import com.kenduck10.common.models.Department;

public class Departments extends FirstClassCollection<Department> {

  public Departments(List<Department> departments) {
    list = departments;
  }
}
