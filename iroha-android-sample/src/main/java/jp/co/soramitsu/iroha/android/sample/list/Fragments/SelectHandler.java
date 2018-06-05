package jp.co.soramitsu.iroha.android.sample.list.Fragments;

import lombok.NonNull;

public interface SelectHandler {

    void onGameSelected(@NonNull final String name,
                        @NonNull final String team1,
                        @NonNull final String team2,
                        @NonNull final String timestamp);
}
