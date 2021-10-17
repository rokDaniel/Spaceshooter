package com.mygdx.game;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LeaderboardListActivity extends ListActivity {

    static List<Map<String,Object>> EntryList = new ArrayList<>();
    int numOfScores;
    LeaderboardEntry leaderBoardArray[];

    static SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_layout);
        HashMap<String,Object> toDisplay;


        sp = getSharedPreferences("leader_list",MODE_PRIVATE);
        numOfScores = sp.getInt("number_of_scores",0);

        leaderBoardArray = new LeaderboardEntry[numOfScores];
        initLeaderArray(numOfScores);


        setDividerColor();
        String name = getIntent().getStringExtra("name");
        if(name!=null) {
            addEntryToLeaderBoard(getIntent().getStringExtra("name"),getIntent().getIntExtra("score",0),numOfScores+1);
        }

        for(int i=0;i<numOfScores;i++){
            leaderBoardArray[i].setName(sp.getString(i+"name","nan"));
            leaderBoardArray[i].setScore(sp.getInt(i+"score",0));
        }
        Arrays.sort(leaderBoardArray);
        EntryList.clear();

        for(int i=0;i<numOfScores;i++){
            toDisplay = new HashMap<>();
            toDisplay.put("name",leaderBoardArray[i].getName());
            toDisplay.put("score",leaderBoardArray[i].getScore());
            EntryList.add(toDisplay);
        }

        String[] from = {"name","score"};
        int[] to  = {R.id.entry_name,R.id.entry_score};
        SimpleAdapter adapter = new SimpleAdapter(this,EntryList,R.layout.leaderboard_cell,from,to);
        setListAdapter(adapter);



    }

    @Override
    protected void onPause() {
        super.onPause();
        int count = 0;
        SharedPreferences.Editor editor = sp.edit();
        for(Map<String, Object> map : EntryList){
            Object[] infoToSave = map.values().toArray();
            editor.putInt(count + "score",(int)infoToSave[0]);
            editor.putString(count + "name",(String)infoToSave[1]);
            count++;
        }
        editor.putInt("number_of_scores",count);
        editor.apply();
    }

    private void setDividerColor(){
        ListView lv = getListView();
        ColorDrawable sage = new ColorDrawable(this.getResources().getColor(R.color.ic_background_color));
        lv.setDivider(sage);
        lv.setDividerHeight(1);
    }

    public static void addEntryToLeaderBoard(String name,int score,int nextNum){

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(nextNum + "name",name);
        editor.putInt(nextNum+"score",score);
        editor.apply();


//        HashMap<String,Object> toDisplay = new HashMap<>();
//        toDisplay.put("name",name);
//        toDisplay.put("score",score);
//        EntryList.add(toDisplay);
    }

    @Override
    protected void onResume() {
        super.onResume();
        numOfScores = sp.getInt("number_of_scores",0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private HashMap<String,Object> copyHashMap(HashMap<String,Object> originalMap){
        HashMap<String, Object> copy = new HashMap<>();
        for (Map.Entry<String, Object> entry : originalMap.entrySet())
        {
            copy.put(entry.getKey(),entry.getValue());
        }
        return copy;
    }

    private void initLeaderArray(int size){
        for(int i =0;i<size;i++){
            leaderBoardArray[i] = new LeaderboardEntry();
        }
    }
}
