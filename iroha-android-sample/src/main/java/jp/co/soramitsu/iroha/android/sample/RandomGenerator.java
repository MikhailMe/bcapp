package jp.co.soramitsu.iroha.android.sample;

import android.util.Pair;

import jp.co.soramitsu.iroha.android.sample.core.Game;
import jp.co.soramitsu.iroha.android.sample.core.Team;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public final class RandomGenerator {

    private static final double DELIMITER = 0.5d;
    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lower = upper.toLowerCase(Locale.ROOT);

    private final static List<String> teamNames = Arrays.asList(
            "Lokomotiv", "CSKA", "Spartak", "Krasnodar", "Zenit",
            "Ufa", "Arsenal", "Dinamo", "Ahmat", "Rubin", "Rostov",
            "Ural", "Amkar", "Anzhi", "Tosno", "CKA");

    private static int randInt() {
        return new Random().nextInt(10) + 1;
    }

    @NonNull
    public static String randomString(final int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            if (new Random().nextDouble() > DELIMITER) {
                sb.append(upper.charAt(randInt()));
            } else {
                sb.append(lower.charAt(randInt()));
            }
        }
        return sb.toString();
    }

    @NonNull
    public static String randomDigitsString(final int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(randInt());
        }
        return sb.toString();
    }

    @NonNull
    public static List<Game> generateList() {
        List<Game> listOfGames = new ArrayList<>();
        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < teamNames.size(); i++) {
            teams.add(new Team(teamNames.get(i)));
        }
        Collections.shuffle(teams);
        for (int i = 0; i < teamNames.size(); i += 2) {
            Pair<Team, Team> pairTeam = new Pair<>(teams.get(i), teams.get(i + 1));
            listOfGames.add(new Game(pairTeam));
        }
        return listOfGames;
    }

}