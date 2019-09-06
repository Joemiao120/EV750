package com.huidisen.ep750.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.huidisen.ep750.R;
import com.huidisen.ep750.bean.ResultStatusBean;
import com.huidisen.ep750.bean.VehicleInfoBean;
import com.huidisen.ep750.net.FileUploader;
import com.huidisen.ep750.net.NetService;
import com.huidisen.ep750.net.NetTask;
import com.huidisen.ep750.service.BackgroundLoadDataService;
import com.huidisen.ep750.service.PollingDeviceService;
import com.huidisen.ep750.utils.ActivityContainer;
import com.huidisen.ep750.utils.AppManageUtil;
import com.huidisen.ep750.utils.Commondata;
import com.huidisen.ep750.utils.MD5Util;
import com.huidisen.ep750.utils.ObjectSaveUtil;
import com.huidisen.ep750.utils.SPUtils;
import com.huidisen.ep750.utils.SoundPoolUtils;
import com.huidisen.ep750.utils.ToastUtils;
import com.huidisen.ep750.utils.XMLUtils;
import com.huidisen.ep750.view.CustomDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by miaoyichao on 16/6/2.
 */
public class SplashActivity extends Activity {
    private NetService netService;
    private static final String FILE_NAME = "task_def.xml";
    private static final int SUCCESS = 1;
    private static final int FAIL = 2;
    private static boolean isFisrt = true;

    private static final String FILE_PATH = Environment.getExternalStorageDirectory() +
            File.separator + FILE_NAME;


    private long startTime = 0;
    LTEReceiver receiver;

    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String md5 = null;
    private String versionName;
    private String versionCode;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    compateMD5();
                    break;
                case FAIL:
                    ToastUtils.showShort(SplashActivity.this, "网络异常");
                    finish();
                    break;
            }
        }
    };


    private Thread thread = new Thread() {
        @Override
        public void run() {
            while (!isConnect) {
                // 判断时间，如果超过1分钟还入不了网的话就直接退出应用
                if (System.currentTimeMillis() - startTime >= 2 * 1000) {
                    // 打印提醒
                    handler.sendEmptyMessage(FAIL);
                    return;
                }
            }

            getCarInfo();
            uploadCrashFile();
        }
    };

    private AppManageUtil appManageUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        startTime = System.currentTimeMillis();
//        receiver = new LTEReceiver();
//        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
//        registerReceiver(receiver, filter);

//        appManageUtil = new AppManageUtil(this);
//        appManageUtil.checkNewVersion();

