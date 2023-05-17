package backend;

import io.github.cdimascio.dotenv.Dotenv;

import io.javalin.Javalin;

public class App {
    // Load environment variables for the .env file
    // (This hack enables retrieving environment variables among the code from a
    // single instance of Dotenv via static function calls)
    public static final Dotenv env = Dotenv.configure().directory("../../").load();

    // Javalin server instance
    private final Javalin server = Javalin.create(cfg -> cfg.plugins.enableDevLogging());

    public App() {
        // Set up routes
        this.server.routes(Router::getRoutes);
    }

    // Start the app
    public void run() {
        System.out.println("Starting...");

        // Start the Javalin server on <HOST>:<PORT> (localhost:8080 by default)
        this.server.start(App.env.get("HOST"), Integer.parseInt(App.env.get("PORT")));
    }

    public static void main(String[] args) {
        System.out.println("Programowanie obiektowe");

        final App app = new App();
        app.run();
    }
}
