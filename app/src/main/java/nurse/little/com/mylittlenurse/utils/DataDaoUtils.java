package nurse.little.com.mylittlenurse.utils;

import android.content.Context;

import java.util.List;

import nurse.little.com.mylittlenurse.DaoSession;
import nurse.little.com.mylittlenurse.SickPeople;
import nurse.little.com.mylittlenurse.SickPeopleDao;
import nurse.little.com.mylittlenurse.bast.NurseApplication;

/**
 * Created by user on 2016/3/17.
 */
public class DataDaoUtils {

    private DaoSession daoSession;
    private SickPeopleDao sickPeopleDao;
    private static DataDaoUtils instance = new DataDaoUtils();

    private DataDaoUtils() {

    }


    public static DataDaoUtils getInstance() {
//        synchronized(DataDaoUtils.class){
//
//        }
//        if (instance == null) {
////            synchronized (DataDaoUtils.class){
//            instance = new DataDaoUtils();
////            }
//        }
        return instance;
    }

    public void initDB(Context context, String dbName) {
        daoSession = NurseApplication.getDaoSession(context, dbName);
        sickPeopleDao = daoSession.getSickPeopleDao();
    }

    /**
     * 查询全部
     *
     * @return
     */
    public List<SickPeople> getSickPeopleAll() {
        return sickPeopleDao.loadAll();// 获取全部数据
    }

    /**
     * 插入数据库
     *
     * @param people
     */
    public void addTOSickPeople(SickPeople people) {
        sickPeopleDao.insert(people);

    }

    /**
     * 删除 全部sickPeople数据库
     */

    public void clearSickPeople()

    {
        sickPeopleDao.deleteAll();

    }
}


