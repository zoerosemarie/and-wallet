package com.ait.android.minesweeper.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.ait.android.minesweeper.Data.MinesweeperModel;
import com.ait.android.minesweeper.MainActivity;
import com.ait.android.minesweeper.R;

public class MinesweeperView extends View {

    private Paint paintBg;
    private Paint paintLine;
    private Paint paintText;

    private LinearLayout layoutContent;

    public MinesweeperView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        layoutContent = (LinearLayout) findViewById(R.id.layoutContent);

        paintBg = new Paint();
        paintBg.setColor(Color.WHITE);
        paintBg.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.BLACK);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);

        paintText = new Paint();
        paintText.setColor(Color.BLACK);
        paintText.setTextSize(60);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);
        drawGameArea(canvas);
        drawSymbols(canvas);
    }

    public void drawSymbols(Canvas canvas) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (MinesweeperModel.getInstance().getFieldContent(i, j).isFlag()) {
                    canvas.drawLine(i * getWidth() / 5, j * getHeight() / 5,
                            (i + 1) * getWidth() / 5,
                            (j + 1) * getHeight() / 5, paintLine);

                    canvas.drawLine((i + 1) * getWidth() / 5, j * getHeight() / 5,
                            i * getWidth() / 5, (j + 1) * getHeight() / 5, paintLine);
                } else if (MinesweeperModel.getInstance().getFieldContent(i, j).isMine() && MinesweeperModel.getInstance().getFieldContent(i, j).isClicked()) {
                    int centerX = i * getWidth() / 5 + getWidth() / 10;
                    int centerY = j * getHeight() / 5 + getHeight() / 10;

                    canvas.drawCircle(centerX,
                            centerY,
                            getWidth() / 10 - 2,
                            paintLine);
                } else if (MinesweeperModel.getInstance().getFieldContent(i, j).isClicked()) {
                    int numMines = MinesweeperModel.getInstance().countNeighborMines(i, j);
                    String mines = Integer.toString(numMines);
                    canvas.drawText(mines, i * (getWidth() / 5) + (getWidth() / 10) - 15, j * (getHeight() / 5) + (getHeight() / 10) + 15, paintText);
                }
            }
        }
    }

    private void drawGameArea(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);

        canvas.drawLine(getWidth() / 5, 0, getWidth() / 5, getHeight(), paintLine);
        canvas.drawLine(2 * (getWidth() / 5), 0, 2 * (getWidth() / 5), getHeight(), paintLine);
        canvas.drawLine(3 * (getWidth() / 5), 0, 3 * (getWidth() / 5), getHeight(), paintLine);
        canvas.drawLine(4 * (getWidth() / 5), 0, 4 * (getWidth() / 5), getHeight(), paintLine);

        canvas.drawLine(0, getHeight() / 5, getWidth(), getHeight() / 5, paintLine);
        canvas.drawLine(0, 2 * (getHeight() / 5), getWidth(), 2 * (getHeight() / 5), paintLine);
        canvas.drawLine(0, 3 * (getHeight() / 5), getWidth(), 3 * (getHeight() / 5), paintLine);
        canvas.drawLine(0, 4 * (getHeight() / 5), getWidth(), 4 * (getHeight() / 5), paintLine);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int tX = ((int) event.getX()) / (getWidth() / 5);
            int tY = ((int) event.getY()) / (getHeight() / 5);

            if (!MinesweeperModel.getInstance().getGameLost()) {
                if(MinesweeperModel.getInstance().getStepMode()) {
                    MinesweeperModel.getInstance().tryField(tX, tY);
                } else {
                    MinesweeperModel.getInstance().placeFlag(tX, tY);
                }
            }
            ((MainActivity)getContext()).endGame();
            invalidate();
        }
        return true;
    }

    public void clearBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                MinesweeperModel.getInstance().getFieldContent(i, j).reset();
            }
        }
        invalidate();
    }

    public void newGame() {
        clearBoard();
        MinesweeperModel.getInstance().generateBoard();
        MinesweeperModel.getInstance().setGameLost(false);
    }
}
