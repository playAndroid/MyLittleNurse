package nurse.little.com.mylittlenurse.broadcastmess;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

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
import nurse.little.com.mylittlenurse.broadcastmess.adapter.XuanJiaoAdapter;
import nurse.little.com.mylittlenurse.utils.ShowToastUtils;

/**
 * 宣传教育界面
 * Created by user on 2016/3/15.
 */
public class BroadCatmessFragment extends BaseFragment {

    //    private View addition;
    private PopupWindow popupWindow;
    private Context context;
    private static final String ENTER_XUCHUAN = "xuanchuan";
    private XuanJiaoDao xuanJiaoDao;
    private RecyclerView recycler_view;
    private SwipeRefreshLayout swipe_refresh;
    private XuanJiaoAdapter xuanJiaoAdapter;
    private View message_look_linear;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_xuan, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            save();
        }
        return true;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_broadcast, container, false);
        swipe_refresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        message_look_linear = view.findViewById(R.id.message_look_linear);

//        addition = view.findViewById(R.id.addition);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(context));
        recycler_view.setHasFixedSize(true);
        context = getActivity();
        DaoSession daoSession = NurseApplication.getDaoSession(context, TableName.DB_SICK_NAME);
        xuanJiaoDao = daoSession.getXuanJiaoDao();
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshListView();
        registerListener();
    }

    private void registerListener() {
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshListView();
            }
        });
    }

    /**
     * 添加数据到数据库并刷新此界面ListView
     */
    private void addItemOnBroadCatmess(String content) {
//        String bstr = (String) SPUtils.get(context, ENTER_XUCHUAN, "未添加宣教记录");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String format = sdf.format(date);
        XuanJiao xuanJiao = new XuanJiao();
        xuanJiao.setContent(content);
        xuanJiao.setTime(format);
        xuanJiaoDao.insert(xuanJiao);
        refreshListView();
    }

    /**
     * 刷新ListView
     */
    private void refreshListView() {
        List<XuanJiao> xuanJiaos = xuanJiaoDao.loadAll();
        if (xuanJiaos != null && !xuanJiaos.isEmpty()) {
            message_look_linear.setVisibility(View.GONE);
            xuanJiaoAdapter = new XuanJiaoAdapter(getContext(), xuanJiaos);
            recycler_view.setAdapter(xuanJiaoAdapter);
            xuanJiaoAdapter.setOnItemClickListener(new XuanJiaoAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, XuanJiao xuanJiao) {

                }

                @Override
                public void onItemLongClick(View view, int position, final XuanJiao xuanJiao) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("是否删除改条数据");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            xuanJiaoDao.delete(xuanJiao);
                            refreshListView();
                        }
                    });
                    builder.show();
                }
            });
        } else {
            message_look_linear.setVisibility(View.VISIBLE);
//            ShowToastUtils.Short("暂无宣教内容");
        }
        if (swipe_refresh.isRefreshing()) {
            swipe_refresh.setRefreshing(false);
        }

    }

    public void save() {
        View view = View.inflate(getContext(), R.layout.popup_keep_book, null);
//                view.getParent().getc
        final EditText luruneirong = (EditText) view.findViewById(R.id.luruneirong);
        luruneirong.setHint("输入宣教内容");
        View queren = view.findViewById(R.id.queren);
        View quxiao = view.findViewById(R.id.quxiao);
        showPopupWindow(view);
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xuanJiao = luruneirong.getText().toString().trim();
                if (!TextUtils.isEmpty(xuanJiao)) {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                    addItemOnBroadCatmess(xuanJiao);
                } else {
                    ShowToastUtils.Short("录入内容不能为空");
                }
            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                luruneirong.setText("");
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
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
        popupWindow.showAtLocation(swipe_refresh, Gravity.CENTER, 0, 0);
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
