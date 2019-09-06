package com.huidisen.ep750.db;

import android.content.Context;


import com.huidisen.ep750.bean.TaskInfoBean;

import java.util.List;


/**
 * Created by miaoyichao on 16/6/5.
 */
public class TaskInfoManager {
    private static TaskInfoManager instance = null;
    private TaskInfoDao dao;
    private static Context mContext;

    private TaskInfoManager(Context context) {
        super();
        dao = new TaskInfoDao(context);
    }

    /**
     * @return
     * @Title: obtainAllSMS
     * @Description: 获取信息列表
     * @author zhangteng
     * 修改时间： 2015-9-6 上午10:28:15
     * 修改内容：（这里用一句话描述此次修改的内容）
     */
    public List<TaskInfoBean> obtainAllInfo() {
        List<TaskInfoBean> beans = dao.queryAll();
        return beans;
    }

    public TaskInfoBean search(String taskId) {

        return dao.queryByTaskId(taskId);
    }

    public void add(TaskInfoBean bean) {
        dao.add(bean);
    }

    public void delete(TaskInfoBean bean) {
        dao.delete(bean);
    }

    public void update(TaskInfoBean bean) {
        dao.update(bean);
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
    public static TaskInfoManager getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new TaskInfoManager(context);
        }
        return instance;
    }


}
