# Room学习

## Migration

数据库增加一个字段后，database的版本号得修改，需要添加一个migrations 策略

SQLite数据库没有布尔值类型，用Integer类型替代。

```java
static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("alter table word add column bar_data integer not null default 0");
        }
    }
```