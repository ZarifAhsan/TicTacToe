package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean playerXTurn = true;

    private int roundCount;

    private int playerXPoints;
    private int playerOPoints;

    private TextView textViewPlayerX;
    private TextView textViewPlayerO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayerX = findViewById(R.id.player_x_score);
        textViewPlayerO = findViewById(R.id.player_O_score);


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.reset_score);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) return;
        
        if (playerXTurn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (playerXTurn) {
                playerXWins();
            } else {
                playerOWins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            playerXTurn = !playerXTurn;
        }

    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) return true;
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) return true;
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) return true;

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) return true;

        return false;
    }

    private void playerXWins() {
        playerXPoints++;
        Toast.makeText(this, "Player X Wins", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void playerOWins() {
        playerOPoints++;
        Toast.makeText(this, "Player O Wins", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "It's a DRAW", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    @SuppressLint("SetTextI18n")
    private void updatePointsText() {
        textViewPlayerX.setText("" + playerXPoints);
        textViewPlayerO.setText("" + playerOPoints);
    }

    private void resetBoard() {
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        playerXTurn = true;
    }

    private void resetGame() {
        playerXPoints = 0;
        playerOPoints = 0;
        updatePointsText();
        resetBoard();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("playerXPoints", playerXPoints);
        outState.putInt("playerOPoints", playerOPoints);
        outState.putBoolean("playerXTurn", playerXTurn);
    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        playerXPoints = savedInstanceState.getInt("playerXPoints");
        playerOPoints = savedInstanceState.getInt("playerOPoints");
        playerXTurn = savedInstanceState.getBoolean("playerXTurn");
    }

}
