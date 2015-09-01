package com.example.mandee.bluehole;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Randy on 8/12/15.
 */
public class Game {
    private Random rand;

    // Borders
    private float screenTop;
    private float screenBottom;
    private float screenLeft;
    private float screenRight;

    // Blue Portal
    private BlueHole blueHole;
    //Next Ball
    private ImageView nextBall;

    // A list of all the colored balls
    private ArrayList<Ball> listOfGoodBalls;
    // A list of all the black balls
    private ArrayList<Ball> listOfBadBalls;

    //Text Bar
    private TextView textBar;
    //Score bar
    private TextView scoreBar;

    private boolean isGameOver = false;

    private int score;

    private RelativeLayout rlayout;

    private Activity mainActivity;

    private int ballSpawnSpeed;

    private int ballMovementSpeed;

    //For the reset of the time
    private int spawnCheck;
    //Increase number of balls spawned
    private int moreBalls;
    //For checking when score is modulus of 8
    private int ballCheck;

    public Game(float top, float bottom, float left, float right, BlueHole blueHole, ImageView nextBall, TextView textBar, TextView scoreBar, RelativeLayout rlayout, Activity mainActivity) {
        this.screenTop = top;
        this.screenBottom = bottom;
        this.screenLeft = left;
        this.screenRight = right;

        this.blueHole = blueHole;
        this.nextBall = nextBall;

        this.rlayout = rlayout;
        this.mainActivity = mainActivity;

        this.listOfGoodBalls = new ArrayList<Ball>();
        this.listOfBadBalls = new ArrayList<Ball>();

        this.textBar = textBar;
        this.scoreBar = scoreBar;
        ballSpawnSpeed = 4000;
        ballMovementSpeed = 10;
        spawnCheck = 1;
        moreBalls = 1;
        ballCheck = 1;
        rand = new Random();
        changeBall();
    }

    /*  =============================================================================
        Renders the game, rendering the game and checks the collision of the balls

        @param      none
        @return     none
        ========================================================================== */
    public void render() {
        //Moves the portal accordingly
        this.blueHole.render();

        //Moves each ball and checks collision
        for (int i = 0; i < listOfGoodBalls.size(); ++i) {
            checkGoodCollision(listOfGoodBalls.get(i));
        }
        for (int i = 0; i < listOfBadBalls.size(); ++i) {
            checkBadCollision(listOfBadBalls.get(i));
        }
    }

    /*  =============================================================================
    Checks the collision and accordingly calculates the scores

    @param      Ball    The ball to check if colliding with the portal
    @return     none
    ========================================================================== */
    private void checkGoodCollision(Ball ball) {
        //If the ball collided with the portal, if it did, removes the ball that collided
        //And calculates the score accordingly
        if(ball.checkCollision() != null){

            ball.removeView();
            listOfGoodBalls.remove(ball);
            if(ball.checkCollision().equals(nextBall.getTag())) {
                removeBlackBall();
                increaseScore(2);

            } else {
                addBlackToBallList(false);
                increaseScore(1);
            }
            changeBall();
        }
        // If there is no collision, renders the ball accordingly
        ball.render(screenTop, screenBottom, screenLeft, screenRight);
    }

    /*  =============================================================================
    Checks collision of the black balls with the blue portal

    @param      Ball    The ball to check if colliding with the portal
    @return     none
    ========================================================================== */
    private void checkBadCollision(Ball ball) {
        // If the ball collides with the portal, game is over
        if(ball.checkCollision() != null) {
            gameOver();
        }
        // If not, then renders the ball
        else {
            ball.render(screenTop, screenBottom, screenLeft, screenRight);
        }
    }

    /*  =============================================================================
    Checks the ball spawn speed depending on the score

    @param      none
    @return     none
    ========================================================================== */
    private void spawnSpeedCheck() {
        int score = Integer.parseInt(getScore());
        if(score / 5 > spawnCheck){
            setBallSpawnSpeed(getBallSpawnSpeed()- 1000);
            spawnCheck++;
        }

        if(getBallSpawnSpeed() < 2000) {
            setBallSpawnSpeed(4000);
            ballCheck++;
            moreBalls++;
        }
    }

    /*  =============================================================================
    Add a good ball to the listOfGoodBalls

    @param      none
    @return     none
    ========================================================================== */
    public void addBallToBallList() {
        // Checks the spawn speed of the balls first in retrospect to the score, which would
        //would adjust the ticks of the ball spawning
        spawnSpeedCheck();

        // Spawns multiple good balls with random properties depending on the score
        for(int i = 0; i < moreBalls; i++) {
            ImageView ballImage = new ImageView(mainActivity);
            int changeOfBallPosX = rand.nextInt(21) - 10;
            int changeOfBallPosY = rand.nextInt(11) + 5;
            int startingBallPosX = rand.nextInt( Math.round(screenRight - screenLeft) - 100) + Math.round(screenLeft - 5);
            int startingBallPosY = Math.round(screenTop - 2);

            // Adds the ball to the good ball list
            Ball ball = new Ball(startingBallPosX, startingBallPosY, changeOfBallPosX, changeOfBallPosY, rlayout, ballImage, blueHole, false);
            listOfGoodBalls.add(ball);
        }
    }

