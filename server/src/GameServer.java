import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class GameServer {

    private static Server server;
    private int tcpPort, udpPort;
    private static GameServer instance = new GameServer();

    public static GameServer getInstance() {
        return instance;
    }

    private GameServer() {
        server = new Server();
        tcpPort = Env.getTcpPort();
        udpPort = Env.getUdpPort();
    }

    private void setup() {
        registerClasses();
        startServer();
        setupListeners();
    }

    private void registerClasses() {
        Kryo kryo = server.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
    }

    private void startServer() {
        try {
            server.start();
            server.bind(tcpPort, udpPort);

        } catch (IOException e) {
            server.close();
            System.out.println("Failed to start server");
        }
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
                else {
                    System.out.println(object);
                }
            }
        });
    }

    public static void main(String[] args) {
        GameServer server = getInstance();
        server.setup();
        System.out.println("Server is ready to serve");
    }
}
