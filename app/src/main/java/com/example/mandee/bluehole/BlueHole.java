package com.example.mandee.bluehole;

import android.widget.ImageView;

/**
 * Created by MANDEE on August/14/15.
 */
public class BlueHole {

    private ImageView blueHole;
    private float bhWidth;
    private float bhHeight;

    public BlueHole(ImageView blueHole, float bhWidth, float bhHeight) {
        this.blueHole = blueHole;
        this.bhWidth = bhWidth;
        this.bhHeight = bhHeight;


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
}
