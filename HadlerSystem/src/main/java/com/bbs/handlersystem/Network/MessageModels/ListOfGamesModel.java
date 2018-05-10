package com.bbs.handlersystem.Network.MessageModels;

import lombok.Getter;
import lombok.NonNull;

import java.util.List;

public class ListOfGamesModel extends MessageModel{

    @Getter
    private List<String> listOfGames;

    public ListOfGamesModel(@NonNull final List<String> listOfGames) {
        this.listOfGames = listOfGames;
    }
}
