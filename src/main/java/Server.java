import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final int port;
    private final ExecutorService threadPool;

    public Server(int port) {
        this.port = port;
        this.threadPool = Executors.newFixedThreadPool(64);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                threadPool.submit(new RequestHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        threadPool.shutdown();
    }
}
