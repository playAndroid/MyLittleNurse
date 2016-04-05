package nurse.little.com.mylittlenurse.broadcastmess;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import nurse.little.com.mylittlenurse.DaoSession;
import nurse.little.com.mylittlenurse.R;
import nurse.little.com.mylittlenurse.XuanJiao;
import nurse.little.com.mylittlenurse.XuanJiaoDao;
import nurse.little.com.mylittlenurse.bast.BaseFragment;
import nurse.little.com.mylittlenurse.bast.NurseApplication;
import nurse.little.com.mylittlenurse.bast.TableName;
import nurse.little.com.mylittlenurse.utils.SPUtils;

/**
 * 宣传教育界面
 * Created by user on 2016/3/15.
 */
public class BroadCatmessFragment extends BaseFragment {

    private View addition;
    private PopupWindow popupWindow;
    private Context context;
    private static final String ENTER_XUCHUAN = "xuanchuan";
    private XuanJiaoDao xuanJiaoDao;
    private LinearLayout linear_layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_broadcast, container, false);
        linear_layout = (LinearLayout) view.findViewById(R.id.linear_layout);
        addition = view.findViewById(R.id.addition);
        context = getContext();
        DaoSession daoSession = NurseApplication.getDaoSession(context, TableName.DB_SICK_NAME);
        xuanJiaoDao = daoSession.getXuanJiaoDao();
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerListener();
    }

    private void registerListener() {
        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = View.inflate(getContext(), R.layout.popup_keep_book, null);
//                view.getParent().getc
                final EditText luruneirong = (EditText) view.findViewById(R.id.luruneirong);
                luruneirong.setHint("输入宣教内容");

                String stringHuayan = (String) SPUtils.get(context, ENTER_XUCHUAN, "");
                if (!TextUtils.isEmpty(stringHuayan)) {
                    luruneirong.setText(stringHuayan);
                }
                View queren = view.findViewById(R.id.queren);
                View quxiao = view.findViewById(R.id.quxiao);
                showPopupWindow(view);
                queren.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String bingShi = luruneirong.getText().toString().trim();
                        SPUtils.put(context, ENTER_XUCHUAN, bingShi);
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        addItemOnBroadCatmess();
                    }
                });
                quxiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }
                });
            }
        });
    }

    /**
     * 添加数据到数据库并刷新此界面ListView
     */
    private void addItemOnBroadCatmess() {
        String bstr = (String) SPUtils.get(context, ENTER_XUCHUAN, "未添加宣教记录");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String format = sdf.format(date);
        XuanJiao xuanJiao = new XuanJiao();
        xuanJiao.setContent(bstr);
        xuanJiao.setTime(format);
        xuanJiaoDao.insert(xuanJiao);
        refreshListView();
    }

    /**
     * 刷新ListView
     */
    private void refreshListView() {
        List<XuanJiao> xuanJiaos = xuanJiaoDao.loadAll();
        if (xuanJiaos != null && xuanJiaos.size() > 0) {
            linear_layout.removeAllViews();
            for (int i = 0; i < xuanJiaos.size(); i++) {
                TextView textView = new TextView(context);
                textView.setTextColor(Color.parseColor("#333333"));
                textView.setText(xuanJiaos.get(i).getContent());
                textView.setTextSize(14);
                linear_layout.addView(textView);
            }
            linear_layout.requestLayout();
        }


    }

    private void showPopupWindow(View view) {
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }

                    return true;
                }
                return false;
            }
        });
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindow.showAtLocation(addition, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                backgroundAlpha(1.0f);
            }
        });
        backgroundAlpha(0.5f);

    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }
}
