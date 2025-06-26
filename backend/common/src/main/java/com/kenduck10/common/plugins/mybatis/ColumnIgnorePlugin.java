package com.kenduck10.common.plugins.mybatis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

public class ColumnIgnorePlugin extends PluginAdapter {
  private List<String> ignoreColumns = new ArrayList<>();
  private List<String> excludeTables = new ArrayList<>();

  @Override
  public boolean validate(List<String> warnings) {
    String ignoreColumnsStr = properties.getProperty("ignoreColumns");
    if (ignoreColumnsStr != null) {
      ignoreColumns = Arrays.asList(ignoreColumnsStr.split(","));
    }

    String excludeTablesStr = properties.getProperty("excludeTables");
    if (excludeTablesStr != null) {
      excludeTables = Arrays.asList(excludeTablesStr.split(","));
    }

    return true;
  }

  @Override
  public void initialized(IntrospectedTable introspectedTable) {
    String tableName = introspectedTable.getTableConfiguration().getTableName();
    if (excludeTables.contains(tableName)) {
      return;
    }
    // 除外対象カラムをIntrospectedTableから削除
    List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
    Iterator<IntrospectedColumn> columnIterator = allColumns.iterator();
    while (columnIterator.hasNext()) {
      IntrospectedColumn column = columnIterator.next();
      if (ignoreColumns.contains(column.getActualColumnName())) {
        columnIterator.remove();
      }
    }
    // Base Columnsからも削除
    List<IntrospectedColumn> baseColumns = introspectedTable.getBaseColumns();
    baseColumns.removeIf(column -> ignoreColumns.contains(column.getActualColumnName()));
  }
}
