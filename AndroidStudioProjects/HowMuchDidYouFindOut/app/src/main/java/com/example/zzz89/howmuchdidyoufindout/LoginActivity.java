package com.example.zzz89.howmuchdidyoufindout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.concurrent.ExecutionException;

/**
 * Created by zzz89 on 2017-10-27.
 */

public class LoginActivity extends AppCompatActivity {
    protected static Activity activity;
    private static Context mContext;
    private ButtonRectangle buttonRectangle;
    private MaterialEditText emailEdittext;
    private MaterialEditText passwordEdittext;
    private OAuthLogin mOAuthLoginModule;
    private OAuthLoginButton mOAuthLoginButton;
    private String Naver_Client_id = "sCXTBLCAvur3AUGB5xbG";
    private String Naver_client_pass = "AUH3sl9LdF";
    private String Naver_app_name = "얼마를 원해요";
    private Intent intent;

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
                    String e_mail = naverAsync.execute(accessToken).get();

                    SaveSharedPreference.setUserName(LoginActivity.this, e_mail);
                    intent = new Intent(LoginActivity.this, AppMainActivity.class);
                    startActivity(intent);

                    Toast.makeText(mContext, "access Token:" + accessToken
                            + ", e-mail:" + e_mail, Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        activity = LoginActivity.this;

        mContext = getApplicationContext();
        emailEdittext = (MaterialEditText)findViewById(R.id.e_mail_address);
        passwordEdittext = (MaterialEditText)findViewById(R.id.Login_password);
        buttonRectangle = (ButtonRectangle)findViewById(R.id.Login_register_button);
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginButton = (OAuthLoginButton)findViewById(R.id.Login_naver);

        buttonRectangle.setOnClickListener(new ButtonRectangle.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email_info = emailEdittext.getText().toString();
                String password_info = passwordEdittext.getText().toString();

                SaveSharedPreference.setUserName(LoginActivity.this, email_info);
                intent = new Intent(LoginActivity.this, AppMainActivity.class);
                startActivity(intent);
            }
        });

        mOAuthLoginModule.init(LoginActivity.this, Naver_Client_id, Naver_client_pass, Naver_app_name);
        naver_init_setting();
    }

    private void naver_init_setting(){
        mOAuthLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOAuthLoginModule.startOauthLoginActivity(LoginActivity.this, mOAuthHandler);
            }
        });
        mOAuthLoginModule.logout(mContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finishAffinity();
    }
}
