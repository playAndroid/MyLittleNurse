package nurse.little.com.mylittlenurse.homeview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import nurse.little.com.mylittlenurse.R;
import nurse.little.com.mylittlenurse.view.CalendarCard;
import nurse.little.com.mylittlenurse.view.CustomDate;

/**
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
    private RecyclerView recycler_yianpai;
    /**
     * 重新安排
     */
    private RecyclerView recycler_weianpai;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_data_layout, container, false);
        cal_week = (LinearLayout) view.findViewById(R.id.cal_week);
        recycler_yianpai = (RecyclerView) view.findViewById(R.id.listview_content);
        recycler_weianpai = (RecyclerView) view.findViewById(R.id.bo_listview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        shcList = new ArrayList<>();
        customDate = new CustomDate();
        shcList.add(Integer.parseInt("02"));
        shcList.add(Integer.parseInt("06"));
        shcList.add(Integer.parseInt("09"));
        calendarCard = new CalendarCard(getContext(), new CalendarCard.OnCellClickListener() {
            @Override
            public void clickDate(CustomDate date) {
//                Toast.makeText(getContext(), date.day + "", Toast.LENGTH_SHORT).show();
                Logger.e(date.year + "年" + date.month + "月" + date.day + "日");
            }
        }, shcList, customDate);
        cal_week.addView(calendarCard);
    }


}
