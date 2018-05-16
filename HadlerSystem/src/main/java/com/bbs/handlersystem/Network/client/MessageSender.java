package com.bbs.handlersystem.Network.client;

import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Network.ContentMessage.ContentOfTransactionMessage;
import com.bbs.handlersystem.Network.Message.JsonMessage;
import com.bbs.handlersystem.Network.Message.MessageType;
import com.bbs.handlersystem.Network.ContentMessage.ContentOfRequestMessage;
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
    static String getRequestClientInfo() {
        ContentOfRequestMessage contentOfRequestMessage = new ContentOfRequestMessage("get client info");
        JsonMessage jm = new JsonMessage<>(contentOfRequestMessage, MessageType.MSG_REQUEST_CLIENT_INFO);
        return jm.toJson();
    }

    // for get list lis of games
    static String getListOfGames() {
        ContentOfRequestMessage request = new ContentOfRequestMessage("get list of games");
        JsonMessage jm = new JsonMessage<>(request, MessageType.MSG_REQUEST_LIST_OF_GAMES);
        return jm.toJson();
    }

    // for get transaction information
    static String getTransaction(final int gameId,
                                 final int cashToBet,
                                 final int coefficient,
                                 @NonNull final Timestamp timestamp) {
        ContentOfTransactionMessage transactionMessage = new ContentOfTransactionMessage(gameId, cashToBet, coefficient, timestamp);
        JsonMessage jm = new JsonMessage<>(transactionMessage, MessageType.MSG_TAKE_TRANSACTION);
        return jm.toJson();
    }

}
