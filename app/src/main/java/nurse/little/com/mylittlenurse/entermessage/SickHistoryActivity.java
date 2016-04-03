package nurse.little.com.mylittlenurse.entermessage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.orhanobut.logger.Logger;

import nurse.little.com.mylittlenurse.R;
import nurse.little.com.mylittlenurse.bast.ResultCode;

/**
 * 病史录入界面
 * Created by user on 2016/3/18.
 */
public class SickHistoryActivity extends AppCompatActivity {


    private View addition;
    private RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_sick_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("病史录入");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_actionbar_back);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);
        initView();
        registerListener();
    }

    private void registerListener() {
        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SickHistoryActivity.this, SicHistoryDetailsAcitivity.class);
                startActivityForResult(intent, ResultCode.SICK_HISTORY);
            }
        });
    }

    private void initView() {
        addition = findViewById(R.id.addition);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResultCode.SICK_HISTORY_DETAILS) {
            /**
             * 详情页回来了
             */
            Logger.e("详情页回来了--------------------->");
        }
    }
}
