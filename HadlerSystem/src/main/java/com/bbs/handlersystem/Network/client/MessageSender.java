package com.bbs.handlersystem.Network.client;

import com.bbs.handlersystem.Client.Bet;
import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Coefficients.Coefficient;
import com.bbs.handlersystem.Network.Message.JsonMessage;
import com.bbs.handlersystem.Network.Message.MessageType;
import com.bbs.handlersystem.Network.ContentMessage.ContentOfSimpleMessage;
import lombok.NonNull;

import java.sql.Timestamp;

public class MessageSender {

    // for adding user to database
    static String getUserAddMessage(@NonNull final String name,
                                    @NonNull final String mobileNumber) {
        User user = new User(name, mobileNumber);
        JsonMessage jm = new JsonMessage<>(user, MessageType.MSG_ADD_USER);
        return jm.toJson();
    }

    // for get client information (name, balance, listOfBets, listOfVisits, currentVisit)
    static String getRequestClientInfo(String name) {
        ContentOfSimpleMessage contentOfSimpleMessage = new ContentOfSimpleMessage(name);
        JsonMessage jm = new JsonMessage<>(contentOfSimpleMessage, MessageType.MSG_REQUEST_CLIENT_INFO);
        return jm.toJson();
    }

    // for get list lis of games
    static String getListOfGames() {
        String stringToSend = "get list of games";
        ContentOfSimpleMessage request = new ContentOfSimpleMessage(stringToSend);
        JsonMessage jm = new JsonMessage<>(request, MessageType.MSG_REQUEST_LIST_OF_GAMES);
        return jm.toJson();
    }

    // for get bet transaction information
    static String getBetTransaction(final int gameId,
                                    final int cashToBet,
                                    @NonNull final User user,
                                    @NonNull final Timestamp timestamp,
                                    @NonNull final Coefficient coefficient) {
        Bet bet = new Bet(gameId, cashToBet, user, timestamp, coefficient);
        JsonMessage jm = new JsonMessage<>(bet, MessageType.MSG_REQUEST_TRANSACTION);
        return jm.toJson();
    }

    // for get token transaction information
    static String getTokenTransaction(@NonNull final String oracle) {
        ContentOfSimpleMessage oracleMessage = new ContentOfSimpleMessage(oracle);
        JsonMessage jm = new JsonMessage<>(oracleMessage, MessageType.MSG_REQUEST_ORACLE);
        return jm.toJson();
    }

}
