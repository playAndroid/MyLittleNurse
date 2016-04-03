package nurse.little.com.mylittlenurse.entermessage;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import nurse.little.com.mylittlenurse.R;
import nurse.little.com.mylittlenurse.bast.ResultCode;
import nurse.little.com.mylittlenurse.utils.ShowToastUtils;

/**
 * 病史录入详情页
 * Created by user on 2016/3/18.
 */
public class SicHistoryDetailsAcitivity extends AppCompatActivity {


    private TextView history_time;
    private EditText sick_history_details;
    private View quxiao;
    private View queren;
    private String sickDetails;
    private String historyTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sick_history_details);
        this.setResult(ResultCode.SICK_HISTORY_DETAILS);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_actionbar_back);
        setSupportActionBar(toolbar);
        initView();
        retisterListener();


    }

    private void retisterListener() {
        history_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        .build()
                        .show();
            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history_time.setText("点此选择时间");
                sick_history_details.setText("");
            }
        });
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 保存信息
                 */
                sickDetails = sick_history_details.getText().toString().trim();
                if (TextUtils.isEmpty(sickDetails)) {
                    sickDetails = "未填写病史描述";
                }
                historyTime = history_time.getText().toString().trim();

                finish();
            }
        });
    }

    private void initView() {
        history_time = (TextView) findViewById(R.id.history_time);
        sick_history_details = (EditText) findViewById(R.id.sick_history_details);
        quxiao = findViewById(R.id.quxiao);
        queren = findViewById(R.id.queren);
    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
//            history_time.setText(date.getTime());
            // Do something with the date. This Date object contains
            // the date and time that the user has selected.
            Logger.e("时间是---------->" + date.getTime());
            /**
             * 确定后返回上页
             */
            long time = date.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = new Date(time);
            String t1 = format.format(d1);
            Logger.e("时间是---------->" + t1);
            history_time.setText(t1);
            ShowToastUtils.Short(t1);

        }

        @Override
        public void onDateTimeCancel() {
            // Overriding onDateTimeCancel() is optional.
            Logger.e("取消了---------->");
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
