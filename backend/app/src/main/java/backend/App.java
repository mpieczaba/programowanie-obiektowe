package backend;

import io.github.cdimascio.dotenv.Dotenv;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;

public class App {
    // Load environment variables for the .env file
    // (This hack enables retrieving environment variables among the code from a
    // single instance of Dotenv via static function calls)
    public static final Dotenv env = Dotenv.configure().directory("../../").load();

    // Socket.IO server
    private final SocketIOServer ws;

    public App() {
        // Create a Socket.IO server instance
        this.ws = new SocketIOServer(new Configuration() {
            {
                setHostname(App.env.get("HOST"));
                setPort(Integer.parseInt(App.env.get("PORT")));
            }
        });

        // Add connection listener to the Socket.IO server
        this.ws.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient client) {
                client.sendEvent("programowanie obiektowe");
            }
        });
    }

    // Function run starts the app
    public void run() {
        System.out.println("Starting...");
        this.ws.start();
    }

    public static void main(String[] args) {
        System.out.println("Programowanie obiektowe");

        final App app = new App();
        app.run();
    }
}
