package com.bbs.handlersystem.Client;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;

public class User {

    @Getter
    @Setter
    @NonNull
    private String nickname;

    @Getter
    @Setter
    @NonNull
    private String mobileNumber;

    @Getter
    @Setter
    @NonNull
    private boolean hasToken;

    @Getter
    @Setter
    @NonNull
    private boolean isOracle;

    public User(@NonNull final String nickname,
                @NonNull final String mobileNumber) {
        this.hasToken = false;
        this.isOracle = false;
        this.nickname = nickname;
        this.mobileNumber = mobileNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nickname, mobileNumber, hasToken, isOracle);
    }

    @Override
    public String toString() {
        return nickname;
    }

}
