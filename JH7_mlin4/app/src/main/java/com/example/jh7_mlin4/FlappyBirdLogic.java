package com.example.jh7_mlin4;

public class FlappyBirdLogic {


		// TODO Auto-generated method stub
	int gapSpace=100;
	BirdPhysics onTouch(BirdPhysics birdLocation)
	{
		BirdPhysics updatedBirdLocation=birdLocation;
		updatedBirdLocation.onTouch();
		return updatedBirdLocation;
	}
	Boolean gameOver(ShaftPoint[] shaftPoint, BirdPhysics birdLocation)
	{
		for (int i=0; i<shaftPoint.length; i++)
		{
			if(shaftPoint[i].top_x<=20 && shaftPoint[i].top_x>-20)
			{
				if (shaftPoint[i].gapPlace> (300-birdLocation.y_top)|| (shaftPoint[i].gapPlace+gapSpace)<(300-(birdLocation.y_top+20)))
				return true;
			}
			
		}
		if (birdLocation.y_top<-373)
			return true;
		if (birdLocation.y_top>300)
			return true;
		return false;
	}
	
	

}
