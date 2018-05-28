package com.mishas.bcapp_client.Activities.Fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mishas.bcapp_client.Activities.Fragments.GameListFragment.SelectHandler;
import com.mishas.bcapp_client.Core.Data.Game;
import com.mishas.bcapp_client.Core.Data.Team;
import com.mishas.bcapp_client.Core.Utils.Pair;
import com.mishas.bcapp_client.R;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class Adapter extends RecyclerView.Adapter<Adapter.GameHolder> {

    @NonNull
    private List<Game> mListOfGames;

    @NonNull
    private SelectHandler mSelectHandler;

    @NonNull
    private final WeakReference<LayoutInflater> mInflater;

    Adapter(@NonNull LayoutInflater inflater,
            @NonNull List<Game> listOfGames,
            @NonNull SelectHandler selectHandler) {
        this.mListOfGames = listOfGames;
        this.mSelectHandler = selectHandler;
        this.mInflater = new WeakReference<>(inflater);
    }

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         final int viewType) {
        LayoutInflater inflater = mInflater.get();
        if (inflater != null) {
            return new GameHolder(inflater.inflate(R.layout.list, parent, false));
        } else {
            throw new RuntimeException("Oops, looks like activity is dead");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder,
                                 final int position) {
        Game currentGame = mListOfGames.get(position);
        Pair<Team, Team> teams = currentGame.getTeams();
        String team1Name = teams.getFirst().getName();
        String team2Name = teams.getSecond().getName();
        holder.team1.setText(team1Name);
        holder.team2.setText(team2Name);
        holder.bindGame(currentGame);
    }

    @Override
    public int getItemCount() {
        return mListOfGames.size();
    }

    final class GameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Game mGame;

        @BindView(R.id.team_1_tv_in_list)
        TextView team1;

        @BindView(R.id.team_2_tv_in_list)
        TextView team2;

        GameHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bindGame(@NonNull Game game) {
            this.mGame = game;
        }

        @Override
        public void onClick(View v) {
            Pair<Team, Team> teamPair = mGame.getTeams();
            mSelectHandler.onGameSelected(
                    teamPair.getFirst().getName(),
                    teamPair.getSecond().getName(),
                    mGame.getTimestamp().toString());
        }

    }
}
