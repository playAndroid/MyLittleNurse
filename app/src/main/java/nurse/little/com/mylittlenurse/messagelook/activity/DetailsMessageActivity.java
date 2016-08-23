package nurse.little.com.mylittlenurse.messagelook.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import nurse.little.com.mylittlenurse.DaoSession;
import nurse.little.com.mylittlenurse.R;
import nurse.little.com.mylittlenurse.SickPeople;
import nurse.little.com.mylittlenurse.SickPeopleDao;
import nurse.little.com.mylittlenurse.bast.BaseActivity;
import nurse.little.com.mylittlenurse.bast.NurseApplication;
import nurse.little.com.mylittlenurse.bast.TableName;
import nurse.little.com.mylittlenurse.utils.ShowToastUtils;

/**
 * 病情详情页
 * Created by user on 2016/3/22.
 */
public class DetailsMessageActivity extends BaseActivity {

    private SickPeople sick;

    @Bind(R.id.tv_xing_ming)
    EditText tv_xing_ming;
    @Bind(R.id.tv_nianling)
    EditText tv_nianling;
    @Bind(R.id.tv_sex)
    EditText tv_sex;
    @Bind(R.id.shengao_counter)
    EditText shengao_counter;
    @Bind(R.id.tizhong_counter)
    EditText tizhong_counter;
    @Bind(R.id.dianhuazu_counter)
    EditText dianhuazu_counter;
    @Bind(R.id.jingmaizu)
    EditText jingmaizu;
    @Bind(R.id.huayandan)
    EditText huayandan;
    @Bind(R.id.yingyangzu)
    EditText yingyangzu;
    @Bind(R.id.bingshidan)
    EditText bingshidan;

    private Long id;
    private SickPeopleDao sickPeopleDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        DaoSession daoSession = NurseApplication.getDaoSession(this, TableName.DB_SICK_NAME);
        sickPeopleDao = daoSession.getSickPeopleDao();
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
            id = sick.getId();
            tv_xing_ming.setText(sick.getName());
            tv_nianling.setText(sick.getAge());
            tv_sex.setText(sick.getSex());
            shengao_counter.setText(sick.getHeight());
            tizhong_counter.setText(sick.getWeight());
            dianhuazu_counter.setText(sick.getTel());
            jingmaizu.setText(sick.getMailou());
            huayandan.setText(sick.getHuayan());
            yingyangzu.setText(sick.getYingyang());
            bingshidan.setText(sick.getBingshi());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details_item, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void initView() {
        ButterKnife.bind(this);
//        tv_xing_ming.set
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.action_save) {
            /**
             * 修改数据
             */
            SickPeople people = new SickPeople();
            people.setId(id);
            people.setName(tv_xing_ming.getText().toString().trim());
            people.setAge(tv_nianling.getText().toString().trim());
            people.setSex(tv_sex.getText().toString().trim());
            people.setHeight(shengao_counter.getText().toString().trim());
            people.setWeight(tizhong_counter.getText().toString().trim());
            people.setTel(dianhuazu_counter.getText().toString().trim());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String time = dateFormat.format(new Date());
            people.setTime(time);
            people.setMailou(jingmaizu.getText().toString().trim());
            people.setHuayan(huayandan.getText().toString().trim());
            people.setYingyang(yingyangzu.getText().toString().trim());
            people.setBingshi(bingshidan.getText().toString().trim());
            sickPeopleDao.update(people);
            ShowToastUtils.Short("已保存修改");
            return true;
        }

        if (item.getItemId() == R.id.action_delete) {
            /**
             * 删除数据
             */
            sickPeopleDao.deleteByKey(id);
            ShowToastUtils.Short("已删除数据");
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
