package com.bbs.handlersystem.Token;

import com.bbs.handlersystem.Client.User;

import java.util.HashMap;
import java.util.Map;

public class TokenManager {

    private static Map<User, Token> userTokens;

    public TokenManager() {
        userTokens = new HashMap<>();
    }

    public static void giveNewTokenToUser() {
        //User user = getUser();
        //Token newToken = new Token();
        //userTokens.put(user, newToken);
    }

}
