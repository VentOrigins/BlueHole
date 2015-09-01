package com.example.mandee.bluehole;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;
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
    //Used for portal
    private BlueHole blueHole;
    //Used for the layout and image of ball
    private RelativeLayout rlayout;
    private ImageView ballImage;
    private Resources r;


    // Color of ball
    private Color ballColor;

    public Ball(int x, int y, int dx, int dy, RelativeLayout rlayout, final ImageView image, BlueHole blueHole, boolean isBlack) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.rlayout = rlayout;
        this.ballImage = image;
        this.blueHole = blueHole;

        // Chooses color of the ball
        Random rand = new Random();

        // Sets the color of the balls
        int color = rand.nextInt(3);
        if (isBlack) {
            ballImage.setBackgroundResource(R.drawable.voblackball);
            ballImage.setTag("Black");
        }
        else {
            if (color == 0) {
                ballImage.setBackgroundResource(R.drawable.voredball);
                ballImage.setTag("Red");
            } else if (color == 1) {
                ballImage.setBackgroundResource(R.drawable.voblueball);
                ballImage.setTag("Blue");
            } else if (color == 2) {
                ballImage.setBackgroundResource(R.drawable.vogreenball);
                ballImage.setTag("Green");
            }
        }

        // Sets the position of the ball
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(30, 30);
        ballImage.setLayoutParams(parms);
        ballImage.requestLayout();
        ballImage.setX(x);
        ballImage.setY(y);

        rlayout.addView(ballImage);
    }

    /*  =============================================================================
    Moves the ball every time the ballRender is ticked, When a ball hits a border, depending
    on the side the ball hits, well change the direction of ball.

    @param      floats      The values of the border in the game
    @return     none
    ========================================================================== */
    public void render(float top, float bottom, float left, float right) {
        if (ballImage.getX() + getDx() < left || ballImage.getX() + getDx() + 30 > right) {
            setDx(-getDx());
        }
        if (getDy() < 0 && ballImage.getY() + getDy() < top) {
            setDy(-getDy());
        }
        if (getDy() > 0 && ballImage.getY() + getDy() + 40 > bottom) {
            setDy(-getDy());
        }
        ballImage.setX((ballImage.getX() + getDx()));
        ballImage.setY((ballImage.getY() + getDy()));
    }

    /*  =============================================================================
        Checks if a ball collided with the blue portal or not.
        Does this by checking distance of center of ball to center of portal.
        If the distance between those two points are less than the combined radius
        of the portal and ball, then they have collided

        @param      none
        @return     Object      Returns the ball's color, null if no ball collided
        ========================================================================== */
    public Object checkCollision() {
        float combinedRadius = getRadius() + blueHole.getRadius();

        if(distanceOfBallToPortal() < combinedRadius) {
            return ballImage.getTag();
        }
        return null;
    }

    /*  =============================================================================
    Calculates the distance of the center of the blue portal to the current ball

    @param      none
    @return     double  Value of distance of ball
    ========================================================================== */
    public double distanceOfBallToPortal() {
        double xDis = Math.pow(blueHole.getCenterX() - getCenterX(),2);
        double yDis = Math.pow(blueHole.getCenterY() - getCenterY(),2);
        return Math.sqrt((xDis+yDis));
    }

    public void removeView() {
        rlayout.removeView(ballImage);
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

    public float getTop() {
        return ballImage.getY();
    }

    public float getBottom() {
        return ballImage.getY() + 60;
    }

    public float getLeft() {
        return ballImage.getX();
    }

    public float getRight() {
        return ballImage.getX() + 60;
    }

    public float getRadius() {
        return 15;
    }

    public float getCenterX() {
        return ballImage.getX() + 15;
    }

    public float getCenterY() {
        return ballImage.getY() + 15;
    }
}
