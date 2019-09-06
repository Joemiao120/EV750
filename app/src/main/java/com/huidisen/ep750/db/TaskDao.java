package com.huidisen.ep750.db;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.huidisen.ep750.bean.TaskBean;
import com.huidisen.ep750.bean.TaskBean.DataBean;
import com.huidisen.ep750.bean.TaskFlyInfoBean;
import com.huidisen.ep750.bean.TaskInfoBean;
import com.huidisen.ep750.model.TaskModel;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by miaoyichao on 16/6/10.
 */
public class TaskDao {
    private Context context;
    private Dao<TaskBean.DataBean, Integer> taskDao;
    private Dao<TaskInfoBean, Integer> taskInfoDao;
    private Dao<TaskFlyInfoBean, Integer> taskFlyInfoDao;
    private DBHelper dbHelper;

    public TaskDao(Context context) {
        this.context = context;
        try {
            dbHelper = dbHelper.getHelper(context);
            taskDao = dbHelper.getDao(TaskBean.DataBean.class);
            taskInfoDao = dbHelper.getDao(TaskInfoBean.class);
            taskFlyInfoDao = dbHelper.getDao(TaskFlyInfoBean.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 后期增加增删该查的操作
    public int add(TaskBean.DataBean bean) {
        try {
            TaskFlyInfoBean taskFlyInfo = new TaskFlyInfoBean();
            TaskInfoBean taskInfo = new TaskInfoBean();
            TaskBean.DataBean task = new TaskBean.DataBean();

            // 航班信息的增加
            taskFlyInfo = bean.getTaskFlyInfo();
            TaskFlyInfoManager.getInstance(context).add(taskFlyInfo);
            // 人物信息的增加
            taskInfo = bean.getTaskInfo();
            TaskInfoManager.getInstance(context).add(taskInfo);
            // 所有信息的增加
            task.setTaskFlyInfo(taskFlyInfo);
            task.setTaskInfo(taskInfo);

            return taskDao.create(task);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void deleteById(int id) {
        try {
            taskDao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 通过taskid删除列
     *
     * @param task_id
     */
    public int deleteByTaskId(int task_id) {
        try {
            List<TaskBean.DataBean> dataBeans = taskDao.queryBuilder().where().eq("task_id", task_id).query();
            for (TaskBean.DataBean dataBean : dataBeans) {
                TaskFlyInfoManager.getInstance(context).delete(dataBean.getTaskFlyInfo());
                TaskInfoManager.getInstance(context).delete(dataBean.getTaskInfo());
                taskDao.delete(dataBean);
            }

            return dataBeans.size();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void delete(TaskBean.DataBean bean) {
        try {
            taskDao.delete(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int update(TaskBean.DataBean bean) {
        try {
            int i = taskDao.update(bean);
            Log.e("taskDao", i + "");
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
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
//            UpdateBuilder<TaskBean.DataBean, Integer> updateBuilder = taskDao
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

    /**
     * 根据id取出信息
     *
     * @param id
     * @return
     */
    public TaskBean.DataBean queryById(int id) {
        TaskBean.DataBean bean = null;
        try {
            bean = taskDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public TaskBean.DataBean queryByTaskId(String taskId) {
        TaskBean.DataBean bean = null;

        try {
            TaskInfoBean taskInfo = TaskInfoManager.getInstance(context).search(taskId);

            if (taskInfo == null){
                return bean;
            }

            bean = taskDao.queryBuilder().where().eq("task_id", taskInfo.get_id()).query().get(0);
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
    public List<TaskBean.DataBean> queryAll() {
        List<TaskBean.DataBean> beans = null;
        try {
            beans = taskDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return beans;
    }

    /**
     *
     */

    public void deleteAll() {
        try {
            taskDao.delete(queryAll());
            taskInfoDao.delete(taskInfoDao.queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
