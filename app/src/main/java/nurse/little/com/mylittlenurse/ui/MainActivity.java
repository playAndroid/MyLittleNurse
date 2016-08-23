package nurse.little.com.mylittlenurse.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import nurse.little.com.mylittlenurse.R;
import nurse.little.com.mylittlenurse.bast.BaseActivity;
import nurse.little.com.mylittlenurse.broadcastmess.BroadCatmessFragment;
import nurse.little.com.mylittlenurse.entermessage.EnterMessageFragment;
import nurse.little.com.mylittlenurse.entermessage.OnUpDataListener;
import nurse.little.com.mylittlenurse.homeview.HomeFragment;
import nurse.little.com.mylittlenurse.messagelook.MessageLookFragment;
import nurse.little.com.mylittlenurse.utils.FragmentFactory;
import nurse.little.com.mylittlenurse.utils.ShowToastUtils;

/**
 * 主界面
 * Created by user on 2016/3/15.
 */
public class MainActivity extends BaseActivity {

    private DrawerLayout drawer_layout;
    private NavigationView navigation_view;
    private ActionBarDrawerToggle drawerToggle;
//    private MenuItem navigaItem;
    protected long endTime;
//    private FragmentState fragmentState = FragmentState.HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("O(∩_∩)O~");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        initView();
        registerListener();
//        replaceFragment(new HomeFragment());
        replaceFragment(FragmentFactory.createFragment(HomeFragment.class));
    }

//    /**
//     * Fragment类型  HOME:主页 ENMS :录入信息 MESLOOK:信息预览 BROCON: 宣教
//     */
//    private enum FragmentState {
//        HOME, ENMS, MESLOOK, BROCON
//    }


    private void initView() {
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        initDrawerView();
    }

    private void initDrawerView() {
        drawerToggle = new ActionBarDrawerToggle(this, drawer_layout, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerToggle.syncState();
        drawer_layout.setDrawerListener(drawerToggle);
    }

    private void registerListener() {
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                int itemId = item.getItemId();
//                navigaItem = item;
                switch (itemId) {
                    case R.id.enter_message:
                        EnterMessageFragment enterMessageFragment = new EnterMessageFragment();
                        enterMessageFragment.setOnUpDataListener(new OnUpDataListener() {
                            @Override
                            public void upDataSuccess() {
                                replaceFragment(FragmentFactory.createFragment(MessageLookFragment.class));
                            }
                        });
                        replaceFragment(enterMessageFragment);
                        break;
                    case R.id.message_look:
                        replaceFragment(FragmentFactory.createFragment(MessageLookFragment.class));
                        break;
                    case R.id.brocat_content:
                        replaceFragment(FragmentFactory.createFragment(BroadCatmessFragment.class));
                        break;
                    case R.id.home_fragment:
                        replaceFragment(FragmentFactory.createFragment(HomeFragment.class));
                        break;
                }
                item.setChecked(true);
                drawer_layout.closeDrawers();
//                drawerToggle.onDrawerClosed(drawer_layout);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item);

    }

    /**
     * 返回键返回上一层视图
     *
     * @param keyCode keyCode
     * @param event   event
     * @return boolean
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - endTime > 2000) {
                //Again according to exit the application
                ShowToastUtils.Short("再按一次就粗去嘞o(╯□╰)o");
                endTime = System.currentTimeMillis();
                return true;
            } else {
                finish();
            }
        }
        return false;
    }
}
