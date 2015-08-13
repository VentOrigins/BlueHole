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
    int ballSpawnSpeed = 5000; //milliseconds
    int ballMovementSpeed = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_hole_main);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        onBlueHoleTouch();
        Game game = gameInit();
        ballSpawnTick(game);
        ballRenderTick(game);
    }

    public void onBlueHoleTouch() {
        RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.rlayout);
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
    }

    public Game gameInit() {
        // Creates the bounds
        Resources r = getResources();
        RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.rlayout);
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());
        int top = rlayout.getTop() + Math.round(px);
        int bottom = rlayout.getBottom() - Math.round(px);
        int left = rlayout.getLeft() + Math.round(px);
        int right = rlayout.getRight() - Math.round(px);

        // Initializes the game
        final Game game = new Game(top, bottom, left, right);
        game.init();

        System.out.println("px" + px + "layout" +rlayout.getBottom() + "Screen right: " + right);

        return game;
    }

    public void ballSpawnTick(final Game game) {
        // Every time 5 seconds, call this
        h.postDelayed(new Runnable() {
            RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.rlayout);

            public void run() {
                RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.rlayout);
                ImageView ballImage = new ImageView(BlueHoleMain.this);
                game.addBallToBallList(rlayout, ballImage);

                h.postDelayed(this, ballSpawnSpeed);
            }
        }, ballSpawnSpeed);
    }

    public void ballRenderTick(final Game game) {
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
