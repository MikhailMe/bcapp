package com.bbs.handlersystem.Transaction;

import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Token.Token;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.sql.Timestamp;

public final class TokenTransaction extends Transaction {

    @Getter
    @Setter
    @NonNull
    private Token token;

    public TokenTransaction(@NonNull final User user,
                            @NonNull final Token token,
                            @NonNull final Timestamp timestamp) {
        super(user, timestamp);
        this.token = token;
    }

}
