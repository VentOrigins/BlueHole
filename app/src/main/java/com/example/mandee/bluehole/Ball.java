package com.example.mandee.bluehole;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by MANDEE on August/10/15.
 */
public class Ball {

    Handler h = new Handler();

    // Position of ball
    private int x;
    private int y;
    // Speed of ball
    private int dx;
    private int dy;

    //Used for the layout and image of ball
    private RelativeLayout rlayout;
    private ImageView ballImage;

    private ImageView blueHole;

    // Color of ball
    private Color ballColor;

    public Ball(int x, int y, int dx, int dy, RelativeLayout rlayout, final ImageView image) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.rlayout = rlayout;

        this.ballImage = image;

        Random rand = new Random();
        if (rand.nextInt(2) == 0)
            ballImage.setBackgroundResource(R.drawable.voredball);
        else {
            ballImage.setBackgroundResource(R.drawable.voblueball);
        }

        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(40,40);
        ballImage.setLayoutParams(parms);
        ballImage.requestLayout();
        ballImage.setX(x);
        ballImage.setY(y);

        rlayout.addView(ballImage);
    }

    public void render(float top, float bottom, float left, float right) {

        checkCollision();

        if (ballImage.getX() + getDx() < left || ballImage.getX() + getDx() + 40 > right) {
            setDx(-getDx());
        }

        if(getDy() < 0 && ballImage.getY() + getDy() < top) {
            setDy(-getDy());
        }
        if(getDy() > 0 && ballImage.getY() + getDy() + 40 > bottom) {
            setDy(-getDy());
        }
        ballImage.setX((ballImage.getX() + getDx()));
        ballImage.setY((ballImage.getY() + getDy()));

        checkCollision();
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void checkCollision() {

//        System.out.println(ballImage.getTop() + " " + blueHole.getTop());
////        System.out.println(ballImage.getBottom() + " " + blueHole.getBottom());
////        System.out.println(ballImage.getLeft() + " " + blueHole.getLeft());
////        System.out.println(ballImage.getRight() + " " + blueHole.getRight());
//        if(ballImage.getLeft() < blueHole.getRight() && ballImage.getLeft() > blueHole.getLeft() && (
//                ballImage.getTop() < blueHole.getTop() && ballImage.getTop() > blueHole.getBottom()
//                || ballImage.getBottom() < blueHole.getTop() && ballImage.getBottom() > blueHole.getBottom())) {
//            rlayout.removeView(ballImage);
//        }
//        if(ballImage.getRight() < blueHole.getRight() && ballImage.getRight() > blueHole.getLeft() && (
//                ballImage.getTop() < blueHole.getTop() && ballImage.getTop() > blueHole.getBottom()
//                || ballImage.getBottom() < blueHole.getTop() && ballImage.getBottom() > blueHole.getBottom())) {
//            rlayout.removeView(ballImage);
//        }
//        if(ballImage.getTop() < blueHole.getTop() && ballImage.getTop() > blueHole.getBottom() && (
//                ballImage.getLeft() < blueHole.getRight() && ballImage.getLeft() > blueHole.getLeft()
//                || ballImage.getRight() < blueHole.getRight() && ballImage.getRight() > blueHole.getLeft())) {
//            rlayout.removeView(ballImage);
//        }
//        if (ballImage.getBottom() < blueHole.getTop() && ballImage.getBottom() > blueHole.getBottom() && (
//                ballImage.getLeft() < blueHole.getRight() && ballImage.getLeft() > blueHole.getLeft()
//                || ballImage.getRight() < blueHole.getRight() && ballImage.getRight() > blueHole.getLeft())) {
//            rlayout.removeView(ballImage);
//        }

    }
}
