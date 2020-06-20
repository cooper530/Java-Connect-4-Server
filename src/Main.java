import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
Thanks to https://cs.lmu.edu/~ray/notes/javanetexamples/ for helping with examples for setting up a server
in Java
 */
public class Main {
    public static void main(String[] args) throws IOException {
        try(ServerSocket listener = new ServerSocket(25565)) {
            System.out.println("Server is running...");
            ExecutorService pool = Executors.newFixedThreadPool(200);
            //Main Loop
            while(true) {
                Game game = new Game();
                pool.execute(game.new Player(listener.accept(), 1));
                pool.execute(game.new Player(listener.accept(), 2));
            }
        }
    }
}