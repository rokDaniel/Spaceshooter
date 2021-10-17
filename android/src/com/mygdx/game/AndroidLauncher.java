package com.mygdx.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import java.util.Locale;

public class AndroidLauncher extends AndroidApplication implements DeviceContext,OpenIntent {
	SpaceShooterGame game;
	static String language;
	PopUpDialog popupRef;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		language = Locale.getDefault().getDisplayLanguage();
		popupRef = new PopUpDialog(this);
		game = new SpaceShooterGame(popupRef, getIntent().getIntExtra("level", 1),this,this);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(game, config);
	}


	@Override
	public int getHeight() {
		return getIntent().getIntExtra("height", 128);
	}

	@Override
	public int getWidth() {
		return getIntent().getIntExtra("width", 72);
	}

	@Override
	public void OpenLevel(int LevelNum) {
		Intent intent = new Intent(AndroidLauncher.this,AndroidLauncher.class);
		intent.putExtra("level",LevelNum);
		intent.putExtra("height", getHeight());
		intent.putExtra("width", getWidth());
		startActivity(intent);
	}

	@Override
	public void OpenMainMenu() {
		Intent intent = new Intent(this,MainActivity.class);

		startActivity(intent);
	}


	@Override
	public void onBackPressed() {
		Resources res = MainActivity.resRef;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final View dialogView = this.getLayoutInflater().inflate(R.layout.pause_background, null);
		game.gameScreen.state = GameScreen.gameState.PAUSE;
		builder.setPositiveButton(res.getString(R.string.pause_btn_continue), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				game.gameScreen.state = GameScreen.gameState.RUN;
			}
		});
		builder.setNegativeButton(res.getString(R.string.gameover_btn_quit), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AndroidLauncher.this.finish();
			}
		});
		builder.setView(dialogView).show();

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		language = Locale.getDefault().getDisplayLanguage();
	}
}


