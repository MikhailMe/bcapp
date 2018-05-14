import com.bbs.handlersystem.Network.client.Client;

import java.util.Scanner;

public class Cli {

    public static void main(String[] args) {

        Client client = new Client();
        client.run();

        do {
            client.sendMessage();
            System.out.println("Go ?");
        } while (!new Scanner(System.in).next().equals("n"));

        client.closeClient();
    }
}
