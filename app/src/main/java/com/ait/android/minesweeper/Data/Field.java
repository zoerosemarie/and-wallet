package com.ait.android.minesweeper.Data;

public class Field {

    private int x;
    private int y;
    private boolean isMine;
    private boolean clicked;
    private boolean isFlag;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
        this.isMine = false;
        this.clicked = false;
        this.isFlag = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public void reset() {
        this.isMine = false;
        this.isFlag = false;
        this.clicked = false; }
}

