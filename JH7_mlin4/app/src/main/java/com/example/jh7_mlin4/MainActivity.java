package com.example.jh7_mlin4;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements Runnable, OnTouchListener, OnClickListener {

	DrawMyFlappyBird drawMyFlappyBird;
	
	 private Handler guiThread;
	 Button newGame, pause;
	 Boolean keepGoing=true;
	 Boolean onPause=false;
	 TextView score;
	 TextView highScore;
	 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		drawMyFlappyBird=(DrawMyFlappyBird)findViewById(R.id.drawMyFlappyBird);
		drawMyFlappyBird.setOnTouchListener(this);
		newGame=(Button)findViewById(R.id.newGame);
		newGame.setOnClickListener(this);
		pause=(Button)findViewById(R.id.pause);
		pause.setOnClickListener(this);
		score=(TextView)findViewById(R.id.score);
		highScore=(TextView)findViewById(R.id.highScore);
		guiThread = new Handler(); 
        Thread t= new Thread(this);
        t.start();
	}
	public void run()
    {
		Runnable work;
		while (keepGoing)
		{
			while(!onPause)
			{
				try{
					Thread.sleep(50);
			         work= new Runnable(){
			            public void run() {
			               drawMyFlappyBird.move(5);
			               drawMyFlappyBird.birdMove(0);
			               score.setText("Score: "+drawMyFlappyBird.count);
			               highScore.setText("High Score: "+drawMyFlappyBird.highScore);
			               if (drawMyFlappyBird.gameOver())
			            	   endGame();
			            }

			         };
			         guiThread.post(work);
			         //add keepGoing stuff here;
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
    }
	public void endGame()
	{
		Log.d("Mine", "Game Endings");
		//appropriate ending
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		if (!onPause)
		{
			Runnable work= new Runnable(){
	            public void run() {
	               drawMyFlappyBird.move(1);
	               drawMyFlappyBird.birdMove(1);
	               
	            }
	         };
	        Log.d("Mine2","Touch Received");
			guiThread.post(work);
		}
		return false;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId())
		{
			case R.id.newGame:
			// handle button A click;
			drawMyFlappyBird.reset();
			break;
			
			case R.id.pause:
			onPause=!onPause;
			// handle button B click;
			break;
		}
	}
}
