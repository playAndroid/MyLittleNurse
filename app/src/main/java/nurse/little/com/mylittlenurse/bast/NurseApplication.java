package nurse.little.com.mylittlenurse.bast;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.orhanobut.logger.Logger;

import cn.bmob.v3.Bmob;
import nurse.little.com.mylittlenurse.DaoMaster;
import nurse.little.com.mylittlenurse.DaoSession;

/**
 * application
 * Created by user on 2016/3/14.
 */
public class NurseApplication extends Application {

    private static Context context;

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    private static final String TAG = "hlk";


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //Stetho 初始化  查看数据库插件
        Stetho.initialize(Stetho.newInitializerBuilder(this).
                enableDumpapp(Stetho.defaultDumperPluginsProvider(this)).
                enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this)).build());
        //log 初始化
        Logger.init(TAG);
        //Bmob初始化
        Bmob.initialize(this, "17e7054d884880d599a26e884698e5ca");
    }

    public static Context getContext() {
        return context;
    }

    public static DaoMaster getDaoMaster(Context context, String tableName) {
        //GreenDao 数据库操作
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, tableName, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession(Context context, String tableName) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context, tableName);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

}
