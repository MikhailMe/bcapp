package jp.co.soramitsu.iroha.android.sample;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.VisibleForTesting;

import lombok.Getter;
import lombok.NonNull;

import javax.inject.Inject;

import jp.co.soramitsu.iroha.android.Keypair;
import jp.co.soramitsu.iroha.android.ModelCrypto;

public class PreferencesUtil {

    @VisibleForTesting
    public static final String SHARED_PREFERENCES_FILE = "shared_preferences_file";

    @VisibleForTesting
    public static final String SAVED_USERNAME = "saved_username";

    private static final String SAVED_PRIVATE_KEY = "saved_private_key";
    private static final String SAVED_PUBLIC_KEY = "saved_public_key";

    private final ModelCrypto mModelCrypto;

    @Getter
    private final SharedPreferences mPreferences;

    @Inject
    public PreferencesUtil(@NonNull ModelCrypto modelCrypto) {
        this.mModelCrypto = modelCrypto;
        mPreferences = SampleApplication.instance.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
    }

    public void saveUsername(@NonNull final String username) {
        mPreferences.edit().putString(SAVED_USERNAME, username).apply();
    }

    public String retrieveUsername() {
        return mPreferences.getString(SAVED_USERNAME, "");
    }

    public void saveKeys(@NonNull Keypair keyPair) {
        mPreferences.edit().putString(SAVED_PUBLIC_KEY, keyPair.publicKey().hex()).apply();
        mPreferences.edit().putString(SAVED_PRIVATE_KEY, keyPair.privateKey().hex()).apply();
    }

    public Keypair retrieveKeys() {
        return mModelCrypto.convertFromExisting(
                mPreferences.getString(SAVED_PUBLIC_KEY, ""),
                mPreferences.getString(SAVED_PRIVATE_KEY, "")
        );
    }

    public void clear() {
        mPreferences.edit().clear().apply();
    }
}
