package com.bbs.handlersystem.Client;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

// TODO: всё запихать в базку

public class User {

    @Getter
    @Setter
    @NonNull
    private String nickname;

    @Getter
    @Setter
    private boolean hasToken;

    @Getter
    @Setter
    private boolean isOracle;

    public User(@NonNull final String nickname) {
        this.nickname = nickname;
        this.hasToken = false;
        this.isOracle = false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + nickname.hashCode();
        hash = 31 * hash + (hasToken ? 0 : 1);
        hash = 31 * hash + (isOracle ? 0 : 1);
        return hash;
    }

    @Override
    public String toString() {
        return nickname;
    }

}
