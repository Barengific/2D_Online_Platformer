package mainServer;

import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.sound.sampled.UnsupportedAudioFileException;
import musicgame.MusicHttpHandler;

/**
 * @authors KIB
 */
public class ServerStart {

    //server variables creation
    static final int port = 1234;
    static ServerSocket ssocket;
    static Socket socket;

    //starts music servers and assigns the connecting player a number (1 or 2)
    public static void main(String[] args) throws IOException {
        ssocket = new ServerSocket(port);
        int player = 1;
        gameMusic();
        gameMusicHaduken();
        while (true) {
            System.out.println("listening" + player);
            socket = ssocket.accept();
            if (player == 1) {
                Threader thread = new Threader(socket, player);
                thread.start();
                player = 2;
            } else if (player == 2) {
                Threader thread = new Threader(socket, 1);
                thread.start();
                player = 3;
            } else if (player == 3) {
                Threader thread = new Threader(socket, 2);
                thread.start();
                player = 4;
            } else if (player == 4) {
                Threader thread = new Threader(socket, 2);
                thread.start();
                player = 1;
            }
        }
    }

    //normal background music
    public static void gameMusic() {
        try {
            //Internet address of host
            InetSocketAddress hostAddress = new InetSocketAddress("localhost", 4446);

            //Create HttpServer which is listening on the given port
            HttpServer httpServer = HttpServer.create(hostAddress, 0);

            //Create the handler for the /MusicServer context
            MusicHttpHandler musicHandler = new MusicHttpHandler();

            //Load up music file into memory - expensive operation, but an easy way to make it work.
            String fileLoc = System.getProperty("user.dir");
            String fileData = fileLoc + File.separator + "src\\musicgame\\fighter.wav";
            String currentPath = new java.io.File(fileData).getCanonicalPath();
            String topDirectory = currentPath.substring(0, currentPath.indexOf("\\fighter.wav"));
            String filePath = topDirectory + "\\fighter.wav";
            musicHandler.loadData(filePath);

            //Create a new context and handler for this context
            httpServer.createContext("/MusicServer", musicHandler);

            //Start the ser
            httpServer.start();
        } catch (UnsupportedAudioFileException | IOException ex) {
            ex.printStackTrace();
        }
    }

    //music for when player hits an obstacle
    public static void gameMusicHaduken() {
        try {
            //Internet address of host
            InetSocketAddress hostAddress = new InetSocketAddress("localhost", 4447);

            //Create HttpServer which is listening on the given port
            HttpServer httpServer = HttpServer.create(hostAddress, 0);

            //Create the handler for the /MusicServer context
            MusicHttpHandler musicHandler = new MusicHttpHandler();

            //Load up music file into memory - expensive operation, but an easy way to make it work.
            String fileLoc = System.getProperty("user.dir");
            String fileData = fileLoc + File.separator + "src\\musicgame\\hadouken.wav";
            String currentPath = new java.io.File(fileData).getCanonicalPath();
            String topDirectory = currentPath.substring(0, currentPath.indexOf("\\hadouken.wav"));
            String filePath = topDirectory + "\\hadouken.wav";
            musicHandler.loadData(filePath);

            //Create a new context and handler for this context
            httpServer.createContext("/MusicServer", musicHandler);

            //Start the ser
            httpServer.start();
        } catch (UnsupportedAudioFileException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
