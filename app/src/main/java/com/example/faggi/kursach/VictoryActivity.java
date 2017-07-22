package com.example.faggi.kursach;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class VictoryActivity extends AppCompatActivity implements View.OnClickListener {

    TextView winnername;
    Button mainmenu;
    ImageView winneravatar;
    String victoryName;
    int victoryAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory);

        Intent victory = getIntent();

        victoryName = victory.getStringExtra("winnerName");
        victoryAvatar = victory.getIntExtra("winnerAvatar", 3);


        victoryName = victoryName +
                " won! GZ!";
        winnername = (TextView) findViewById(R.id.textView_winnerName);
        winnername.setText(victoryName);

        mainmenu = (Button) findViewById(R.id.button_returntomenu);
        mainmenu.setOnClickListener(this);

        winneravatar = (ImageView) findViewById(R.id.imageView_winnerAvatar);

        switch (victoryAvatar) {
            case 0:
                winneravatar.setImageResource(R.drawable.blackandwhite);
                break;
            case 1:
                winneravatar.setImageResource(R.drawable.redish);
                break;
            case 2:
                winneravatar.setImageResource(R.drawable.blueish);
                break;
            case 3:
                winneravatar.setImageResource(R.drawable.standard);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_returntomenu) {
            finish();
        }
    }
}
