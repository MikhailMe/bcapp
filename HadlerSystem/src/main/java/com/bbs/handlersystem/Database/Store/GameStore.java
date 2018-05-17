package com.bbs.handlersystem.Database.Store;

import com.bbs.handlersystem.Data.Game;

public interface GameStore extends Store<Game> {

    String GET_GAME_BY_ID = "SELECT team1, team2, game_date FROM games WHERE id = ?";
    String ADD_GAME_QUERY = "INSERT INTO games (team1, team2, game_date) VALUES  (?, ?, ?)";
    String GET_GAME_ID_QUERY = "SELECT id FROM  games WHERE games.team1 = ? AND games.team2 = ?";

}
