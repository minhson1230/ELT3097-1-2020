package com.huyhoang.CrazyMath;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

import com.huyhoang.CrazyMath.R;

public class PlayActivity extends Activity implements View.OnClickListener,
		Runnable {
	public static final int TIME = 3;
	public static final String SCORE = "Score";

	private TextView tvBest;
	private TextView tvScore;
	private TextView tvMath;
	private TextView tvTime;
	private ImageButton btLeft;
	private ImageButton btRight;
	private Random mRandom;
	private int number1;
	private int number2;
	private int number3;
	private int answer;
	private int time;
	private int score;
	private boolean isResult;
	private boolean isCheck;
	private SharedPreferences pref;
	private Handler mHandler;
	private int bestScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_layout);
		initView();
		typeFace();
		score = 0;
		mRandom = new Random();
		mHandler = new Handler();
		randomQuestion();
		setGameStatus();
	}

	private void initView() {
		tvBest = (TextView) findViewById(R.id.tv_best_play);
		tvScore = (TextView) findViewById(R.id.tv_score_play);
		tvMath = (TextView) findViewById(R.id.tv_math);
		tvTime = (TextView) findViewById(R.id.tv_time);
		btLeft = (ImageButton) findViewById(R.id.bt_left);
		btRight = (ImageButton) findViewById(R.id.bt_right);
		btLeft.setOnClickListener(this);
		btRight.setOnClickListener(this);

	}

	private void setGameStatus() {
		String strScore = getString(R.string.score, score);
		tvScore.setText(strScore);
		pref = getSharedPreferences(SCORE, Context.MODE_PRIVATE);
		bestScore = pref.getInt(SCORE, 0);
		String strBest = getString(R.string.best, bestScore);
		tvBest.setText(strBest);
	}

	private void typeFace() {
		Typeface typeface = Typeface.createFromAsset(getAssets(),
				MyActivity.FONT_PATH);
		tvBest.setTypeface(typeface);
		tvScore.setTypeface(typeface);
		tvMath.setTypeface(typeface);
		tvTime.setTypeface(typeface);
	}

	private void randomQuestion() {
		time = TIME;
		number1 = mRandom.nextInt(20);
		number2 = mRandom.nextInt(20);
		answer = number1 + number2;
		isResult = mRandom.nextBoolean();
		int other = mRandom.nextInt();
		if (isResult == true) {
			number3 = answer;
			isCheck = true;
		} else {
			if (number1 % 2 == 0) {
				if (other <= 5 && other >= -5) {
					number3 = answer + other;
				} else {
					number3 = answer + 1;
				}
				isCheck = false;
			} else {
				if (other <= 5 && other >= -5) {
					number3 = answer - other;
				} else {
					number3 = answer - 1;
				}
				isCheck = false;
			}
		}
		String result = number1 + " + " + number2 + " = " + number3;
		tvMath.setText(result);
		for (int i = 0; i < time + 1; i++) {
			mHandler.postDelayed(this, i * 1000);
		}
	}

	@Override
	public void onClick(View v) {
		mHandler.removeCallbacksAndMessages(null);
		switch (v.getId()) {
		case R.id.bt_left:
			if (isCheck == false) {
				score++;
				MediaPlayer mediaPlayerPositive = MediaPlayer.create(
						getApplicationContext(), R.raw.positive);
				mediaPlayerPositive.start();
				setGameStatus();
				randomQuestion();
			} else {
				gameOver();
				MediaPlayer mediaPlayerNegative = MediaPlayer.create(
						getApplicationContext(), R.raw.gameover);
				mediaPlayerNegative.start();
			}
			break;

		case R.id.bt_right:
			if (isCheck == true) {
				score++;
				MediaPlayer mediaPlayerPositive = MediaPlayer.create(
						getApplicationContext(), R.raw.positive);
				mediaPlayerPositive.start();
				setGameStatus();
				randomQuestion();
			} else {
				gameOver();
				MediaPlayer mediaPlayerNegative = MediaPlayer.create(
						getApplicationContext(), R.raw.gameover);
				mediaPlayerNegative.start();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void run() {
		tvTime.setText(time + "");
		time--;
		if (time == -1) {
			gameOver();
			MediaPlayer mediaPlayerNegative = MediaPlayer.create(
					getApplicationContext(), R.raw.gameover);
			mediaPlayerNegative.start();

		}
	}

	private void gameOver() {
		if (score > bestScore) {
			pref.edit().putInt(SCORE, score).commit();
		}
		FragmentManager fm = getFragmentManager();
		DialogFragment myDialog = MyDialog.newInstance(score);
		try {
			myDialog.show(fm, "Dialog");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean onKeyDown(final int keyCode, final KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			finish();
			System.exit(0);
		}
		return true;
	}

}
