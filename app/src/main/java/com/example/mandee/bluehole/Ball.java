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

/**
 * Created by MANDEE on August/10/15.
 */
public class Ball {

    Handler h = new Handler();
    private long speeds; //milliseconds
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int numbers;
    private RelativeLayout rlayout;
    private ImageView ballImage;
    private ImageView blueHole;
    public int screenTop;
    public int screenBottom;
    public int screenLeft;
    public int screenRight;

    private Color ballColor;


    public Ball(long speed, int x, int y, int dx, int dy, final RelativeLayout rlayout, final ImageView blueHole, final ImageView ballImage, int top, int bottom, int left, int right) {
        this.speeds = speed;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.rlayout = rlayout;
        this.ballImage = ballImage;
        this.blueHole = blueHole;
        ballImage.setBackgroundResource(R.drawable.vo);

        this.screenTop = top;
        this.screenBottom = bottom;
        this.screenLeft = left;
        this.screenRight = right;

//        this.ballColor = BLUE;

        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(60,60);
        ballImage.setLayoutParams(parms);
        ballImage.requestLayout();
        ballImage.setX(x);
        ballImage.setY(-1);

        rlayout.addView(ballImage);

        // Every 10 millseconds, call this
        h.postDelayed(new Runnable() {
            public void run() {

                checkCollision();

                if (ballImage.getX() + getDx() < getLeft() || ballImage.getX() + getDx() + 60 > getRight()) {
                    setDx(-getDx());
                }

                if(getDy() < 0 && ballImage.getY() + getDy() < getTop()) {
                    setDy(-getDy());
                }
                if(getDy() > 0 && ballImage.getY() + getDy() + 60 > getBottom()) {
                    setDy(-getDy());
                }


                ballImage.setX((ballImage.getX() + getDx()));
                ballImage.setY((ballImage.getY() + getDy()));
                checkCollision();

                h.postDelayed(this, speeds);
                //If touches bluehole delete
                //Gameball next
            }
        }, speeds);

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

    public int getTop() {
        return screenTop;
    }

    public int getBottom() {
        return screenBottom;
    }

    public int getLeft() {
        return screenLeft;
    }

    public int getRight() {
        return screenRight;
    }

    public void checkCollision() {

        System.out.println(ballImage.getTop() + " " + blueHole.getTop());
//        System.out.println(ballImage.getBottom() + " " + blueHole.getBottom());
//        System.out.println(ballImage.getLeft() + " " + blueHole.getLeft());
//        System.out.println(ballImage.getRight() + " " + blueHole.getRight());
        if(ballImage.getLeft() < blueHole.getRight() && ballImage.getLeft() > blueHole.getLeft() && (
                ballImage.getTop() < blueHole.getTop() && ballImage.getTop() > blueHole.getBottom()
                || ballImage.getBottom() < blueHole.getTop() && ballImage.getBottom() > blueHole.getBottom())) {
            rlayout.removeView(ballImage);
        }
        if(ballImage.getRight() < blueHole.getRight() && ballImage.getRight() > blueHole.getLeft() && (
                ballImage.getTop() < blueHole.getTop() && ballImage.getTop() > blueHole.getBottom()
                || ballImage.getBottom() < blueHole.getTop() && ballImage.getBottom() > blueHole.getBottom())) {
            rlayout.removeView(ballImage);
        }
        if(ballImage.getTop() < blueHole.getTop() && ballImage.getTop() > blueHole.getBottom() && (
                ballImage.getLeft() < blueHole.getRight() && ballImage.getLeft() > blueHole.getLeft()
                || ballImage.getRight() < blueHole.getRight() && ballImage.getRight() > blueHole.getLeft())) {
            rlayout.removeView(ballImage);
        }
        if (ballImage.getBottom() < blueHole.getTop() && ballImage.getBottom() > blueHole.getBottom() && (
                ballImage.getLeft() < blueHole.getRight() && ballImage.getLeft() > blueHole.getLeft()
                || ballImage.getRight() < blueHole.getRight() && ballImage.getRight() > blueHole.getLeft())) {
            rlayout.removeView(ballImage);
        }

    }



}
