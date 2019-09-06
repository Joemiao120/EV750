package com.huidisen.ep750.net;

import com.google.gson.Gson;
import com.huidisen.ep750.utils.Commondata;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;

/**
 * Created by lovexiaov on 16/6/4.
 */
public class FileUploader {

    /**
     * @param filePath 文件全路径
     */
    public static void uploadFile(String filePath, final String fileCurrentDay) {

        final String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
        final File file = new File(filePath);

        String url = String.format("%smobileLog/uploadVehicleErrLog.do", Commondata.HTTP_IP);
        Logger.i(url);
        OkHttpUtils.post().url(url)
                .addFile("logFile", fileName, new File(filePath))
                .addParams("vehicleId", Commondata.DEVICE_CODE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Logger.e("FileUploadError: %s", e.getMessage());
                    }

                    @Override
                    public void onResponse(String result, int id) {
                        Logger.i("UploadFileSuccess: %s", result);
                        Gson gson = new Gson();
                        FileUploadResult fileUploadResult = gson.fromJson(result, FileUploadResult.class);
                        if (fileUploadResult.status == 200) {
                            Logger.d("文件上传成功，删除本地文件！");
                            if (!fileName.equals(fileCurrentDay)) {
                                file.delete();
                            }

                        }
                    }
                });

    }


    private static class FileUploadResult {
        String result;
        int status;
    }
}
