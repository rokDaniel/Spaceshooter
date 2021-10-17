package com.mygdx.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PopUpDialog implements DialogPopupInCore {
    private AndroidLauncher gameActivity; // This is the main android activity
    private static int score;
    AlertDialog alertDialog;



    public PopUpDialog(AndroidLauncher gameActivity) {
        this.gameActivity = gameActivity;
        this.score = 0;
    }

    @Override
    public void OpenGameOverAlertDialog(final int level,final int score, final AlertDialogCallback AlertDialogcallback) {
        final Resources res = MainActivity.resRef;
        gameActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(gameActivity);
                builder.setCancelable(false);
                View dialogView = gameActivity.getLayoutInflater().inflate(R.layout.gameover_layout, null);
                final EditText nameToLeaderboard = dialogView.findViewById(R.id.name_leaderbord);
                final Button submitBtn = dialogView.findViewById(R.id.btn_submit);
                builder.setPositiveButton(res.getString(R.string.gameover_btn_again), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialogcallback.positiveButtonPressed();
                    }
                });

                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitBtn.setClickable(false);
                        submitBtn.setActivated(false);
                        submitBtn.setText(res.getString(R.string.popup_text_saved_checked));
                        addEntryLeaderBoard(nameToLeaderboard.getText().toString(), score, level);
                    }
                });
                alertDialog = builder.setView(dialogView).show();
            }
        });
    }

    @Override
    public void openWinningScreen(final int level, int score, final AlertDialogCallback callBack) {
        final int thisRoundScore = score;
        final Resources res = MainActivity.resRef;
        if( level >= LevelSelection.highestLevelReached){
            LevelSelection.highestLevelReached = level;
        }
        gameActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(gameActivity);
                builder.setCancelable(false);
                View dialogView = gameActivity.getLayoutInflater().inflate(R.layout.gameover_layout, null);
                TextView text = dialogView.findViewById(R.id.win_over_text);
                text.setText(res.getString(R.string.winpopup_text_title));
                final EditText nameToLeaderboard = dialogView.findViewById(R.id.name_leaderbord);
                final Button submitBtn = dialogView.findViewById(R.id.btn_submit);
                builder.setPositiveButton(res.getString(R.string.winpopup_btn_Next), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.positiveButtonPressed();
                    }
                });

                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitBtn.setClickable(false);
                        submitBtn.setActivated(false);
                        submitBtn.setText("Score Saved!");
                        addEntryLeaderBoard(nameToLeaderboard.getText().toString(), thisRoundScore, level);
                    }
                });

                alertDialog = builder.setView(dialogView).show();
            }
        });
    }


    @Override
    public void addEntryLeaderBoard(String name,int score,int levelNum) {
        int nextNum = LeaderboardListActivity.sp.getInt("number_of_scores",0);
        LeaderboardListActivity.sp.edit().putInt("number_of_scores",nextNum+1).apply();
        LeaderboardListActivity.addEntryToLeaderBoard(name,score,nextNum);
    }

    @Override
    public String getTextFromResources(String toGet) {
        Resources res = MainActivity.resRef;
        String toReturn = null;
        switch (toGet){
            case "score":{
                toReturn = res.getString(R.string.game_text_score);
                return toReturn;
            }
            case "lives":{
                toReturn = res.getString(R.string.game_text_lives);
                return toReturn;
            }
            case "shield":{
                toReturn = res.getString(R.string.game_text_shield);
                return toReturn;
            }
        }
        return toReturn;
    }

    @Override
    public String getLanguageOfDevice() {
        return AndroidLauncher.language;
    }


}


