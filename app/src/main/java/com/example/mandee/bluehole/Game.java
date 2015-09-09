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

    // private BluePortal bluePortal;
    private ArrayList<Ball> listOfGoodBalls;
    private ArrayList<Ball> listOfBadBalls;
    // Borders
    private float screenTop;
    private float screenBottom;
    private float screenLeft;
    private float screenRight;

    // Blue Portal
    private BlueHole blueHole;
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
    private int ballIncreaseSpeed;

    public Game(float top, float bottom, float left, float right, BlueHole blueHole, TextView textBar, TextView scoreBar, RelativeLayout rlayout, Activity mainActivity) {
        // bluePortal = new BluePortal();
        this.listOfGoodBalls = new ArrayList<Ball>();
        this.listOfBadBalls = new ArrayList<Ball>();
        this.screenTop = top;
        this.screenBottom = bottom;
        this.screenLeft = left;
        this.screenRight = right;

        this.blueHole = blueHole;

        this.rlayout = rlayout;
        this.mainActivity = mainActivity;

        this.textBar = textBar;
        this.scoreBar = scoreBar;


        ballSpawnSpeed = 4000;
        ballMovementSpeed = 10;
        spawnCheck = 1;
        moreBalls = 1;


        ballIncreaseSpeed = 1;


        rand = new Random();
        changeBall();
    }




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

    private void checkGoodCollision(Ball ball) {

        if(ball.checkCollision() != null){

            ball.removeView();
            listOfGoodBalls.remove(ball);
            increaseScore(1);
            checkIncreaseSpeed();

        }

        else {
            ball.render(screenTop, screenBottom, screenLeft, screenRight);
            if(ball.checkCollision() != null) {
                ball.removeView();
                listOfGoodBalls.remove(ball);
                increaseScore(1);
                checkIncreaseSpeed();
            }

        }
    }

    private void checkBadCollision(Ball ball) {
        if(ball.checkCollision() != null) {
            gameOver();
        }
        else {
            ball.render(screenTop, screenBottom, screenLeft, screenRight);
            if(ball.checkCollision() != null) {
                gameOver();
            }
        }

    }

    private void spawnSpeedCheck() {


        int score = Integer.parseInt(getScore());
        if(score / 3 > spawnCheck){
            setBallSpawnSpeed(getBallSpawnSpeed()- 1000);
            spawnCheck++;
        }

        if(getBallSpawnSpeed() < 2000) {
            setBallSpawnSpeed(4000);
            moreBalls++;
        }

    }

    public void addBallToBallList() {

        spawnSpeedCheck();

        for(int i = 0; i < moreBalls; i++) {
            ImageView ballImage = new ImageView(mainActivity);



            int changeOfBallPosX = rand.nextInt(11) - 10;
            int changeOfBallPosY = rand.nextInt(11) + 5;


            int startingBallPosX = rand.nextInt( Math.round(screenRight - screenLeft) - 100) + Math.round(screenLeft - 5);
            int startingBallPosY = Math.round(screenTop - 2);



            Ball ball = new Ball(startingBallPosX, startingBallPosY, changeOfBallPosX, changeOfBallPosY, rlayout, ballImage, blueHole, false);
            listOfGoodBalls.add(ball);
        }

    }

    public void addBlackToBallList(boolean firstSpawn) {
        int times = 1;
        int xChange = 10;
        int yChange = 8;
        int score = Integer.parseInt(getScore());
        for(int i = 0; i < times; i++) {
            ImageView ballImage = new ImageView(mainActivity);
            int changeOfBallPosX = rand.nextInt(xChange) - (5);
            int changeOfBallPosY = rand.nextInt(yChange) + (5);
            int startingBallPosX = rand.nextInt( Math.round(screenRight - screenLeft) - 100) + Math.round(screenLeft - 5);
            int startingBallPosY = Math.round(screenTop - 2);


            Ball ball = new Ball(startingBallPosX, startingBallPosY, changeOfBallPosX, changeOfBallPosY, rlayout, ballImage, blueHole, true);
            listOfBadBalls.add(ball);
        }

    }

//
//    private void removeBlackBall() {
//
//
//        if(listOfBadBalls.size() > 0) {
//            listOfBadBalls.get(0).removeView();
//            listOfBadBalls.remove(0);
//        }
//
//
//    }

//
//    public float getTop() {
//        return screenTop;
//    }
//
//    public float getBottom() {
//        return screenBottom;
//    }
//
//    public float getLeft() {
//        return screenLeft;
//    }
//
//    public float getRight() {
//
//        return screenRight;
//    }

    private void changeBall() {
//        int color = rand.nextInt(3);
//        if (color == 0) {
//            nextBall.setImageResource(R.drawable.voredball);
//            nextBall.setTag("Red");
//        }
//        else if (color == 1) {
//            nextBall.setImageResource(R.drawable.voblueball);
//            nextBall.setTag("Blue");
//        }
//        else if (color == 2) {
//            nextBall.setImageResource(R.drawable.vogreenball);
//            nextBall.setTag("Green");
//        }

    }
    private void increaseScore(int increase) {
        score = Integer.parseInt(scoreBar.getText().subSequence(7, scoreBar.getText().length()).toString());
        score = score + increase;
        scoreBar.setText("Score: " + Integer.toString(score));
    }

    private void gameOver() {

        textBar.setVisibility(View.VISIBLE);
        textBar.setText("Game Over");
        isGameOver = true;

    }

    public void printAllBalls() {
        System.out.print("Balls: ");
        for (int i = 0; i < listOfGoodBalls.size(); ++i) {
            System.out.print(i + ",");
        }
        System.out.print("\n");
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public String getScore() {
        return Integer.toString(score);
    }

    public void restart() {

        for (int i = 0; i < listOfGoodBalls.size(); ++i) {
            listOfGoodBalls.get(i).removeView();
        }
        for (int i = 0; i < listOfBadBalls.size(); ++i) {
            listOfBadBalls.get(i).removeView();
        }
        listOfGoodBalls.clear();
        listOfBadBalls.clear();

        resetValues();

        changeBall();
        score = 0;

        textBar.setVisibility(View.INVISIBLE);
        scoreBar.setText("Score: 0");

        isGameOver = false;

    }

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
    public int getBallSpawnSpeed() {
        return ballSpawnSpeed;
    }

    public int getBallMovementSpeed() {
        return ballMovementSpeed;
    }

    private void setBallSpawnSpeed(int spawnSpeed) {
        ballSpawnSpeed = spawnSpeed;
    }

    private void resetValues() {
        ballSpawnSpeed = 4000;
        ballMovementSpeed = 10;
        spawnCheck = 1;
        moreBalls = 1;
        ballIncreaseSpeed = 1;

    }

    private void checkIncreaseSpeed() {
        int score = Integer.parseInt(getScore());
        if(score / 10 >= ballIncreaseSpeed) {
            System.out.println("INCREASED");
            for (int i = 0; i < listOfBadBalls.size(); ++i) {
                listOfBadBalls.get(i).increaseXDis(1);
                listOfBadBalls.get(i).increaseYDis(2);
            }
            ballIncreaseSpeed++;
        }

    }



}
