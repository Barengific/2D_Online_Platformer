package mainServer;

import Messages.LevelInfo;
import Messages.Messenger;
import Messages.PlayerMove;
import Messages.Scoreboard;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @authors KIB
 */
public class Threader extends Thread {

    //server variable and players' positions and states creation
    Socket socket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    Messenger msg;
    int pNo;
    static volatile double[] playersXY = {-580, 320, -580, 320};
    static volatile boolean connState1 = false;
    static volatile boolean connState2 = false;
    static volatile boolean finState1 = false;
    static volatile boolean finState2 = false;
    static volatile int level = 1;

    //constructor to get the same socket and player number from mp2Server
    public Threader(Socket socket, int pNo) {
        this.socket = socket;
        this.pNo = pNo;
    }

    //player thread keeps both players updated
    @Override
    public synchronized void run() {
        ArrayList<Scoreboard> lists = readScore();
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            while (true) {
                msg = (Messenger) ois.readObject();
                oos = new ObjectOutputStream(socket.getOutputStream());
                if ("connectClient".equals(msg.getHeader())) {
                    if (pNo == 1) {
                        oos.writeObject(new Messenger("connectSuccess", new PlayerMove(1, "", -580, 320, "", -580, 320)));
                        oos.flush();
                    } else if (pNo == 2) {
                        oos.writeObject(new Messenger("connectSuccess", new PlayerMove(2, "", -580, 320, "", -580, 320)));
                        oos.flush();
                    }
                } else if (msg.getHeader().equals("PlayerMoveUpdate")) {
                    if (msg.getPlayerNo() == 1) {
                        connState1 = true;
                        finState1 = msg.getFinState1();
                        playersXY[0] = msg.getXMove1();
                        playersXY[1] = msg.getYMove1();
                    } else if (msg.getPlayerNo() == 2) {
                        connState2 = true;
                        finState2 = msg.getFinState2();
                        playersXY[2] = msg.getXMove2();
                        playersXY[3] = msg.getYMove2();
                    }
                    if (finState1) {
                        if (newScoreAllowed(msg.getName1(), msg.getTimer1(), level)) {
                            writeScore(msg.getName1(), msg.getTimer1(), level);
                        }
                        ArrayList<Scoreboard> list = readScore();
                        oos.writeObject(new Messenger("FinishState",
                                new PlayerMove(msg.getPlayerNo(), msg.getName1(), playersXY[0], playersXY[1], msg.getName2(), playersXY[2], playersXY[3]),
                                new LevelInfo(level, msg.getTimer1(), msg.getTimer2(), connState1, connState2, finState1, finState2),
                                list));
                        oos.flush();
                        break;
                    } else if (finState2) {
                        if (newScoreAllowed(msg.getName2(), msg.getTimer2(), level)) {
                            writeScore(msg.getName2(), msg.getTimer2(), level);
                        }
                        ArrayList<Scoreboard> list = readScore();
                        oos.writeObject(new Messenger("FinishState",
                                new PlayerMove(msg.getPlayerNo(), msg.getName1(), playersXY[0], playersXY[1], msg.getName2(), playersXY[2], playersXY[3]),
                                new LevelInfo(level, msg.getTimer1(), msg.getTimer2(), connState1, connState2, finState1, finState2),
                                list));
                        oos.flush();
                        break;
                    } else if ((finState1 && finState2) == false) {
                        oos.writeObject(new Messenger("PlayerMoveUpdate",
                                new PlayerMove(msg.getPlayerNo(), msg.getName1(), playersXY[0], playersXY[1], msg.getName2(), playersXY[2], playersXY[3]),
                                new LevelInfo(level, msg.getTimer1(), msg.getTimer2(), connState1, connState2, finState1, finState2),
                                lists));
                        oos.flush();
                    }
                } else {
                    oos.writeObject(new Messenger("something went wrong"));
                    oos.flush();
                }
            }//count 5 seconds before reseting players' states and positions
            final long startTime = System.currentTimeMillis();
            while (true) {
                long nowTime = (System.currentTimeMillis() - startTime) / 1000;
                if (nowTime > 5) {
                    playersXY[0] = -580;
                    playersXY[1] = 320;
                    connState1 = false;
                    finState1 = false;
                    playersXY[2] = -580;
                    playersXY[3] = 320;
                    connState2 = false;
                    finState2 = false;
                }
            }
        } catch (IOException ex) {
            System.out.println("" + ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Threader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //populate arraylist of type scoreboard from text file
    public ArrayList<Scoreboard> readScore() {
        ArrayList<Scoreboard> scorer = new ArrayList<>();
        try {
            String fileLoc = System.getProperty("user.dir");
            String fileData = fileLoc + File.separator + "src\\gamedata\\scoreboard.txt";
            FileReader fr = new FileReader(fileData);
            BufferedReader br = new BufferedReader(fr);
            for (String pScore = br.readLine(); pScore != null; pScore = br.readLine()) {
                String[] score = pScore.split(",");
                scorer.add(new Scoreboard(score[0], Double.valueOf(score[1]), Integer.valueOf(score[2])));
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return scorer;
    }

    //write winner player to file
    public void writeScore(String name, double time, int level) {
        System.out.println("writing");
        System.out.println(name + time + level);
        if (!name.equals("not")) {
            try {
                String fileLoc = System.getProperty("user.dir");
                String fileData = fileLoc + File.separator + "src\\gamedata\\scoreboard.txt";
                FileWriter fw = new FileWriter(fileData, true);
                BufferedWriter bw = new BufferedWriter(fw);
                String newPlayerStat = "\n" + name + "," + time + "," + level;
                bw.write(newPlayerStat);
                bw.flush();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    //ensure new states are only written once and no duplicate submission
    public static volatile boolean writeBool = true;

    private boolean newScoreAllowed(String name, double time, int level) {
        writeScore(name, time, level);
        writeBool = false;
        return writeBool;
    }
}
