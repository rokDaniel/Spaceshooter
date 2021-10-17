package com.mygdx.game;

public abstract class AlertDialogCallback {
    public abstract void positiveButtonPressed();
    public void negativeButtonPressed(){};
    public void cancelled(){};
}
