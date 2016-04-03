package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MySickpeopleClass {

    //辅助文件生成的相对路径
    public static final String DAO_PATH = "/MyLittleNurse/app/src/main/java-gen";

//    E:\MyLittleNurse\app

    //辅助文件的包名
    public static final String PACKAGE_NAME = "nurse.little.com.mylittlenurse";
    //数据库的版本号
    public static final int DATA_VERSION_CODE = 1;

    public static void main(String[] args) throws Exception {
        // 正如你所见的，你创建了一个用于添加实体（Entity）的模式（Schema）对象。
        // 两个参数分别代表：数据库版本号与自动生成代码的包路径。
        Schema schema = new Schema(DATA_VERSION_CODE, PACKAGE_NAME);
//      当然，如果你愿意，你也可以分别指定生成的 Bean 与 DAO 类所在的目录，只要如下所示：
//      Schema schema = new Schema(1, "me.itangqi.bean");
//      schema.setDefaultJavaPackageDao("me.itangqi.dao");

        // 模式（Schema）同时也拥有两个默认的 flags，分别用来标示 entity 是否是 activie 以及是否使用 keep sections。
        // schema2.enableActiveEntitiesByDefault();
        // schema2.enableKeepSectionsByDefault();

        // 一旦你拥有了一个 Schema 对象后，你便可以使用它添加实体（Entities）了。
        addSickPeople(schema, "SickPeople");
        addDiaylsDateInfo(schema, "DiaylsDate");
        addSickHistory(schema, "SickHistory");

        // 最后我们将使用 DAOGenerator 类的 generateAll() 方法自动生成代码，此处你需要根据自己的情况更改输出目录（既之前创建的 java-gen)。
        // 其实，输出目录的路径可以在 build.gradle 中设置，有兴趣的朋友可以自行搜索，这里就不再详解。
        new DaoGenerator().generateAll(schema, DAO_PATH);
    }

    /**
     * 插入病史数据库
     *
     * @param schema
     * @param sickHistory
     */
    private static void addSickHistory(Schema schema, String sickHistory) {
        // 一个实体（类）就关联到数据库中的一张表，此处表名为「Note」（既类名）
        Entity people = schema.addEntity(sickHistory);
        // 你也可以重新给表命名
        // note.setTableName("NODE");

        // greenDAO 会自动根据实体类的属性值来创建表字段，并赋予默认值
        // 接下来你便可以设置表中的字段：
        people.addIdProperty().primaryKey().autoincrement();
        people.addStringProperty("time");
        // 与在 Java 中使用驼峰命名法不同，默认数据库中的命名是使用大写和下划线来分割单词的。
        // For example, a property called “creationDate” will become a database column “CREATION_DATE”.
        people.addStringProperty("name");
        people.addStringProperty("sickDes");
    }

    /**
     * 插入透析日期数据库
     *
     * @param schema
     * @param sickHistory
     */
    private static void addDiaylsDateInfo(Schema schema, String sickHistory) {
        // 一个实体（类）就关联到数据库中的一张表，此处表名为「Note」（既类名）
        Entity people = schema.addEntity(sickHistory);
        // 你也可以重新给表命名
        // note.setTableName("NODE");

        // greenDAO 会自动根据实体类的属性值来创建表字段，并赋予默认值
        // 接下来你便可以设置表中的字段：
        people.addIdProperty().primaryKey().autoincrement();
        people.addStringProperty("date");
        // 与在 Java 中使用驼峰命名法不同，默认数据库中的命名是使用大写和下划线来分割单词的。
        // For example, a property called “creationDate” will become a database column “CREATION_DATE”.
        people.addStringProperty("name");
    }

    /**
     * @param schema
     */
    private static void addNote(Schema schema) {
        // 一个实体（类）就关联到数据库中的一张表，此处表名为「Note」（既类名）
        Entity note = schema.addEntity("Note");
        // 你也可以重新给表命名
        // note.setTableName("NODE");

        // greenDAO 会自动根据实体类的属性值来创建表字段，并赋予默认值
        // 接下来你便可以设置表中的字段：
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        // 与在 Java 中使用驼峰命名法不同，默认数据库中的命名是使用大写和下划线来分割单词的。
        // For example, a property called “creationDate” will become a database column “CREATION_DATE”.
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }

    private static void addSickPeople(Schema schema, String tableName) {
        // 一个实体（类）就关联到数据库中的一张表，此处表名为「Note」（既类名）
        Entity people = schema.addEntity(tableName);
        // 你也可以重新给表命名
        // note.setTableName("NODE");

        // greenDAO 会自动根据实体类的属性值来创建表字段，并赋予默认值
        // 接下来你便可以设置表中的字段：
        people.addIdProperty().primaryKey().autoincrement();
        people.addStringProperty("name").notNull();
        // 与在 Java 中使用驼峰命名法不同，默认数据库中的命名是使用大写和下划线来分割单词的。
        // For example, a property called “creationDate” will become a database column “CREATION_DATE”.
        people.addStringProperty("age");
        people.addStringProperty("sex");
        people.addStringProperty("height");
        people.addStringProperty("weight");
        people.addStringProperty("tel");
        people.addStringProperty("time");
        people.addStringProperty("mailou");
        people.addStringProperty("huayan");
        people.addStringProperty("yingyang");
//        people.addStringProperty("age");
//        people.addDateProperty("date");
    }
}
