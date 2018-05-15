import com.bbs.handlersystem.Network.client.Client;

import java.util.Scanner;

public class Cli {

    public static void main(String[] args) throws InterruptedException {

        Client client = new Client();
        client.run();
        Scanner scanner = new Scanner(System.in);
        do {
            client.sendRequestListOfGamesMessage();
            /*System.out.println("write name: ");
            String name = scanner.next();
            System.out.println("write mobile number: ");
            String mobileNumber = scanner.next();
            client.sendUserAddMessage(name, mobileNumber);*/
            System.out.println("One more time ?");
        } while (!new Scanner(System.in).next().equals("n"));
    }
}

