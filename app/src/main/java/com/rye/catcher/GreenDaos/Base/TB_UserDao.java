//package com.rye.catcher.GreenDaos.Base;
//
//import android.database.Cursor;
//import android.database.sqlite.SQLiteStatement;
//
//import org.greenrobot.greendao.AbstractDao;
//import org.greenrobot.greendao.Property;
//import org.greenrobot.greendao.internal.DaoConfig;
//import org.greenrobot.greendao.database.Database;
//import org.greenrobot.greendao.database.DatabaseStatement;
//
//// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
///**
// * DAO for table "TB__USER".
//*/
//public class TB_UserDao extends AbstractDao<TB_User, Long> {
//
//    public static final String TABLENAME = "TB__USER";
//
//    /**
//     * Properties of entity TB_User.<br/>
//     * Can be used for QueryBuilder and for referencing column names.
//     */
//    public static class Properties {
//        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
//        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
//        public final static Property Sex = new Property(2, String.class, "sex", false, "SEX");
//        public final static Property Salary = new Property(3, String.class, "salary", false, "SALARY");
//    }
//
//
//    public TB_UserDao(DaoConfig config) {
//        super(config);
//    }
//
//    public TB_UserDao(DaoConfig config, DaoSession daoSession) {
//        super(config, daoSession);
//    }
//
//    /** Creates the underlying database table. */
//    public static void createTable(Database db, boolean ifNotExists) {
//        String constraint = ifNotExists? "IF NOT EXISTS ": "";
//        db.execSQL("CREATE TABLE " + constraint + "\"TB__USER\" (" + //
//                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
//                "\"NAME\" TEXT," + // 1: name
//                "\"SEX\" TEXT," + // 2: sex
//                "\"SALARY\" TEXT);"); // 3: salary
//    }
//
//    /** Drops the underlying database table. */
//    public static void dropTable(Database db, boolean ifExists) {
//        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TB__USER\"";
//        db.execSQL(sql);
//    }
//
//    @Override
//    protected final void bindValues(DatabaseStatement stmt, TB_User entity) {
//        stmt.clearBindings();
//
//        Long id = entity.getId();
//        if (id != null) {
//            stmt.bindLong(1, id);
//        }
//
//        String name = entity.getName();
//        if (name != null) {
//            stmt.bindString(2, name);
//        }
//
//        String sex = entity.getSex();
//        if (sex != null) {
//            stmt.bindString(3, sex);
//        }
//
//        String salary = entity.getSalary();
//        if (salary != null) {
//            stmt.bindString(4, salary);
//        }
//    }
//
//    @Override
//    protected final void bindValues(SQLiteStatement stmt, TB_User entity) {
//        stmt.clearBindings();
//
//        Long id = entity.getId();
//        if (id != null) {
//            stmt.bindLong(1, id);
//        }
//
//        String name = entity.getName();
//        if (name != null) {
//            stmt.bindString(2, name);
//        }
//
//        String sex = entity.getSex();
//        if (sex != null) {
//            stmt.bindString(3, sex);
//        }
//
//        String salary = entity.getSalary();
//        if (salary != null) {
//            stmt.bindString(4, salary);
//        }
//    }
//
//    @Override
//    public Long readKey(Cursor cursor, int offset) {
//        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
//    }
//
//    @Override
//    public TB_User readEntity(Cursor cursor, int offset) {
//        TB_User entity = new TB_User( //
//            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
//            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
//            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // sex
//            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // salary
//        );
//        return entity;
//    }
//
//    @Override
//    public void readEntity(Cursor cursor, TB_User entity, int offset) {
//        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
//        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
//        entity.setSex(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
//        entity.setSalary(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
//     }
//
//    @Override
//    protected final Long updateKeyAfterInsert(TB_User entity, long rowId) {
//        entity.setId(rowId);
//        return rowId;
//    }
//
//    @Override
//    public Long getKey(TB_User entity) {
//        if(entity != null) {
//            return entity.getId();
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public boolean hasKey(TB_User entity) {
//        return entity.getId() != null;
//    }
//
//    @Override
//    protected final boolean isEntityUpdateable() {
//        return true;
//    }
//
//}
