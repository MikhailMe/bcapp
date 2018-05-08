package com.bbs.handlersystem.Network.Message;

public enum MessageType {

    // for testing, simple message
    MSG_DEFAULT,

    // for take client information
    MSG_GET_CLIENT_INFO_SYN,
    MSG_GET_CLIENT_INFO_ACK,

    // for get to client list of games
    MSG_GET_LIST_GAMES_SYN,
    MSG_GET_LIST_GAMES_SYN_ACK,
    MSG_GET_LIST_GAMES_ACK,

    // for send bet transaction
    MSG_TAKE_TRANSACTION_SYN,
    MSG_TAKE_TRANSACTION_ACK,

    // for send request about oracle
    MSG_SET_ORACLE_SYN,
    MSG_SET_ORACLE_ACK;
}
