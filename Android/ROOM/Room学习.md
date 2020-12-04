# Room学习

## Migration

### 迁移策略

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

### 删除字段

SQLite是轻量级数据库，没有删除字段的功能，要想实现删除功能应该执行四个步骤：

1. 新建一张字段相同但删除了先要删掉的字段的信标。**（新建表）**

2. 将原有数据库的一些字段复制到信标。**（复制内容）**

3. 删除旧的数据库。**（删除）**

4. 修改新建数据库名字为原数据库名字**（改名）**

```java
      static final Migration MIGRATION_3_4 = new Migration(3,4) {
           @Override
           public void migrate(@NonNull SupportSQLiteDatabase database) {
               database.execSQL("create table word_temp (id integer primary key not null,englishWord TEXT,chineseMeaning text,bar_data integer not null default 0)");
               database.execSQL("insert into word_temp (id,englishWord,chineseMeaning,bar_data) select id,englishWord,chineseMeaning,bar_data from word;");
               database.execSQL("drop table word");
               database.execSQL("alter table word_temp rename to word");
           }
       }
```

   

```
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://m.youdao.com/dict?le=eng&q=" +                   holder.textViewEnglish.getText());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });
```