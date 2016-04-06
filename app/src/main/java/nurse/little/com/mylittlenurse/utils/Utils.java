package nurse.little.com.mylittlenurse.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by user on 2016/3/16.
 */
public class Utils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * * 动态设置ListView的高度
     *
     * * @param listView
     *
     *
     */

    public static void setListViewHeightBasedOnChildren(ListView listView) {

        if (listView == null)
            return;

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {

            // pre-condition

            return;

        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {

            View listItem = listAdapter.getView(i, null, listView);

            listItem.measure(0, 0);

            totalHeight += listItem.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);

    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     *            要隐藏软键盘的activity
     */
    public static void hideSoftKeyBoard(Activity activity) {
        final View v = activity.getWindow().peekDecorView();
        if (v != null && v.getWindowToken() != null) {
            try {
                ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示软键盘
     *
     * @param context
     * @param editText
     */
    public static void showSoftKeyBroad(Context context, EditText editText) {
        InputMethodManager mgr = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // only will trigger it if no physical keyboard is open
        mgr.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }
}
