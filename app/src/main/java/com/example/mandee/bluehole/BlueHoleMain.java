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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class BlueHoleMain extends ActionBarActivity {
    Handler h = new Handler();

    private BlueHole blueHole;
    private Resources r;
    private RelativeLayout gameLayout;
    private RelativeLayout rlayout;

    private boolean ifPaused = false;
    private Game game;
    private boolean ifFirstTimeRunning = true;
    private boolean ifGameRunning = false;

    private TextView textBar;
    private TextView scoreBar;

    // Runnables
    private Runnable ballSpawn;
    private Runnable ballRender;
    private Runnable gameOver;
    private Runnable bluePortalSpawn;

    // Used for SharedPreferences to store the score
    private Context thisContext;

    // Used to create the advertisement banner
    private AdView adView;

    /*  =============================================================================
    Initializes the activity. Sets up thisContext to be this context to be used for
    the sharedPreferences. Initializes the advertisement when activity begins.
    Also hides the visibility of the advertisement.

    @param      Default parameter
    @return     none
    ========================================================================== */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_hole_main);

        // Declares the context for the future SharedPreferences
        thisContext = this;

        // Creates the advertisement
        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setVisibility(View.GONE);
    }

    /*  =============================================================================
    When the app exits, onPause is called, removing the runnables to pause the game.

    @param      none
    @return     none
    ========================================================================== */
    @Override
    public void onPause() {
        super.onPause();
        // Sets pause to be true
        ifPaused = true;

        // Pauses the ball spawning and rendering
        // When game is opened up to the foreground again, onWindowFocusChanged recreates
        // the runnables
        h.removeCallbacks(ballRender);
        h.removeCallbacks(ballSpawn);

        // Pauses the ad when user exits the game into background
        if (adView != null) {
            adView.pause();
        }
    }

    /*  =============================================================================
    Whenever the app is opened up, from the beginning or throughout the time when app
    was paused, sets ifPause to false and resumes the advertisement display. When
    ifPaused is false, the runnables are running again.

    @param      none
    @return     none
    ========================================================================== */
    @Override
    public void onResume() {
        super.onResume();
        //Sets pause to be false
        ifPaused = false;

        // When the game is resumed and it is in the game over mode, resumes the ad
        if (adView != null) {
            adView.resume();
        }
    }

    /*  =============================================================================
    If the activity is in focus and is the first time the app is running, initializes everything.
    This part is here instead of onResume or onCreate because we needed it to display after
    onCreate is finished in order for the images and layouts to display correctly.

    @param      boolean     Is true if the app is in focus
    @return     none
    ========================================================================== */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // If the activity is in focus, and this is the first time the app is running, initializes
        // everything including the BlueHole, the game, the runnables, and the black balls.
        if (hasFocus && ifFirstTimeRunning) {
            ifFirstTimeRunning = false;

            //Initializes the gameLayout and rlayout
            gameLayout = (RelativeLayout) findViewById(R.id.gameLayout);
            rlayout = (RelativeLayout) findViewById(R.id.rlayout);
            r = getResources();

            //Functions that initializes everything from the app
            createBlueHole();
            onBlueHoleTouch();
            game = gameInit();
            blackBallSpawn();
            createRunnables();
        }
        // If the game is not in its game over state, recreates the ball runnables
        if(!game.isGameOver()) {
            ballSpawnTick(2000);
            ballRenderTick(2000);
        }
    }

    /*  =============================================================================
    Override to also destroy the advertisement.

    @param      none
    @return     none
    ========================================================================== */
    @Override
    public void onDestroy() {
        super.onDestroy();

        // Destroys the advertisement
        if (adView != null) {
            adView.destroy();
        }
    }

    /*  =============================================================================
    Creates the blue hole by setting up the image, height, and width

    @param      none
    @return     none
    ========================================================================== */
    public void createBlueHole() {
        gameLayout = (RelativeLayout) findViewById(R.id.gameLayout);
        rlayout = (RelativeLayout) findViewById(R.id.rlayout);
        ImageView imageViewBH = (ImageView) findViewById(R.id.bluehole);
        float bhWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics());
        float bhHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics());
        imageViewBH.setScaleX(1.0f);
        imageViewBH.setScaleY(1.0f);
        this.blueHole = new BlueHole(imageViewBH, bhWidth, bhHeight);

    }

    /*  =============================================================================
    Function to initialize the on touch listener. The onTouchListener detects whenever
    the screen is clicked in order for the blue portal to move where the screen is tapped.

    @param      none
    @return     none
    ========================================================================== */
    public void onBlueHoleTouch() {
        // Sets up the on touch listener to move the blue portal around
        rlayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // When game is over, do not move the portal
                if (game.isGameOver() || ifGameRunning == false) {
                    return false;
                }
                // Sets the boundaries in where the screen can be tapped
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());
                int top = gameLayout.getTop() + Math.round(px);
                int bottom = gameLayout.getBottom() - Math.round(px);
                int left = gameLayout.getLeft() + Math.round(px);
                int right = gameLayout.getRight() - Math.round(px);

                // Sets the image's center accordingly
                ImageView imageBH = (ImageView) findViewById(R.id.bluehole);
                int x = imageBH.getWidth() / 2;
                int y = imageBH.getHeight() / 2;
                // If tapped out of the boundaries, does not move the portal
                if (event.getX() < left || event.getX() > right || event.getY() < top || event.getY() > bottom) {
                    return false;
                }

                // Sets the image to the tapp location
                imageBH.setX((int) event.getX() - x);
                imageBH.setY((int) event.getY() - y);

                //Sets the image size to be start off small in order for it to scale up in size
                //as the portal moves
                imageBH.setScaleX(0.1f);
                imageBH.setScaleY(0.1f);

                return false;
            }
        });
    }

    /*  =============================================================================
    Initializes the game class

    @param      none
    @return     none
    ========================================================================== */
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
        final Game game = new Game(top, bottom, left, right, blueHole, textBar, scoreBar, rlayout, this);
        return game;
    }

    /*  =============================================================================
    Adds the black balls into the game.

    @param      none
    @return     none
    ========================================================================== */
    private void blackBallSpawn() {
        for(int i = 0; i < 1; i++) {
            game.addBlackToBallList(true);
        }
    }

    /*  =============================================================================
    Creates the 3 runnables: ballSpawn, ballRender, and gameOver

    @param      none
    @return     none
    ========================================================================== */
    private void createRunnables() {
        //Runnable for the ball spawn, ticks the ballSpawn depending on the games ball spawn speed
        ballSpawn = new Runnable() {
            public void run() {
                if (!ifPaused && !game.isGameOver()) {
                    game.addBallToBallList();
                    h.postDelayed(this, game.getBallSpawnSpeed());
                }
            }
        };

        //Runnable for the ball render, ticks the ballRender depending on the games ball movement speed
        ballRender  = new Runnable() {
            public void run() {
                if (!ifPaused && !game.isGameOver()) {
                    ifGameRunning = true;
                    game.render();
                    if(game.isGameOver()) {
                        ifGameRunning = false;
                        gameOver();
                    }
                    h.postDelayed(this, game.getBallMovementSpeed());
                }
            }
        };

        gameOver = new Runnable() {
            public void run() {
                // The shared preferences stores the highest score into the phone
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

                // Displays the game over views
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

    /*  =============================================================================
    Starts the ball spawn tick. When the game starts or restarts, this will start
    the ballSpawn runnable.

    @param      int     THe time it will take for the runnable to tick
    @return     none
    ========================================================================== */
    public void ballSpawnTick(int seconds) {
        h.postDelayed(ballSpawn, seconds);
    }

    /*  =============================================================================
    Starts the ball render tick. When the game starts or restarts, this will start
    the ballRender runnable.

    @param      int     THe time it will take for the runnable to tick
    @return     none
    ========================================================================== */
    public void ballRenderTick(int seconds) {
        h.postDelayed(ballRender, seconds);
    }

    /*  =============================================================================
    When the game is over, starts the game over runnable

    @param      none
    @return     none
    ========================================================================== */
    private void gameOver() {
        // Starts the Game Over runnable
        h.postDelayed(gameOver, 100);

        // Pauses the rendering of the ball spawning and rendering
        h.removeCallbacks(ballSpawn);
        h.removeCallbacks(ballRender);

        // Hides the advertisement
        if (adView.getVisibility() == AdView.GONE) {
            adView.setVisibility(View.VISIBLE);
        }
    }

    /*  =============================================================================
    Restarts the game when the restart button is clicked when the game is over.

    @param      View
    @return     none
    ========================================================================== */
    public void restartGame(View view) {
        // Restarts the game class with the default values
        game.restart();

        // Changes the view of the game, removing the game over views
        TextView highScore = (TextView) findViewById(R.id.highScore);
        Button restart = (Button) findViewById(R.id.restartButton);
        Button mainMenu = (Button) findViewById(R.id.mainMenuButton);
        highScore.setVisibility(View.INVISIBLE);
        restart.setVisibility(View.INVISIBLE);
        mainMenu.setVisibility(View.INVISIBLE);

        //Restarts the ball spawning and ticks
        blackBallSpawn();
        ballSpawnTick(2000);
        ballRenderTick(2000);

        // Restart the blue hole
        blueHole.reset();

        // Hides the advertisements when game is restarted
        if (adView.getVisibility() == AdView.VISIBLE) {
            adView.setVisibility(View.GONE);
        }
    }

    /*  =============================================================================
    Goes to main menu after game ended and the main menu button is clicked

    @param      View
    @return     none
    ========================================================================== */
    public void mainMenu(View view) {
        // The game class ends
        game.endGame();

        // Start the activity of the main menu
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
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
}
