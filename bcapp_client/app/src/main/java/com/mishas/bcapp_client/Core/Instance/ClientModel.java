package com.mishas.bcapp_client.Core.Instance;

import com.mishas.bcapp_client.Core.Data.Account;
import com.mishas.bcapp_client.Core.Data.User;
import com.mishas.bcapp_client.Core.Data.Wallet;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public final class ClientModel {

    private static volatile ClientModel sClientModel = null;

    public static ClientModel getClientModel() {
        if (sClientModel== null) {
            synchronized (ClientModel.class) {
                if (sClientModel == null)
                    sClientModel = new ClientModel();
            }
        }
        return sClientModel;
    }

    @Getter
    @Setter
    @NonNull
    private static User sUser;

    @Getter
    @Setter
    @NonNull
    private static Wallet sWallet;

    @Getter
    @Setter
    @NonNull
    private static Account sAccount;

    public static void init(@NonNull final User user) {
        sUser = user;
        sWallet = new Wallet(sUser);
        sAccount = new Account(sWallet);
    }

    private ClientModel() {

    }
}
