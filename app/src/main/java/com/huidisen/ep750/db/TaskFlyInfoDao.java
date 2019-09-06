package com.huidisen.ep750.db;

import android.content.Context;

import com.huidisen.ep750.bean.TaskFlyInfoBean;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by miaoyichao on 16/6/10.
 */
public class TaskFlyInfoDao {
    private Context context;
    private Dao<TaskFlyInfoBean, Integer> flyInfoDao;
    private DBHelper dbHelper;

    public TaskFlyInfoDao(Context context) {
        this.context = context;
        try {
            dbHelper = dbHelper.getHelper(context);
            flyInfoDao = dbHelper.getDao(TaskFlyInfoBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 后期增加增删该查的操作
    public void add(TaskFlyInfoBean bean) {
        try {
            flyInfoDao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        try {
            flyInfoDao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(TaskFlyInfoBean bean) {
        try {
            flyInfoDao.delete(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(TaskFlyInfoBean bean) {
        try {
            flyInfoDao.update(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据某一个条件更新数据库
     *
     * @param srcAddress
     * @return
     */
    public boolean updateEmailReaded(String srcAddress) {
//        try {
//            UpdateBuilder<TaskFlyInfoBean, Integer> updateBuilder = flyInfoDao
//                    .updateBuilder();
//            updateBuilder.updateColumnValue("read",
//                    CommonAttribute.SMS_READ_STAUTS_READ);
//            updateBuilder.where().eq("srcAddress", srcAddress);
//            int count = updateBuilder.update();
//            return count > 0 ? true : false;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return false;
    }

    public TaskFlyInfoBean queryById(int id) {
        TaskFlyInfoBean bean = null;
        try {
            bean = flyInfoDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }


    /**
     * 获取全部的任务信息
     *
     * @return
     */
    public List<TaskFlyInfoBean> queryAll() {
        List<TaskFlyInfoBean> beans = null;
        try {
            beans = flyInfoDao.queryBuilder()
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }
}
