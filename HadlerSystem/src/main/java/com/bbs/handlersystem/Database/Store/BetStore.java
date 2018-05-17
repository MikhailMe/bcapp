package com.bbs.handlersystem.Database.Store;

import com.bbs.handlersystem.Client.Bet;

public interface BetStore extends Store<Bet> {

    String ADD_BET_QUERY = "INSERT INTO bets (game_id, cash_to_bet, user_id, bet_date, coefficient) VALUES (?, ?, ?, ?, ?)";
    String GET_BET_ID_QUERY = "SELECT game_id, cash_to_bet, user_id, bet_date, coefficient FROM bets WHERE id = ?";
    String GET_BET_BY_ID_QUERY = "SELECT id FROM bets WHERE user_id = ?";
}
