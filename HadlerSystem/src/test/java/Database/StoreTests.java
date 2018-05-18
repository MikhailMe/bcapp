package Database;

import com.bbs.handlersystem.Client.Account;
import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Client.Wallet;
import com.bbs.handlersystem.Database.Store.AccountStore;
import com.bbs.handlersystem.Database.Store.UserStore;
import com.bbs.handlersystem.Database.Store.WalletStore;
import com.bbs.handlersystem.Database.StoreImpl.AccountStoreImpl;
import com.bbs.handlersystem.Database.StoreImpl.UserStoreImpl;
import com.bbs.handlersystem.Database.StoreImpl.WalletStoreImpl;
import com.bbs.handlersystem.Utils.RandomGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.logging.Logger;

public class StoreTests {

    private User user1;
    private User user2;
    private Wallet wallet1;
    private Wallet wallet2;
    private Account account1;
    private Account account2;

    private static String name1;
    private static String name2;
    private static String mobileNumber1;
    private static String mobileNumber2;

    private UserStore userStore;
    private WalletStore walletStore;
    private AccountStore accountStore;

    private static final int LENGTH = 10;
    private static Logger log = Logger.getLogger(StoreTests.class.getName());

    {
        userStore = new UserStoreImpl();
        walletStore = new WalletStoreImpl();
        accountStore = new AccountStoreImpl();
    }

    @Test
    public void testInitializer() {
        log.info("started initializer test");
        Assert.assertNotNull(userStore);
        Assert.assertNotNull(walletStore);
        Assert.assertNotNull(accountStore);
        log.info("finished initializer test");
    }

    private void generateData() {
        generateNames();
        generateMobileNumbers();
    }

    private void generateNames() {
        log.info("started generating names");
        name1 = RandomGenerator.createRandomString(LENGTH);
        name2 = RandomGenerator.createRandomString(LENGTH);
        log.info("finished generating names");
    }

    private void generateMobileNumbers() {
        log.info("started generating mobile numbers");
        mobileNumber1 = RandomGenerator.createRandomDigitsString(LENGTH);
        mobileNumber2 = RandomGenerator.createRandomDigitsString(LENGTH);
        log.info("finished generating mobile numbers");
    }

    private void createUsers() {
        log.info("started creating users");
        user1 = new User(name1, mobileNumber1);
        user2 = new User(name2, mobileNumber2);
        log.info("finished creating users");
    }

    @Test
    public void testCreateUsers() {
        log.info("started creating user test");
        Assert.assertNull(user1);
        Assert.assertNull(user2);
        log.info("finished creating user test");
    }

    private void putUsersToStore() throws SQLException {
        log.info("started putting users to store");
        userStore.add(user1);
        userStore.add(user2);
        log.info("finished putting users to store");
    }

    @Test
    public void testPutUsersToStore() {
        log.info("started putting users to store");
        Assert.assertEquals(userStore.size(), 2);
        log.info("finished putting users to store");
    }

    private void createWallets() {
        log.info("started creating wallets");
        wallet1 = new Wallet(user1);
        wallet2 = new Wallet(user2);
        log.info("finished creating wallets");
    }

    @Test
    public void testCreateWallets() {
        log.info("started creating wallet test");
        Assert.assertNull(wallet1);
        Assert.assertNull(wallet2);
        log.info("finished creating wallet test");
    }

    private void putWalletsToStore() throws SQLException {
        log.info("started putting wallets to store");
        walletStore.add(wallet1);
        walletStore.add(wallet2);
        log.info("finished putting wallets to store");
    }

    @Test
    public void testPutWalletsToStore() {
        log.info("started putting users to store");
        Assert.assertEquals(walletStore.size(), 2);
        log.info("finished putting users to store");
    }

    private void createAccounts() {
        log.info("started creating accounts");
        account1 = new Account(wallet1);
        account2 = new Account(wallet2);
        log.info("finished creating accounts");
    }

    @Test
    public void testCreateAccounts() {
        log.info("started creating account test");
        Assert.assertNull(account1);
        Assert.assertNull(account2);
        log.info("finished creating account test");
    }

    private void putAccountsToStore() throws SQLException {
        log.info("started putting accounts to store");
        accountStore.add(account1);
        accountStore.add(account2);
        log.info("finished putting accounts to store");
    }

    @Test
    public void testPutAccountsToStore() {
        log.info("started putting accounts to store");
        Assert.assertEquals(accountStore.size(), 2);
        log.info("finished putting accounts to store");
    }

    private void putObjectsToStore() throws SQLException {
        log.info("started putting account test");
        putUsersToStore();
        putWalletsToStore();
        putAccountsToStore();
        log.info("finished creating account test");
    }

    private void before() throws SQLException {
        log.info("started method: before()");
        generateData();
        createUsers();
        createWallets();
        createAccounts();
        putObjectsToStore();
        log.info("finished method: before()");
    }

    @Test
    public void testAccountIds() throws SQLException {
        log.info("started method: testIds()");
        before();
        long id1 = accountStore.getId(user1.getNickname());
        assert id1 >= 0;
        long id2 = accountStore.getId(user2.getNickname());
        assert id2 >= 0;
        Assert.assertEquals(++id1, id2);
        after();
        log.info("finished method: testIds()");
    }

    private void after() {
        log.info("started method: after()");
        user1 = null;
        user2 = null;
        wallet1 = null;
        wallet2 = null;
        account1 = null;
        account2 = null;
        accountStore = null;
        log.info("finished method: after()");
    }

}
