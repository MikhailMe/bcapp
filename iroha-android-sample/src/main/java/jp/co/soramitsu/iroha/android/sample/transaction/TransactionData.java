package jp.co.soramitsu.iroha.android.sample.transaction;

import com.google.gson.GsonBuilder;

import lombok.Value;

@Value
public class TransactionData {

    private String name;
    private String betSum;
    private String betCoefficient;
    private String team1Name;
    private String team2Name;
    private String timestampGame;

    public String makeDescription() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
