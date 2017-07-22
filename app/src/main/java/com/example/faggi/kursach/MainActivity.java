package com.example.faggi.kursach;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton player1wb;
    ImageButton player1reddish;
    ImageButton player1blueish;
    ImageButton player1standard;
    ImageButton player2wb;
    ImageButton player2reddish;
    ImageButton player2blueish;
    ImageButton player2standard;

    TextView player1pointer;
    TextView player2pointer;

    Button startGameMultiplayer;
    Button startGameAI;
    Button startGameMultiplayerRus;
    Button startGameAIRus;

    EditText player1name;
    EditText player2name;

    int player1avatar = 3;
    int player2avatar = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        player1wb = (ImageButton) findViewById(R.id.imageButton_bwplayer1);
        player1wb.setOnClickListener(this);
        player1reddish = (ImageButton) findViewById(R.id.imageButton_reddishplayer1);
        player1reddish.setOnClickListener(this);
        player1blueish = (ImageButton) findViewById(R.id.imageButton_blueishplayer1);
        player1blueish.setOnClickListener(this);
        player1standard = (ImageButton) findViewById(R.id.imageButton_standardplayer1);
        player1standard.setOnClickListener(this);

        player2wb = (ImageButton) findViewById(R.id.imageButton_bwplayer2);
        player2wb.setOnClickListener(this);
        player2reddish = (ImageButton) findViewById(R.id.imageButton_reddishplayer2);
        player2reddish.setOnClickListener(this);
        player2blueish = (ImageButton) findViewById(R.id.imageButton_blueishplayer2);
        player2blueish.setOnClickListener(this);
        player2standard = (ImageButton) findViewById(R.id.imageButton_standardplayer2);
        player2standard.setOnClickListener(this);

        player1pointer = (TextView) findViewById(R.id.text_player1pointer);
        player1pointer.setText("Player 1"); // stupid warning
        player2pointer = (TextView) findViewById(R.id.text_player2pointer);
        player2pointer.setText("Player 2");

        player1name = (EditText) findViewById(R.id.editText_player1name);
        player2name = (EditText) findViewById(R.id.editText_player2name);

        startGameMultiplayer = (Button) findViewById(R.id.button_startGame);
        startGameMultiplayer.setOnClickListener(this);
        startGameAI = (Button) findViewById(R.id.button_playai);
        startGameAI.setOnClickListener(this);

        startGameMultiplayerRus = (Button) findViewById(R.id.button_startGameRus);
        startGameMultiplayerRus.setOnClickListener(this);
        startGameAIRus = (Button) findViewById(R.id.button_playaiRus);
        startGameAIRus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imageButton_bwplayer1:
                player1avatar = 0;
                break;
            case R.id.imageButton_reddishplayer1:
                player1avatar = 1;
                break;
            case R.id.imageButton_blueishplayer1:
                player1avatar = 2;
                break;
            case R.id.imageButton_standardplayer1:
                player1avatar = 3;
                break;
            case R.id.imageButton_bwplayer2:
                player2avatar = 0;
                break;
            case R.id.imageButton_reddishplayer2:
                player2avatar = 1;
                break;
            case R.id.imageButton_blueishplayer2:
                player2avatar = 2;
                break;
            case R.id.imageButton_standardplayer2:
                player2avatar = 3;
                break;
            case R.id.button_startGame:
                Intent startgamem = new Intent(MainActivity.this, GameActivity.class);
                startgamem.putExtra("player1name", player1name.getText().toString());
                startgamem.putExtra("player2name", player2name.getText().toString());
                startgamem.putExtra("player1avatar", player1avatar);
                startgamem.putExtra("player2avatar", player2avatar);
                startgamem.putExtra("vocabularyid", R.raw.wordsenglish);
                startActivity(startgamem);
                break;
            case R.id.button_playai:
                Intent startgamebot = new Intent(MainActivity.this, GameActivity.class);
                startgamebot.putExtra("player1name", player1name.getText().toString());
                startgamebot.putExtra("player2name", "Bot Jack");
                startgamebot.putExtra("player1avatar", player1avatar);
                startgamebot.putExtra("CheckAI", true);
                startgamebot.putExtra("vocabularyid", R.raw.wordsenglish);
                startActivity(startgamebot);
                break;
            case R.id.button_startGameRus:
                Intent startgamemRus = new Intent(MainActivity.this, GameActivity.class);
                startgamemRus.putExtra("player1name", player1name.getText().toString());
                startgamemRus.putExtra("player2name", player2name.getText().toString());
                startgamemRus.putExtra("player1avatar", player1avatar);
                startgamemRus.putExtra("player2avatar", player2avatar);
                startgamemRus.putExtra("vocabularyid", R.raw.wordsrussian);
                startActivity(startgamemRus);
                break;
            case R.id.button_playaiRus:
                Intent startgamebotRus = new Intent(MainActivity.this, GameActivity.class);
                startgamebotRus.putExtra("player1name", player1name.getText().toString());
                startgamebotRus.putExtra("player2name", "Бот Джек");
                startgamebotRus.putExtra("player1avatar", player1avatar);
                startgamebotRus.putExtra("CheckAI", true);
                startgamebotRus.putExtra("vocabularyid", R.raw.wordsrussian);
                startActivity(startgamebotRus);
                break;
        }
    }
}
