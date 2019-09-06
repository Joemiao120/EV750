package com.huidisen.ep750.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.huidisen.ep750.bean.TaskBean;
import com.huidisen.ep750.bean.TaskFlyInfoBean;
import com.huidisen.ep750.bean.TaskInfoBean;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by miaoyichao on 16/5/29.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String TABLE_NAME = "sqlite_huidesen.db";
    private static final int DB_VERSION = 1;
    private static DBHelper instance;
    private Map<String, Dao> daos = new HashMap<String, Dao>();


    public DBHelper(Context context) {
        super(context, TABLE_NAME, null, DB_VERSION);
    }

//    @Override
//    public synchronized SQLiteDatabase getWritableDatabase() {
//        return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
//                SQLiteDatabase.OPEN_READWRITE);
//    }
//
//    public synchronized SQLiteDatabase getReadableDatabase() {
//        return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
//                SQLiteDatabase.OPEN_READONLY);
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, TaskBean.DataBean.class);
            TableUtils.createTable(connectionSource, TaskFlyInfoBean.class);
            TableUtils.createTable(connectionSource, TaskInfoBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, TaskBean.DataBean.class, true);
            TableUtils.dropTable(connectionSource, TaskFlyInfoBean.class, true);
            TableUtils.dropTable(connectionSource, TaskInfoBean.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DBHelper getHelper(Context context) {
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null)
                    instance = new DBHelper(context);
            }
        }

        return instance;
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();

        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
