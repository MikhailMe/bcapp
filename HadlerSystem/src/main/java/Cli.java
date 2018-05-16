import com.bbs.handlersystem.Network.client.Client;

import java.sql.Timestamp;
import java.util.Scanner;

public class Cli {

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        client.run();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter number:");
            System.out.println("1: add user to database");
            System.out.println("2: request to get client information");
            System.out.println("3: request to get list of games");
            System.out.println("4: send bet transaction");
            System.out.println("5: send token transaction");
            System.out.println("6: exit");
            switch (scanner.nextInt()) {
                case 1:
                    System.out.println("write name: ");
                    String name = scanner.next();
                    System.out.println("write mobile number: ");
                    String mobileNumber = scanner.next();
                    client.sendUserAddMessage(name, mobileNumber);
                    break;
                case 2:
                    client.sendRequestClientInfoMessage();
                    break;
                case 3:
                    client.sendRequestListOfGamesMessage();
                    break;
                case 4:
                    System.out.println("write gameId: ");
                    int gameId = scanner.nextInt();
                    System.out.println("write cash to bet: ");
                    int cashToBet = scanner.nextInt();
                    // get coefficient from list of games
                    int coefficient = 10;
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    client.sendBetTransactionMessage(gameId, cashToBet, coefficient, timestamp);
                    break;
                case 5:
                    // TODO
                    return;
                case 6:
                    System.out.println("Goodbuy");
                    client.closeClient();
                    return;
                default:
                    System.out.println("Invalid number");
                    System.out.println("Do you want try one more time ? 0/1");
                    if (scanner.nextInt() == 1) {
                        System.out.println("Goodbuy");
                        return;
                    }
                    break;
            }
        }
    }
}

