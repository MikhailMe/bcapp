import com.bbs.handlersystem.Network.Server.NettyServer;

public class Main {

    public static void main(String[] args) {
        /*NettyServer server = new NettyServer();
        server.startServer();*/

        String sensorid = "admin@test";
        boolean status = true;
        Sender sender = null;
        try {
            sender = new Sender("localhost", 50051);
            String comment = "comment";
            String verifierId = "test@test";
            //sender.sendVerifierUpdate(verifierId, sensorid, status, comment);
            System.out.println("Sending message succeed! verifierId: " + verifierId + ", sensorId: " + sensorid + ", status: " + status + ", comment: " + comment);
        } catch (Exception e) {
            System.out.println("Error while sending message: " + e.getMessage());
        } finally {
            try {
                if (sender != null) {
                    sender.shutdown();
                }
            } catch (InterruptedException e) {
                System.out.println("Error while shutdown sender: " + e.getMessage());
            }
        }
    }
}
