package com.example.mandee.bluehole;

import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;


public class BlueHoleMain extends ActionBarActivity {
    Handler h = new Handler();
<<<<<<< HEAD
    private int ballSpawnSpeed = 5000; //milliseconds
    private int ballMovementSpeed = 10;
    private BlueHole blueHole;
    private Resources r;
    private RelativeLayout gameLayout;
    private RelativeLayout rlayout;
    private ImageView nextBall;


=======
    int ballSpawnSpeed = 5000; //milliseconds
    int ballMovementSpeed = 10;
    boolean ifPaused = false;
    Game game;
    boolean ifFirstTimeRunning = true;
>>>>>>> 00a52e17ea08146200f810f3ab061e41cd9c1cb6

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_hole_main);
    }

    @Override
    public void onPause() {
        super.onPause();
        ifPaused = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        ifPaused = false;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
<<<<<<< HEAD
        createBlueHole();
        onBlueHoleTouch();
        Game game = gameInit();
=======
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && ifFirstTimeRunning) {
            ifFirstTimeRunning = false;
            onBlueHoleTouch();
            game = gameInit();
        }
>>>>>>> 00a52e17ea08146200f810f3ab061e41cd9c1cb6
        ballSpawnTick(game);
        ballRenderTick(game);
    }

    public void createBlueHole() {
        nextBall = (ImageView) findViewById(R.id.nextBall);
        nextBall.setTag("Red");
        gameLayout = (RelativeLayout) findViewById(R.id.gameLayout);
        rlayout = (RelativeLayout) findViewById(R.id.rlayout);
        ImageView imageViewBH = (ImageView) findViewById(R.id.bluehole);
        r = getResources();
        float bhWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics());
        float bhHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics());

        this.blueHole = new BlueHole(imageViewBH, bhWidth, bhHeight);
    }

    public void onBlueHoleTouch() {

        rlayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16, r.getDisplayMetrics());
                int top = gameLayout.getTop() + Math.round(px);
                int bottom = gameLayout.getBottom() - Math.round(px);
                int left = gameLayout.getLeft() + Math.round(px);
                int right = gameLayout.getRight() - Math.round(px);
                ImageView imageBH = (ImageView) findViewById(R.id.bluehole);
                int x = imageBH.getWidth()/2;
                int y = imageBH.getHeight()/2;
                if(event.getX() < left || event.getX() > right || event.getY() < top || event.getY() > bottom) {
                    return false;
                }
                blueHole.setX((int) event.getX() - x);
                blueHole.setY((int) event.getY() - y);
                return false;
            }
        });
    }

    public Game gameInit() {
        // Creates the bounds
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());
        float top = gameLayout.getTop() + px;
        float bottom = gameLayout.getBottom() - px;
        float left = gameLayout.getLeft() + px;
        float right = gameLayout.getRight() - px;

        TextView textBar = (TextView) findViewById(R.id.textBar);
        TextView scoreBar = (TextView) findViewById(R.id.scoreBar);


        // Initializes the game
        final Game game = new Game(top, bottom, left, right, blueHole, nextBall, textBar, scoreBar);


        return game;
    }

    public void ballSpawnTick(final Game game) {
        // Every time 5 seconds, call this
        h.postDelayed(new Runnable() {


            public void run() {
                if (!ifPaused) {
                    ImageView ballImage = new ImageView(BlueHoleMain.this);
                    game.addBallToBallList(rlayout, ballImage);
                    game.printAllBalls();
                    h.postDelayed(this, ballSpawnSpeed);
                }
            }
        }, ballSpawnSpeed);
    }

    public void ballRenderTick(final Game game) {
        // Every 50 milliseconds, call this
        h.postDelayed(new Runnable() {
            public void run() {
                if (!ifPaused) {
                    game.render();
                    h.postDelayed(this, ballMovementSpeed);
                }
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


        return super.onOptionsItemSelected(item);
    }
}
