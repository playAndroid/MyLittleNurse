package nurse.little.com.mylittlenurse.broadcastmess;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nurse.little.com.mylittlenurse.R;
import nurse.little.com.mylittlenurse.bast.BaseFragment;

/**
 * 宣传教育界面
 * Created by user on 2016/3/15.
 */
public class BroadCatmessFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_broadcast, container, false);
        return view;
    }
}
