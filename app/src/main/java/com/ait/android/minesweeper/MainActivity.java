package com.ait.android.minesweeper;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ait.android.minesweeper.Data.MinesweeperModel;
import com.ait.android.minesweeper.View.MinesweeperView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layoutContent;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutContent = (LinearLayout) findViewById(R.id.layoutContent);
        text = (TextView) findViewById(R.id.text);

        MinesweeperModel.getInstance().generateBoard();

        final MinesweeperView minesweeperView = (MinesweeperView) findViewById(R.id.minesweeperView);
        final ToggleButton tg = (ToggleButton) findViewById(R.id.btToggle);
        tg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MinesweeperModel.getInstance().setStepMode(tg.isChecked());
            }

        });

        final Button btnNew = (Button) findViewById(R.id.btnNew);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minesweeperView.newGame();
            }
        });

    }

    public void endGame() {
        if (MinesweeperModel.getInstance().gameWon()) {
            Snackbar.make(layoutContent, getText(R.string.game_won_text), Snackbar.LENGTH_LONG).show();
            revealBoard();
        } else if (MinesweeperModel.getInstance().getGameLost()) {
            Snackbar.make(layoutContent, getString(R.string.game_lost_text), Snackbar.LENGTH_LONG).show();
            revealBoard();
        }
    }

    public void revealBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                MinesweeperModel.getInstance().getFieldContent(i, j).setClicked(true);
            }
        }
    }
}
