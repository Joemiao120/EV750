package com.huidisen.ep750.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.webkit.DownloadListener;

import com.huidisen.ep750.activity.SplashActivity;
import com.huidisen.ep750.bean.UpdateInfoBean;
import com.huidisen.ep750.net.NetTask;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lovexiaov on 16/7/28.
 */
public class AppManageUtil {
    private SplashActivity context;
    Call<UpdateInfoBean> checkUpdateRequest = NetTask.generateRequest().checkUpdate();
    RequestCall downloadRequest;

    public AppManageUtil(SplashActivity context) {
        this.context = context;
    }

    public void checkNewVersion() {
        checkUpdateRequest.enqueue(new Callback<UpdateInfoBean>() {
            @Override
            public void onResponse(Call<UpdateInfoBean> call, Response<UpdateInfoBean> response) {
                UpdateInfoBean updateInfo = response.body();
                Logger.t(AppManageUtil.class.getSimpleName()).d(updateInfo.toString());
                if (getVersionCode(context) < Integer.valueOf(updateInfo.code)) {
//                    EventBus.getDefault().post(updateInfo);
                    Logger.d("Start download APK!");
                    downloadAndInstallAPK(updateInfo.updateUrl);
                }
            }

            @Override
            public void onFailure(Call<UpdateInfoBean> call, Throwable t) {
                Logger.t("update").e(t.getLocalizedMessage());
            }
        });

    }

    public void downloadAndInstallAPK(String url) {
        // TODO
        downloadRequest = OkHttpUtils.get().url(url).build();
        downloadRequest
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "ev750.apk") {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        Logger.t(AppManageUtil.class.getSimpleName())
                                .e("Update App Error: %s", e.getLocalizedMessage());
                        DialogUtil.dismissDialog();
                        ActivityContainer.getInstance().finishAllActivity();
                        context.finish();
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        Logger.d("Download APK success.");
                        DialogUtil.dismissDialog();
                        ActivityContainer.getInstance().finishAllActivity();
                        installAPK(context, response.getAbsolutePath());
                        context.finish();

                    }


                });
    }

    private static void installAPK(Context context, String filePath) {
        Logger.d("Start install APK.");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
        context.startActivity(intent);

        ActivityContainer.getInstance().finishAllActivity();
    }

    public void cancel() {
        checkUpdateRequest.cancel();

        if (downloadRequest != null)
            downloadRequest.cancel();

    }

    private int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