    /*  =============================================================================
    Add a black ball to the listOfBadBalls

    @param      boolean
    @return     none
    ========================================================================== */
    public void addBlackToBallList(boolean firstSpawn) {
        int times = 1;
        int xChange = 10;
        int yChange = 8;
        int score = Integer.parseInt(getScore());
        // Spawns several black balls at the same time depending on score
        if(score < 15) {
            times = 1;
        }
        else if(score < 30) {
            times = 2;
        }
        else if(score < 45) {
            times = 3;
        }
        else if(score > 45) {
            times = 4;
        }

        //Spawns the balls with random properties
        for(int i = 0; i < times; i++) {
            ImageView ballImage = new ImageView(mainActivity);
            int changeOfBallPosX = rand.nextInt(xChange) - (xChange+5);
            int changeOfBallPosY = rand.nextInt(yChange) + 5;
            int startingBallPosX = rand.nextInt( Math.round(screenRight - screenLeft) - 100) + Math.round(screenLeft - 5);
            int startingBallPosY = Math.round(screenTop - 2);

            //Adds the black ball into the bad list
            Ball ball = new Ball(startingBallPosX, startingBallPosY, changeOfBallPosX, changeOfBallPosY, rlayout, ballImage, blueHole, true);
            listOfBadBalls.add(ball);
        }
    }

    /*  =============================================================================
    Removes a black ball

    @param      none
    @return     none
    ========================================================================== */
    private void removeBlackBall() {
        if(listOfBadBalls.size() > 0) {
            listOfBadBalls.get(0).removeView();
            listOfBadBalls.remove(0);
        }
    }

    /*  =============================================================================
    This adjusts the next ball tag

    @param      none
    @return     none
    ========================================================================== */
    private void changeBall() {
        int color = rand.nextInt(3);
        if (color == 0) {
            nextBall.setImageResource(R.drawable.voredball);
            nextBall.setTag("Red");
        }
        else if (color == 1) {
            nextBall.setImageResource(R.drawable.voblueball);
            nextBall.setTag("Blue");
        }
        else if (color == 2) {
            nextBall.setImageResource(R.drawable.vogreenball);
            nextBall.setTag("Green");
        }
    }

    /*  =============================================================================
    Increases the current score with the parameter increase

    @param      int     The amount to increase the score by
    @return     none
    ========================================================================== */
    private void increaseScore(int increase) {
        score = Integer.parseInt(scoreBar.getText().subSequence(7, scoreBar.getText().length()).toString());
        score = score + increase;
        scoreBar.setText("Score: " + Integer.toString(score));
    }

    /*  =============================================================================
    When the game is over

    @param      none
    @return     none
    ========================================================================== */
    private void gameOver() {
        nextBall.setImageDrawable(null);
        textBar.setText("Game Over");
        isGameOver = true;
    }

    /*  =============================================================================
    Ends the game, clears all the good and black balls in their lists

    @param      none
    @return     none
    ========================================================================== */
    public void endGame() {
        for (int i = 0; i < listOfGoodBalls.size(); ++i) {
            listOfGoodBalls.get(i).removeView();
        }
        for (int i = 0; i < listOfBadBalls.size(); ++i) {
            listOfBadBalls.get(i).removeView();
        }
        listOfGoodBalls.clear();
        listOfBadBalls.clear();

        isGameOver = false;
    }

    /*  =============================================================================
    Restarts the game in resetting all the values

    @param      none
    @return     none
    ========================================================================== */
    public void restart() {
        endGame();
        listOfGoodBalls.clear();
        listOfBadBalls.clear();
        resetValues();
        changeBall();
        textBar.setText("Golden Ball: ");
        scoreBar.setText("Score: 0");
    }

    /*  =============================================================================
    Resets private variables to the original value

    @param      none
    @return     none
    ========================================================================== */
    private void resetValues() {
        ballSpawnSpeed = 4000;
        ballMovementSpeed = 10;
        spawnCheck = 1;
        moreBalls = 1;
        ballCheck = 1;
        score = 0;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public String getScore() {
        return Integer.toString(score);
    }

    public int getBallSpawnSpeed() {
        return ballSpawnSpeed;
    }

    public int getBallMovementSpeed() {
        return ballMovementSpeed;
    }

    private void setBallSpawnSpeed(int spawnSpeed) {
        ballSpawnSpeed = spawnSpeed;
    }
}
