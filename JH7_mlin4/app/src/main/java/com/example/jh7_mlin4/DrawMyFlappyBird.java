package com.example.jh7_mlin4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class DrawMyFlappyBird extends View  {
	
	Paint paint = new Paint(Color.WHITE);
	float width, height;
	int numberOfShafts=3;
	int shaftWidth=40;
	int count=0;
	int highScore=0;
	Context context;
	RectF birdDraw;
	Bitmap birdImage;
	FlappyBirdLogic flappyBirdLogic;
	//InputStream imageStream;
    // Keeping instance variables can cut down on the number of calls to "new" which
    // can ease the burden on the Garbage collector.  onDraw routines can be called a lot
    
    /*Paint translucentRedPen, bluePen;
    RectF rectf= new RectF();
    int backgroundColor;*/
	ShaftPoint[] shaftPoint;
	BirdPhysics birdPhysics;
	
    public DrawMyFlappyBird(Context context) {
		super(context);
		this.context = context;
        init();
        flappyBirdLogic=new FlappyBirdLogic();
        
        
		// TODO Auto-generated constructor stub
	}
    public void reset()
    {
    	
    	init();
    	invalidate();
    	if (count>highScore)
    		highScore=count;
    	count=0;
    	
    }
    
    public DrawMyFlappyBird (Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        flappyBirdLogic=new FlappyBirdLogic();
        init();
    }
    public DrawMyFlappyBird (Context context, AttributeSet ats, int defaultStyle) {
        super(context, ats, defaultStyle);
        this.context = context;
        flappyBirdLogic=new FlappyBirdLogic();
        init();

        
    }
    // initialize all of our instance variables one time.
    private void init()
    {
    	int width=800; //change this so i'ts nohardcode
    	Log.d("Mine", "width= "+width);
    	int spacing=width/numberOfShafts;
    	shaftPoint=new ShaftPoint[numberOfShafts];
    	for (int i=0; i<shaftPoint.length; i++)
    	{
    		int GapPlace=(int) (Math.random()*600);
    		shaftPoint[i]=new ShaftPoint(width+i*spacing, GapPlace, width+i*spacing+shaftWidth);
    	}
    	//InputStream is=this.getResources().openRawResource(R.drawable.spaceship);
    	//Bitmap originalBitmap=BitmapFactory.decodeStream(is);
    	//birdImage=Picture.createFromStream(is);
    	birdImage=BitmapFactory.decodeResource(getResources(), R.drawable.spaceship);
    	birdPhysics=new BirdPhysics();
        Log.d("Mine","init called in DrawMyFlappyBird");
    }



    public void move(int speedOfMoving)
    {
    	for (int j=0; j<shaftPoint.length; j++)
    	{
        	if (shaftPoint[j].bottom_x<=0)
        	{
        		shaftPoint[j].top_x=(int) width;
        		shaftPoint[j].bottom_x=shaftPoint[j].top_x+shaftWidth;
        		shaftPoint[j].gapPlace=(int)(Math.random()*600);
        		count++;
        		//Log.d("Mine", "Shaft "+j + " has reached the end. top_x: "+shaftPoint[j].top_x + " bottom_x: " +shaftPoint[j].bottom_x);
        	}
        	else
        	{
        		shaftPoint[j].top_x-=speedOfMoving;
        		shaftPoint[j].bottom_x-=speedOfMoving;
        		//Log.d("Mine", "Shaft "+j + " is moving. top_x: "+shaftPoint[j].top_x + " bottom_x: " +shaftPoint[j].bottom_x);
        		
        	}
        	//Log.d("Mine", "top_x: "+shaftPoint[j].top_x + " bottom_x: " +shaftPoint[j].bottom_x);

        	invalidate();
    	}

    }

    public void birdMove(int touch)
    {
    	if (touch==0) //means no touch
    		birdPhysics.speedUp();
    	else if (touch==1) //means touch
    		birdPhysics.onTouch();
    }
    
    Boolean gameOver()
    {
    	if (flappyBirdLogic.gameOver(shaftPoint, birdPhysics))
    		reset();
    	return flappyBirdLogic.gameOver(shaftPoint, birdPhysics);
    }
    @Override
    protected void onDraw(Canvas canvas)	 {

        width = getWidth();
        height = getHeight();
        // Draw the background...
        //Log.d("Mine", "start onDraw width="+width + " height="+height);
        canvas.save();
        canvas.scale(width/600, height/650);
        for (int i=0; i<shaftPoint.length; i++)
        {
        	canvas.drawRect(shaftPoint[i].top_x, 0, shaftPoint[i].bottom_x, shaftPoint[i].gapPlace, new Paint(Color.WHITE));
        	canvas.drawRect(shaftPoint[i].top_x, shaftPoint[i].gapPlace+100, shaftPoint[i].bottom_x, 800, new Paint(Color.WHITE));
        }
        birdDraw =new RectF(0, 300+(int)birdPhysics.y_top*(-1),20, 300+(int)birdPhysics.y_top*(-1)+20);
        Rect birdDrawScale=new Rect(0,0,500,500);
        canvas.drawBitmap(birdImage, birdDrawScale, birdDraw, paint);
        //canvas.drawRect(0, 300+(int)birdPhysics.y_top*(-1),20, 300+(int)birdPhysics.y_top*(-1)+20,paint);
        Log.d("Mine", "birdPhysics.y_top= "+birdPhysics.y_top);
        
        canvas.restore();        
    }
}