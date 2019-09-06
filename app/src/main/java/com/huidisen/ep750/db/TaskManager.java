package com.huidisen.ep750.db;

import android.content.Context;

import com.huidisen.ep750.bean.TaskBean;
import com.huidisen.ep750.utils.Commondata;

import java.util.List;

/**
 * Created by miaoyichao on 16/6/10.
 */
public class TaskManager {
    private static TaskManager instance = null;
    private TaskDao dao;
    private static Context mContext;

    private TaskManager(Context context) {
        super();
        dao = new TaskDao(context);
    }

    /**
     * @return
     * @Title: obtainAllSMS
     * @Description: 获取信息列表
     * @author zhangteng
     * 修改时间： 2015-9-6 上午10:28:15
     * 修改内容：（这里用一句话描述此次修改的内容）
     */
    public List<TaskBean.DataBean> obtainAllInfo() {
        List<TaskBean.DataBean> beans = dao.queryAll();
        return beans;
    }

    public void deleteAll() {
        dao.deleteAll();
    }

    public boolean containInfo(TaskBean.DataBean bean) {
        List<TaskBean.DataBean> beans = obtainAllInfo();
        if (beans != null && bean.getTaskInfo() != null) {
            for (TaskBean.DataBean info : beans) {
                if (bean.getTaskInfo().getId().equals(info.getTaskInfo().getId())) {
                    return true;
                }
            }
        }

        return false;
    }

    public TaskBean.DataBean queryById(int id) {
        return dao.queryById(id);
    }

    public TaskBean.DataBean queryByTaskId(String taskId) {
        return dao.queryByTaskId(taskId);
    }

    public int  add(TaskBean.DataBean bean) {
        return dao.add(bean);
    }

    public int update(TaskBean.DataBean bean) {
        return dao.update(bean);
    }

    public void delete(TaskBean.DataBean bean) {
        dao.delete(bean);
    }

    public int  deleteByTaskId(int taskId) {
        return dao.deleteByTaskId(taskId);
    }


    /**
     * @return 1 true, 0 没有任务, -1 false
     */
    public int obtainDjsTask() {
        List<TaskBean.DataBean> beans = obtainAllInfo();

        if (beans != null) {
            for (TaskBean.DataBean info : beans) {
                if (info.getTaskInfo().getStatusCode().equals(Commondata.TASKNODE_DJS)) {
                    return 1;
                }
            }
        } else
            return 0;
        return -1;
    }

    public int obtainWwcTask() {
        List<TaskBean.DataBean> beans = obtainAllInfo();
        if (beans != null) {
            for (TaskBean.DataBean info : beans) {
                if (!(info.getTaskInfo().getStatusCode().equals(Commondata.TASKNODE_DJS))) {
                    return 1;
                }
            }
        } else
            return 0;
        return -1;
    }


    /**
     * @param context 上下文环境
     * @return
     * @Title: getInstance
     * @Description: 实例化方法
     * @author zhangteng
     * 修改时间： 2015-9-6 上午10:29:23
     * 修改内容：（这里用一句话描述此次修改的内容）
     */
    public static TaskManager getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new TaskManager(context);
        }
        return instance;
    }
}
