package com.huidisen.ep750.utils;

import android.content.Context;
import android.graphics.Color;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by lovexiaov on 16/7/5.
 */
public class DialogUtil {

    public static KProgressHUD dialog;

    public static void showDialog(Context context, String info) {
        dismissDialog();

        DialogUtil.dialog = KProgressHUD.create(context).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(info).setCancellable(true).setAnimationSpeed(2).setWindowColor(Color.GRAY)
                .setDimAmount(0.5f);

        DialogUtil.dialog.show();
    }

    public static void showDialogUnCancellable(Context context, String info) {
        dismissDialog();

        DialogUtil.dialog = KProgressHUD.create(context).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(info).setCancellable(false).setAnimationSpeed(2).setWindowColor(Color.GRAY)
                .setDimAmount(0.5f);

        DialogUtil.dialog.show();
    }

    public static void dismissDialog() {
        if (DialogUtil.dialog != null && DialogUtil.dialog.isShowing()) {
            DialogUtil.dialog.dismiss();
        }
        DialogUtil.dialog = null;
    }
}
