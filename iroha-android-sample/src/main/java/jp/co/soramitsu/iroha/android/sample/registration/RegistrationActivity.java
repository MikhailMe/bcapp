package jp.co.soramitsu.iroha.android.sample.registration;

import android.os.Bundle;
import android.content.Intent;
import android.app.ProgressDialog;
import android.view.WindowManager;
import android.annotation.SuppressLint;
import android.support.v7.app.AlertDialog;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;

import com.jakewharton.rxbinding2.view.RxView;

import java.net.ConnectException;

import javax.inject.Inject;

import jp.co.soramitsu.iroha.android.sample.R;
import jp.co.soramitsu.iroha.android.sample.SampleApplication;
import jp.co.soramitsu.iroha.android.sample.main.MainActivity;
import jp.co.soramitsu.iroha.android.sample.databinding.ActivityRegistrationBinding;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView {

    private ProgressDialog mProgressDialog;

    private ActivityRegistrationBinding binding;

    @Inject
    RegistrationPresenter mRegistrationPresenter;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        SampleApplication.instance.getApplicationComponent().inject(this);
        mRegistrationPresenter.setMView(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (mRegistrationPresenter.isRegistered()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        createProgressDialog();

        RxView.clicks(binding.singup)
                .subscribe(view -> {
                    showProgressDialog();
                    mRegistrationPresenter.createAccount(binding.username.getText().toString().trim());
                });
    }

    @Override
    public void didRegistrationSuccess() {
        dismissProgressDialog();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void didRegistrationError(Throwable error) {
        dismissProgressDialog();
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.error_dialog_title))
                .setMessage(error.getCause() instanceof ConnectException ?
                        getString(R.string.general_error) :
                        error.getLocalizedMessage())
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    if (error.getCause() instanceof ConnectException) {
                        finish();
                    }
                })
                .create();
        alertDialog.show();
    }

    private void createProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.please_wait));
    }

    @Override
    public void onStop() {
        super.onStop();
        mRegistrationPresenter.onStop();
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }
}
