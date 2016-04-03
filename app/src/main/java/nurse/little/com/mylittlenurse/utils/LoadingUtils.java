package nurse.little.com.mylittlenurse.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * progress_dialog
 * Created by user on 2016/3/11.
 */
public class LoadingUtils {

    private static ProgressDialog progressDialog;

    public static void showProgressDialog(Context context, String str) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setMessage(str);
        progressDialog.show();
    }

    public static void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
