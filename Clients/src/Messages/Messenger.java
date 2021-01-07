package Messages;

import java.io.Serializable;
import java.util.ArrayList;

public class Messenger implements Serializable {

    private static final long serialVersionUID = 1234L;

    //object type and variable creations
    String header;
    PlayerMove pMove;
    LevelInfo lvlInfo;
    Scoreboard scores;

    ArrayList<Scoreboard> scoreList;

    //constructors
    public Messenger(String header) {
        this.header = header;
    }

    public Messenger(String header, PlayerMove pMove) {
        this.header = header;
        this.pMove = pMove;
    }

    public Messenger(String header, PlayerMove pMove, LevelInfo lvlInfo) {
        this.header = header;
        this.pMove = pMove;
        this.lvlInfo = lvlInfo;
    }

    public Messenger(String header, PlayerMove pMove, LevelInfo lvlInfo, Scoreboard scores) {
        this.header = header;
        this.pMove = pMove;
        this.lvlInfo = lvlInfo;
        this.scores = scores;
    }

    public Messenger(String header, PlayerMove pMove, ArrayList<Scoreboard> scoreList) {
        this.header = header;
        this.pMove = pMove;
        this.scoreList = scoreList;
    }

    public Messenger(String header, PlayerMove pMove, LevelInfo lvlInfo, ArrayList<Scoreboard> scoreList) {
        this.header = header;
        this.pMove = pMove;
        this.lvlInfo = lvlInfo;
        this.scoreList = scoreList;
    }

    //setters and getters
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    //player move - includes player 1 and twoo
    public PlayerMove getpMove() {
        return pMove;
    }

    public void setpMove(PlayerMove pMove) {
        this.pMove = pMove;
    }

    public int getPlayerNo() {
        return pMove.getPlayerNo();
    }

    public String getName1() {
        return pMove.getName1();
    }

    public String getName2() {
        return pMove.getName2();
    }

    public double getXMove1() {
        return pMove.getxMove1();
    }

    public double getYMove1() {
        return pMove.getyMove1();
    }

    public double getXMove2() {
        return pMove.getxMove2();
    }

    public double getYMove2() {
        return pMove.getyMove2();
    }

    //level information
    public LevelInfo getLvlInfo() {
        return lvlInfo;
    }

    public void setLvlInfo(LevelInfo lvlInfo) {
        this.lvlInfo = lvlInfo;
    }

    public int getLevel() {
        return lvlInfo.getLevel();
    }

    public double getTimer1() {
        return lvlInfo.getTimer1();
    }

    public double getTimer2() {
        return lvlInfo.getTimer2();
    }

    public boolean getState1() {
        return lvlInfo.isState1();
    }

    public boolean getState2() {
        return lvlInfo.isState2();
    }

    public boolean getFinState1() {
        return lvlInfo.isFinState1();
    }

    public boolean getFinState2() {
        return lvlInfo.isFinState2();
    }

    //scoreboard info
    public ArrayList<Scoreboard> getScoreBoard() {
        return scoreList;
    }

    public void setScoreBoard(ArrayList<Scoreboard> scoreList) {
        this.scoreList = scoreList;
    }
}
