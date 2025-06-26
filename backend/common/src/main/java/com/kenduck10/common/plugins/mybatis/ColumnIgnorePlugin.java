package com.kenduck10.common.plugins.mybatis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;

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
  public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass,
      IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,
      Plugin.ModelClassType modelClassType) {

    String tableName = introspectedTable.getTableConfiguration().getTableName();
    String columnName = introspectedColumn.getActualColumnName();

    // 除外テーブルの場合は通常通り生成
    if (excludeTables.contains(tableName)) {
      return true;
    }

    // // テーブル固有のプロパティをチェック
    // String includeTimestamps = introspectedTable.getTableConfiguration()
    // .getProperty("includeTimestamps");
    // if ("true".equals(includeTimestamps)) {
    // return true;
    // }

    // 除外対象カラムの場合は生成しない
    return !ignoreColumns.contains(columnName);
  }
}
