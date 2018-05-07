package com.bbs.handlersystem.Network.Models;

import lombok.Getter;
import lombok.NonNull;

import java.util.List;

public class ListModel {

    @Getter
    private List<String> listOfGames;

    public ListModel(@NonNull final List<String> listOfGames) {
        this.listOfGames = listOfGames;
    }
}