//        ToastUtils.showShort(this, Commondata.DEVICE_CODE);

        netService = NetTask.generateRequest();
        SoundPoolUtils.playSound(this, 1, SoundPoolUtils.WELCOME);
        ActivityContainer.getInstance().addActivity(this);

        isFisrt = true;
    }


    private boolean isConnect = false;

    @Override
    protected void onResume() {
        super.onResume();

        if (!isFisrt) {
            return;
        }

        // 硬件交互
        Intent loadDataService = new Intent(SplashActivity.this, BackgroundLoadDataService.class);
        stopService(loadDataService);
        startService(loadDataService);

        // 检查设备
        Intent checkDeviceIntent = new Intent(SplashActivity.this, PollingDeviceService.class);
        stopService(checkDeviceIntent);
        startService(checkDeviceIntent);

        getCarInfo();
        uploadCrashFile();


        // 判断开机
//        int number = (int) SPUtils.get(this, Commondata.NUMBER_BOOT_COMPLETED, 1);
//        if (number == 1) {
//            thread.start();
//        } else {
//            getCarInfo();
//            uploadCrashFile();
//        }
    }


    private void compateMD5() {
        String md5 = (String) SPUtils.get(this, Commondata.MD5, "");
        if (md5.equals("")) {
            downloadFile(Commondata.DOWNLOAD_FILE);
        } else {
            retrofit2.Call<ResultStatusBean> result = netService.compareMD5(md5);
            result.enqueue(new Callback<ResultStatusBean>() {
                @Override
                public void onResponse(Call<ResultStatusBean> call, Response<ResultStatusBean> response) {
                    if (response.body() != null && response.body().getStatus() == 200 &&
                            response.body().getResult().equals(Commondata.SUCCESS)) {
                        if (!response.body().isData())
                            downloadFile(Commondata.DOWNLOAD_FILE);
                        else {
                            enterMainActivity();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResultStatusBean> call, Throwable t) {

                }
            });
        }
    }


    /**
     * 获取车辆信息
     */
    private void getCarInfo() {
        getVersionName();

        retrofit2.Call<VehicleInfoBean> flightInfoCall = netService.getCarInfo(Commondata.DEVICE_CODE, versionName, versionCode);
        flightInfoCall.enqueue(new Callback<VehicleInfoBean>() {
            @Override
            public void onResponse(Call<VehicleInfoBean> call, Response<VehicleInfoBean> response) {
                if (response.body() != null &&
                        response.body().getStatus() == 200 && response.body().getResult().equals(Commondata.SUCCESS)) {
                    // 保存车辆信息
                    ObjectSaveUtil.saveObject(SplashActivity.this, response.body().getData(), Commondata.CAR_INFO);
                    // 保存接受的任务类型
                    SPUtils.put(SplashActivity.this, Commondata.IS_FLIGHT, response.body().getData().isFlightDep());
                    // 车辆的类型,供非航班车辆申请任务使用
                    SPUtils.put(SplashActivity.this, Commondata.CAR_DEF, response.body().getData().getExcuteTaskType().get(0));
                    Log.e("SplashActivity", response.body().getData().getCarType());
                    // 保存部门id
                    SPUtils.put(SplashActivity.this, Commondata.DEP_ID, response.body().getData().getDepId());

                    handler.sendEmptyMessage(SUCCESS);

                } else {
                    handler.sendEmptyMessage(FAIL);
                }
            }

            /**
             * huoqu 车辆信息失败
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<VehicleInfoBean> call, Throwable t) {
                handler.sendEmptyMessage(FAIL);
            }
        });
    }

    private void getVersionName() {

        try {
            // 获取packagemanager的实例
            PackageManager packageManager = getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = null;
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionName = packInfo.versionName;
            versionCode = String.valueOf(packInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            versionCode = "";
            versionName = "";
        }
    }

    private void enterMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 下载xml文件
     *
     * @param fileUrl
     */
    private void downloadFile(final String fileUrl) {
        final File file = new File(FILE_PATH);
        retrofit2.Call<ResponseBody> call = netService.download(fileUrl);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.e("Download", "server contacted and has file");
                    new AsyncTask<Void, Long, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Void... voids) {
                            boolean writtenToDisk = writeResponseBodyToDisk(response.body(), file);
                            Log.e("Download", "file download was a success? " + writtenToDisk);

                            if (writtenToDisk) {
                                try {
                                    md5 = MD5Util.getFileMD5String(file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                SPUtils.put(SplashActivity.this, Commondata.MD5, md5);

                                // 比较MD5值,看看是否下载完成
                                compateMD5();
                                // 解析xml文件并保存到本地
                                XMLUtils.parseXML(SplashActivity.this, Environment.getExternalStorageDirectory() + File.separator + FILE_NAME);
                            }

                            return writtenToDisk;
                        }

                        @Override
                        protected void onPostExecute(Boolean aBoolean) {
                            // 写入失败，就解析默认的
                            if (!aBoolean) {
                                XMLUtils.parseAssetsXML(SplashActivity.this);
                            }

                            enterMainActivity();
                        }
                    }.execute();

                } else {
                    Log.e("Download", "server contact failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // 第一次下载失败的时候进去解析本地内置文件
                if ((int) SPUtils.get(SplashActivity.this, Commondata.NUMBER_LOGIN, 0) == 0) {
                    XMLUtils.parseAssetsXML(SplashActivity.this);
                }
                enterMainActivity();
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, File futureStudioIconFile) {
        try {

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.w("saveFile", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    private void showErrorDialog(String error) {
        new CustomDialog(this, error).show();
    }

    /**
     * 上传log文件
     */
    private void uploadCrashFile() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String time = formatter.format(new Date());
                final String fileName = "crash-" + time + ".log";

                File fileDirectory = new File(Commondata.CRASH_HANDLER_DIR);

                // 遍历文件夹
                if (fileDirectory.exists()) {
                    File[] files = fileDirectory.listFiles();
                    if (files.length > 0) {
                        for (File file : files) {
                            // 上传并删除
                            FileUploader.uploadFile(Commondata.CRASH_HANDLER_DIR + file.getName(), fileName);
                        }
                    }
                }
            }
        }).start();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            appManageUtil.cancel();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onPause() {
        super.onPause();
        isFisrt = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (receiver != null) {
//            unregisterReceiver(receiver);
//        }

        ActivityContainer.getInstance().removeActivity(this);
    }


    public class LTEReceiver extends BroadcastReceiver {
        String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
        int TYPE_LTE = 7;

        @Override
        public void onReceive(Context context, Intent intent) {

            if (CONNECTIVITY_ACTION.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

                NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                if ((info != null) && (info.getType() == TYPE_LTE) && info.isConnected()) {
                    Toast.makeText(context, "LTE 网络已连接", Toast.LENGTH_LONG).show();
//                    isConnect = true;
                }
            }
        }
    }

}
