package Messages;

import java.io.Serializable;

public class Scoreboard implements Serializable {

    private static final long serialVersionUID = 1234L;

    //variable creation
    String playerName;
    double playerTime;
    int playerLevel;

    //constructor
    public Scoreboard(String playerName, double playerTime, int playerLevel) {
        this.playerName = playerName;
        this.playerTime = playerTime;
        this.playerLevel = playerLevel;
    }

    //getters and setters
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public double getPlayerTime() {
        return playerTime;
    }

    public void setPlayerTime(double playerTime) {
        this.playerTime = playerTime;
    }

    public int getPlayerLevel() {
        return playerLevel;
    }

    public void setPlayerLevel(int playerLevel) {
        this.playerLevel = playerLevel;
    }

    @Override
    public String toString() {
        return ("StudentName:" + this.getPlayerName()
                + " playertime: " + this.getPlayerTime()
                + " level: " + this.getPlayerLevel());
    }
}
