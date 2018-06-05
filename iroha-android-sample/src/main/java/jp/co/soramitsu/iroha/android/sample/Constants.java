package jp.co.soramitsu.iroha.android.sample;

public interface Constants {

    // keys section
    String PUBLIC_KEY = "889f6b881e331be21487db77dcf32c5f8d3d5e8066e78d2feac4239fe91d416f";
    String PRIVATE_KEY = "0f0ce16d2afbb8eca23c7d8c2724f0c257a800ee2bbd54688cec6b898e3f7e33";

    // major definitions section
    String DOMAIN_ID = "test";
    String ASSET_CAPTION = "irh";
    String CREATOR_CAPTION = "admin";
    String ACCOUNT_DETAILS = "account_details";

    // delimiters section
    String DELIMITER_FOR_ASSET = "#";
    String DELIMITER_FOR_ACCOUNT = "@";

    // ids section
    String ASSET_ID = ASSET_CAPTION + DELIMITER_FOR_ASSET + DOMAIN_ID;
    String CREATOR_ID = CREATOR_CAPTION + DELIMITER_FOR_ACCOUNT + DOMAIN_ID;

    // digits parameters section
    long QUERY_COUNTER = 1;
    String DEFAULT_BALANCE = "500";
    int MAX_ACCOUNT_DETAILS_SIZE = 32;
    int CONNECTION_TIMEOUT_SECONDS = 20;

    // messages section
    String TRANSACTION_FAILED = "Transaction failed";
    String TRANSACTION_SUCCESS = "Transaction success";
}
