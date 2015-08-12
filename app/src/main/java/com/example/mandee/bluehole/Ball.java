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
    public int screenWidth;
    public int screenHeight;

    private Color ballColor;


    public Ball(long speed, int x, int y, int dx, int dy, RelativeLayout rlayout, final ImageView ballImage, int screenWidth, int screenHeight) {
        this.speeds = speed;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.rlayout = rlayout;
        this.ballImage = ballImage;
        ballImage.setBackgroundResource(R.drawable.vo);

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.ballColor = BLUE;

        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(60,60);
        ballImage.setLayoutParams(parms);
        ballImage.requestLayout();
        ballImage.setX(x);
        ballImage.setY(-1);

        rlayout.addView(ballImage);

        // Every 10 millseconds, call this
        h.postDelayed(new Runnable() {
            public void run() {
                if (ballImage.getX() + getDx() < 0.0 || ballImage.getX() + getDx() > getScreenWidth()) {
                    setDx(-getDx());
                }
                if (ballImage.getY() + getDy() < 0.0 || ballImage.getY() + getDy() > getScreenHeight()) {
                    setDy(-getDy());
                }
                ballImage.setX((ballImage.getX() + getDx()));
                ballImage.setY((ballImage.getY() + getDy()));

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
    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
