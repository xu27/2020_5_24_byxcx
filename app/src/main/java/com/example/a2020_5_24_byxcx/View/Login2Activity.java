package com.example.a2020_5_24_byxcx.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2020_5_24_byxcx.Base.MobTechHelper;
import com.example.a2020_5_24_byxcx.Modle.Dao.UserDBUtils;
import com.example.a2020_5_24_byxcx.Modle.Dao.UserModel;
import com.example.a2020_5_24_byxcx.R;
import com.example.xcxlibrary.Util.MD5Encoder;
import com.example.xcxlibrary.Util.SharePrenceUtil;
import com.google.android.material.textfield.TextInputLayout;

public class Login2Activity extends AppCompatActivity {

    private static final String TAG = "Login2Activity";

    //登陆模式
    private boolean FLAG_MODE = false;

    @BindView(R.id.main_toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.login2_phone)
    protected EditText phone;
    @BindView(R.id.login2_password)
    protected EditText password;
    @BindView(R.id.login2_yanzhengma)
    protected EditText yanzhengma;

    @BindView(R.id.login2_layout_phone)
    protected TextInputLayout phoneLayout;
    @BindView(R.id.login2_layout_password)
    protected TextInputLayout passwordLayout;
    @BindView(R.id.login2_layout_yanzhengma)
    protected TextInputLayout yanzhengmaLayout;

    @BindView(R.id.login2_get_yanzheng)
    protected Button getyanzheng;
    @BindView(R.id.login)
    protected Button login;
    @BindView(R.id.login2_switchingmode)
    protected TextView switchingmode;

    AnimatorSet show, hide;
    private int time;

    private Unbinder mUnbinder = null;
    private static String CN = "86";
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
        setContentView(R.layout.activity_login2);
        mUnbinder = ButterKnife.bind(this);
        dbUtils = new UserDBUtils(this);
        show = new AnimatorSet();
        hide = new AnimatorSet();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @OnClick(R.id.login)
    protected void islogin() {
        UserModel userModel = dbUtils.queryUserModelById(MD5Encoder.encode(phone.getText().toString()));
        if (userModel != null) {
            if (FLAG_MODE) {
                //密码登陆
                if (password.getText().toString().equals(userModel.getPassword())) {
                    login();
                } else {
                    phoneLayout.setError("密码错误");

                }
            } else {
                //手机验证码登陆
                MobTechHelper.submitCode(CN, phone.getText().toString(), yanzhengma.getText().toString(), new MobTechHelper.Callback() {
                    @Override
                    public void ok() {
                        login();
                    }

                    @Override
                    public void err() {
                        yanzhengmaLayout.setError("验证码错误");
                    }
                });
            }
        } else {
            phoneLayout.setError("手机号错误");
        }
    }


    @OnClick(R.id.login2_switchingmode)
    protected void setFLAG_MODE() {
        if (!FLAG_MODE) {
            Log.d(TAG, "setFLAG_MODE: " + FLAG_MODE);
            //属性动画
            if (!hide.isRunning()) {
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(getyanzheng, "alpha", 1f, 0f);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(getyanzheng, "translationY", 0f, 150f);
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(yanzhengma, "alpha", 1f, 0f);
                ObjectAnimator animator4 = ObjectAnimator.ofFloat(yanzhengma, "translationY", 0f, 150f);
                ObjectAnimator animator5 = ObjectAnimator.ofFloat(password, "alpha", 0f, 1f);
                ObjectAnimator animator6 = ObjectAnimator.ofFloat(password, "translationY", -150f, 0f);
                hide.play(animator1).with(animator2).with(animator3).with(animator4).with(animator5).with(animator6);
                hide.setDuration(500);
                hide.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        Log.d(TAG, "onAnimation: ");
                        password.setEnabled(true);
                    }
                });
                hide.start();
                FLAG_MODE = true;
                return;
            }
        }
        if (FLAG_MODE) {
            Log.d(TAG, "setFLAG_MODE: " + FLAG_MODE);
            //属性动画
            if (!show.isRunning()) {
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(getyanzheng, "alpha", 0f, 1f);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(getyanzheng, "translationY", 150f, 0f);
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(yanzhengma, "alpha", 0f, 1f);
                ObjectAnimator animator4 = ObjectAnimator.ofFloat(yanzhengma, "translationY", 150f, 0f);
                ObjectAnimator animator5 = ObjectAnimator.ofFloat(password, "alpha", 1f, 0f);
                ObjectAnimator animator6 = ObjectAnimator.ofFloat(password, "translationY", 0f, -150f);
                show.play(animator1).with(animator2).with(animator3).with(animator4).with(animator5).with(animator6);
                show.play(animator1).with(animator2).with(animator3).with(animator4).with(animator5).with(animator6);
                show.setDuration(500);
                show.start();
                FLAG_MODE = false;
                return;
            }
        }
    }

    @OnClick(R.id.login2_get_yanzheng)
    protected void get_yanzheng(View view) {
        if (dbUtils.queryUserModelById(MD5Encoder.encode(phone.getText().toString())) != null)
            MobTechHelper.sendCode(CN, phone.getText().toString(), new MobTechHelper.Callback() {
                @Override
                public void ok() {
                    getyanzheng.setEnabled(false);
                    timewait60s();
                }

                @Override
                public void err() {
                    showmsg("请求短信失败");
                }
            });
    }

    private void login() {
        SharePrenceUtil.saveInt(Login2Activity.this, "login", 1);
        try {
            SharePrenceUtil.saveString(Login2Activity.this, "Pid", MD5Encoder.encode(phone.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        SharePrenceUtil.saveLong(Login2Activity.this, "lastLogonTime", System.currentTimeMillis());
        finish();
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

    private void showmsg(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Login2Activity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        MobTechHelper.unregister();
        super.onDestroy();
    }
}
