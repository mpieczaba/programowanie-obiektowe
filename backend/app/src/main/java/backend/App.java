package backend;

import io.github.cdimascio.dotenv.Dotenv;

import io.javalin.Javalin;

public class App {
    // Load environment variables for the .env file
    // (This hack enables retrieving environment variables among the code from a
    // single instance of Dotenv via static function calls)
    public static final Dotenv env = Dotenv.configure().directory("../../").load();

    private final Javalin server;

    public App() {
        // Create a new Javalin server instance
        this.server = Javalin.create(cfg -> cfg.plugins.enableDevLogging());
    }

    // Start the app
    public void run() {
        System.out.println("Starting...");

        // Start the Javalin server on localhost:<PORT> (8080 by default)
        this.server.start(Integer.parseInt(App.env.get("PORT")));
    }

    public static void main(String[] args) {
        System.out.println("Programowanie obiektowe");

        final App app = new App();
        app.run();
    }
}
