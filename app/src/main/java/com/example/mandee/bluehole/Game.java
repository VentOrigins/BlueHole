package com.example.mandee.bluehole;

import android.app.Activity;
import android.view.View;
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
    //For what number the number of balls should increase
    private int spawnNum;
    //For the reset of the time
    private int spawnCheck;
    //Increase number of balls spawned
    private int moreBalls;
    //For checking when score is modulus of 8
    private int ballIncreaseSpeed;

    public Game(float top, float bottom, float left, float right, BlueHole blueHole, TextView textBar, TextView scoreBar, RelativeLayout rlayout, Activity mainActivity) {
        this.screenTop = top;
        this.screenBottom = bottom;
        this.screenLeft = left;
        this.screenRight = right;

        this.blueHole = blueHole;

        this.rlayout = rlayout;
        this.mainActivity = mainActivity;

        this.listOfGoodBalls = new ArrayList<Ball>();
        this.listOfBadBalls = new ArrayList<Ball>();

        this.textBar = textBar;
        this.scoreBar = scoreBar;

        this.ballSpawnSpeed = 2500;
        this.ballMovementSpeed = 10;
        this.spawnCheck = 1;
        this.moreBalls = 1;

        this.spawnNum = 2;

        ballIncreaseSpeed = 1;

        rand = new Random();
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
            increaseScore(1);
            checkIncreaseSpeed();

        }
        //If ball collided with wall 4 times
        if(ball.getWallBounces() == 4) {
            ball.changeToBlack();
            listOfGoodBalls.remove(ball);
            listOfBadBalls.add(ball);
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
        if(score / spawnNum >= spawnCheck){
//            setBallSpawnSpeed(getBallSpawnSpeed()- 1000);
            spawnCheck++;
            moreBalls++;
            spawnNum++;
        }

//        if(getBallSpawnSpeed() < 2000) {
//            setBallSpawnSpeed(4000);
//            moreBalls++;
//        }

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
            int changeOfBallPosX = rand.nextInt(11) - 10;
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

        //Spawns the balls with random properties
        for(int i = 0; i < times; i++) {
            ImageView ballImage = new ImageView(mainActivity);
            int changeOfBallPosX = rand.nextInt(xChange) - (5);
            int changeOfBallPosY = rand.nextInt(yChange) + (5);
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
        textBar.setVisibility(View.VISIBLE);
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
        resetValues();
        textBar.setVisibility(View.INVISIBLE);
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
        ballIncreaseSpeed = 1;
        spawnCheck = 1;
        moreBalls = 1;
        score = 0;
        spawnNum = 2;
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

    private void checkIncreaseSpeed() {
        int score = Integer.parseInt(getScore());
        if(score / 2 >= ballIncreaseSpeed) {
            System.out.println("INCREASED");
            for (int i = 0; i < listOfBadBalls.size(); ++i) {
                listOfBadBalls.get(i).increaseXDis(0.20);
                listOfBadBalls.get(i).increaseYDis(0.40);
            }
            ballIncreaseSpeed++;
        }

    }
}
