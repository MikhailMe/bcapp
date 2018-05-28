package com.mishas.bcapp_client.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.mishas.bcapp_client.Core.Data.User;
import com.mishas.bcapp_client.Core.Instance.ClientModel;
import com.mishas.bcapp_client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class AuthorizationActivity extends AppCompatActivity {

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

    private String mNicknameString;
    private String mMobileNumberString;

    private static final String EMPTY = "";
    private static final String CODE_HINT = "Enter code here";
    private static final String CODE_REMINDER = "Please enter code";
    private static final String FILL_FIELD_REMINDER = "Please fill in all fields";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
    }

    private void initView() {
        ClientModel.getClientModel();
        mCode.setVisibility(View.INVISIBLE);
        mResetBtn.setVisibility(View.INVISIBLE);
        mCheckBtn.setVisibility(View.INVISIBLE);
    }

    private void sendMessage() {
        mNicknameString = mNickname.getText().toString();
        mMobileNumberString = mMobileNumber.getText().toString();

        if (!mNicknameString.isEmpty() && !mMobileNumberString.isEmpty()) {
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
            Toast.makeText(getApplicationContext(), FILL_FIELD_REMINDER, Toast.LENGTH_SHORT).show();
        }
    }

    private void check() {
        String code = mCode.getText().toString();
        if (!code.isEmpty()) {
            User user = new User(mNicknameString, mMobileNumberString);
            ClientModel.init(user);
            Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), CODE_REMINDER, Toast.LENGTH_SHORT).show();
        }
    }

    private void reset() {
        initView();

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

