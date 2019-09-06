package com.huidisen.ep750.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lovexiaov on 16/7/28.
 */
public class UpdateInfoBean {

    /**
     * code : 2.1
     * desc : 有更新，请下载
     * update_url : http://101.200.216.128:8080/HDS_EV750app/xxx.apk
     */

    @SerializedName("code") public String code;
    @SerializedName("desc") public String desc;
    @SerializedName("update_url") public String updateUrl;

    @Override
    public String toString() {
        return "UpdateInfoBean{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", updateUrl='" + updateUrl + '\'' +
                '}';
    }
}
