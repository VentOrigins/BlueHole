package com.example.mandee.bluehole;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    public void render(Matrix matrix, Bitmap source) {
//        Matrix m = new Matrix();
//        float angle = 1;
//        m.postRotate(angle);
//        blueHole.setImageBitmap(Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), m, true));
    }

    public void setX(float xPos) {
        this.blueHole.setX(xPos);
    }

    public void setY(float yPos) {
        this.blueHole.setY(yPos);
    }

    public float getX() {
        return this.blueHole.getX();
    }

    public float getY() {
        return this.blueHole.getY();
    }

    public float getWidth() {
        return bhWidth;
    }

    public float getHeight() {
        return bhHeight;
    }

    public void setPivotX(float xPos) {
        this.blueHole.setPivotX(xPos);
    }

    public void setPivotY(float yPos) {
        this.blueHole.setPivotY(yPos);
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
}
