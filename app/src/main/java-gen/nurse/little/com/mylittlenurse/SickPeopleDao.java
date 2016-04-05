package nurse.little.com.mylittlenurse;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import nurse.little.com.mylittlenurse.SickPeople;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SICK_PEOPLE".
*/
public class SickPeopleDao extends AbstractDao<SickPeople, Long> {

    public static final String TABLENAME = "SICK_PEOPLE";

    /**
     * Properties of entity SickPeople.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Age = new Property(2, String.class, "age", false, "AGE");
        public final static Property Sex = new Property(3, String.class, "sex", false, "SEX");
        public final static Property Height = new Property(4, String.class, "height", false, "HEIGHT");
        public final static Property Weight = new Property(5, String.class, "weight", false, "WEIGHT");
        public final static Property Tel = new Property(6, String.class, "tel", false, "TEL");
        public final static Property Time = new Property(7, String.class, "time", false, "TIME");
        public final static Property Mailou = new Property(8, String.class, "mailou", false, "MAILOU");
        public final static Property Huayan = new Property(9, String.class, "huayan", false, "HUAYAN");
        public final static Property Yingyang = new Property(10, String.class, "yingyang", false, "YINGYANG");
        public final static Property Bingshi = new Property(11, String.class, "bingshi", false, "BINGSHI");
    };


    public SickPeopleDao(DaoConfig config) {
        super(config);
    }
    
    public SickPeopleDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SICK_PEOPLE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NAME\" TEXT NOT NULL ," + // 1: name
                "\"AGE\" TEXT," + // 2: age
                "\"SEX\" TEXT," + // 3: sex
                "\"HEIGHT\" TEXT," + // 4: height
                "\"WEIGHT\" TEXT," + // 5: weight
                "\"TEL\" TEXT," + // 6: tel
                "\"TIME\" TEXT," + // 7: time
                "\"MAILOU\" TEXT," + // 8: mailou
                "\"HUAYAN\" TEXT," + // 9: huayan
                "\"YINGYANG\" TEXT," + // 10: yingyang
                "\"BINGSHI\" TEXT);"); // 11: bingshi
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SICK_PEOPLE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, SickPeople entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
 
        String age = entity.getAge();
        if (age != null) {
            stmt.bindString(3, age);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(4, sex);
        }
 
        String height = entity.getHeight();
        if (height != null) {
            stmt.bindString(5, height);
        }
 
        String weight = entity.getWeight();
        if (weight != null) {
            stmt.bindString(6, weight);
        }
 
        String tel = entity.getTel();
        if (tel != null) {
            stmt.bindString(7, tel);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(8, time);
        }
 
        String mailou = entity.getMailou();
        if (mailou != null) {
            stmt.bindString(9, mailou);
        }
 
        String huayan = entity.getHuayan();
        if (huayan != null) {
            stmt.bindString(10, huayan);
        }
 
        String yingyang = entity.getYingyang();
        if (yingyang != null) {
            stmt.bindString(11, yingyang);
        }
 
        String bingshi = entity.getBingshi();
        if (bingshi != null) {
            stmt.bindString(12, bingshi);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public SickPeople readEntity(Cursor cursor, int offset) {
        SickPeople entity = new SickPeople( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // age
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // sex
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // height
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // weight
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // tel
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // time
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // mailou
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // huayan
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // yingyang
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11) // bingshi
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, SickPeople entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setAge(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSex(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setHeight(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setWeight(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTel(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setTime(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setMailou(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setHuayan(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setYingyang(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setBingshi(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(SickPeople entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(SickPeople entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
