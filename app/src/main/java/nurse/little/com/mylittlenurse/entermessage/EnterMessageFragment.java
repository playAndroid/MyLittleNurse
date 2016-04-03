package nurse.little.com.mylittlenurse.entermessage;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import nurse.little.com.mylittlenurse.DaoSession;
import nurse.little.com.mylittlenurse.R;
import nurse.little.com.mylittlenurse.SickPeople;
import nurse.little.com.mylittlenurse.SickPeopleDao;
import nurse.little.com.mylittlenurse.bast.BaseFragment;
import nurse.little.com.mylittlenurse.bast.NurseApplication;
import nurse.little.com.mylittlenurse.bast.TableName;
import nurse.little.com.mylittlenurse.utils.SPUtils;
import nurse.little.com.mylittlenurse.utils.ShowToastUtils;
import nurse.little.com.mylittlenurse.view.CalendarCard;
import nurse.little.com.mylittlenurse.view.CustomDate;
import nurse.little.com.mylittlenurse.view.nicespinner.NiceSpinner;

/**
 * 录入信息界面
 * Created by user on 2016/3/15.
 */
public class EnterMessageFragment extends BaseFragment {


    /**
     * 性别
     */
    private NiceSpinner niceSpinner;
    /**
     * 静脉
     */
    private NiceSpinner jingmaizu;
    //数据库
    DaoSession daoSession;
    SickPeopleDao sickPeopleDao;

    private TextView xingming;//姓名
    private TextView nianLing;//年龄
    private TextView shenGao;//身高
    private TextView tiZhong;//体重
    private TextView dianHua;//电话
    private RelativeLayout luruhuayan;//录入化验单
    private RelativeLayout luruyingyang;//录入营养
    private Button tiJiao;//提交
    private List<String> dataset;
    private List<String> dataset1;
    private String xingbie = "男";//0 男 1 女
    private String jingmai = "其他";//0 其他
    //-----------------字段
    private String xingmingStr;
    private String nianLingStr;
    private String shenGaoStr;
    private String tiZhongStr;
    private String dianhuaStr;
    private OnUpDataListener onUpDataListener;//提交成功监听
    //    private RelativeLayout rl_week_riqi;
    private LinearLayout week_popup;


    /**
     * 日历月份里的含有排班的天数
     */
    private ArrayList<Integer> shcList;
    /**
     * 日历的元素
     */
    private CustomDate customDate;
    /**
     * 自定义日历
     */
    private CalendarCard calendarCard;
    private View quxiao;
    private View queren;
    private PopupWindow popupWindow;
    //    private RelativeLayout lurubingshi;
    private Context context;
    //    private SharedPreferences sp;
//    private SharedPreferences.Editor edit;
    private static final String ENTER_YINGYANG = "yingyang";
    private static final String ENTER_HUAYAN = "huayan";
    private StringBuffer setDataTimeList;//存储按下日历的集合
    private ScrollView scroll_view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i("onCreate执行了");
        context = getContext();
//        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
//        edit = sp.edit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.i("onCreateView执行了");
        View view = inflater.inflate(R.layout.activity_enter_message, container, false);
        niceSpinner = (NiceSpinner) view.findViewById(R.id.nice_spinner);
        jingmaizu = (NiceSpinner) view.findViewById(R.id.jingmaizu);
        xingming = (TextView) view.findViewById(R.id.xin_counter);
        nianLing = (TextView) view.findViewById(R.id.nianling_counter);
        shenGao = (TextView) view.findViewById(R.id.shengao_counter);
        tiZhong = (TextView) view.findViewById(R.id.tizhong_counter);
        dianHua = (TextView) view.findViewById(R.id.dianhuazu_counter);
        luruhuayan = (RelativeLayout) view.findViewById(R.id.luruhuayan);
        luruyingyang = (RelativeLayout) view.findViewById(R.id.luruyingyang);
        tiJiao = (Button) view.findViewById(R.id.updata);
        scroll_view = (ScrollView) view.findViewById(R.id.scroll_view);
//        lurubingshi = (RelativeLayout) view.findViewById(R.id.lurubingshi);

//        rl_week_riqi = (RelativeLayout) view.findViewById(R.id.rl_week_riqi);

