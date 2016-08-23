package nurse.little.com.mylittlenurse.messagelook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import nurse.little.com.mylittlenurse.DaoSession;
import nurse.little.com.mylittlenurse.R;
import nurse.little.com.mylittlenurse.SickPeople;
import nurse.little.com.mylittlenurse.SickPeopleDao;
import nurse.little.com.mylittlenurse.bast.BaseFragment;
import nurse.little.com.mylittlenurse.bast.NurseApplication;
import nurse.little.com.mylittlenurse.bast.TableName;
import nurse.little.com.mylittlenurse.messagelook.activity.DetailsMessageActivity;

/**
 * 信息预览
 * Created by user on 2016/3/15.
 */
public class MessageLookFragment extends BaseFragment {

    private SwipeRefreshLayout refresh_layout;
    private RecyclerView recycler_view;
    private SickPeopleDao sickPeopleDao;
    private View message_look_linear;
    private List<SickPeople> sickPeoples;
    private MessageLookAdapter messageLookAdapter;
    //    private DataDaoUtils dataDaoUtils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaoSession daoSession = NurseApplication.getDaoSession(getContext(), TableName.DB_SICK_NAME);
        sickPeopleDao = daoSession.getSickPeopleDao();
        sickPeoples = sickPeopleDao.loadAll();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_message_look, container, false);
        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        message_look_linear = view.findViewById(R.id.message_look_linear);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecycler();
        initSwipeRefresh();

    }

    private void initSwipeRefresh() {
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(), "刷新了", Toast.LENGTH_SHORT).show();
                /**
                 * 查询数据库,返回结果后取消刷新
                 */
                sickPeoples.clear();
                sickPeoples = sickPeopleDao.loadAll();
                message_look_linear.setVisibility(sickPeoples.isEmpty() ? View.VISIBLE : View.GONE);
                messageLookAdapter.setAdapterList(sickPeoples);
//                recycler_view.setAdapter(messageLookAdapter);
                messageLookAdapter.notifyDataSetChanged();
                if (refresh_layout.isRefreshing()) {
                    refresh_layout.setRefreshing(false);
                }
            }
        });

    }

    private void initRecycler() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        message_look_linear.setVisibility(sickPeoples.isEmpty() ? View.VISIBLE : View.GONE);
        messageLookAdapter = new MessageLookAdapter(getContext(), sickPeoples);
        recycler_view.setAdapter(messageLookAdapter);
        messageLookAdapter.setOnItemClickLitener(new MessageLookAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position, SickPeople people) {
                /**
                 * 跳转详情页
                 */
                sickPeoples = sickPeopleDao.loadAll();
//                Collections.reverse(sickPeoples);//集合倒序
                Intent intent = new Intent(getContext(), DetailsMessageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("sick", people);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, final int position, final SickPeople people) {
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
                        messageLookAdapter.deleveData(position);
                        sickPeoples.remove(people);
                        message_look_linear.setVisibility(sickPeoples.isEmpty() ? View.VISIBLE : View.GONE);
                    }
                });
                builder.show();
            }
        });
    }


}
