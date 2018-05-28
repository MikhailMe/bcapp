package com.mishas.bcapp_client.Activities.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mishas.bcapp_client.Core.Data.Game;
import com.mishas.bcapp_client.Core.Utils.RandomGenerator;
import com.mishas.bcapp_client.R;

import java.util.List;

import lombok.NonNull;


public final class GameListFragment extends Fragment {

    @NonNull
    private RecyclerView mRecyclerViewListOfGames;

    @NonNull
    private SelectHandler mSelectHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @NonNull final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_list, container, false);
        mRecyclerViewListOfGames = view.findViewById(R.id.recycler_view_games);
        Context context = getActivity().getApplicationContext();
        LinearLayoutManager llr = new LinearLayoutManager(context);
        mRecyclerViewListOfGames.setLayoutManager(llr);
        mRecyclerViewListOfGames.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(mRecyclerViewListOfGames.getContext(), llr.getOrientation());
        Drawable drawable = getActivity().getResources().getDrawable(R.drawable.line_divider);
        dividerItemDecoration.setDrawable(drawable);
        mRecyclerViewListOfGames.addItemDecoration(dividerItemDecoration);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerViewListOfGames);
        List<Game> games = RandomGenerator.generateList();
        mRecyclerViewListOfGames.setAdapter(new Adapter(LayoutInflater.from(context), games, mSelectHandler));
        return view;
    }

    public interface SelectHandler {
        void onGameSelected(@NonNull final String team1,
                            @NonNull final String team2,
                            @NonNull final String timestamp);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mSelectHandler = (SelectHandler) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mSelectHandler = null;
    }
}
