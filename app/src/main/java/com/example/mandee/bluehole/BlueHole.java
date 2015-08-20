package com.example.mandee.bluehole;

import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by MANDEE on August/14/15.
 */
public class BlueHole {

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

    public void render() {
        float angle = blueHole.getRotation();
        angle += 10.0f;
        if(angle >= 360.0f) {
            angle = 0;
        }
        blueHole.setRotation(angle);
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

    public void reset() {

        blueHole.setX(startX);
        blueHole.setY(startY);

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
