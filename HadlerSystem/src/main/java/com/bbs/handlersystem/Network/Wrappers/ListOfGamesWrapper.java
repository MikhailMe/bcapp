package com.bbs.handlersystem.Network.Wrappers;

import com.bbs.handlersystem.Data.Game;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class ListOfGamesWrapper {

    @Getter
    @Setter
    @NonNull
    List<Game> listOfGames;

    public ListOfGamesWrapper(@NonNull final List<Game> listOfGames) {
        this.listOfGames = listOfGames;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), listOfGames);
    }

    @Override
    public String toString() {
        return Arrays.toString(listOfGames.toArray());
    }
}
