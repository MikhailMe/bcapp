package jp.co.soramitsu.iroha.android.sample.list.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.soramitsu.iroha.android.sample.R;
import lombok.NonNull;

public final class AccountFragment extends Fragment {

    @BindView(R.id.list_of_bets_btn)
    Button listOfBetsBtn;

    @BindView(R.id.list_of_visits_btn)
    Button listOfVisitsBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

}
