package com.ait.android.minesweeper.Data;

import java.util.Random;

public class MinesweeperModel {

    private static MinesweeperModel minesweeperModel = null;

    private MinesweeperModel() {
    }

    public static MinesweeperModel getInstance() {
        if (minesweeperModel == null) {
            minesweeperModel = new MinesweeperModel();
        }

        return minesweeperModel;
    }

    private Field[][] model = new Field[5][5];

    private Boolean stepMode = false;

    public Boolean getStepMode() {
        return stepMode;
    }

    public void setStepMode(Boolean stepMode) {
        this.stepMode = stepMode;
    }

    private Boolean gameLost = false;

    public void setGameLost(Boolean gameLost) { this.gameLost = gameLost; }

    public Boolean getGameLost() { return gameLost; }

    public void generateBoard() {
         for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                model[i][j] = new Field(i, j);
            }
        }
        generateMines();
    }

    public void generateMines() {
        Random rand = new Random();

        for (int i = 0; i < 3; i++) {
            int row = rand.nextInt(5);
            int column = rand.nextInt(5);
            this.model[row][column].setMine(true);
        }
    }

    public Field getFieldContent(int x, int y) {
        return model[x][y];
    }

    public int countNeighborMines(int x, int y) {
        int ret = 0;

        int left = (x - 1) >= 0 ? (x - 1) : x;
        int right = (x + 1) < 5 ? (x + 1) : x;
        int lower = (y - 1) >= 0 ? (y - 1) : y;
        int upper = (y + 1) < 5 ? (y + 1) : y;

        for (int i = left; i <= right; i++) {
            for (int j = lower; j <= upper; j++) {
                if (this.model[i][j].isMine()) {
                    ret++;
                }
            }
        }
        return ret;
    }

    public void placeFlag(int x, int y) {
        if(this.model[x][y].isMine()) {
            this.model[x][y].setFlag(true);
        } else {
            this.gameLost = true;
        }
    }

    public void tryField(int x, int y) {
        if(this.model[x][y].isMine()) {
            this.gameLost = true;
        } else {
            this.model[x][y].setClicked(true);
        }
    }

    public Boolean gameWon() {
        Boolean ret = true;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (model[i][j].isMine() && !model[i][j].isFlag()) {
                    ret = false;
                }
            }
        }
        return ret;
    }
}
