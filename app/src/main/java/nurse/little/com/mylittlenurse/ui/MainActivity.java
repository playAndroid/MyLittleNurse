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
import android.widget.FrameLayout;

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
    private FrameLayout frame_layout;
    private NavigationView navigation_view;
    private ActionBarDrawerToggle drawerToggle;
    private MenuItem navigaItem;
    protected long endTime;
    private Toolbar toolbar;
    private boolean isHome = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//        }
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏 一些手机如果有虚拟键盘的话,虚拟键盘就会变成透明的,挡住底部按钮点击事件所以,最后不要用
//            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        //试试git好使了没
//        StatusBarCompat.compat(this, getResources().getColor(R.color.primary));
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("O(∩_∩)O~");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        initView();
        registerListener();
//        replaceFragment(new HomeFragment());
        replaceFragment(FragmentFactory.createFragment(HomeFragment.class));
    }


    private void initView() {
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        frame_layout = (FrameLayout) findViewById(R.id.frame_layout);
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
                navigaItem = item;
                switch (itemId) {
                    case R.id.enter_message:
                        isHome = false;
                        EnterMessageFragment enterMessageFragment = new EnterMessageFragment();
                        enterMessageFragment.setOnUpDataListener(new OnUpDataListener() {
                            @Override
                            public void upDataSuccess() {
                                isHome = true;
//                                replaceFragment(new HomeFragment());
                                replaceFragment(FragmentFactory.createFragment(MessageLookFragment.class));
                            }
                        });
                        replaceFragment(enterMessageFragment);
                        break;
                    case R.id.message_look:
                        isHome = false;
//                        replaceFragment(new MessageLookFragment());
                        replaceFragment(FragmentFactory.createFragment(MessageLookFragment.class));
                        break;
                    case R.id.brocat_content:
                        isHome = false;
//                        replaceFragment(new BroadCatmessFragment());
                        replaceFragment(FragmentFactory.createFragment(BroadCatmessFragment.class));
                        break;
//                    case R.id.enter_bingshi:
//                        Intent intent = new Intent(MainActivity.this, SickHistoryActivity.class);
//                        startActivity(intent);
//                        break;
                    case R.id.home_fragment:
                        isHome = true;
//                        replaceFragment(new HomeFragment());
                        replaceFragment(FragmentFactory.createFragment(HomeFragment.class));
                        break;
                }
                item.setChecked(true);
                drawer_layout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (navigaItem != null) {
            navigaItem.setChecked(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isHome) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
//
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
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int itemId = item.getItemId();
        if (itemId == R.id.action_save) {
            ShowToastUtils.Short("保存排班");
            HomeFragment homeFragment = (HomeFragment) FragmentFactory.createFragment(HomeFragment.class);
            homeFragment.save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 返回键返回上一层视图
     *
     * @param keyCode
     * @param event
     * @return
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