        //获取数据库的操作
        daoSession = NurseApplication.getDaoSession(getContext(), TableName.DB_SICK_NAME);
        sickPeopleDao = daoSession.getSickPeopleDao();
//        QueryBuilder<SickPeople> sickPeopleQueryBuilder = sickPeopleDao.queryBuilder();
//        QueryBuilder<SickPeople> where = sickPeopleQueryBuilder.where(SickPeopleDao.Properties.Id.eq(3));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.i("onActivityCreated执行了");
        initSpinner();
        registerListener();
    }

    private void registerListener() {
        tiJiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAllData()) {
                    /**
                     * 插入数据
                     */
                    insertDataFromPage();
                } else {
                    Logger.e("姓名,身高,体重,电话不能为空");
                    ShowToastUtils.Short("姓名,身高,体重,电话不能为空");
                }
            }
        });


        /**
         * //在PopupWindow里面就加上下面代码，让键盘弹出时，不会挡住pop窗口。
         popWin.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
         popWin.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
         //点击空白处时，隐藏掉pop窗口
         popWin.setFocusable(true);
         */

//        rl_week_riqi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /**
//                 * 弹出日历
//                 */
//                View view = View.inflate(getContext(), R.layout.popup_calendar, null);
//                quxiao = view.findViewById(R.id.quxiao);
//                queren = view.findViewById(R.id.queren);
//                shcList = new ArrayList<>();
//                customDate = new CustomDate();
//                setDataTimeList = new StringBuffer();
//                week_popup = (LinearLayout) view.findViewById(R.id.week_popup);
//                CalendarCard calendarCard = new CalendarCard(getContext(), new CalendarCard.OnCellClickListener() {
//                    @Override
//                    public void clickDate(CustomDate date) {
////                        calendarCard.gett
//                        Logger.e(date.year + "年" + date.month + "月" + date.day + "日");
//                        setDataTimeList.append(date.year + "年" + date.month + "月" + date.day + "日");
//                    }
//                }, shcList, customDate);
//                week_popup.addView(calendarCard);
//                showPopupWindow(view);
////                registerPopupWionBtn();
//                quxiao.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        popupWindow.dismiss();
//                    }
//                });
//
//                queren.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        edit.putString(ENTER_DATA_TIME, setDataTimeList.toString());
//                        edit.commit();
//                        Logger.e(sp.getString(ENTER_DATA_TIME, "-1"));
//                    }
//                });
//            }
//        });

        luruhuayan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = View.inflate(getContext(), R.layout.popup_keep_book, null);
                final EditText luruneirong = (EditText) view.findViewById(R.id.luruneirong);
                luruneirong.setHint("输入化验单内容");

                String stringHuayan = (String) SPUtils.get(getContext(), ENTER_HUAYAN, "");
                if (!TextUtils.isEmpty(stringHuayan)) {
                    luruneirong.setText(stringHuayan);
                }
//                luruneirong.setHint(string);
                View queren = view.findViewById(R.id.queren);
                View quxiao = view.findViewById(R.id.quxiao);
                showPopupWindow(view);
                queren.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String huayan = luruneirong.getText().toString().trim();
                        SPUtils.put(context, ENTER_HUAYAN, huayan);
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
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

        luruyingyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = View.inflate(getContext(), R.layout.popup_keep_book, null);
//                view.getParent().getc
                final EditText luruneirong = (EditText) view.findViewById(R.id.luruneirong);
                luruneirong.setHint("输入营养内容");

                String stringHuayan = (String) SPUtils.get(context, ENTER_YINGYANG, "");
                if (!TextUtils.isEmpty(stringHuayan)) {
                    luruneirong.setText(stringHuayan);
                }
