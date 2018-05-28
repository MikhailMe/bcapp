package com.mishas.bcapp_client.Activities.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mishas.bcapp_client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class AccountFragment extends Fragment {

    @BindView(R.id.list_of_bets_btn)
    Button listOfBetsBtn;

    @BindView(R.id.list_of_visits_btn)
    Button listOfVisitsBtn;

    public AccountFragment() {
    }

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
