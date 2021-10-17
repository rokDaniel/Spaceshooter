package com.mygdx.game;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {
    SharedPreferences sp;
    boolean notFirstTimeUse;
    public static Resources resRef;
    MediaPlayer player;
    Thread musicPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        sp = getSharedPreferences("player_detailes",MODE_PRIVATE);
        musicPlaying = new Thread(){

                public void run(){
                    player = MediaPlayer.create(MainActivity.this, R.raw.interstellar_odyssey);
                    player.setLooping(true);
                    player.start();
                }
            };
        musicPlaying.start();
        if(sp.contains("not_first_time_use")) {
            notFirstTimeUse = sp.getBoolean("not_first_time_use", true);
        }
        else
            notFirstTimeUse = false;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        final int width = displayMetrics.widthPixels;
        resRef = getResources();

        Button play_btn = findViewById(R.id.play_btn);
        Button leader_btn = findViewById(R.id.scoreboard_btn);
        Button quit_btn = findViewById(R.id.quit_btn);

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                if(!notFirstTimeUse){
                    intent = new Intent(MainActivity.this, TutorialActivity.class);
                }
                else {
                    intent = new Intent(MainActivity.this, LevelSelection.class);
                }
                intent.putExtra("height", height);
                intent.putExtra("width", width);
                sp.edit().putBoolean("music_again",false).apply();
                startActivity(intent);
            }
        });

        leader_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LeaderboardListActivity.class);
                startActivity(intent);
            }
        });

        quit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
                System.exit(0);

            }
        });

        LeaderboardListActivity.sp = getSharedPreferences("leader_list",MODE_PRIVATE);
    }
}
