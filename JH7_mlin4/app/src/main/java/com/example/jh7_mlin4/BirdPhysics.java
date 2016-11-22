package com.example.jh7_mlin4;

import android.util.Log;

public class BirdPhysics {
	double v_y=0;
	double period=0.05;
	double gravity=-200;
	double acceleration=0;
	double y_top=0;
	void speedUp()
	{
		v_y+=(gravity+acceleration)*period;
		if(acceleration!=0)
			acceleration-=3500;
		if(Math.abs(v_y)>340)
		{
			if (v_y<0)
				v_y=-340;
			if (v_y>0)
				v_y=340;
		}
		locationDeterminer();
	}
	void onTouch()
	{
		acceleration=3500;
		speedUp();
		locationDeterminer();
	}
	void locationDeterminer()
	{
		y_top+=(v_y*period);
		Log.d("Mine", "Calculated y_top= "+y_top+ " v_y= "+v_y);

	}
	
	
}
