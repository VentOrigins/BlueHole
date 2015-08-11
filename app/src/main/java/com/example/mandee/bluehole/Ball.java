package com.example.mandee.bluehole;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
    private int numbers;
    private RelativeLayout rlayout;
    private ImageView testingImage;


    public Ball(long speed, int number,RelativeLayout rlayout, ImageView testImage) {
        this.speeds = speed;
        this.numbers = number;
        this.rlayout = rlayout;
        this.testingImage = testImage;
        testImage.setBackgroundResource(R.drawable.vo);

        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(60,60);
        testImage.setLayoutParams(parms);
        testImage.requestLayout();
        testImage.setX(1 + numbers);
        testImage.setY(-1);

        rlayout.addView(testImage);
        h.postDelayed(new Runnable() {
            public void run() {
                System.out.println(numbers);
                testingImage.setY((testingImage.getY() + 1));
                h.postDelayed(this, speeds);
                //If touches bluehole delete
                //Gameball next
            }
        }, speeds);

    }
}
