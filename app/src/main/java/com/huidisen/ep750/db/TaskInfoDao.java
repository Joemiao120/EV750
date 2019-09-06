package com.huidisen.ep750.db;

import android.content.Context;

import com.huidisen.ep750.bean.TaskInfoBean;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by miaoyichao on 16/5/31.
 */
public class TaskInfoDao {
    private Context context;
    private Dao<TaskInfoBean, Integer> taskDao;
    private DBHelper dbHelper;

    public TaskInfoDao(Context context) {
        this.context = context;
        try {
            dbHelper = dbHelper.getHelper(context);
            taskDao = dbHelper.getDao(TaskInfoBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 后期增加增删该查的操作
    public void add(TaskInfoBean bean) {
        try {
            taskDao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        try {
            taskDao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(TaskInfoBean bean) {
        try {
            taskDao.delete(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(TaskInfoBean bean) {
        try {
            taskDao.update(bean);
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
//            UpdateBuilder<TaskInfoBean, Integer> updateBuilder = taskDao
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

    public TaskInfoBean queryById(int id) {
        TaskInfoBean bean = null;
        try {
            bean = taskDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public TaskInfoBean queryByTaskId(String taskId){
        try {
            List<TaskInfoBean> beans = taskDao.queryBuilder().where().eq("id",taskId).query();
            if (beans.size() > 0){
                return beans.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取全部的任务信息
     *
     * @return
     */
    public List<TaskInfoBean> queryAll() {
        List<TaskInfoBean> beans = null;
        try {
            beans = taskDao.queryBuilder()
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }
}
