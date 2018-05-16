package com.bbs.handlersystem.Transaction;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public final class BetTransaction extends Transaction {

    @Getter
    @Setter
    @NonNull
    protected int gameId;

    @Getter
    @Setter
    @NonNull
    protected int betToCash;

    @Getter
    @Setter
    @NonNull
    protected int coefficient;

}
