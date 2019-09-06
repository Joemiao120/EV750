package com.huidisen.ep750.model;

import android.content.Context;
import android.util.Log;

import com.huidisen.ep750.bean.EmployeeInfoBean;
import com.huidisen.ep750.bean.ResultStatusBean;
import com.huidisen.ep750.bean.SingleTaskBean;
import com.huidisen.ep750.bean.TaskBean;
import com.huidisen.ep750.db.TaskManager;
import com.huidisen.ep750.net.NetService;
import com.huidisen.ep750.net.NetTask;
import com.huidisen.ep750.utils.Commondata;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by miaoyichao on 16/5/31.
 */
public class TaskModelImpl implements TaskModel {
    private Context context;
    private static TaskModelImpl instance;

    public TaskModelImpl(Context context) {
        this.context = context.getApplicationContext();

    }

    public static TaskModelImpl getInstance(Context context) {
        if (instance == null) {
            instance = new TaskModelImpl(context);
        }
        return instance;
    }

    @Override
    public void getTask(final Callback.OnTaskListener listener) {
        NetService netService = NetTask.generateRequest();
        retrofit2.Call<TaskBean> flightInfoCall = netService.requestAllTasks(Commondata.DEVICE_CODE);
        flightInfoCall.enqueue(new retrofit2.Callback<TaskBean>() {
            @Override
            public void onResponse(Call<TaskBean> call, Response<TaskBean> response) {
                if (response == null) {
                    listener.onError(null);
                } else if (response.body() != null && response.body().getResult().equals(Commondata.SUCCESS) &&
                        response.body().getStatus() == 200) {

                    saveToDB(response);
                    listener.onSuccess();
                } else {
                    if (response.body() != null && response.body().getResult() != null)
                        listener.onError("获取全部任务失败");
                    else listener.onError(null);
                }
            }

            @Override
            public void onFailure(Call<TaskBean> call, Throwable t) {
                Logger.e("onFailure: %s", t.toString());
                listener.onError("获取全部任务失败");
            }
        });
    }

    @Override
    public void ReportTaskNode(String idTask, String workerId, String status, final Callback.OnReportTaskNodeListener listener) {
        NetService netService = NetTask.generateRequest();
        retrofit2.Call<ResultStatusBean> employeeInfoCall = netService.reportTaskNode(workerId,
                Commondata.DEVICE_CODE, idTask, status);
        employeeInfoCall.enqueue(new retrofit2.Callback<ResultStatusBean>() {
            @Override
            public void onResponse(Call<ResultStatusBean> call, Response<ResultStatusBean> response) {
                // 在这里更新数据库
                if (response.body() != null && response.body().getResult().equals(Commondata.SUCCESS) &&
                        response.body().getStatus() == 200) {
                    listener.onSuccess();
                } else {
                    if (response.body() != null && response.body().getResult() != null)
                        listener.onError("任务上报节点失败");
                    else
                        listener.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ResultStatusBean> call, Throwable t) {
                listener.onError(null);
            }
        });

    }

    @Override
    public void GetJobNum(String jobNum, final Callback.OnGetJobNumListener listener) {

        Log.e("miao", jobNum + "jobNum ===");
        NetService netService = NetTask.generateRequest();
        retrofit2.Call<EmployeeInfoBean> employeeInfoCall = netService.getEmployeeInfo(jobNum, Commondata.DEVICE_CODE);
        employeeInfoCall.enqueue(new retrofit2.Callback<EmployeeInfoBean>() {
            @Override
            public void onResponse(Call<EmployeeInfoBean> call, Response<EmployeeInfoBean> response) {
                if (response.body() != null && response.body().getResult().equals(Commondata.SUCCESS) &&
                        response.body().getStatus() == 200) {
                    listener.onSuccess(response.body().getData().getUserId());
                } else {
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<EmployeeInfoBean> call, Throwable t) {
                listener.onError();
            }
        });
    }

    @Override
    public void GetTaskInfoById(String dataId, final Callback.OnGetTaskInfoByIdListener listener) {
        NetService netService = NetTask.generateRequest();
        retrofit2.Call<SingleTaskBean> flightInfoCall = netService.getTaskInfoById(dataId);
        flightInfoCall.enqueue(new retrofit2.Callback<SingleTaskBean>() {
            @Override
            public void onResponse(Call<SingleTaskBean> call, Response<SingleTaskBean> response) {
                // 更新数据
                if (response.body() != null && response.body().getResult().equals(Commondata.SUCCESS) &&
                        response.body().getStatus() == 200) {
                    // 加到数据库
                    if (addToDB(response) > 0);
                        listener.onSuccess();
                } else {
                    if (response.body() != null && response.body().getResult() != null)
                        listener.onError("获取任务失败");
                    else
                        listener.onError(null);
                }

            }

            @Override
            public void onFailure(Call<SingleTaskBean> call, Throwable t) {
                listener.onError(null);
            }
        });


//        NetService netService = NetTask.generateRequest();
//        retrofit2.Call<TaskBean.DataBean> flightInfoCall = netService.getTaskInfoById(dataId);
//        flightInfoCall.enqueue(new retrofit2.Callback<TaskBean.DataBean>() {
//            @Override
//            public void onResponse(Call<TaskBean.DataBean> call, Response<TaskBean.DataBean> response) {
//                // 更新数据
//                if (response.body().getResult().equals(Commondata.SUCCESS) &&
//                        response.body().getStatus() == 200) {
//                    // 加到数据库
//                    addToDB(response);
//                    listener.onSuccess();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<TaskBean.DataBean> call, Throwable t) {
//                Log.e("onFailure",t.toString());
//            }
//        });
    }

    @Override
    public void ApplyNoFlightTask(String type, final Callback.OnApplyTaskListener listener) {
        NetService netService = NetTask.generateRequest();
        retrofit2.Call<ResultStatusBean> employeeInfoCall = netService.applyNoFlightTask(type, Commondata.DEVICE_CODE);
        employeeInfoCall.enqueue(new retrofit2.Callback<ResultStatusBean>() {
            @Override
            public void onResponse(Call<ResultStatusBean> call, Response<ResultStatusBean> response) {
                // 在这里更新数据库
//                Log.e("miao",response.body().toString());
                if (response.body() != null && response.body().getResult().equals(Commondata.SUCCESS) &&
                        response.body().getStatus() == 200) {
                    listener.onSuccess();
                } else {
                    if (response.body() != null && response.body().getResult() != null)
//                        listener.onError(response.body().getResult());
                        listener.onError("申请非航班任务失败");
                    else
                        listener.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ResultStatusBean> call, Throwable t) {
                listener.onError(null);
            }
        });
    }

    private void saveToDB(Response<TaskBean> response) {
        TaskManager manager = TaskManager.getInstance(context);

        manager.deleteAll();

        // 保存数据库
        Log.e("TaskModelImpl", response.body().toString());
        // 大小
        int size = response.body().getData().size();

        for (int i = 0; i < size; i++) {
            TaskBean.DataBean bean = response.body().getData().get(i);
            manager.add(bean);
        }
    }

    private int addToDB(Response<SingleTaskBean> response) {
        TaskManager manager = TaskManager.getInstance(context);

        TaskBean.DataBean taskBean = response.body().getData();
        // 保存数据库
        Log.e("TaskModelImpl", response.body().toString());

        if (taskBean.getTaskInfo().getStatusCode().equals(Commondata.TASKNODE_WC)){
            return 0;
        }

        if (manager.containInfo(taskBean)) {
            return manager.update(taskBean);
        } else {
            return manager.add(taskBean);
        }
    }


}
