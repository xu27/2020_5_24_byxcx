package com.example.xcxlibrary.Util;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {
    private static final String TAG = "CheckUtil";


    /**
     * 手机号验证
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,4-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            Log.e(TAG, "isMobileNO: ", e);
            flag = false;
        }
        return flag;
    }

    /**
     * 验证邮箱地址是否正确
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            Log.e(TAG, "checkEmail: ", e);
            flag = false;
        }

        return flag;
    }

    /**
     * 密码验证
     *
     * @param pwd
     * @return
     */
    public static boolean checkPwd(String pwd) {
        String regExp = "^[\\w_]{6,20}$";
        if (pwd.matches(regExp)) {
            return true;
        }
        return false;
    }

    /**
     * 用户名验证
     *
     * @param name
     * @return
     */
    public static boolean checkName(String name) {
        String regExp = "^[^0-9][\\w_]{5,9}$";
        if (name.matches(regExp)) {
            return true;
        } else {
            return false;
        }
    }
}
