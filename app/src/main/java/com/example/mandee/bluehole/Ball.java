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

    // Position of ball
    private int x;
    private int y;
    // Speed of ball
    private int dx;
    private int dy;

    //Used for the layout and image of ball
    private RelativeLayout rlayout;
    private ImageView ballImage;

    // Color of ball
    private Color ballColor;

    public Ball(int x, int y, int dx, int dy, RelativeLayout rlayout, final ImageView image) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.rlayout = rlayout;
        this.ballImage = image;
        ballImage.setBackgroundResource(R.drawable.vo);

        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(60,60);
        ballImage.setLayoutParams(parms);
        ballImage.requestLayout();
        ballImage.setX(x);
        ballImage.setY(-1);

        rlayout.addView(ballImage);
    }

    public void render(int width, int height) {
        if (ballImage.getX() + getDx() < 0.0 || ballImage.getX() + getDx() > width) {
            setDx(-getDx());
        }
        if (ballImage.getY() + getDy() < 0.0 || ballImage.getY() + getDy() > height) {
            setDy(-getDy());
        }
        ballImage.setX((ballImage.getX() + getDx()));
        ballImage.setY((ballImage.getY() + getDy()));
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
}
