import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final int port;
    private final List<String> validPaths;
    private final ExecutorService threadPool;

    public Server(int port, List<String> validPaths) {
        this.port = port;
        this.validPaths = validPaths;
        this.threadPool = Executors.newFixedThreadPool(64); // Фиксированный пул на 64 потока
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running on port " + port);
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    // Обрабатываем подключение в новом потоке из пула
                    threadPool.submit(new RequestHandler(socket, validPaths));
                } catch (IOException e) {
                    System.err.println("Error accepting connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Could not start server: " + e.getMessage());
        } finally {
            threadPool.shutdown();
        }
    }

}
