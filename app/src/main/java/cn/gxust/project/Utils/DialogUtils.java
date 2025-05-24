package cn.gxust.project.Utils;

import android.app.AlertDialog;
import android.content.Context;

public class DialogUtils {

    public static void showDialog(String title,
                                  String message,
                                  String positiveButtonText,
                                  Context context,
                                  Runnable onConfirm) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, (dialog, which) -> {
                    if (onConfirm != null) {
                        onConfirm.run();
                    }
                })
                .setCancelable(false)
                .show();
    }
}
