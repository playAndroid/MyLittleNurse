package nurse.little.com.mylittlenurse.bast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import nurse.little.com.mylittlenurse.R;

/**
 * Activity基类
 * Created by user on 2016/3/15.
 */
public class BaseActivity extends AppCompatActivity {


    /**
     * 替换fragment
     *
     * @param id_content
     * @param fragment
     */
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_right);
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }
}
