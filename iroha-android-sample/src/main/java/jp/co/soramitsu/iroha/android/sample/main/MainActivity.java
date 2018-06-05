package jp.co.soramitsu.iroha.android.sample.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.net.ConnectException;

import javax.inject.Inject;

import jp.co.soramitsu.iroha.android.sample.Constants;
import jp.co.soramitsu.iroha.android.sample.R;
import jp.co.soramitsu.iroha.android.sample.SampleApplication;
import jp.co.soramitsu.iroha.android.sample.bet.BetActivity;
import jp.co.soramitsu.iroha.android.sample.databinding.ActivityMainBinding;
import jp.co.soramitsu.iroha.android.sample.list.Fragments.GameListFragment;
import jp.co.soramitsu.iroha.android.sample.list.Fragments.SelectHandler;
import jp.co.soramitsu.iroha.android.sample.main.history.HistoryFragment;
import jp.co.soramitsu.iroha.android.sample.registration.RegistrationActivity;
import lombok.Getter;
import lombok.NonNull;

public class MainActivity extends AppCompatActivity implements MainView, SelectHandler {

    private ActivityMainBinding binding;

    @NonNull
    private ProgressDialog dialog;

    @Getter
    @NonNull
    private String mName;

    @Inject
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        SampleApplication.instance.getApplicationComponent().inject(this);
        presenter.setView(this);

        createProgressDialog();
        configureRefreshLayout();

        RxView.clicks(binding.logout)
                .subscribe(v -> {
                    AlertDialog alertDialog = new AlertDialog.Builder(this)
                            .setTitle(getString(R.string.logout))
                            .setMessage(getString(R.string.logout_description))
                            .setCancelable(true)
                            .setPositiveButton(android.R.string.ok, (dialog, which) -> presenter.logout())
                            .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
                            .create();
                    alertDialog.setOnShowListener(arg0 ->
                            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                                    .setTextColor(getResources().getColor(R.color.hint)));
                    alertDialog.setOnShowListener(arg0 ->
                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setTextColor(getResources().getColor(R.color.iroha)));
                    alertDialog.show();
                });

        RxView.clicks(binding.bio)
                .subscribe(v -> {
                    LayoutInflater inflater = LayoutInflater.from(this);
                    final View view = inflater.inflate(R.layout.dialog_account_details, null);
                    final EditText details = view.findViewById(R.id.details);
                    final TextView symbolsLeft = view.findViewById(R.id.symbols_left);

                    symbolsLeft.setText(String.valueOf(Constants.MAX_ACCOUNT_DETAILS_SIZE));

                    RxTextView.textChanges(details)
                            .map(CharSequence::toString)
                            .subscribe(text ->
                                    symbolsLeft.setText(String.valueOf(Constants.MAX_ACCOUNT_DETAILS_SIZE - text.length())));

                    AlertDialog alertDialog = new AlertDialog.Builder(this)
                            .setTitle(getString(R.string.account_details))
                            .setMessage(getString(R.string.bio))
                            .setCancelable(true)
                            .setPositiveButton(android.R.string.ok, (dialog, which) -> presenter.setAccountDetails(details.getText().toString()))
                            .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
                            .create();


                    alertDialog.setOnShowListener(arg0 ->
                            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                                    .setTextColor(getResources().getColor(R.color.hint)));
                    alertDialog.setOnShowListener(arg0 ->
                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setTextColor(getResources().getColor(R.color.iroha)));

                    alertDialog.setView(view);
                    alertDialog.show();
                });

        setupViewPager();
        binding.tabs.setupWithViewPager(binding.content);

        presenter.onCreate();
    }

    private void configureRefreshLayout() {
        binding.swiperefresh.setOnRefreshListener(presenter::updateData);
    }

    private void setupViewPager() {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new GameListFragment(), "LIST OF GAMES");
        adapter.addFragment(new HistoryFragment(), "HISTORY");
        binding.content.setAdapter(adapter);
    }

    @Override
    public void setUsername(@NonNull final String username) {
        binding.username.setText(username);
        mName = username;
    }

    @Override
    public void setAccountDetails(@NonNull final String details) {
        binding.bio.setText(details.isEmpty() ? getString(R.string.bio) : details);
    }

    @Override
    public void setAccountBalance(@NonNull final String balance) {
        binding.balance.setText(balance);
    }

    @Override
    public void showRegistrationScreen() {
        startActivity(new Intent(this, RegistrationActivity.class));
        finish();
    }

    @Override
    public void showProgress() {
        dialog.show();
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }

    @Override
    public void showError(Throwable throwable) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.error_dialog_title))
                .setMessage(
                        throwable.getCause() instanceof ConnectException ?
                                getString(R.string.general_error) :
                                throwable.getLocalizedMessage()
                )
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    if (throwable.getCause() instanceof ConnectException) {
                        finish();
                    }
                })
                .create();
        alertDialog.show();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void refreshData(boolean animate) {
        presenter.updateData();
    }

    private void createProgressDialog() {
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage(getString(R.string.please_wait));
    }

    @Override
    public void onGameSelected(@NonNull final String name,
                               @NonNull final String team1,
                               @NonNull final String team2,
                               @NonNull final String timestamp) {
        startActivity(BetActivity.newIntent(this, name, team1, team2, timestamp));
    }
}
