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

public final class OracleActivity extends AppCompatActivity {

    @BindView(R.id.yeapBtn)
    Button yeapBtn;

    @BindView(R.id.nopeBtn)
    Button nopeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oracle);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.yeapBtn, R.id.nopeBtn})
    public void onClick(View v) {
        startActivity(new Intent(OracleActivity.this, MainActivity.class));
    }
}
