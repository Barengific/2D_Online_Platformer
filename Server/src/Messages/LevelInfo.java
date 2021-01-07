package Messages;

import java.io.Serializable;

public class LevelInfo implements Serializable {
    private static final long serialVersionUID = 1234L;
    
    //variable creation
    int level;
    double timer1;
    double timer2;
    boolean state1;
    boolean state2;
    boolean finState1 = false;
    boolean finState2 = false;

    //constructors
    public LevelInfo(int level, double timer1, double timer2, boolean state1, boolean state2, boolean finState1, boolean finState2) {
        this.level = level;
        this.timer1 = timer1;
        this.timer2 = timer2;
        this.state1 = state1;
        this.state2 = state2;
        this.finState1 = finState1;
        this.finState2 = finState2;
    }

    //setters and getters
    public boolean isFinState1() {
        return finState1;
    }

    public void setFinState1(boolean finState1) {
        this.finState1 = finState1;
    }

    public boolean isFinState2() {
        return finState2;
    }

    public void setFinState2(boolean finState2) {
        this.finState2 = finState2;
    }

    public boolean isState1() {
        return state1;
    }

    public void setState1(boolean state1) {
        this.state1 = state1;
    }

    public boolean isState2() {
        return state2;
    }

    public void setState2(boolean state2) {
        this.state2 = state2;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getTimer1() {
        return timer1;
    }

    public void setTimer1(double timer1) {
        this.timer1 = timer1;
    }

    public double getTimer2() {
        return timer2;
    }

    public void setTimer2(double timer2) {
        this.timer2 = timer2;
    }
}