//                luruneirong.setHint(string);
                View queren = view.findViewById(R.id.queren);
                View quxiao = view.findViewById(R.id.quxiao);
                showPopupWindow(view);
                queren.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String huayan = luruneirong.getText().toString().trim();
                        SPUtils.put(context, ENTER_YINGYANG, huayan);
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
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
//        lurubingshi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                View view = View.inflate(getContext(), R.layout.popop_sickhistory, null);
////                showPopupWindow(view);
////                new SlideDateTimePicker.Builder(getFragmentManager())
////                        .setListener(listener)
////                        .setInitialDate(new Date())
////                        .build()
////                        .show();
//                Intent intent = new Intent(getContext(), SickHistoryActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void showPopupWindow(View view) {
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
//                popupWindow.setContentView(view);
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
        popupWindow.showAtLocation(scroll_view, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                backgroundAlpha(1.0f);
            }
        });
        backgroundAlpha(0.5f);

    }

    private boolean checkAllData() {
        xingmingStr = xingming.getText().toString();
        nianLingStr = nianLing.getText().toString();
        shenGaoStr = shenGao.getText().toString();
        tiZhongStr = tiZhong.getText().toString();
        dianhuaStr = dianHua.getText().toString();
        if (TextUtils.isEmpty(xingmingStr) || TextUtils.isEmpty(nianLingStr)
                || TextUtils.isEmpty(shenGaoStr) || TextUtils.isEmpty(tiZhongStr)
                || TextUtils.isEmpty(dianhuaStr)) {
            return false;
        }

//        if (xingmingStr.equals(sickPeopleDao.queryRaw("SickPeopleDao.Properties.Name = ?", xingmingStr))) {
//            ShowToastUtils.Short("姓名已存在");
//            Logger.e(String.valueOf(sickPeopleDao.queryRaw("NAME = ?", xingmingStr)));
//            return false;
//        }

        return true;
    }

    /**
     * 初始化并设置Spinner
     */
    private void initSpinner() {
        dataset = new LinkedList<>(Arrays.asList("男", "女"));
        niceSpinner.attachDataSource(dataset);
        niceSpinner.setBackgroundColor(Color.WHITE);
        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "" + dataset.get(position), Toast.LENGTH_SHORT).show();
                xingbie = dataset.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                Toast.makeText(getContext(), "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });
        dataset1 = new LinkedList<>(Arrays.asList("其他", "AVF", "AVG", "CUC"));
        jingmaizu.attachDataSource(dataset1);
        jingmaizu.setBackgroundColor(Color.WHITE);
        jingmaizu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "" + dataset1.get(position), Toast.LENGTH_SHORT).show();
                jingmai = dataset1.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 插入数据
     */
    public void insertDataFromPage() {
        SickPeople people = new SickPeople();
        people.setName(xingmingStr);
        people.setAge(nianLingStr);
        people.setSex(xingbie);
        people.setHeight(shenGaoStr);
        people.setWeight(tiZhongStr);
        people.setTel(dianhuaStr);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormat.format(new Date());
        people.setTime(time);
        people.setMailou(jingmai);
        people.setHuayan((String) SPUtils.get(context, ENTER_HUAYAN, "未录入化验单"));
        people.setYingyang((String) SPUtils.get(context, ENTER_YINGYANG, "未录入营养单"));
        /**
         *
         this.id = id;
         this.name = name;
         this.age = age;
         this.sex = sex;
         this.height = height;
         this.weight = weight;
         this.tel = tel;
         this.time = time;
         this.mailou = mailou;
         this.huayan = huayan;
         this.yingyang = yingyang;
         */
        sickPeopleDao.insert(people);
        ShowToastUtils.Short("录入成功");
        if (onUpDataListener != null) {
            onUpDataListener.upDataSuccess();
        }


    }

    public void setOnUpDataListener(OnUpDataListener onUpDataListener) {
        this.onUpDataListener = onUpDataListener;
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

    private void setAnimation(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.i("onDestroy执行");
//        edit.putString("huayandan", "");
//        edit.commit();
//        if (edit != null) {
//            edit.clear();
//            edit.commit();
//        }
//        SPUtils.clear(context);
        SPUtils.remove(context, ENTER_HUAYAN);
        SPUtils.remove(context, ENTER_YINGYANG);
    }
}
