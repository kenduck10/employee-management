# Employee Management Backend

Spring Boot multi-project 構成による従業員管理システムのバックエンドです。

## プロジェクト構成

```
backend/
├── build.gradle                    # ルートビルド設定
├── settings.gradle                 # プロジェクト設定
├── common/                         # 共通モジュール
│   ├── build.gradle
│   ├── src/main/java/
│   │   └── com/example/employee/common/
│   │       ├── CommonApplication.java
│   │       ├── model/              # MyBatis自動生成モデル
│   │       └── mapper/             # MyBatis自動生成マッパー
│   └── src/main/resources/
│       ├── application.yml
│       ├── generatorConfig.xml     # MyBatis Generator設定
│       └── db/migration/           # Flywayマイグレーション
└── batch/
    └── employee_addition/          # 従業員追加バッチ
        ├── build.gradle
        ├── src/main/java/
        │   └── com/example/employee/batch/
        │       ├── EmployeeAdditionBatchApplication.java
        │       ├── config/         # バッチ設定
        │       ├── dto/            # データ転送オブジェクト
        │       ├── processor/      # アイテムプロセッサー
        │       ├── reader/         # CSVリーダー
        │       └── writer/         # データベースライター
        └── src/main/resources/
            ├── application.yml
            └── data/
                └── employees.csv   # サンプルCSVファイル
```

## 技術スタック

- **フレームワーク**: Spring Boot 3.2.0
- **ビルドツール**: Gradle
- **O/R マッパー**: MyBatis 3.0.3
- **DB マイグレーション**: Flyway 9.22.3
- **バッチ処理**: Spring Batch
- **データベース**: MySQL 8.0

## セットアップ

### 1. データベース準備

MySQL サーバーを起動し、データベースを作成してください：

```sql
CREATE DATABASE employee_management;
```

### 2. 依存関係のインストール

```bash
cd backend
./gradlew build
```

### 3. データベースマイグレーション

```bash
cd common
../gradlew flywayMigrate
```

### 4. MyBatis モデル・マッパー生成

```bash
cd common
../gradlew mybatisGenerate
```

## 使用方法

### Common モジュールの実行

```bash
cd common
../gradlew bootRun
```

### バッチ処理の実行

```bash
cd batch/employee_addition
../../gradlew bootRun
```

## データベーススキーマ

### departments テーブル

- id (BIGINT, PRIMARY KEY)
- name (VARCHAR(100))
- description (TEXT)
- created_at (TIMESTAMP)
- updated_at (TIMESTAMP)

### employees テーブル

- id (BIGINT, PRIMARY KEY)
- employee_number (VARCHAR(20), UNIQUE)
- first_name (VARCHAR(50))
- last_name (VARCHAR(50))
- email (VARCHAR(100), UNIQUE)
- phone (VARCHAR(20))
- hire_date (DATE)
- department_id (BIGINT, FOREIGN KEY)
- position (VARCHAR(100))
- salary (DECIMAL(10,2))
- status (ENUM: 'ACTIVE', 'INACTIVE', 'TERMINATED')
- created_at (TIMESTAMP)
- updated_at (TIMESTAMP)

## バッチ処理

### 従業員追加バッチ

CSV ファイルから従業員データを読み込み、データベースに登録するバッチ処理です。

**CSV フォーマット:**

```csv
employeeNumber,firstName,lastName,email,phone,hireDate,departmentId,position,salary,status
EMP001,John,Doe,john.doe@example.com,555-0101,2024-01-15,1,Software Engineer,75000.00,ACTIVE
```

**実行方法:**

```bash
cd batch/employee_addition
../../gradlew bootRun
```

## 開発

### MyBatis モデル・マッパーの再生成

テーブル構造を変更した場合：

1. Flyway マイグレーションファイルを作成
2. マイグレーション実行: `./gradlew flywayMigrate`
3. MyBatis 再生成: `./gradlew mybatisGenerate`

### 新しいバッチジョブの追加

1. `batch/` ディレクトリに新しいサブプロジェクトを作成
2. `settings.gradle` にプロジェクトを追加
3. Spring Batch の設定を追加

## 設定

### データベース接続

`application.yml` でデータベース接続設定を変更できます：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/employee_management
    username: root
    password: password
```

### MyBatis 設定

`generatorConfig.xml` で MyBatis Generator の設定を変更できます。
