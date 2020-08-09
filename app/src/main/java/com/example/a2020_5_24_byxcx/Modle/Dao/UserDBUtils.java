package com.example.a2020_5_24_byxcx.Modle.Dao;

import android.content.Context;
import android.util.Log;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class UserDBUtils {

    private static final String TAG = "dbDBUtils";

    public DaoManager mManager;

    public UserDBUtils(Context context) {
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成db记录的插入，如果表未创建，先创建db表
     *
     * @return
     */
    public boolean insertdb(UserModel db) {
        boolean flag = false;
        flag = mManager.getDaoSession().getUserModelDao().insertOrReplace(db) == -1 ? false : true;
        Log.i(TAG, "insert Meizi :" + flag + "-->" + db.toString());
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     *
     * @return
     */
    public boolean insertMultUserModel(final List<UserModel> UserModelList) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (UserModel db : UserModelList) {
                        mManager.getDaoSession().insertOrReplace(db);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改一条数据
     *
     * @return
     */
    public boolean updateUserModel(UserModel db) {
        boolean flag = false;
        try {
            mManager.getDaoSession().update(db);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改收藏数据
     *
     * @return
     */
    public boolean updateUserModel_NewsList(UserModel db) {
        boolean flag = false;
        try {
            mManager.getDaoSession().update(db);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单条记录\
     *
     * @return
     */
    public boolean deleteUserModel(UserModel db) {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(db);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     *
     * @return
     */
    public boolean deleteAll() {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(UserModel.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    public List<UserModel> queryAllUserModel() {
        return mManager.getDaoSession().loadAll(UserModel.class);
    }

    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public UserModel queryUserModelById(String key) {
        return mManager.getDaoSession().load(UserModel.class, key);
    }

    /**
     * 根据主键id查询收藏
     *
     * @param key
     * @return
     */
    public List<UserModel> queryUserModel_NewsListById(String key) {
        return mManager.getDaoSession().load(UserModel.class, key).getUserModelList();
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<UserModel> queryUserModelByNativeSql(String sql, String[] conditions) {
        return mManager.getDaoSession().queryRaw(UserModel.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     *
     * @return
     */
    public List<UserModel> queryUserModelByQueryBuilder(long pid) {
        QueryBuilder<UserModel> queryBuilder = mManager.getDaoSession().queryBuilder(UserModel.class);
        return queryBuilder.where(UserModelDao.Properties.Pid.eq(pid)).list();
    }
    /*public List<UserModel> queryUserModelByQueryBuilder_toType(String type) {
        QueryBuilder<UserModel> queryBuilder = mManager.getDaoSession().queryBuilder(UserModel.class);
        List<UserModel> list = queryBuilder.where(UserModelDao.Properties.Type.eq(type)).list();
        return list;
    }
    public List<UserModel> queryUserModelByQueryBuilder_toTitle(String title) {
        QueryBuilder<UserModel> queryBuilder = mManager.getDaoSession().queryBuilder(UserModel.class);
        List<UserModel> list = queryBuilder.where(UserModelDao.Properties.Title.eq(title)).list();
        return list;
    }*/

    public void close() {
        mManager.closeConnection();
    }
}
