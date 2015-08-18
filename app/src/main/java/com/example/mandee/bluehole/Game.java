package com.example.mandee.bluehole;

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
    private ArrayList<Ball> listOfBalls;
    // Borders
    private float screenTop;
    private float screenBottom;
    private float screenLeft;
    private float screenRight;

    // Blue Portal
    private BlueHole blueHole;
    //Next Ball
    private ImageView nextBall;
    //Text Bar
    private TextView textBar;
    //Score bar
    private TextView scoreBar;

    private boolean isGameOver = false;

    private int score;

    private RelativeLayout rlayout;



    public Game(float top, float bottom, float left, float right, BlueHole blueHole, ImageView nextBall, TextView textBar, TextView scoreBar) {
        // bluePortal = new BluePortal();
        this.listOfBalls = new ArrayList<Ball>();
        this.screenTop = top;
        this.screenBottom = bottom;
        this.screenLeft = left;
        this.screenRight = right;

        this.blueHole = blueHole;
        this.nextBall = nextBall;

        this.textBar = textBar;
        this.scoreBar = scoreBar;

        rand = new Random();
    }


    public void render() {
        //Moves each ball
        for (int i = 0; i < listOfBalls.size(); ++i) {

            if(listOfBalls.get(i).checkCollision() != null){
                if(nextBall.getTag().equals(listOfBalls.get(i).checkCollision())) {
                    listOfBalls.get(i).removeView();
                    listOfBalls.remove(i);
                    changeBall();
                } else {
                    gameOver();
                }
            }

            else {
                listOfBalls.get(i).render(screenTop, screenBottom, screenLeft, screenRight);
                if(listOfBalls.get(i).checkCollision() != null){
                    if(nextBall.getTag().equals(listOfBalls.get(i).checkCollision())) {
                        listOfBalls.get(i).removeView();
                        listOfBalls.remove(i);
                        changeBall();
                    } else {
                        gameOver();
                    }
                }

            }


        }
    }

    public void addBallToBallList(RelativeLayout rlayout, ImageView ballImage) {

        int startingBallPosX = rand.nextInt( Math.round(screenRight - screenLeft) - 100) + Math.round(screenLeft - 5);
        int startingBallPosY = Math.round(screenTop-2);
//        int changeOfBallPosX = rand.nextInt(41) - 20;
//        int changeOfBallPosY = rand.nextInt(21) + 1;

        int changeOfBallPosX = rand.nextInt(21) - 10;
        int changeOfBallPosY = rand.nextInt(11) + 1;

        Ball ball = new Ball(startingBallPosX, startingBallPosY, changeOfBallPosX, changeOfBallPosY, rlayout, ballImage, blueHole);
        listOfBalls.add(ball);
    }

    public float getTop() {
        return screenTop;
    }

    public float getBottom() {
        return screenBottom;
    }

    public float getLeft() {
        return screenLeft;
    }

    public float getRight() {

        return screenRight;
    }

    private void changeBall() {

        score = Integer.parseInt(scoreBar.getText().subSequence(7, scoreBar.getText().length()).toString());
        score++;
        scoreBar.setText("Score: " + Integer.toString(score));
        int color = rand.nextInt(4);
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
        else if (color == 3) {
            nextBall.setImageResource(R.drawable.voblackball);
            nextBall.setTag("Black");
        }

    }

    private void gameOver() {

        nextBall.setImageDrawable(null);
        textBar.setText("Game Over");
        isGameOver = true;

    }

    public void printAllBalls() {
        System.out.print("Balls: ");
        for (int i = 0; i < listOfBalls.size(); ++i) {
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

        for (int i = 0; i < listOfBalls.size(); ++i) {
            listOfBalls.get(i).removeView();
        }
        listOfBalls.clear();


        nextBall.setImageResource(R.drawable.voredball);
        nextBall.setTag("Red");

        textBar.setText(null);
        scoreBar.setText("Score: 0");

        isGameOver = false;

    }

}
