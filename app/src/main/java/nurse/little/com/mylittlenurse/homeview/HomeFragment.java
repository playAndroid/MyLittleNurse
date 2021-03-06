package nurse.little.com.mylittlenurse.homeview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import nurse.little.com.mylittlenurse.DaoSession;
import nurse.little.com.mylittlenurse.DiaylsDate;
import nurse.little.com.mylittlenurse.DiaylsDateDao;
import nurse.little.com.mylittlenurse.R;
import nurse.little.com.mylittlenurse.SickPeople;
import nurse.little.com.mylittlenurse.SickPeopleDao;
import nurse.little.com.mylittlenurse.bast.NurseApplication;
import nurse.little.com.mylittlenurse.bast.TableName;
import nurse.little.com.mylittlenurse.utils.ShowToastUtils;
import nurse.little.com.mylittlenurse.utils.Utils;
import nurse.little.com.mylittlenurse.view.CalendarCard;
import nurse.little.com.mylittlenurse.view.CustomDate;

/**
 * 主页Fragment
 * Created by user on 2016/3/15.
 */
public class HomeFragment extends Fragment {
    /**
     * 日历模块添加到这个线性布局里面
     */
    private LinearLayout cal_week;

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
    /**
     * 已安排
     */
    private ListView recycler_yianpai;
    /**
     * 重新安排
     */
    private ListView recycler_weianpai;
    private SickPeopleDao sickPeopleDao;

    /**
     * 重新安排按下的position
     */
    private ArrayList<String> upDateNum = new ArrayList<>();
    private DiaylsDateDao diaylsDateDao;
    private ArrayList<String> names;
    private DiaylsDate diaylsDate;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                save();
                break;
            case R.id.action_delete:
                delete();
                break;
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_data_layout, container, false);
        cal_week = (LinearLayout) view.findViewById(R.id.cal_week);
        recycler_yianpai = (ListView) view.findViewById(R.id.listview_content);
        recycler_weianpai = (ListView) view.findViewById(R.id.bo_listview);
        return view;
    }

    /**
     * 删除数据
     */
    public void delete() {
        if (null != diaylsDate) {
            diaylsDateDao.delete(diaylsDate);
            initRecyclerYi();
            calendarCard.changeState(fillDataList());
            ShowToastUtils.Short("已删除");
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DaoSession daoSession = NurseApplication.getDaoSession(getContext(), TableName.DB_SICK_NAME);
        sickPeopleDao = daoSession.getSickPeopleDao();
        diaylsDateDao = daoSession.getDiaylsDateDao();
        initCalendar();
        initRecyclerWei();
        initRecyclerYi();
    }

    /**
     * 已安排
     */
    private void initRecyclerYi() {
        QueryBuilder<DiaylsDate> query = diaylsDateDao.queryBuilder()
                .where(DiaylsDateDao.Properties.Date
                        .eq(customDate.year + "" + customDate.month + "" + (customDate.day < 10 ? "0" + customDate.day : customDate.day)));
        if (query != null && query.list().size() > 0) {
            List<DiaylsDate> list = query.list();
            diaylsDate = list.get(0);
            String name = list.get(0).getName();
            if (!TextUtils.isEmpty(name)) {
                String[] split = name.split("-");
                AttCurrentAdapter adapter = new AttCurrentAdapter(getContext(), split);
                recycler_yianpai.setAdapter(adapter);
                Utils.setListViewHeightBasedOnChildren(recycler_yianpai);
            }
        } else {
            String[] split = new String[]{"未安排患者"};
            AttCurrentAdapter adapter = new AttCurrentAdapter(getContext(), split);
            recycler_yianpai.setAdapter(adapter);
            Utils.setListViewHeightBasedOnChildren(recycler_yianpai);
        }

    }

    /**
     * 初始化未安排
     */
    private void initRecyclerWei() {
        List<SickPeople> sickPeoples = sickPeopleDao.loadAll();
        if (sickPeoples != null && sickPeoples.isEmpty()) {
            names = new ArrayList<>();
            for (int i = 0; i < sickPeoples.size(); i++) {
                names.add(sickPeoples.get(i).getName());
            }
            AttDataAdapter adapter = new AttDataAdapter(getContext(), names, new AttDataAdapter.OnTextClickListener() {
                @Override
                public void clickData(ArrayList<String> sList) {
                    if (shcList != null) {
                        upDateNum.clear();
                        for (int i = 0; i < sList.size(); i++) {
                            Logger.e("clickData" + sList.get(i));
                            upDateNum.add(names.get(Integer.parseInt(sList.get(i))));
                        }
                    }
                }
            });
            recycler_weianpai.setAdapter(adapter);
            Utils.setListViewHeightBasedOnChildren(recycler_weianpai);
        }
    }

    private void initCalendar() {
        shcList = fillDataList();
        customDate = new CustomDate();
        calendarCard = new CalendarCard(getContext(), new CalendarCard.OnCellClickListener() {


            @Override
            public void clickDate(CustomDate date) {
                Logger.e(date.year + "年" + date.month + "月" + date.day + "日");
                //存年月日 查也按年月日查
                customDate = date;
                initRecyclerYi();
            }
        }, shcList, customDate);
        cal_week.addView(calendarCard);
    }

    /**
     * 填充数据到shcList
     */
    private ArrayList<Integer> fillDataList() {
        ArrayList<Integer> shc = new ArrayList<>();
        List<DiaylsDate> diaylsDates = diaylsDateDao.loadAll();
        for (int i = 0; i < diaylsDates.size(); i++) {
            String date = diaylsDates.get(i).getDate();
            shc.add(Integer.parseInt(date.substring(date.length() - 2)));
        }
        return shc;
    }


    public void save() {
        if (upDateNum != null && !upDateNum.isEmpty()) {
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < upDateNum.size(); i++) {
                String s = upDateNum.get(i);
                if (i == upDateNum.size() - 1) {
                    buffer.append(s);
                } else {
                    buffer.append(s).append("-");
                }
            }
            DiaylsDate diaylsDate = new DiaylsDate();
            diaylsDate.setName(buffer.toString());
            diaylsDate.setDate(customDate.year + "" + customDate.month + "" + (customDate.day < 10 ? "0" + customDate.day : customDate.day));

            /**
             * 查询是否存在 ,存在则删除
             */
            QueryBuilder<DiaylsDate> query = diaylsDateDao.queryBuilder()
                    .where(DiaylsDateDao.Properties.Date
                            .eq(customDate.year + "" + customDate.month + "" + (customDate.day < 10 ? "0" + customDate.day : customDate.day)));
            if (query.list().size() > 0) {
                diaylsDateDao.deleteByKey(query.list().get(0).getId());
            }
            diaylsDateDao.insert(diaylsDate);
            ShowToastUtils.Short("保存成功");
        } else {
            ShowToastUtils.Short("未录入或选择病人信息");
        }
//        initRecyclerWei();
        initRecyclerYi();
        calendarCard.changeState(fillDataList());

    }
}
