package com.bbs.handlersystem.Network.Message;

public enum MessageType {

    // for testing, simple message
    MSG_DEFAULT,

    // for adding user to database
    MSG_ADD_USER,

    // for take client information
    MSG_GET_CLIENT_INFO,

    // for get to client list of games
    MSG_GET_LIST_GAMES,

    // for send bet transaction
    MSG_TAKE_TRANSACTION,

    // for oracles
    MSG_REQUEST_ORACLE,
    MSG_RESPONSE_ORACLE;
}
