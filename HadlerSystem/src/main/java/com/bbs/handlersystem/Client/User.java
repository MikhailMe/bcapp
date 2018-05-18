package com.bbs.handlersystem.Client;

import com.bbs.handlersystem.Database.StoreImpl.MainStore;
import com.bbs.handlersystem.Token.Token;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.sql.SQLException;
import java.util.Objects;

public final class User {

    @Getter
    @NonNull
    private String nickname;

    @Getter
    @NonNull
    private String mobileNumber;

    @Getter
    @NonNull
    private Token token;

    @Setter
    @Getter
    private long tokenId;

    @Getter
    private boolean isOracle;


    public User(@NonNull final String nickname,
                @NonNull final String mobileNumber) {
        this.isOracle = false;
        this.nickname = nickname;
        this.mobileNumber = mobileNumber;
    }

    public void setNickname (@NonNull final String nickname) {
        this.nickname = nickname;
        try {
            MainStore.userStore.updateNickname(nickname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setMobileNumber(@NonNull final String mobileNumber) {
        this.mobileNumber = mobileNumber;
        try {
            MainStore.userStore.updateMobileNumber(mobileNumber, getNickname());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setToken(@NonNull final Token token) {
        this.token = token;
        try {
            this.tokenId = MainStore.tokenStore.add(token);
            //MainStore.tokenStore.removeTokenById(--tokenId);
            //MainStore.tokenStore.updateTokenById(++tokenId);
            //MainStore.userStore.updateToken(tokenId, getNickname());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setIsOracle(final boolean isOracle) {
        this.isOracle = isOracle;
        try {
            MainStore.userStore.updateOracle(isOracle, getNickname());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nickname, mobileNumber, token, tokenId, isOracle);
    }

    @Override
    public String toString() {
        return nickname;
    }

}
