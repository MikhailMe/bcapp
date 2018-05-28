package com.mishas.bcapp_client.Activities.Fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mishas.bcapp_client.Core.Data.Game;
import com.mishas.bcapp_client.Core.Data.Team;
import com.mishas.bcapp_client.Core.Utils.Pair;
import com.mishas.bcapp_client.R;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Adapter extends RecyclerView.Adapter<Adapter.ListHolder> implements View.OnClickListener {

    @NonNull
    private List<Game> mListOfGames;

    @NonNull
    private final WeakReference<LayoutInflater> mInflater;

    public Adapter(@NonNull LayoutInflater inflater,
                   @NonNull List<Game> listOfGames) {
        this.mListOfGames = listOfGames;
        mInflater = new WeakReference<>(inflater);
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = mInflater.get();
        if (inflater != null) {
            return new ListHolder(inflater.inflate(R.layout.list, parent, false));
        } else {
            throw new RuntimeException("Oops, looks like activity is dead");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        Game currentGame = mListOfGames.get(position);
        Pair<Team, Team> teams = currentGame.getTeams();
        String team1Name = teams.getFirst().getName();
        String team2Name = teams.getSecond().getName();
        holder.team1.setText(team1Name);
        holder.team2.setText(team2Name);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mListOfGames.size();
    }

    @Override
    public void onClick(View v) {
        // TODO: implement me
    }

    class ListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.team_1_tv_in_list)
        TextView team1;

        @BindView(R.id.team_2_tv_in_list)
        TextView team2;

        ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
