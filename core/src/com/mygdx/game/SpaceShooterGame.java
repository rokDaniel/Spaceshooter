package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;


import java.util.Random;

public class SpaceShooterGame extends Game {

	static DialogPopupInCore callBack;
	DeviceContext dc;
	static OpenIntent openIntentObject;
	GameScreen gameScreen;
	int levelNum;
	int count = 0;

	public static Random random = new Random();

	SpaceShooterGame(DialogPopupInCore DialogPopup, int levelNum, DeviceContext dc,OpenIntent openIntentObject){
		callBack = DialogPopup;
		this.openIntentObject = openIntentObject;
		this.dc = dc;
		this.levelNum = levelNum;
	}
	@Override
	public void create() {
		gameScreen = new GameScreen(levelNum,dc,callBack);
		setScreen(gameScreen);
		count++;
	}

	@Override
	public void dispose() {
		gameScreen.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		gameScreen.resize(width, height);
	}


	public static void GameOverDialog(final int levelNum,final int score){
		callBack.OpenGameOverAlertDialog(levelNum,score, new AlertDialogCallback() {
			@Override
			public void positiveButtonPressed() {
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {

						SpaceShooterGame.openIntentObject.OpenLevel(levelNum);
						Gdx.app.exit();
					}
				});
			}

			@Override
			public void negativeButtonPressed() {
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						openIntentObject.OpenMainMenu();
					}
				});
				Gdx.app.exit();
			}
		});

	}
	public static void winningDialog(final int levelNum,int score){

		callBack.openWinningScreen(levelNum,score, new AlertDialogCallback() {
			@Override
			public void positiveButtonPressed() {
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {

						SpaceShooterGame.openIntentObject.OpenLevel(levelNum);
						Gdx.app.exit();
					}
				});
			}

			@Override
			public void negativeButtonPressed() {
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						openIntentObject.OpenMainMenu();
					}
				});
				Gdx.app.exit();
			}
		});

	}
}
