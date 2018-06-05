package jp.co.soramitsu.iroha.android.sample.bet;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.net.ConnectException;
import java.util.Locale;

import javax.inject.Inject;

import jp.co.soramitsu.iroha.android.sample.R;
import jp.co.soramitsu.iroha.android.sample.SampleApplication;
import jp.co.soramitsu.iroha.android.sample.databinding.ActivityBetBinding;
import jp.co.soramitsu.iroha.android.sample.oracle.OracleActivity;
import lombok.Getter;
import lombok.NonNull;

public final class BetActivity extends AppCompatActivity implements BetView, View.OnClickListener {

    private static final String FORGET_MESSAGE = "You forget enter a bet sum!";

    private static final String EXTRA_NAME = "com.mishas.bcappclient.name";
    private static final String EXTRA_TEAM_1 = "com.mishas.bcappclient.team1";
    private static final String EXTRA_TEAM_2 = "com.mishas.bcappclient.team2";
    private static final String EXTRA_TIMESTAMP = "com.mishas.bcappclient.timestamp";

    private String name;
    private String team1;
    private String team2;
    private String drawCoef;
    private String team1WinCoef;
    private String team2WinCoef;
    private String timestamp;

    @Inject
    BetPresenter mBetPresenter;

    private ActivityBetBinding binding;

    @Getter
    private float chooseCoef;

    public static Intent newIntent(@NonNull Context context,
                                   @NonNull final String name,
                                   @NonNull final String team1,
                                   @NonNull final String team2,
                                   @NonNull final String timestamp) {
        Intent intent = new Intent(context, BetActivity.class);
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_TEAM_1, team1);
        intent.putExtra(EXTRA_TEAM_2, team2);
        intent.putExtra(EXTRA_TIMESTAMP, timestamp);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bet);
        SampleApplication.instance.getApplicationComponent().inject(this);
        mBetPresenter.setMBetActivity(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
        initListeners();
    }

    private void initView() {
        getGamesParams();
        getGameCoefs();
        chooseCoef = -1.0f;
        binding.gameDateATv.setText(timestamp);
        binding.yourWinATv.setEnabled(false);
        setTeams(team1, team2);
        setCoefs(team1WinCoef, drawCoef, team2WinCoef);

        binding.betSumATv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty() && getChooseCoef() != -1) {
                    int money = Integer.parseInt(s.toString());
                    float income = money * getChooseCoef();
                    binding.yourWinATv.setText(String.format(Locale.ENGLISH, "%.2f", income));
                } else if (s.toString().isEmpty()) {
                    binding.yourWinATv.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getGamesParams() {
        name = getIntent().getStringExtra(EXTRA_NAME);
        team1 = getIntent().getStringExtra(EXTRA_TEAM_1);
        team2 = getIntent().getStringExtra(EXTRA_TEAM_2);
        timestamp = getIntent().getStringExtra(EXTRA_TIMESTAMP);
    }

    private void getGameCoefs() {
        team1WinCoef = "1.3";
        drawCoef = "1.9";
        team2WinCoef = "2.6";
    }

    private void initListeners() {
        binding.team1winCoefBtn.setOnClickListener(this);
        binding.drawCoefBtn.setOnClickListener(this);
        binding.team2winCoefBtn.setOnClickListener(this);
        binding.nextToOracle.setOnClickListener(this);
    }

    private void setTeams(@NonNull final String team1,
                          @NonNull final String team2) {
        binding.team1Tv.setText(team1);
        binding.team2Tv.setText(team2);
        binding.team1winTv.setText(String.format("%s win", team1));
        binding.team2winTv.setText(String.format("%s win", team2));
    }

    private void setCoefs(@NonNull final String team1winCoef,
                          @NonNull final String drawCoef,
                          @NonNull final String team2winCoef) {
        binding.team1winCoefBtn.setText(team1winCoef);
        binding.drawCoefBtn.setText(drawCoef);
        binding.team2winCoefBtn.setText(team2winCoef);
    }

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
                if (!binding.betSumATv.getText().toString().isEmpty() && !binding.yourWinATv.getText().toString().isEmpty()) {

                    mBetPresenter.sendTransaction(name, binding.betSumATv.getText().toString());

                } else {
                    Toast.makeText(getApplicationContext(), FORGET_MESSAGE, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void pushTeam1Win() {
        binding.team1winCoefBtn.setBackground(getResources().getDrawable(R.drawable.choose_rectangle));
        binding.team2winCoefBtn.setBackground(getResources().getDrawable(R.drawable.rectangle));
        binding.drawCoefBtn.setBackground(getResources().getDrawable(R.drawable.rectangle));
        setChooseCoef(binding.team1winCoefBtn);
    }

    private void pushDraw() {
        binding.team1winCoefBtn.setBackground(getResources().getDrawable(R.drawable.rectangle));
        binding.team2winCoefBtn.setBackground(getResources().getDrawable(R.drawable.rectangle));
        binding.drawCoefBtn.setBackground(getResources().getDrawable(R.drawable.choose_rectangle));
        setChooseCoef(binding.drawCoefBtn);
    }

    private void pushTeam2Win() {
        binding.team1winCoefBtn.setBackground(getResources().getDrawable(R.drawable.rectangle));
        binding.team2winCoefBtn.setBackground(getResources().getDrawable(R.drawable.choose_rectangle));
        binding.drawCoefBtn.setBackground(getResources().getDrawable(R.drawable.rectangle));
        setChooseCoef(binding.team2winCoefBtn);
    }

    private void setChooseCoef(@NonNull Button button) {
        chooseCoef = Float.parseFloat(button.getText().toString());
        if (!binding.betSumATv.getText().toString().isEmpty()) {
            float income = Integer.parseInt(binding.betSumATv.getText().toString()) * chooseCoef;
            binding.yourWinATv.setText(String.format(Locale.ENGLISH, "%.2f", income));
        } else {
            binding.betSumATv.setText("");
            binding.yourWinATv.setText("");
        }
    }

    @Override
    public void didSendSuccess() {
        binding.betSumATv.setText("");
        binding.yourWinATv.setText("");
        Toast.makeText(this, getString(R.string.transaction_successful), Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, OracleActivity.class));
    }

    @Override
    public void didSendError(Throwable error) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.error_dialog_title))
                .setMessage(
                        error.getCause() instanceof ConnectException ?
                                getString(R.string.general_error) :
                                error.getLocalizedMessage()
                )
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    if (error.getCause() instanceof ConnectException) {
                        finish();
                    }
                })
                .create();
        alertDialog.show();
    }

}
