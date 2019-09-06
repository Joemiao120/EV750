package com.huidisen.ep750.model;

/**
 * Created by miaoyichao on 16/6/2.
 */
public class Callback {
    /**
     * 获取全部任务的接口
     */
    public interface OnTaskListener {

        void onSuccess();

        void onError(String error);
    }

    /**
     * 上报节点的接口
     */
    public interface OnReportTaskNodeListener {

        void onSuccess();

        void onError(String error);
    }
    /**
     * 获取用户信息和车牌号
     */
    public interface OnGetJobNumListener {

        void onSuccess(String userId);

        void onError();
    }

    /**
     * 获取单条任务的接口
     */
    public interface OnGetTaskInfoByIdListener {

        void onSuccess();

        void onError(String error);
    }

    /**
     * 接受任务
     */
    public interface OnAcceptTaskListener {

        void onSuccess();

        void onError(String taskNode);
    }

    /**
     * 下载xml文件
     */
    public interface OnUploadInfoListener {

        void onSuccess();

        void onError(String error);
    }

    /**
     * 下载xml文件
     */
    public interface OnApplyTaskListener {

        void onSuccess();

        void onError(String error);
    }
}
