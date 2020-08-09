package com.example.a2020_5_24_byxcx.Base;

import android.app.Activity;
import android.util.Log;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class MobTechHelper {

    public MobTechHelper() {
    }

    public static void submitCode(String country, String phone, String code, Callback callback) {
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {

                    callback.ok();
                } else {

                    callback.err();
                }
            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    public static void sendCode(String country, String phone, Callback callback) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {

                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    callback.ok();
                } else {
                    callback.err();
                }

            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }

    public static void unregister() {
        SMSSDK.unregisterAllEventHandler();
    }

    public interface Callback {
        void ok();

        void err();
    }
}
