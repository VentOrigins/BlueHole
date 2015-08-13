package com.example.mandee.bluehole;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
    int ballSpawnSpeed = 5000; //milliseconds
    int ballMovementSpeed = 50;

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

        RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.rlayout);
        rlayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) findViewById(R.id.bluehole);
                int x = image.getRight();
                int y = image.getTop();
                System.out.println(x);
                System.out.println(y);
                image.setX((int) event.getX());
                image.setY((int) event.getY());
                return true;
            }
        });

        // Initializes the game
        final Game game = new Game(screenWidth, screenHeight);
        game.init();

        System.out.println("Screen width: " + screenWidth);
        System.out.println("Screen height: " + screenHeight);

        // Every time 5 seconds, call this
        h.postDelayed(new Runnable() {
            public void run() {
                RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.rlayout);
                ImageView ballImage = new ImageView(BlueHoleMain.this);
                game.addBallToBallList(rlayout, ballImage);

                h.postDelayed(this, ballSpawnSpeed);
            }
        }, ballSpawnSpeed);

        // Every 50 milliseconds, call this
        h.postDelayed(new Runnable() {
            public void run() {
                game.render();

                h.postDelayed(this, ballMovementSpeed);
            }
        }, ballMovementSpeed);

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
