package com.huidisen.ep750.model;

import android.content.Context;

/**
 * Created by miaoyichao on 16/5/31.
 */

public interface TaskModel {
    void getTask( Callback.OnTaskListener listener);

    void ReportTaskNode(String idTask, String employeeId,
                        String status, Callback.OnReportTaskNodeListener listener);

    void GetJobNum( String jobNum,
                   Callback.OnGetJobNumListener listener);

    void GetTaskInfoById(String dataId,
                   Callback.OnGetTaskInfoByIdListener listener);

    void ApplyNoFlightTask(String type, Callback.OnApplyTaskListener listener);
}
