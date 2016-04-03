package nurse.little.com.mylittlenurse.utils;

import android.support.v4.app.Fragment;

import java.util.HashMap;

/**
 * this is FragmentFactory
 * Created by user on 2016/1/22.
 */
public class FragmentFactory {

    private static HashMap<String, Fragment> hashMap = new HashMap();

    public static Fragment createFragment(Class c) {
        Fragment fragment = null;
        try {
            if (hashMap.containsKey(c.getName())) {
                fragment = hashMap.get(c.getName());
            } else {
                fragment = (Fragment) Class.forName(c.getName()).newInstance();
                hashMap.put(c.getName(), fragment);
            }

        } catch (InstantiationException e) {
            ShowToastUtils.Short("创建实体类失败");
        } catch (IllegalAccessException e) {
            ShowToastUtils.Short("类型定义错误");
        } catch (ClassNotFoundException e) {
            ShowToastUtils.Short("找不到类");
        }
        return fragment;
    }
}
