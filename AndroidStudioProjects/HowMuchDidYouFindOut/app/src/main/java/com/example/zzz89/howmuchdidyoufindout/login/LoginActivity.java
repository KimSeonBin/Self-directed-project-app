package com.example.zzz89.howmuchdidyoufindout.login;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.zzz89.howmuchdidyoufindout.MainActivity;
import com.example.zzz89.howmuchdidyoufindout.R;
import com.example.zzz89.howmuchdidyoufindout.db.SaveSharedPreference;
import com.example.zzz89.howmuchdidyoufindout.server_api.collection_rest_api;
import com.example.zzz89.howmuchdidyoufindout.server_api.user;
import com.gc.materialdesign.views.ButtonRectangle;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;

/**
 * Created by zzz89 on 2017-10-27.
 */

public class LoginActivity extends AppCompatActivity {
    public static Activity activity;
    private static Context mContext;
    private ButtonRectangle buttonRectangle;
    private MaterialEditText emailEdittext;
    private MaterialEditText passwordEdittext;
    private ProgressDialog dialog;
    private OAuthLogin mOAuthLoginModule;
    private OAuthLoginButton mOAuthLoginButton;
    private String Naver_Client_id = "sCXTBLCAvur3AUGB5xbG";
    private String Naver_client_pass = "AUH3sl9LdF";
    private String Naver_app_name = "얼마를 원해요";
    private collection_rest_api rest_api;

    private OAuthLoginHandler mOAuthHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginModule.getAccessToken(mContext);
                String refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);
                String tokenType = mOAuthLoginModule.getTokenType(mContext);

                NaverAsync naverAsync = new NaverAsync(mOAuthLoginModule, mContext);
                try {
                    String temp = naverAsync.execute(accessToken).get();
                    String e_mail[] = temp.split(" ");
                    Log.d("naver mail", e_mail[0]);
                    SaveSharedPreference.setUserName(LoginActivity.this, e_mail[0]);
                    post_all(e_mail[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        activity = LoginActivity.this;
        MainActivity mainActivity = (MainActivity)MainActivity.activity;
        if(mainActivity != null){
            mainActivity.finish();
        }

        mContext = getApplicationContext();
        emailEdittext = (MaterialEditText) findViewById(R.id.e_mail_address);
        passwordEdittext = (MaterialEditText) findViewById(R.id.Login_password);
        buttonRectangle = (ButtonRectangle) findViewById(R.id.Login_register_button);
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.Login_naver);
        dialog = new ProgressDialog(this);
        buttonRectangle.setOnClickListener(new ButtonRectangle.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_info = emailEdittext.getText().toString();
                String password_info = passwordEdittext.getText().toString();
                if(!check_appropriate_email(email_info)){
                    emailEdittext.setError("적절하지 않은 입력값입니다.");
                    return;
                }

                post_all(email_info);
                SaveSharedPreference.setUserName(LoginActivity.this, email_info);
            }
        });

        mOAuthLoginModule.init(LoginActivity.this, Naver_Client_id, Naver_client_pass, Naver_app_name);
        naver_init_setting();
        rest_api = new collection_rest_api();
        rest_api.start_intent_setting(getApplicationContext());
        rest_api.getProgressbar(dialog, getWindow());
    }

    private void naver_init_setting() {
        mOAuthLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOAuthLoginModule.startOauthLoginActivity(LoginActivity.this, mOAuthHandler);
            }
        });
        mOAuthLoginModule.logout(mContext);
    }

    private void post_all(String username) {
        runProgressBar(username);
        rest_api.retrofit_setting(this);
        rest_api.retrofit_post_user(new user(username, SaveSharedPreference.getToken(LoginActivity.this)));
    }

    private boolean check_appropriate_email(String email){
        Pattern p = Pattern.compile("^[a-zA-Z0-9]+[@]([a-zA-Z0-9]+[\\.][a-zA-Z0-9]*)+$");
        Matcher matcher = p.matcher(email);
        return matcher.find();
    }

    private void runProgressBar(String user_id){
        String message_content = user_id + "로\n가입 중 입니다";
        dialog.show(this, "", message_content);

        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finishAffinity();
    }
}
