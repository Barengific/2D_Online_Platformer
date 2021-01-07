package mainClient;

import Messages.LevelInfo;
import Messages.Messenger;
import Messages.PlayerMove;
import Messages.Scoreboard;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class PlayerRun extends Thread {

    //variables and objecs creation
    ObjectOutputStream oos;
    ObjectInputStream ois;
    ClientStart main;
    Messenger msg;
    PlayerMove pMove;
    ArrayList<Scoreboard> scoreTbl;

    Circle recP1;
    Circle recP2;
    LevelInfo lvlInfo;
    StackPane root;
    Label finishMsg;

    boolean state1;
    boolean state2;
    boolean finState1 = false;
    boolean finState2 = false;
    public double timer1;
    public double timer2;
    String pName1 = "not";
    String pName2 = "not";

    final int port = 1234;
    final String host = "localhost";

    //constructor
    public PlayerRun(PlayerMove pMove, Circle recP1, Circle recP2, StackPane root, Label label) {
        this.pMove = pMove;
        this.recP1 = recP1;
        this.recP2 = recP2;
        this.root = root;
        this.finishMsg = label;
    }

    @Override
    public synchronized void run() {
        try {
            Socket socket = new Socket(host, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            main = new ClientStart();
            while (true) {
                msg = new Messenger("PlayerMoveUpdate",
                        new PlayerMove(pMove.getPlayerNo(), pName1, pMove.getxMove1(), pMove.getyMove1(), pName2, pMove.getxMove2(), pMove.getyMove2()),
                        new LevelInfo(1, timer1, timer2, state1, state2, finState1, finState2));
                oos.writeObject(msg);
                oos.flush();
                ois = new ObjectInputStream(socket.getInputStream());
                msg = (Messenger) ois.readObject();

                if (pMove.getPlayerNo() == 1) {
                    main.updateMovesP2(recP2, msg.getXMove2(), msg.getYMove2());
                    setState1(msg.getState1());
                    setState2(msg.getState2());
                    setScoreboard(msg.getScoreBoard());
                    if (msg.getHeader().equals("FinishState")) {
                        System.out.println(msg.getName1() + " - " + msg.getTimer1());
                        System.out.println(timer1 + " -" + timer2);
                        if (msg.getFinState1()) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    main.finMake(getScoreboard());
                                    finishMsg.setText("You Won The Game");
                                }
                            });
                        } else if (msg.getFinState2()) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    main.finMake(getScoreboard());
                                    finishMsg.setText("you've lost");
                                }
                            });
                        }
                    }
                } else if (pMove.getPlayerNo() == 2) {
                    main.updateMovesP1(recP1, msg.getXMove1(), msg.getYMove1());
                    setState1(msg.getState1());
                    setState2(msg.getState2());
                    setScoreboard(msg.getScoreBoard());
                    if (msg.getHeader().equals("FinishState")) {
                        System.out.println(msg.getName2() + " -" + msg.getTimer2());
                        System.out.println(timer1 + " -" + timer2);
                        if (msg.getFinState1()) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    main.finMake(getScoreboard());
                                    finishMsg.setText("You Have Lost");
                                }
                            });
                        } else if (msg.getFinState2()) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    main.finMake(getScoreboard());
                                    finishMsg.setText("You Won The Game");
                                }
                            });
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PlayerRun.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //getters and setters
    public void setTimer1(double timer1) {
        this.timer1 = timer1;
    }

    public void setTimer2(double timer2) {
        this.timer2 = timer2;
    }

    public void setPName1(String pName1) {
        this.pName1 = pName1;
    }

    public void setPName2(String pName2) {
        this.pName2 = pName2;
    }

    public void setFinState1(boolean state1) {
        this.finState1 = state1;
    }

    public void setFinState2(boolean state2) {
        this.finState2 = state2;
    }

    public void setState1(boolean state1) {
        this.state1 = state1;
    }

    public void setState2(boolean state2) {
        this.state2 = state2;
    }

    public boolean getState1() {
        return state1;
    }

    public boolean getState2() {
        return state2;
    }

    public void setScoreboard(ArrayList<Scoreboard> scoreTbl) {
        this.scoreTbl = scoreTbl;
    }

    public ArrayList<Scoreboard> getScoreboard() {
        return scoreTbl;
    }

    public void setpMove(int playerNo, double xMove1, double yMove1, String shape, double xMove2, double yMove2) {
        pMove.playerNo = playerNo;
        pMove.xMove1 = xMove1;
        pMove.yMove1 = yMove1;
        pMove.pName1 = shape;
        pMove.xMove2 = xMove2;
        pMove.yMove2 = yMove2;
    }
}
