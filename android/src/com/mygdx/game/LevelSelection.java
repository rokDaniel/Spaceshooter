package com.mygdx.game;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;




public class LevelSelection extends Activity {

    static int highestLevelReached;
    Button[] btnArray;


    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_selection);


        sp = getSharedPreferences("player_detailes",MODE_PRIVATE);
        if(sp.contains("highestLevel"))
            highestLevelReached = sp.getInt("highestLevel",0);
        else
            highestLevelReached = 0;



        int height = getIntent().getIntExtra("height", 128);
        int width = getIntent().getIntExtra("width", 72);

        btnArray = new Button[6];

        btnArray[0] = findViewById(R.id.level_one);
        btnArray[1] = findViewById(R.id.level_two);
        btnArray[2] = findViewById(R.id.level_three);
        btnArray[3] = findViewById(R.id.level_four);
        btnArray[4] = findViewById(R.id.level_five);
        btnArray[5] = findViewById(R.id.level_six);

        for (int i = 0; i < 6; i++) {
               btnArray[i].setOnClickListener(new OnLevelClick(height, width, i + 1));
               btnArray[i].setClickable(false);
        }
        btnArray[0].setClickable(true);
        ButtonsRefresh();
    }

    class OnLevelClick implements View.OnClickListener{
        private int height;
        private int width;
        private int levelNum;
        OnLevelClick(int height, int width,int levelNum){
            this.height = height;
            this.width = width;
            this.levelNum = levelNum;
        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LevelSelection.this,AndroidLauncher.class);
            intent.putExtra("height", height);
            intent.putExtra("width", width);
            intent.putExtra("level",levelNum);
            startActivity(intent);
            //LevelSelection.this.finish();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ButtonsRefresh(){
        for(int i = 0;i<highestLevelReached+1;i++){
            btnArray[i].setClickable(true);
            btnArray[i].setBackground(getDrawable(R.drawable.level_btn_active_selector));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("highestLevel",highestLevelReached);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ButtonsRefresh();
    }
}

