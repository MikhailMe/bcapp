package com.bbs.handlersystem.Network.Message;

import com.bbs.handlersystem.Client.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageMaker {

    private static Gson gson;

    static {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public static String getAddUserMessage(User user) {
        return gson.toJson(user);
    }

    public static String getClientInfoMessage() {
        return null;
    }

    public static String getListOfGamesMessage() {
        return null;
    }

    public static String takeTranscationMessage() {
        return null;
    }

    public static String requestOracleMessage() {
        return null;
    }

    public static String responseOracleMessage() {
        return null;
    }

    public static String defaultMessage() {
        return null;
    }
}
