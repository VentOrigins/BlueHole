package com.example.mandee.bluehole;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.Random;


public class BlueHoleMain extends ActionBarActivity {
    Handler h = new Handler();
    int delay = 10000; //milliseconds
    int num = 0;
    Random rand = new Random();

    // The screen size of device
    int screenWidth = 0;
    int screenHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_hole_main);

        // Get screen size and stores it into screenWidth and screenHeight
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        screenWidth = display.widthPixels;
        screenHeight = display.heightPixels;

        System.out.println("Screen width: " + screenWidth);
        System.out.println("Screen height: " + screenHeight);

        RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.rlayout);
        //Global class game ball which indicates which ball to get

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive

        rlayout.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View v, MotionEvent event) {
                RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.rlayout);
                Resources r = getResources();
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16, r.getDisplayMetrics());
                int top = rlayout.getTop() + Math.round(px);
                int bottom = rlayout.getBottom() - Math.round(px);
                int left = rlayout.getLeft() + Math.round(px);
                int right = rlayout.getRight() - Math.round(px);
                ImageView image = (ImageView) findViewById(R.id.bluehole);
                int x = image.getWidth()/2;
                int y = image.getHeight()/2;
                if(event.getX() < left || event.getX() > right || event.getY() < top || event.getY() > bottom) {
                    return false;
                }
                image.setX((int) event.getX()-x);
                image.setY((int) event.getY() - y);
                return false;
            }
        });

        // Every time 5 seconds, call this
        h.postDelayed(new Runnable() {
            RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.rlayout);
            public void run() {
                Resources r = getResources();
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());
                int top = rlayout.getTop() + Math.round(px);
                int bottom = rlayout.getBottom() - Math.round(px);
                int left = rlayout.getLeft() + Math.round(px);
                int right = rlayout.getRight() - Math.round(px);

                // The argument values for the ball's constructor
                long ballSpeed = 30;
                int startingBallPosX = rand.nextInt(right);
                int startingBallPosY = top;
                System.out.println("The starting x position: " + startingBallPosX);
                int changeOfBallPosX = rand.nextInt(41) - 20;
                int changeOfBallPosY = rand.nextInt(21) + 1;
                System.out.println("The changeOfBall Pos X: " + changeOfBallPosX);
                System.out.println("The changeOfBall Pos Y: " + changeOfBallPosY);
                RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.rlayout);
                ImageView ballImage = new ImageView(BlueHoleMain.this);
                ImageView blueHole = (ImageView) findViewById(R.id.bluehole);

                Ball x = new Ball(ballSpeed, startingBallPosX, startingBallPosY, changeOfBallPosX, changeOfBallPosY, rlayout, blueHole, ballImage, top, bottom, left, right);
                num = num + 10;

                h.postDelayed(this, delay);
            }
        }, delay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_blue_hole_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
