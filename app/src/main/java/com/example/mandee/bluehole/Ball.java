package com.example.mandee.bluehole;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
    //Used for portal
    private BlueHole blueHole;
    //Used for the layout and image of ball
    private RelativeLayout rlayout;
    private ImageView ballImage;
    private Resources r;


    // Color of ball
    private Color ballColor;

    public Ball(int x, int y, int dx, int dy, RelativeLayout rlayout, final ImageView image, BlueHole blueHole) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.rlayout = rlayout;

        this.ballImage = image;
        this.blueHole = blueHole;
        ballImage.setBackgroundResource(R.drawable.vo);

        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(60,60);
        ballImage.setLayoutParams(parms);
        ballImage.requestLayout();
        ballImage.setX(x);
        ballImage.setY(y);

        rlayout.addView(ballImage);
    }

    public void render(int top, int bottom, int left, int right) {

        if (ballImage.getX() + getDx() < left || ballImage.getX() + getDx() + 60 > right) {
            setDx(-getDx());
        }

        if(getDy() < 0 && ballImage.getY() + getDy() < top) {
            setDy(-getDy());
        }
        if(getDy() > 0 && ballImage.getY() + getDy() + 60 > bottom) {
            setDy(-getDy());
        }
        ballImage.setX((ballImage.getX() + getDx()));
        ballImage.setY((ballImage.getY() + getDy()));

    }

    public int getDx() {
        return this.dx;
    }

    public int getDy() {
        return this.dy;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
    //Return true if collison happens
    public boolean checkCollision() {

        //Check if the ball's bottom is less than the blue hole's top  but greater than the bluehole's bottom
        //If it is than check if ball's left side is less than the bluehole's right but greater than the bluehole's left
        //Or check if ball's right side is less than the bluehole's right but greater than the bluehole's left side
        if(ballImage.getY()+ 60 > blueHole.getTop() && ballImage.getY() + 60 < blueHole.getBottom()
                && ( (ballImage.getX() < blueHole.getRight() && ballImage.getX() > blueHole.getLeft())
                || ballImage.getX() + 60 < blueHole.getRight() && ballImage.getX() + 60 > blueHole.getLeft())) {
            rlayout.removeView(ballImage);
            return true;
        }
        //Check if the ball's top is less than the bluehole's bottom but greater than the bluehole's top
        //If it is check if the ball's left side is less t han the bluehole's right but greater than the bluehole's left
        //Or check if ball's rightside is less than the bluehole's right but greater than the bluehole's left side
        if(ballImage.getY() < blueHole.getBottom() && ballImage.getY() > blueHole.getTop()
                && ( (ballImage.getX() < blueHole.getRight() && ballImage.getX() > blueHole.getLeft())
                || ballImage.getX() + 60 < blueHole.getRight() && ballImage.getX() + 60 > blueHole.getLeft())) {
            rlayout.removeView(ballImage);
            return true;
        }
        return false;
    }
}
