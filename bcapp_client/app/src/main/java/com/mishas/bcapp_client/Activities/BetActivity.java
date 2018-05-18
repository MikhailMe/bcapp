package com.mishas.bcapp_client.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.mishas.bcapp_client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Getter;
import lombok.NonNull;

public class BetActivity extends AppCompatActivity {

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


    private String team1;
    private String team2;
    private String team1Win;
    private String team2Win;
    private String team1WinCoef;
    private String drawCoef;
    private String team2WinCoef;

    @Getter
    private float chooseCoef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        init();
        chooseCoef = -1.0f;

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
                    String income = String.valueOf(money * getChooseCoef());
                    yourWinATv.setText(income);
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
        // here need init team1, team2, team1WinCoef, drawCoef, team2WinCoef
        team1 = "cska";
        team2 = "zenit";
        team1Win = team1 + " win";
        team2Win = team2 + " win";
        team1WinCoef = "1.0";
        drawCoef = "2.0";
        team2WinCoef = "3.0";
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

    @OnClick({R.id.team1winCoefBtn, R.id.drawCoefBtn, R.id.team2winCoefBtn})
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

    private void setChooseCoef(Button button) {
        chooseCoef = Float.parseFloat(button.getText().toString());
        if (!betSumATv.getText().toString().isEmpty()) {
            float income = Integer.parseInt(betSumATv.getText().toString()) * chooseCoef;
            yourWinATv.setText(String.valueOf(income));
        } else {
            betSumATv.setText("");
            yourWinATv.setText("");
        }
    }

}
