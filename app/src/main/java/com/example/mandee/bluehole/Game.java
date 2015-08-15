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
    // Borders
    private float screenTop;
    private float screenBottom;
    private float screenLeft;
    private float screenRight;

    private BlueHole blueHole;


    public Game() {
        // bluePortal = new BluePortal();
        this.listOfBalls = new ArrayList<Ball>();
        this.screenTop = 0;
        this.screenBottom = 0;
        this.screenLeft = 0;
        this.screenRight = 0;

    }

    public Game(int top, int bottom, int left, int right, BlueHole blueHole) {
        // bluePortal = new BluePortal();
        this.listOfBalls = new ArrayList<Ball>();
        this.screenTop = top;
        this.screenBottom = bottom;
        this.screenLeft = left;
        this.screenRight = right;

        this.blueHole = blueHole;
    }

    public void render() {
        // Moves the portal

        //Moves each ball
        for (int i = 0; i < listOfBalls.size(); ++i) {

            if(listOfBalls.get(i).checkCollision()) {
                listOfBalls.remove(i);
            }
            else {
                listOfBalls.get(i).render(screenTop, screenBottom, screenLeft, screenRight);
                if(listOfBalls.get(i).checkCollision()) {
                    listOfBalls.remove(i);
                }

            }


        }
    }

    public void addBallToBallList(RelativeLayout rlayout, ImageView ballImage) {
        Random rand = new Random();

        // The argument values for the ball's constructor
        int startingBallPosX = rand.nextInt(screenRight - screenLeft) + screenLeft;
        int startingBallPosY = 0;
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

    public void printAllBalls() {
        System.out.print("Balls: ");
        for (int i = 0; i < listOfBalls.size(); ++i) {
            System.out.print(i + ",");
        }
        System.out.print("\n");
    }
}
