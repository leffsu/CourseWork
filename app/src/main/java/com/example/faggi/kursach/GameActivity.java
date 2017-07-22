package com.example.faggi.kursach;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    // misc
    final String statusOff = "Not your's yet!";
    final String statusOn = "It's your turn!";
    final String MISC_EMPTYWORD = null;
    // avatars and flashlights
    ImageView player1avatarpic;
    ImageView player2avatarpic;
    ImageView player1status;
    ImageView player2status;
    // buttons for sending a word and conceding
    Button sendWord;
    Button concede;
    // textviews for showing a main word, statuses and names
    TextView mainWord;
    TextView player1turnstatus;
    TextView player2turnstatus;
    TextView player1names;
    TextView player2names;
    // sending word field
    EditText writeWord;
    // turn identifier
    int turnStatus = 1;
    // avatar IDs
    int player1avatar = 1;
    int player2avatar = 1;
    // vocabulary file
    int file;
    // main word itself and player names
    String activeWord;
    String player1name = "1";
    String player2name = "1";
    // history
    ArrayList<String> wordHistory; // array list for saving words already used
    // ai module on/off
    boolean AIIdentifier; // true - AI plays, false - player vs player
    // ?
    Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent i = getIntent();

        // getting avatars, names and vocabulary
        player1name = i.getStringExtra("player1name");
        player2name = i.getStringExtra("player2name");
        player1avatar = i.getIntExtra("player1avatar", 3);
        player2avatar = i.getIntExtra("player2avatar", 3);
        AIIdentifier = i.getBooleanExtra("CheckAI", false);
        file = i.getIntExtra("vocabularyid", R.raw.wordsenglish);

        // initialize buttons
        sendWord = (Button) findViewById(R.id.button_sendWord);
        sendWord.setOnClickListener(this);
        concede = (Button) findViewById(R.id.button_concede);
        concede.setOnClickListener(this);

        //initializing of flashlights
        player1turnstatus = (TextView) findViewById(R.id.textView_player1turnstatus);
        player1turnstatus.setText(statusOn);
        player2turnstatus = (TextView) findViewById(R.id.textView_player2turnstatus);
        player2turnstatus.setText(statusOff);

        //initializing a write word field
        writeWord = (EditText) findViewById(R.id.editText_writeWord);

        //getting a main word
        mainWord = (TextView) findViewById(R.id.textView_mainWord);
        Game.gameWord = readWord();
        mainWord.setText(Game.gameWord);

        // initializing
        player1status = (ImageView) findViewById(R.id.imageView_player1status);
        player1status.setImageResource(R.drawable.flashlighton);
        player2status = (ImageView) findViewById(R.id.imageView_player2status);
        player2status.setImageResource(R.drawable.flashlightoff);

        //setting names
        player1names = (TextView) findViewById(R.id.textView_player1nameq);
        player1names.setText(player1name);
        player2names = (TextView) findViewById(R.id.textView_player2nameq);
        player2names.setText(player2name);

        //setting avatars
        player1avatarpic = (ImageView) findViewById(R.id.imageView_player1avatar);
        player2avatarpic = (ImageView) findViewById(R.id.imageView_player2avatar);

        //adding necessary stuff into history!
        wordHistory.add(MISC_EMPTYWORD);
        wordHistory.add(Game.gameWord);

        // initalize the char array for checking words
        Game.onStart();

        // avatar switch
        switch (player1avatar) {
            case 0:
                player1avatarpic.setImageResource(R.drawable.blackandwhite);
                break;
            case 1:
                player1avatarpic.setImageResource(R.drawable.redish);
                break;
            case 2:
                player1avatarpic.setImageResource(R.drawable.blueish);
                break;
            case 3:
                player1avatarpic.setImageResource(R.drawable.standard);
                break;
        }
        switch (player2avatar) {
            case 0:
                player2avatarpic.setImageResource(R.drawable.blackandwhite);
                break;
            case 1:
                player2avatarpic.setImageResource(R.drawable.redish);
                break;
            case 2:
                player2avatarpic.setImageResource(R.drawable.blueish);
                break;
            case 3:
                player2avatarpic.setImageResource(R.drawable.standard);
                break;
        }
        turnDecider();
    }

    // I want player to always go first...
    void turnDecider() {
        if (!AIIdentifier) {
            randomizeTurn();
        }
        if (AIIdentifier) {
            makeBotSecond();
        }
    }

    // ...that's why we make a bot second
    void makeBotSecond() {
        player2status.setImageResource(R.drawable.flashlightoff);
        player1status.setImageResource(R.drawable.flashlighton);
        player2turnstatus.setText(statusOff);
        player1turnstatus.setText(statusOn);
        turnStatus = 1;
    }

    // bot can lose nonetheless
    void botLost() {

        Intent victory1 = new Intent(this, VictoryActivity.class);
        victory1.putExtra("winnerName", player1name);
        victory1.putExtra("winnerAvatar", player1avatar);
        startActivity(victory1);
        finish();
    }

    // my bad ai I'll have to remake
    void AITurn() {
        if (turnStatus == 2) {
            String eachline = "kotiki";
            InputStream inputStream = getResources().openRawResource(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (eachline != null) {
                float chance = r.nextFloat();
                try {
                    eachline = bufferedReader.readLine();
                    if (Game.checkWord(eachline)) {
                        if (!wordHistory.contains(eachline)) {
                            if (chance <= 0.9f) {
                                AlertDialog alertDialog = new AlertDialog.Builder(GameActivity.this).create();
                                alertDialog.setTitle("Bot turn");
                                alertDialog.setMessage("The word Bot chose is " + eachline);
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK ",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();

                                wordHistory.add(eachline);
                                player2status.setImageResource(R.drawable.flashlightoff);
                                player1status.setImageResource(R.drawable.flashlighton);
                                player2turnstatus.setText(statusOff);
                                player1turnstatus.setText(statusOn);
                                turnStatus = 1;
                                return;
                            } else botLost();
                        }
                    }
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // cycling through the vocabularies
    String vocRead(BufferedReader bufferedReader) throws java.io.IOException {
        float chanceDetector;
        if (file == R.raw.wordsrussian)
            chanceDetector = 0.0009f;
        else chanceDetector = 0.00003f;
        String eachline = "kotiki";
        while (eachline != null) {
            float chance = r.nextFloat();
            try {
                eachline = bufferedReader.readLine();
                if (chance <= chanceDetector) {
                    return eachline;
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // getting a word after one-two cycles (or more because of how random and cruel my life is)
    String readWord() {
        String eachline = null;
        InputStream inputStream = getResources().openRawResource(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            eachline = vocRead(bufferedReader);
            while (eachline == null)
                eachline = vocRead(bufferedReader);

            bufferedReader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return eachline;
    }

    // players' start turn order should be random
    void randomizeTurn() {
        int turnRandomizer = r.nextInt(4);
        if (turnRandomizer % 2 == 1) {
            player2status.setImageResource(R.drawable.flashlightoff);
            player1status.setImageResource(R.drawable.flashlighton);
            player2turnstatus.setText(statusOff);
            player1turnstatus.setText(statusOn);
            turnStatus = 1;
        } else {
            player1status.setImageResource(R.drawable.flashlightoff);
            player2status.setImageResource(R.drawable.flashlighton);
            player1turnstatus.setText(statusOff);
            player2turnstatus.setText(statusOn);
            turnStatus = 2;
        }
    }

    void firstWon() {
        Intent victory1 = new Intent(this, VictoryActivity.class);
        victory1.putExtra("winnerName", player1name);
        victory1.putExtra("winnerAvatar", player1avatar);
        startActivity(victory1);
        finish();
    }

    void secondWon() {
        Intent victory2 = new Intent(this, VictoryActivity.class);
        victory2.putExtra("winnerName", player2name);
        victory2.putExtra("winnerAvatar", player2avatar);
        startActivity(victory2);
        finish();
    }
//    public void onResume() {
//        super.onResume();
//        randomizeTurn();
//    }

    // ?
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sendWord:
                if (!wordHistory.contains(writeWord.getText().toString())) {
                    if (Game.checkWord(writeWord.getText().toString())) {
                        wordHistory.add(writeWord.getText().toString());
                        writeWord.setText("");
                        switch (turnStatus) {
                            case 1:
                                player1status.setImageResource(R.drawable.flashlightoff);
                                player2status.setImageResource(R.drawable.flashlighton);
                                player1turnstatus.setText(statusOff);
                                player2turnstatus.setText(statusOn);
                                turnStatus = 2;
                                if (AIIdentifier)
                                    AITurn();
                                break;
                            case 2:
                                player2status.setImageResource(R.drawable.flashlightoff);
                                player1status.setImageResource(R.drawable.flashlighton);
                                player2turnstatus.setText(statusOff);
                                player1turnstatus.setText(statusOn);
                                turnStatus = 1;
                                break;
                        }
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(GameActivity.this).create();
                        alertDialog.setTitle("Word not found");
                        alertDialog.setMessage("We sent this word for the moderation!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        writeWord.setText("");
                        SendToServer.send(activeWord);
                    }
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(GameActivity.this).create();
                    alertDialog.setTitle("Word was already used!");
                    alertDialog.setMessage(writeWord.getText().toString());
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    writeWord.setText("");
                }
                break;
            case R.id.button_concede:
                if (turnStatus == 1) {
                    if (player2name == null) {
                        player2name = "Player 2";
                    }
                    secondWon();
                }
                if (turnStatus == 2) {
                    if (player1name == null) {
                        player1name = "Player 1";
                    }
                    firstWon();
                }
                break;
        }
    }
}
