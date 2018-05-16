package com.bbs.handlersystem.Network.Message;

public enum MessageType {

    // for testing, simple message
    MSG_DEFAULT,

    // for adding user to database
    MSG_ADD_USER,

    // for take client information
    MSG_REQUEST_CLIENT_INFO,
    MSG_RESPONSE_CLIENT_INFO,

    // for get to client list of games
    MSG_REQUEST_LIST_OF_GAMES,
    MSG_RESPONSE_LIST_OF_GAMES,

    // for send bet transaction
    MSG_REQUEST_TRANSACTION,
    MSG_RESPONSE_TRANSACTION,

    // for oracles
    MSG_REQUEST_ORACLE,
    MSG_RESPONSE_ORACLE;
}
