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


    public Game() {
        // bluePortal = new BluePortal();
        this.listOfBalls = new ArrayList<Ball>();
        this.screenTop = 0;
        this.screenBottom = 0;
        this.screenLeft = 0;
        this.screenRight = 0;



    }

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
        // Moves the portal

        //Moves each ball
        for (int i = 0; i < listOfBalls.size(); ++i) {

            if(listOfBalls.get(i).checkCollision() != null){
                if(nextBall.getTag().equals(listOfBalls.get(i).checkCollision())) {
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

        int score = Integer.parseInt(scoreBar.getText().toString());
        score++;
        scoreBar.setText(Integer.toString(score));
        if (rand.nextInt(2) == 0) {
            nextBall.setImageResource(R.drawable.voredball);
            nextBall.setTag("Red");
        }
        else {
            nextBall.setImageResource(R.drawable.voblueball);
            nextBall.setTag("Blue");
        }

    }

    private void gameOver() {
        nextBall.setImageDrawable(null);
        textBar.setText("Game Over");

    }
}
