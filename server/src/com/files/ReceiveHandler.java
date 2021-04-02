public class ReceiveHandler implements Listener {

    private Map<Connection, Float> clients = new HashMap<>();

    public void connected (Connection connection) {
        System.out.println("Added connection: ", connection);
        clients.put(connection, 0.0);
    }

    public void disconnected (Connection connection) {
        System.out.println("Removed connection: ", connection);
        clients.remove(connection);
    }

    public void received(Connection connection, Object object) { 
        if (! clients.size() < 2) {
            SomeResponse response = new SomeResponse();
            response.text = "Not enough clients connected yet";
            clients.sendTCP(response);   
        }
        if (! clients.contains(connection)) clients.add(connection); // Doesn't take into account different game rooms
        if (object instanceof Score) {
            System.out.println("Score: ", object.score);
            clients.put(connection, object.score);
            for (Connection client : clients.keySet()) {
                if (client != connection) {
                    Score score = new Score();
                    score.score = clients.get(client);
                    connection.sendUDP(score);
                }
            }
        }
        else {
            System.out.println("Else: ", object);
        }
    }
}