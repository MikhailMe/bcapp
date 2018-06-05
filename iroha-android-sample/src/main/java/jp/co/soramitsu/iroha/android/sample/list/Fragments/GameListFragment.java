package jp.co.soramitsu.iroha.android.sample.list.Fragments;

import android.support.v4.app.Fragment;
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

import java.util.List;
import java.util.Objects;

import jp.co.soramitsu.iroha.android.sample.R;
import jp.co.soramitsu.iroha.android.sample.RandomGenerator;
import jp.co.soramitsu.iroha.android.sample.core.Game;
import jp.co.soramitsu.iroha.android.sample.main.MainActivity;
import lombok.NonNull;

public final class GameListFragment extends Fragment {

    @NonNull
    private RecyclerView mRecyclerViewListOfGames;

    @NonNull
    private SelectHandler mSelectHandler;

    @NonNull
    private String mName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@android.support.annotation.NonNull final LayoutInflater inflater,
                             @NonNull final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game_list, container, false);
        // init recycler view
        mRecyclerViewListOfGames = view.findViewById(R.id.recycler_view_games);
        Context context = Objects.requireNonNull(getActivity()).getApplicationContext();
        LinearLayoutManager llr = new LinearLayoutManager(context);
        mRecyclerViewListOfGames.setLayoutManager(llr);
        mRecyclerViewListOfGames.setHasFixedSize(true);
        // set divider for recycler view
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(mRecyclerViewListOfGames.getContext(), llr.getOrientation());
        Drawable drawable = getActivity().getResources().getDrawable(R.drawable.line_divider);
        dividerItemDecoration.setDrawable(drawable);
        mRecyclerViewListOfGames.addItemDecoration(dividerItemDecoration);
        // centralize objects in recycler view
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerViewListOfGames);
        // generate games
        List<Game> games = RandomGenerator.generateList();

        mRecyclerViewListOfGames.setAdapter(new GameAdapter(LayoutInflater.from(context), mName, games, mSelectHandler));

        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity activity = null;
        if (context instanceof MainActivity){
            activity = (MainActivity) context;
        }
        mName = activity.getMName();
        mSelectHandler = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mSelectHandler = null;
    }
}
