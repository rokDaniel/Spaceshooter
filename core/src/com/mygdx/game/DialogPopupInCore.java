package com.mygdx.game;



public interface DialogPopupInCore {

    public void OpenGameOverAlertDialog(int level,int score,AlertDialogCallback callBack);
    public void openWinningScreen(int level,int score, AlertDialogCallback callBack);
    public void addEntryLeaderBoard(String name,int score,int levelNum);

    public String getTextFromResources(String toGet);
    public String getLanguageOfDevice();
}
