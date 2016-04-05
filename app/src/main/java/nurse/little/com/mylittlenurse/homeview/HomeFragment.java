package nurse.little.com.mylittlenurse.homeview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

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
    public CustomDate nowDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_data_layout, container, false);
        cal_week = (LinearLayout) view.findViewById(R.id.cal_week);
        recycler_yianpai = (ListView) view.findViewById(R.id.listview_content);
        recycler_weianpai = (ListView) view.findViewById(R.id.bo_listview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DaoSession daoSession = NurseApplication.getDaoSession(getContext(), TableName.DB_SICK_NAME);
        sickPeopleDao = daoSession.getSickPeopleDao();
        diaylsDateDao = daoSession.getDiaylsDateDao();
        initCalendar();
        initRecyclerWei();
    }

    /**
     * 初始化未安排
     */
    private void initRecyclerWei() {
        List<SickPeople> sickPeoples = sickPeopleDao.loadAll();
        if (sickPeoples == null || sickPeoples.isEmpty()) {
            return;
        } else {
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
        shcList = new ArrayList<>();
        customDate = new CustomDate();
        List<DiaylsDate> diaylsDates = diaylsDateDao.loadAll();
        for (int i = 0; i < diaylsDates.size(); i++) {
            String date = diaylsDates.get(i).getDate();
//            try {
            shcList.add(Integer.parseInt(date.substring(date.length() - 2)));
//            } catch (Exception e) {
////                throw new IllegalFormatException("");
//            }

        }
        calendarCard = new CalendarCard(getContext(), new CalendarCard.OnCellClickListener() {


            @Override
            public void clickDate(CustomDate date) {
//                Toast.makeText(getContext(), date.day + "", Toast.LENGTH_SHORT).show();
                Logger.e(date.year + "年" + date.month + "月" + date.day + "日");
                //存年月日 查也按年月日查
                customDate = date;
            }
        }, shcList, customDate);
        cal_week.addView(calendarCard);
    }


    public void save() {
        if (upDateNum != null && !upDateNum.isEmpty()) {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < upDateNum.size(); i++) {
                String s = upDateNum.get(i);
                if (i == upDateNum.size() - 1) {
                    buffer.append(s);
                } else {
                    buffer.append(s + "-");
                }
            }
            DiaylsDate diaylsDate = new DiaylsDate();
            diaylsDate.setName(buffer.toString());
            diaylsDate.setDate(customDate.year + "" + customDate.month + "" + customDate.day);
//            QueryBuilder<DiaylsDate> qb = diaylsDateDao.queryBuilder();
//            qb.where(DiaylsDateDao.Properties.Date.eq(customDate.year + "" + customDate.month + "" + customDate.day));
//            List<DiaylsDate> list = qb.list();
//            if (list == null) {
                diaylsDateDao.insert(diaylsDate);
//            } else {
//                diaylsDateDao.update(diaylsDate);
//            }

            ShowToastUtils.Short("保存成功");
        } else {
            ShowToastUtils.Short("未录入或选择病人信息");
        }

    }
}
