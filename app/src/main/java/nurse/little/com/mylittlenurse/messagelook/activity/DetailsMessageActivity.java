package nurse.little.com.mylittlenurse.messagelook.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import nurse.little.com.mylittlenurse.R;
import nurse.little.com.mylittlenurse.SickPeople;
import nurse.little.com.mylittlenurse.bast.BaseActivity;

/**
 * 病情详情页
 * Created by user on 2016/3/22.
 */
public class DetailsMessageActivity extends BaseActivity {

    private SickPeople sick;

    @Bind(R.id.tv_xing_ming)
    TextView tv_xing_ming;
    @Bind(R.id.tv_nianling)
    TextView tv_nianling;
    @Bind(R.id.tv_sex)
    TextView tv_sex;
    @Bind(R.id.shengao_counter)
    TextView shengao_counter;
    @Bind(R.id.tizhong_counter)
    TextView tizhong_counter;
    @Bind(R.id.dianhuazu_counter)
    TextView dianhuazu_counter;
    @Bind(R.id.jingmaizu)
    TextView jingmaizu;
    @Bind(R.id.huayandan)
    TextView huayandan;
    @Bind(R.id.yingyangzu)
    TextView yingyangzu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        Intent intent = getIntent();
        sick = (SickPeople) intent.getSerializableExtra("sick");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_actionbar_back);
        setSupportActionBar(toolbar);
        initView();
        initData();
    }

    private void initData() {
        if (sick != null) {
            tv_xing_ming.setText(sick.getName());
            tv_nianling.setText(sick.getAge());
            tv_sex.setText(sick.getSex());
            shengao_counter.setText(sick.getHeight());
            tizhong_counter.setText(sick.getWeight());
            dianhuazu_counter.setText(sick.getTel());
            jingmaizu.setText(sick.getMailou());
            huayandan.setText(sick.getHuayan());
            yingyangzu.setText(sick.getYingyang());
        }

    }

    private void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
