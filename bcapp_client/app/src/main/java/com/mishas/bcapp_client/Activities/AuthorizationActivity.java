package com.mishas.bcapp_client.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.mishas.bcapp_client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthorizationActivity extends AppCompatActivity {

    @BindView(R.id.checkBtn)
    Button mCheckBtn;

    @BindView(R.id.sendMessageBtn)
    Button mSendMessageBtn;

    @BindView(R.id.resetBtn)
    Button mResetBtn;

    @BindView(R.id.nicknameTIL)
    TextInputLayout mNicknameTIL;

    @BindView(R.id.mobileNumberTIL)
    TextInputLayout mMobileNumberTIL;

    @BindView(R.id.codeTv)
    AutoCompleteTextView mCode;

    @BindView(R.id.nicknameTv)
    AutoCompleteTextView mNickname;

    @BindView(R.id.mobileNumberTv)
    AutoCompleteTextView mMobileNumber;

    @BindView(R.id.codeTil)
    TextInputLayout codeTil;

    private String code;
    private String nickname;
    private String mobileNumber;

    private static final String EMPTY = "";
    private static final String CODE_HINT = "enter code here" ;
    private static final String NICKNAME = "nickname";
    private static final String MOBILE_NUMBER = "mobileNumber";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        mCode.setVisibility(View.INVISIBLE);
        mResetBtn.setVisibility(View.INVISIBLE);
        mCheckBtn.setVisibility(View.INVISIBLE);
    }

    private void sendMessage() {
        nickname = mNickname.getText().toString();
        mobileNumber = mMobileNumber.getText().toString();

        if (!nickname.isEmpty() && !mobileNumber.isEmpty()) {
            mNickname.setEnabled(false);
            mMobileNumber.setEnabled(false);
            mSendMessageBtn.setEnabled(false);
            mNickname.setHintTextColor(getResources().getColor(R.color.yellow));
            mMobileNumber.setHintTextColor(getResources().getColor(R.color.yellow));

            mCode.setText(EMPTY);
            codeTil.setHint(CODE_HINT);
            mCode.setVisibility(View.VISIBLE);
            mResetBtn.setVisibility(View.VISIBLE);
            mCheckBtn.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void check() {
        code = mCode.getText().toString();
        if (!code.isEmpty()) {
            Intent intent = new Intent(AuthorizationActivity.this, BetActivity.class);
            intent.putExtra(NICKNAME, nickname);
            intent.putExtra(MOBILE_NUMBER, mobileNumber);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter code", Toast.LENGTH_SHORT).show();
        }
    }

    private void reset() {
        mCode.setVisibility(View.INVISIBLE);
        mResetBtn.setVisibility(View.INVISIBLE);
        mCheckBtn.setVisibility(View.INVISIBLE);


        codeTil.setHint(EMPTY);
        mNickname.setText(EMPTY);
        mMobileNumber.setText(EMPTY);

        mNickname.setEnabled(true);
        mMobileNumber.setEnabled(true);
        mSendMessageBtn.setEnabled(true);
    }

    @OnClick({R.id.sendMessageBtn, R.id.checkBtn, R.id.resetBtn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMessageBtn:
                sendMessage();
                break;
            case R.id.checkBtn:
                check();
                break;
            case R.id.resetBtn:
                reset();
                break;
        }
    }
}

