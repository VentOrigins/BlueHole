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
import android.widget.RelativeLayout;

import java.util.Random;


public class BlueHoleMain extends ActionBarActivity {
    Handler h = new Handler();
    int delay = 1000; //milliseconds
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_hole_main);
        RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.rlayout);
        ImageView iv = new ImageView(this);
        iv.setBackgroundResource(R.drawable.vo);
        rlayout.addView(iv);


        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive

        rlayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView) findViewById(R.id.bluehole);
                int x = image.getRight();
                int y = image.getTop();
                System.out.println(x);
                System.out.println(y);
                image.setX((int) event.getX());
                image.setY((int) event.getY());
                return true;
            }

        });

        h.postDelayed(new Runnable() {
            public void run() {
                ImageView image = (ImageView) findViewById(R.id.bluehole);
                int randomNum = rand.nextInt((300 - 1) + 1) + 1;
                image.setX(randomNum);
                image.setY(randomNum);


                h.postDelayed(this, delay);
            }
        }, delay);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_blue_hole_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
