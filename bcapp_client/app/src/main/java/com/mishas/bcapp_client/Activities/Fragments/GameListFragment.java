package com.mishas.bcapp_client.Activities.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mishas.bcapp_client.Core.Data.Game;
import com.mishas.bcapp_client.Core.Utils.GamesGenerator;
import com.mishas.bcapp_client.R;

import java.util.List;


public final class GameListFragment extends Fragment {

    RecyclerView listOfGames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_list, container, false);
        listOfGames = view.findViewById(R.id.recycler_view_games);
        Context context = getActivity().getApplicationContext();
        listOfGames.setLayoutManager(new LinearLayoutManager(context));
        listOfGames.setHasFixedSize(true);
        List<Game> games = GamesGenerator.generateList();
        listOfGames.setAdapter(new Adapter(LayoutInflater.from(context), games));
        return view;
    }

}
