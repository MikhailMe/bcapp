package com.bbs.handlersystem.Network.ContentMessage;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

public class ContentOfGameMessage {

    @Getter
    @Setter
    @NonNull
    private String team1;

    @Getter
    @Setter
    @NonNull
    private String team2;

    @Getter
    @Setter
    @NonNull
    private Timestamp timestamp;

    public ContentOfGameMessage(@NonNull final String team1,
                                @NonNull final String team2,
                                @NonNull final Timestamp timestamp) {
        this.team1 = team1;
        this.team2 = team2;
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), team1, team2, timestamp);
    }

    @Override
    public String toString() {
        return team1 + " : " + team2 + " at " + timestamp;
    }
}
