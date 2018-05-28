package com.mishas.bcapp_client.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mishas.bcapp_client.R;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Getter;
import lombok.NonNull;

public final class BetActivity extends AppCompatActivity {

    @BindView(R.id.team1Tv)
    TextView team1Tv;

    @BindView(R.id.team2Tv)
    TextView team2Tv;

    @BindView(R.id.team1winTv)
    TextView team1WinTv;

    @BindView(R.id.team2winTv)
    TextView team2WinTv;

    @BindView(R.id.team1winCoefBtn)
    Button team1winCoefBtn;

    @BindView(R.id.drawCoefBtn)
    Button drawCoefBtn;

    @BindView(R.id.team2winCoefBtn)
    Button team2winCoefBtn;

    @BindView(R.id.betSumATv)
    AutoCompleteTextView betSumATv;

    @BindView(R.id.yourWinATv)
    AutoCompleteTextView yourWinATv;

    @BindView(R.id.game_date_ATv)
    AutoCompleteTextView gameDateATv;

    @NonNull
    private static final String EXTRA_TEAM_1 = "com.mishas.bcappclient.team1";

    @NonNull
    private static final String EXTRA_TEAM_2 = "com.mishas.bcappclient.team2";

    @NonNull
    private static final String EXTRA_TIMESTAMP = "com.mishas.bcappclient.timestamp";

    private String team1;
    private String team2;
    private String team1Win;
    private String team2Win;
    private String team1WinCoef;
    private String drawCoef;
    private String team2WinCoef;
    private String gameDate;

    @Getter
    private float chooseCoef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
    }

    public static Intent newIntent(Context context, String team1, String team2, String timestamp) {
        Intent intent = new Intent(context, BetActivity.class);
        intent.putExtra(EXTRA_TEAM_1, team1);
        intent.putExtra(EXTRA_TEAM_2, team2);
        intent.putExtra(EXTRA_TIMESTAMP, timestamp);
        return intent;
    }

    private void initView() {
        init();
        chooseCoef = -1.0f;
        gameDateATv.setText(gameDate);
        yourWinATv.setEnabled(false);
        setTeams(team1, team2);
        setCoefs(team1WinCoef, drawCoef, team2WinCoef);

        betSumATv.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty() && getChooseCoef() != -1) {
                    int money = Integer.parseInt(s.toString());
                    float income = money * getChooseCoef();
                    yourWinATv.setText(String.format(Locale.ENGLISH, "%.2f", income));
                } else if (s.toString().isEmpty()) {
                    yourWinATv.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void init() {
        team1 = getIntent().getStringExtra(EXTRA_TEAM_1);
        team2 = getIntent().getStringExtra(EXTRA_TEAM_2);
        gameDate = getIntent().getStringExtra(EXTRA_TIMESTAMP);

        team1Win = team1 + " win";
        team2Win = team2 + " win";
        team1WinCoef = "1.3";
        drawCoef = "1.9";
        team2WinCoef = "2.6";
    }

    private void setTeams(@NonNull final String team1,
                          @NonNull final String team2) {
        team1Tv.setText(team1);
        team2Tv.setText(team2);
        team1WinTv.setText(team1Win);
        team2WinTv.setText(team2Win);
    }


    private void setCoefs(@NonNull final String team1winCoef,
                          @NonNull final String drawCoef,
                          @NonNull final String team2winCoef) {
        team1winCoefBtn.setText(team1winCoef);
        drawCoefBtn.setText(drawCoef);
        team2winCoefBtn.setText(team2winCoef);
    }

    @OnClick({R.id.team1winCoefBtn, R.id.drawCoefBtn, R.id.team2winCoefBtn, R.id.next_to_oracle})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.team1winCoefBtn:
                pushTeam1Win();
                break;
            case R.id.drawCoefBtn:
                pushDraw();
                break;
            case R.id.team2winCoefBtn:
                pushTeam2Win();
                break;
            case R.id.next_to_oracle:
                if (!betSumATv.getText().toString().isEmpty() && !yourWinATv.getText().toString().isEmpty()) {
                    startActivity(new Intent(BetActivity.this, OracleActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Бабки забыл поставить!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void pushTeam1Win() {
        team1winCoefBtn.setBackground(getResources().getDrawable(R.drawable.choose_rectangle));
        team2winCoefBtn.setBackground(getResources().getDrawable(R.drawable.rectangle));
        drawCoefBtn.setBackground(getResources().getDrawable(R.drawable.rectangle));
        setChooseCoef(team1winCoefBtn);

    }

    private void pushDraw() {
        team1winCoefBtn.setBackground(getResources().getDrawable(R.drawable.rectangle));
        team2winCoefBtn.setBackground(getResources().getDrawable(R.drawable.rectangle));
        drawCoefBtn.setBackground(getResources().getDrawable(R.drawable.choose_rectangle));
        setChooseCoef(drawCoefBtn);
    }

    private void pushTeam2Win() {
        team1winCoefBtn.setBackground(getResources().getDrawable(R.drawable.rectangle));
        team2winCoefBtn.setBackground(getResources().getDrawable(R.drawable.choose_rectangle));
        drawCoefBtn.setBackground(getResources().getDrawable(R.drawable.rectangle));
        setChooseCoef(team2winCoefBtn);
    }

    private void setChooseCoef(@NonNull Button button) {
        chooseCoef = Float.parseFloat(button.getText().toString());
        if (!betSumATv.getText().toString().isEmpty()) {
            float income = Integer.parseInt(betSumATv.getText().toString()) * chooseCoef;
            yourWinATv.setText(String.format(Locale.ENGLISH, "%.2f", income));
        } else {
            betSumATv.setText("");
            yourWinATv.setText("");
        }
    }

}
