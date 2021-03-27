import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class GameServer {

    private static Server server = new Server();
    private int tcpPort, udpPort;
    private static GameServer instance = new GameServer();

    public static GameServer getInstance() {
        return instance;
    }

    private GameServer() {
        tcpPort = Env.getTcpPort();
        udpPort = Env.getUdpPort();
    }

    private void setup() {
        try {
            server.start();
            server.bind(tcpPort, udpPort);
            register(SomeRequest.class);
            register(SomeResponse.class);
        } catch (IOException e) {
            server.close();
            System.out.println("Failed to start server");
        }
        this.setupListeners();
    }

    private void setupListeners() {
        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof SomeRequest) {
                    SomeRequest request = (SomeRequest) object;
                    System.out.println(request.text);

                    SomeResponse response = new SomeResponse();
                    response.text = "Thanks";
                    connection.sendTCP(response);
                }
            }
        });
    }

    private void register(Class Message) {
        server.getKryo().register(Message);
    }

    public static void main(String[] args) {
        GameServer server = getInstance();
        server.setup();
        System.out.println("Server is ready to serve");
    }

}
