package mainClient;

import Messages.Messenger;
import Messages.PlayerMove;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RunConn implements Runnable {

    //variable and objects creations
    ObjectInputStream ois;
    ObjectOutputStream oos;
    Messenger msg;
    PlayerMove pMove;

    int sCode;
    final int port = 1234;
    final String host = "127.0.0.1";

    //this classed is used to check if server is up and running
    //if true it will obtain a player number
    @Override
    public void run() {
        try (Socket socket = new Socket(host, port)) {
            oos = new ObjectOutputStream(socket.getOutputStream());
            msg = new Messenger("connectClient");
            oos.writeObject(msg);
            oos.flush();

            ois = new ObjectInputStream(socket.getInputStream());
            msg = (Messenger) ois.readObject();
            if (msg.getHeader().equals("connectSuccess")) {
                setStatusCode(msg.getPlayerNo());
            }
            System.out.println(msg.getHeader());
            oos.close();
            ois.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public void setStatusCode(int sCode) {
        this.sCode = sCode;
    }

    public int getStatusCode() {
        return sCode;
    }
}
