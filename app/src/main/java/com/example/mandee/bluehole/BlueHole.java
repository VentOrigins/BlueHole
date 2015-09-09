package com.example.mandee.bluehole;

import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by MANDEE on August/14/15.
 */
public class BlueHole {

    // Set variables of how the image is of the blue portal
    private ImageView blueHole;
    private float bhWidth;
    private float bhHeight;
    private float startX;
    private float startY;

    public BlueHole(ImageView blueHole, float bhWidth, float bhHeight) {
        this.blueHole = blueHole;
        this.bhWidth = bhWidth;
        this.bhHeight = bhHeight;
        startX = blueHole.getX();
        startY = blueHole.getY();
    }

    /*  =============================================================================
    Rendering is called every time the runnable ticks for the ballRender

    @param      none
    @return     none
    ========================================================================== */
    public void render() {
        // Resizes the blue portal when it spawns
        if (blueHole.getScaleX() < 1.0f && blueHole.getScaleY() < 1.0f) {
            blueHole.setScaleX(blueHole.getScaleX() + 0.2f);
            blueHole.setScaleY(blueHole.getScaleY() + 0.2f);
        }

        // Continues to rotate the blue portal
        float angle = blueHole.getRotation();
        angle += 10.0f;
        if(angle >= 360.0f) {
            angle = 0;
        }
        blueHole.setRotation(angle);
    }

    /*  =============================================================================
    Resets the blue portal's beginning position when game restarts

    @param      none
    @return     none
    ========================================================================== */
    public void reset() {
        blueHole.setX(startX);
        blueHole.setY(startY);
    }

    public void setX(float xPos) {
        this.blueHole.setX(xPos);
    }

    public void setY(float yPos) {
        this.blueHole.setY(yPos);
    }

    public float getTop() {
        return this.blueHole.getY();
    }

    public float getBottom() {
        return this.blueHole.getY() + this.bhHeight;
    }

    public float getLeft() {
        return this.blueHole.getX();
    }

    public float getRight() {
        return this.blueHole.getX()+ this.bhWidth;
    }

    public ImageView getImage() {
        return blueHole;
    }

    public float getRadius() {
        return bhWidth / 2;
    }

    public float getCenterX() {
        return this.blueHole.getX() + (bhWidth/2);
    }

    public float getCenterY() {
        return this.blueHole.getY() + (bhHeight/2);
    }
}
