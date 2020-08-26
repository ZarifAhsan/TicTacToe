package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

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
    private int playerYPoints;

    private TextView textViewPlayerX;
    private TextView textViewPlayerY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayerX = findViewById(R.id.player_x_score);
        textViewPlayerY = findViewById(R.id.player_y_score);


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
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
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
                playerYWins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            playerXTurn = !playerXTurn;
        }

    }

    private boolean checkForWin() {
        String[][] feild = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                feild[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (feild[i][0].equals(feild[i][1])
                    && feild[i][0].equals(feild[i][2])
                    && !feild[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (feild[0][i].equals(feild[1][i])
                    && feild[0][i].equals(feild[2][i])
                    && !feild[0][i].equals("")) {return true;}
        }

        if (feild[0][0].equals(feild[1][1])
                && feild[0][0].equals(feild[2][2])
                && !feild[0][0].equals("")) {return true;}

        if (feild[0][2].equals(feild[1][1])
                && feild[0][2].equals(feild[2][0])
                && !feild[0][2].equals("")) {return true;}

        return false;
    }

    private void playerXWins() {
        playerXPoints++;
        Toast.makeText(this, "Player X Wins", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void playerYWins() {
        playerYPoints++;
        Toast.makeText(this, "Player Y Wins", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "It's a DRAW", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayerX.setText("" + playerXPoints);
        textViewPlayerY.setText("" + playerYPoints);
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
}