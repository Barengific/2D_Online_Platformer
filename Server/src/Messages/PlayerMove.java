package Messages;

import java.io.Serializable;

public class PlayerMove implements Serializable {

    private static final long serialVersionUID = 1234L;

    //creation of the varaibles
    public int playerNo;
    public double xMove1;
    public double yMove1;
    public String pName1;
    public String pName2;
    public double xMove2;
    public double yMove2;

    //constructor
    public PlayerMove(int playerNo, String pName1, double xMove1, double yMove1, String pName2, double xMove2, double yMove2) {
        this.playerNo = playerNo;
        this.pName1 = pName1;
        this.xMove1 = xMove1;
        this.yMove1 = yMove1;
        this.pName2 = pName2;
        this.xMove2 = xMove2;
        this.yMove2 = yMove2;
    }

    //getters and setters
    public double getxMove1() {
        return xMove1;
    }

    public void setxMove1(double xMove1) {
        this.xMove1 = xMove1;
    }

    public double getyMove1() {
        return yMove1;
    }

    public void setyMove1(double yMove1) {
        this.yMove1 = yMove1;
    }

    public double getxMove2() {
        return xMove2;
    }

    public void setxMove2(double xMove2) {
        this.xMove2 = xMove2;
    }

    public double getyMove2() {
        return yMove2;
    }

    public void setyMove2(double yMove2) {
        this.yMove2 = yMove2;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }

    public String getName1() {
        return pName1;
    }

    public void setName1(String pName1) {
        this.pName1 = pName1;
    }

    public String getName2() {
        return pName2;
    }

    public void setName2(String pName2) {
        this.pName2 = pName2;
    }
}
