package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    private TextView playerOne;
    private TextView playerOneScore;
    private TextView playerTwo;
    private TextView playerTwoScore;
    private TextView roundsCount;
    private TextView turnViewer;

    private String playerOneName;
    private String playerTwoName;

    private Button cells[][] = new Button[3][3];
    private int rounds = 0;
    private int occupiedCellCount = 0;
    private int playerOnePoints= 0;
    private int playerTwoPoints = 0;
    private String playerOneSign = "X";
    private String playerTwoSign = "O";
    private Boolean playerOneTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        playerOne = findViewById(R.id.player_one_name);
        playerTwo = findViewById(R.id.player_two_name);
        playerOneScore = findViewById(R.id.player_one_score);
        playerTwoScore = findViewById(R.id.player_two_score);
        roundsCount = findViewById(R.id.rounds_count);
        turnViewer = findViewById(R.id.turn_viewer);

        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++) {
                String cellId = "cell_" + i + j;
                int resId = getResources().getIdentifier(cellId, "id", getPackageName());
                cells[i][j] = findViewById(resId);
            }

        Intent intent = getIntent();

        if (intent != null) {
            Log.i("Intent inside", "Intent is not null");
            playerOneName = intent.getStringExtra(Keystore.KEY_PLAYER_ONE);
            playerTwoName = intent.getStringExtra(Keystore.KEY_PLAYER_TWO);
            playerOne.setText(playerOneName);
            playerTwo.setText(playerTwoName);
            playerOneScore.setText(Integer.toString(playerOnePoints));
            playerTwoScore.setText(Integer.toString(playerOnePoints));
            roundsCount.setText(Integer.toString(rounds));
            turnViewer.setText("It's "+ playerOneName + "'s turn");
        }
    }

    public void clickCell(View view) {

        Button clickedCell = (Button) view;

        if (!clickedCell.getText().toString().equals("")) return;

        if (playerOneTurn) {
            clickedCell.setText(playerOneSign);
            turnViewer.setText("It's " + playerTwoName + "'s turn");
        }
        else {
            clickedCell.setText(playerTwoSign);
            turnViewer.setText("It's " + playerOneName + "'s turn");
        }

        occupiedCellCount++;

        if (checkWinner())
            if (playerOneTurn) playerOneWins();
            else playerTwoWins();
        else if (occupiedCellCount == 9) roundDrawn();
        else playerOneTurn = !playerOneTurn;
    }

    private Boolean checkWinner() {

        String[][] cellInputs = new String[3][3];

        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++)
                cellInputs[i][j] = cells[i][j].getText().toString();

        for (int i=0; i<3; i++) {

            if (cellInputs[i][0].equals(cellInputs[i][1]) &&
                    cellInputs[i][1].equals(cellInputs[i][2]) &&
                    !cellInputs[i][2].equals("")) return true;

            if (cellInputs[0][i].equals(cellInputs[1][i]) &&
                    cellInputs[1][i].equals(cellInputs[2][i]) &&
                    !cellInputs[2][i].equals("")) return true;
        }

        if (cellInputs[0][0].equals(cellInputs[1][1]) &&
                cellInputs[1][1].equals(cellInputs[2][2]) &&
                !cellInputs[2][2].equals("")) return true;

        if (cellInputs[0][2].equals(cellInputs[1][1]) &&
                cellInputs[1][1].equals(cellInputs[2][0]) &&
                !cellInputs[2][0].equals("")) return true;

        return false;
    }

    private void playerOneWins() {
        playerOnePoints++;
        rounds++;
        Toast.makeText(this, playerOneName +" Wins!", Toast.LENGTH_SHORT).show();
        updatePointsTable();
        playerOneTurn = false;
        resetBoard();
    }

    private void playerTwoWins() {
        playerTwoPoints++;
        rounds++;
        Toast.makeText(this, playerTwoName + " Wins!", Toast.LENGTH_SHORT).show();
        updatePointsTable();
        playerOneTurn = true;
        resetBoard();
    }

    private void roundDrawn() {
        rounds++;
        Toast.makeText(this, "Round Drawn!", Toast.LENGTH_SHORT).show();
        updatePointsTable();
        resetBoard();
    }

    private void updatePointsTable() {
        playerOneScore.setText(Integer.toString(playerOnePoints));
        playerTwoScore.setText(Integer.toString(playerTwoPoints));
        roundsCount.setText(Integer.toString(rounds));
    }

    private void resetBoard() {
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++)
                cells[i][j].setText("");

        occupiedCellCount = 0;
    }

}