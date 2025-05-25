package cn.gxust.project.Utils;

import android.app.AlertDialog;
import android.content.Context;

public class DialogUtils {

    public static void showConfirmDialog(String title,
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
                    dialog.dismiss();
                })
                .setCancelable(false)
                .show();
    }

    public static void showConfirmDialogWithCancel(String title,
                                                   String message,
                                                   String cancelButtonText,
                                                   String confirmButtonText,
                                                   Context context,
                                                   Runnable onCancel,
                                                   Runnable onConfirm) {

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(cancelButtonText, (dialog, which) -> {
                    if (onCancel != null) {
                        onCancel.run();
                    }
                    dialog.dismiss();
                })
                .setPositiveButton(confirmButtonText, (dialog, which) -> {
                    if (onConfirm != null) {
                        onConfirm.run();
                    }
                    dialog.dismiss();
                })
                .show();

    }
}
