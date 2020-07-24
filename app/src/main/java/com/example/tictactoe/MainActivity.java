package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.security.KeyStore;

public class MainActivity extends AppCompatActivity {

    private EditText editTextOne;
    private EditText editTextTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextOne = findViewById(R.id.player_one_name);
        editTextTwo = findViewById(R.id.player_two_name);
    }

    public void openGameActivity(View view) {
        Intent intent = new Intent(this, GameActivity.class);

        String playerOneName = editTextOne.getText().toString();
        String playerTwoName = editTextTwo.getText().toString();

        intent.putExtra(Keystore.KEY_PLAYER_ONE, playerOneName);
        intent.putExtra(Keystore.KEY_PLAYER_TWO, playerTwoName);

        startActivity(intent);
    }
}