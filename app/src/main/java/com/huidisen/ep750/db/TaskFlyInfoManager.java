package com.huidisen.ep750.db;

import android.content.Context;


import com.huidisen.ep750.bean.TaskBean;
import com.huidisen.ep750.bean.TaskFlyInfoBean;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by miaoyichao on 16/6/10.
 */
public class TaskFlyInfoManager {
    private static TaskFlyInfoManager instance = null;
    private TaskFlyInfoDao dao;
    private static Context mContext;

    private TaskFlyInfoManager(Context context) {
        super();
        dao = new TaskFlyInfoDao(context);
    }

    /**
     * @return
     * @Title: obtainAllSMS
     * @Description: 获取信息列表
     * @author zhangteng
     * 修改时间： 2015-9-6 上午10:28:15
     * 修改内容：（这里用一句话描述此次修改的内容）
     */
    public List<TaskFlyInfoBean> obtainAllInfo() {
        List<TaskFlyInfoBean> beans = dao.queryAll();
        return beans;
    }

    public void add(TaskFlyInfoBean bean) {
        dao.add(bean);
    }

    public void delete(TaskFlyInfoBean bean) {
        dao.delete(bean);
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
    public static TaskFlyInfoManager getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new TaskFlyInfoManager(context);
        }
        return instance;
    }
}
