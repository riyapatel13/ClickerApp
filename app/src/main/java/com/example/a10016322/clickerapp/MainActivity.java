package com.example.a10016322.clickerapp;

//offset the ones
//offset the grandmas in kitchen
//animate the grandmas
//make a moving background


import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    ImageView cookie, grandma;
    RelativeLayout layout;
    LinearLayout kitchen;
    TextView counter;
    AtomicInteger count;
    AtomicInteger grandmaPrice = new AtomicInteger(15);
    AtomicInteger grandmaNums = new AtomicInteger(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count = new AtomicInteger(0);

        layout = (RelativeLayout)(findViewById(R.id.layout_id));
        kitchen = (LinearLayout)(findViewById(R.id.miniLayout_id));
        counter = (TextView)(findViewById(R.id.counter_id));
        cookie = (ImageView)(findViewById(R.id.cookie_id));
        cookie.setImageResource(R.drawable.perfectcookie);
        grandma = (ImageView)(findViewById(R.id.grandma_id));
        grandma.setImageResource(R.drawable.grandma);
        grandma.setVisibility(View.INVISIBLE);

        final Thread passiveCookies = new Thread("Passive Cookies")
        {
            @Override
            public void run() {
                do {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    counter.post(new Runnable() {
                        @Override
                        public void run() {
                            count.getAndAdd(grandmaNums.get());
                            counter.setText(count+" cookies");
                        }
                    });
                }while(1!=0);
            }
        };
        passiveCookies.start();



        cookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count.addAndGet(1);
                counter.setText(count+" cookies");

                //how to make plus one appear at cursor
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.05f, 1.0f, 1.05f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(30);
                cookie.startAnimation(scaleAnimation);

                final TextView plusOne = new TextView(MainActivity.this);
                plusOne.setText("+1");
                plusOne.setTextColor(Color.WHITE);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ABOVE, R.id.cookie_id);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                plusOne.setPadding(0,0,0,0);
                layout.addView(plusOne, params);

                TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -2);
                cookie.bringToFront();
                translateAnimation.setDuration(1500);
                plusOne.startAnimation(translateAnimation);
                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        layout.removeView(plusOne);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                if (count.get()>=grandmaPrice.get())
                    grandma.setVisibility(View.VISIBLE);

            }
        });
        grandma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grandmaNums.getAndAdd(1);
                count.getAndAdd(-grandmaPrice.get());
                counter.setText(count+" cookies");
                grandma.setVisibility(View.INVISIBLE);
                if (count.get()>=grandmaPrice.get())
                    grandma.setVisibility(View.VISIBLE);
                final ImageView grandma2 = new ImageView(MainActivity.this);
                grandma2.setImageResource(R.drawable.grandma);
                kitchen.addView(grandma2);
                grandma2.setClickable(false);
            }
        });

    }
}
