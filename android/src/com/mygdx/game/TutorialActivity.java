package com.mygdx.game;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TutorialActivity extends Activity {
    int height,width;
    int counter = 0;

    SharedPreferences sp;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_layout);
        height = getIntent().getIntExtra("height", 128);
        width = getIntent().getIntExtra("width", 128);
        sp = getSharedPreferences("player_detailes",MODE_PRIVATE);

       // final ImageView Arrow = findViewById(R.id.);
        final ImageView image1 = findViewById(R.id.finger1);
        final ImageView image2 = findViewById(R.id.finger2);
        final ImageView image3 = findViewById(R.id.finger3);
        final ImageView image4 = findViewById(R.id.finger4);
        final TextView text = findViewById(R.id.explain_textview);
        text.setText(getString(R.string.tutorial_text_explenation0));

        image1.setAlpha(1f);
        image2.setAlpha(0f);
        image3.setAlpha(0f);
        image4.setAlpha(0f);

        final RelativeLayout layout = findViewById(R.id.layout_tutorial);
        ObjectAnimator ob;

        ob = ObjectAnimator.ofFloat(image1,"translationX",width*6/8).setDuration(1500);
        ob.setRepeatCount(3);
        ob.setRepeatMode(ValueAnimator.REVERSE);
        ob.start();


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator ob;
                switch(counter){
                    case 0:{
                        //finger1 disappear
                        ob = ObjectAnimator.ofFloat(image1,"alpha",0f).setDuration(500);
                        ob.start();
                        //finger 2 appear and moves
                        ob = ObjectAnimator.ofFloat(image2,"alpha",1f).setDuration(500);
                        ob.start();
                        //change text
                        text.setText(getString(R.string.tutorial_text_explenation1));
                        counter++;
                        break;
                    }
                    case 1:{
                        //finger1 disappear
                        ob = ObjectAnimator.ofFloat(image2,"alpha",0f).setDuration(500);
                        ob.start();
                        //finger 2 appear and moves
                        ob = ObjectAnimator.ofFloat(image3,"alpha",1f).setDuration(500);
                        ob.start();
                        counter++;
                        //change text
                        text.setText(getString(R.string.tutorial_text_explenation2));
                        break;
                    }
                    case 2:{
                        //finger1 disappear
                        ob = ObjectAnimator.ofFloat(image3,"alpha",0f).setDuration(500);
                        ob.start();
                        //finger 2 appear and moves
                        ob = ObjectAnimator.ofFloat(image4,"alpha",1f).setDuration(500);
                        ob.start();
                        counter++;
                        //change text
                        text.setText(getString(R.string.tutorial_text_explenation3));
                        break;
                    }
                    case 3:{
                        Intent intent = new Intent(TutorialActivity.this,LevelSelection.class);
                        intent.putExtra("height", height);
                        intent.putExtra("width", width);
                        startActivity(intent);
                        TutorialActivity.this.finish();
                    }

                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferenceFirstUse();
    }

    private void sharedPreferenceFirstUse(){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("not_first_time_use",true);
        editor.apply();
    }
}
