import com.bbs.handlersystem.Network.Server.NettyServer;

public class Main {

    public static void main(String[] args) {
        NettyServer server = new NettyServer();
        server.startServer();
    }
}
