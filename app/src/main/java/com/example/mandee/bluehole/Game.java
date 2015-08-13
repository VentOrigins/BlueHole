package com.example.mandee.bluehole;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Randy on 8/12/15.
 */
public class Game {
    // Blue Portal
    // private BluePortal bluePortal;
    private ArrayList<Ball> listOfBalls;

    private int screenWidth;
    private int screenHeight;

    public Game() {
        // bluePortal = new BluePortal();
        this.listOfBalls = new ArrayList<Ball>();
        this.screenWidth = 0;
        this.screenHeight = 0;
    }

    public Game(int screenWidth, int screenHeight) {
        // bluePortal = new BluePortal();
        this.listOfBalls = new ArrayList<Ball>();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void init() {

    }

    public void render() {
        // Moves the portal

        //Moves each ball
        for (int i = 0; i < listOfBalls.size(); ++i) {
            listOfBalls.get(i).render(screenWidth, screenHeight);
        }
    }

    public void addBallToBallList(RelativeLayout rlayout, ImageView ballImage) {
        Random rand = new Random();

        // The argument values for the ball's constructor
        int startingBallPosX = rand.nextInt(screenWidth);
        int startingBallPosY = 0;
        int changeOfBallPosX = rand.nextInt(41) - 20;
        int changeOfBallPosY = rand.nextInt(21) + 1;

        Ball ball = new Ball(startingBallPosX, startingBallPosY, changeOfBallPosX, changeOfBallPosY, rlayout, ballImage);
        listOfBalls.add(ball);
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
