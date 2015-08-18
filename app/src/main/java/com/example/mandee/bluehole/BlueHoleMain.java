package com.example.mandee.bluehole;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;


public class BlueHoleMain extends ActionBarActivity {
    Handler h = new Handler();

    int ballSpawnSpeed = 5000; //milliseconds
    int ballMovementSpeed = 10;

    private BlueHole blueHole;
    private Resources r;
    private RelativeLayout gameLayout;
    private RelativeLayout rlayout;
    private ImageView nextBall;



    private boolean ifPaused = false;
    private Game game;
    private boolean ifFirstTimeRunning = true;
    private TextView textBar;
    private TextView scoreBar;

    private Runnable ballSpawn;
    private Runnable ballRender;
    private Runnable gameOver;
    private Context thisContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_hole_main);
        thisContext = this;
        createRunnables();


    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("Paused");
        ifPaused = true;
        h.removeCallbacks(ballRender);
        h.removeCallbacks(ballSpawn);

    }

    @Override
    public void onResume() {
        super.onResume();
        ifPaused = false;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && ifFirstTimeRunning) {
            System.out.println(ifFirstTimeRunning + " ifFistTimeRunning");
            ifFirstTimeRunning = false;
            createBlueHole();
            onBlueHoleTouch();

            game = gameInit();
        }
        if(!game.isGameOver()) {
            ballSpawnTick();
            ballRenderTick();
        }

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
                if(game.isGameOver()){
                    return false;
                }
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());
                int top = gameLayout.getTop() + Math.round(px);
                int bottom = gameLayout.getBottom() - Math.round(px);
                int left = gameLayout.getLeft() + Math.round(px);
                int right = gameLayout.getRight() - Math.round(px);
                ImageView imageBH = (ImageView) findViewById(R.id.bluehole);
                int x = imageBH.getWidth() / 2;
                int y = imageBH.getHeight() / 2;

                if (event.getX() < left || event.getX() > right || event.getY() < top || event.getY() > bottom) {
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

        textBar = (TextView) findViewById(R.id.textBar);
        scoreBar = (TextView) findViewById(R.id.scoreBar);


        // Initializes the game
        final Game game = new Game(top, bottom, left, right, blueHole, nextBall, textBar, scoreBar);


        return game;
    }

    public void ballSpawnTick() {
        // Every time 5 seconds, call this

        h.postDelayed(ballSpawn, ballSpawnSpeed);
    }

    public void ballRenderTick() {
        // Every 50 milliseconds, call this


        h.postDelayed(ballRender, ballMovementSpeed);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }

    public void restartGame(View view) {

        game.restart();

        TextView highScore = (TextView) findViewById(R.id.highScore);
        Button restart = (Button) findViewById(R.id.restartButton);
        Button mainMenu = (Button) findViewById(R.id.mainMenuButton);
        highScore.setVisibility(View.INVISIBLE);
        restart.setVisibility(View.INVISIBLE);
        mainMenu.setVisibility(View.INVISIBLE);


        ballSpawnTick();
        ballRenderTick();

        blueHole.reset();

    }

    private void createRunnables() {

        ballSpawn = new Runnable() {
            public void run() {
                if (!ifPaused && !game.isGameOver()) {
                    ImageView ballImage = new ImageView(BlueHoleMain.this);
                    game.addBallToBallList(rlayout, ballImage);
                    game.addBlackToBallList(rlayout, ballImage);
//                    game.printAllBalls();
                    h.postDelayed(this, ballSpawnSpeed);
                }

            }
        };

        ballRender  = new Runnable() {
            public void run() {
                if (!ifPaused && !game.isGameOver()) {
                    game.render();
                    if(game.isGameOver()) {
                        gameOver();
                    }
                    h.postDelayed(this, ballMovementSpeed);
                }

            }
        };

        gameOver = new Runnable() {
            public void run() {
                SharedPreferences prefs = thisContext.getSharedPreferences("myHighScore", Context.MODE_PRIVATE);
                int score = prefs.getInt("highScore", 0); //0 is the default value
                //If stored highScore is lower than gameHigh score commit new highScore and
                //Store new high score into score
                if(score < Integer.parseInt(game.getScore())){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("highScore", Integer.parseInt(game.getScore()));
                    editor.commit();
                    score = Integer.parseInt(game.getScore());
                }

                TextView highScore = (TextView) findViewById(R.id.highScore);
                Button restart = (Button) findViewById(R.id.restartButton);
                Button mainMenu = (Button) findViewById(R.id.mainMenuButton);

                highScore.setVisibility(View.VISIBLE);
                highScore.bringToFront();
                restart.setVisibility(View.VISIBLE);
                restart.bringToFront();
                mainMenu.setVisibility(View.VISIBLE);
                mainMenu.bringToFront();

                highScore.setText("High Score: " + Integer.toString(score));
            }
        };

    }

    private void gameOver() {



        h.postDelayed(gameOver, 100);

        h.removeCallbacks(ballSpawn);
        h.removeCallbacks(ballRender);
    }

    public void mainMenu(View view) {

        game.endGame();

        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
