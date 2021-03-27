import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class GameServer {

    private static Env env = new Env();
    private static Server server = new Server();
    private int tcpPort = env.getTcpPort(), udpPort = env.getUdpPort();
    private GameServer gameServer = new GameServer();

    private GameServer() {

    }

    private void setup(){
        try {
            server.start();
            server.bind(tcpPort, udpPort);
        }
        catch (IOException e) {
            server.close();
            System.out.println("Failed to start server");
        }
    }
    private void setupListeners(){
        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
               if (object instanceof SomeRequest) {
                  SomeRequest request = (SomeRequest)object;
                  System.out.println(request.text);
         
                  SomeResponse response = new SomeResponse();
                  response.text = "Thanks";
                  connection.sendTCP(response);
               }
            }
         });
    }

}
