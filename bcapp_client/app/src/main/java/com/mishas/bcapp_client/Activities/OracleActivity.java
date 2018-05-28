package com.mishas.bcapp_client.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mishas.bcapp_client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OracleActivity extends AppCompatActivity {

    @BindView(R.id.yeapBtn)
    Button yeapBtn;

    @BindView(R.id.nopeBtn)
    Button nopeBtn;

    private boolean isOralce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oracle);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.yeapBtn, R.id.nopeBtn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yeapBtn:
                isOralce = true;
                startActivity(new Intent(OracleActivity.this, MainActivity.class));
                break;
            case R.id.nopeBtn:
                isOralce = false;
                startActivity(new Intent(OracleActivity.this, MainActivity.class));
                break;
        }
    }
}
