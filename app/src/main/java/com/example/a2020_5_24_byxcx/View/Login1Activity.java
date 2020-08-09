package com.example.a2020_5_24_byxcx.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a2020_5_24_byxcx.Base.TextListenerAdapter;
import com.example.a2020_5_24_byxcx.Modle.Dao.NewsModle;
import com.example.a2020_5_24_byxcx.Modle.Dao.UserDBUtils;
import com.example.a2020_5_24_byxcx.Modle.Dao.UserModel;
import com.example.a2020_5_24_byxcx.R;
import com.example.a2020_5_24_byxcx.View.custom.MySeekbar;
import com.example.xcxlibrary.BaseActivity;
import com.example.xcxlibrary.Util.CheckUtil;
import com.example.xcxlibrary.Util.MD5Encoder;
import com.example.xcxlibrary.Util.SharePrenceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

public class Login1Activity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "Login1Activity";

    private static String APPKEY = "2fbf12b75fe5e";
    private static String APPSECRET = "cc952829d05ea582fc7331fca0dc6672";
    private static String CN = "86";
    private String pwd;
    private int time = 60;
    private boolean flag1 = false;
    private boolean flag2 = false;
    private boolean flag3 = false;

    @BindView(R.id.login1_phone)
    protected EditText phone;
    @BindView(R.id.login1_password)
    protected EditText password;
    @BindView(R.id.login1_password2)
    protected EditText confirm;
    @BindView(R.id.login1_yanzheng)
    protected EditText yanzheng;
    @BindView(R.id.login_get_yanzheng)
    protected Button getyanzheng;
    @BindView(R.id.login1_but)
    protected Button login;
    @BindView(R.id.login1_phone_img)
    protected ImageView pimg;
    @BindView(R.id.login1_password_img)
    protected ImageView pimg2;
    @BindView(R.id.login1_password2_img)
    protected ImageView pimg3;
    @BindView(R.id.login1_CheckSeekbar)
    protected MySeekbar mySeekbar;
    @BindView(R.id.login1_toolbar)
    protected Toolbar toolbar;

    private Unbinder mUnbinder = null;
    private InputMethodManager manager;
    private UserDBUtils dbUtils;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.d(TAG, "handleMessage: " + msg.what);
            if (msg.what == 0) {
                getyanzheng.setEnabled(true);
                getyanzheng.setText("获取验证码");
            } else {
                getyanzheng.setText(msg.what + "秒");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        getyanzheng.setEnabled(false);
        getyanzheng.setOnClickListener(this);
        login.setOnClickListener(this);
        manager = (InputMethodManager) Login1Activity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        dbUtils = new UserDBUtils(this);
        phone.addTextChangedListener(new TextListenerAdapter() {
            @Override
            public void afterTextChanged(Editable editable) {
                if (pimg.getVisibility() == View.INVISIBLE) {
                    pimg.setVisibility(View.VISIBLE);
                }
                if (CheckUtil.isMobileNO(editable.toString())) {
                    pimg.setImageDrawable(getResources().getDrawable(R.drawable.ic_is_ok));
                    flag1 = true;
                } else {
                    pimg.setImageDrawable(getResources().getDrawable(R.drawable.ic_is_ok2));
                    flag1 = false;
                }
                isFlag();
            }
        });
        password.addTextChangedListener(new TextListenerAdapter() {
            @Override
            public void afterTextChanged(Editable editable) {
                if (pimg2.getVisibility() == View.INVISIBLE) {
                    pimg2.setVisibility(View.VISIBLE);
                }
                if (CheckUtil.checkPwd(editable.toString())) {
                    pimg2.setImageDrawable(getResources().getDrawable(R.drawable.ic_is_ok));
                    pwd = editable.toString();
                    flag2 = true;
                } else {
                    pimg2.setImageDrawable(getResources().getDrawable(R.drawable.ic_is_ok2));
                    flag2 = false;
                }
                isFlag();
            }
        });
        confirm.addTextChangedListener(new TextListenerAdapter() {
            @Override
            public void afterTextChanged(Editable editable) {
                if (pimg3.getVisibility() == View.INVISIBLE) {
                    pimg3.setVisibility(View.VISIBLE);
                }
                if (pwd.equals(editable.toString())) {
                    pimg3.setImageDrawable(getResources().getDrawable(R.drawable.ic_is_ok));
                    flag3 = true;
                } else {
                    pimg3.setImageDrawable(getResources().getDrawable(R.drawable.ic_is_ok2));
                    flag3 = false;
                }
                isFlag();
            }
        });
        mySeekbar.setListener(new MySeekbar.CheckListener() {
            @Override
            public void Checkok() {
                mySeekbar.setEnabled(false);
                getyanzheng.setEnabled(true);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_get_yanzheng:
                if (isFlag()) {
                    sendCode(CN, phone.getText().toString());
                }
                break;
            case R.id.login1_but:
                if (isFlag()) {
                    submitCode(CN, phone.getText().toString(), yanzheng.getText().toString());
                    login.setEnabled(false);
                    login.setText("注册中...");
                }
                //finish();
                break;
        }

    }


    private void showToast(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Login1Activity.this.getApplication(), s, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void hideSoftInput(View view) {
        if (manager != null) {
            manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public void sendCode(String country, String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        Log.d(TAG, "sendCode: ");
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {

                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    Log.d(TAG, "sendCode: ok");
                    timewait60s();
                } else {

                    Log.d(TAG, "sendCode: no:" + result);
                }

            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }

    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, String phone, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    register();
                } else {
                    showToast("验证失败");
                    login.setEnabled(true);
                    login.setText("注册");
                }
            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    public boolean isFlag() {
        if (!flag1) {
            return flag1;
        }
        if (!flag2) {
            return flag2;
        }
        if (!flag3) {
            return flag3;
        }
        mySeekbar.setVisibility(View.VISIBLE);
        return true;
    }

    private void timewait60s() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getyanzheng.setEnabled(false);
            }
        });
        time = 60;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (time != 0) {
                    Log.d(TAG, "run: time--");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    time--;
                    Message msg = new Message();
                    msg.what = time;
                    mHandler.sendMessage(msg);
                }
            }
        }).start();
    }

    private void register() {
        UserModel userModle = new UserModel();
        userModle.setPid(MD5Encoder.encode(phone.getText().toString()));
        userModle.setPhone(phone.getText().toString());
        userModle.setName(phone.getText().toString());
        userModle.setPassword(password.getText().toString());
        dbUtils.insertdb(userModle);
        SharePrenceUtil.saveInt(Login1Activity.this, "login", 1);
        try {
            SharePrenceUtil.saveString(Login1Activity.this, "Pid", MD5Encoder.encode(phone.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        SharePrenceUtil.saveLong(Login1Activity.this, "lastLogonTime", System.currentTimeMillis());
        showToast("注册成功");
        finish();
    }
}
