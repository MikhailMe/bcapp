package jp.co.soramitsu.iroha.android.sample.oracle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.co.soramitsu.iroha.android.sample.R;
import jp.co.soramitsu.iroha.android.sample.main.MainActivity;

public final class OracleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oracle);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.yeapBtn, R.id.nopeBtn})
    public void onClick(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }
}