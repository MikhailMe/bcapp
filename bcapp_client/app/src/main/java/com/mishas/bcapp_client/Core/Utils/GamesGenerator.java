package com.mishas.bcapp_client.Core.Utils;

import com.mishas.bcapp_client.Core.Data.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GamesGenerator {

    List teamNames = Arrays.asList(
            "Lokomotiv", "CSKA", "Spartak", "Krasnodar", "Zenit",
            "Ufa", "Arsenal", "Dinamo", "Ahmat", "Rubin", "Rostov",
            "Ural", "Amkar", "Anzhi", "Tosno", "CKA");

    public static List<Game> generateList() {
        return new ArrayList<>();
    }
}
