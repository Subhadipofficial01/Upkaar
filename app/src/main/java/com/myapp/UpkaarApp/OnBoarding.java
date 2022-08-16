package com.myapp.UpkaarApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnBoarding extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotslayout;
    Button letsGetStarted;
    SliderAdapter sliderAdapter;
    TextView[]dots;
    int currentPos;

    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_boarding);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Hooks
        viewPager = findViewById(R.id.slider);
        dotslayout = findViewById(R.id.dots);
        letsGetStarted = findViewById(R.id.get_started_btn);

        letsGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);

            }
        });

        //Get Adapter
       sliderAdapter = new SliderAdapter(this);

       viewPager.setAdapter(sliderAdapter);
       addDots(0);

       viewPager.addOnPageChangeListener(changeListener);
    }

    //skip button function
    public void skip(View view){
        startActivity(new Intent(getApplicationContext(), Login.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);

    }

    //Next button function
    public void next(View view){
        viewPager.setCurrentItem(currentPos + 1);

    }

    //Add dots to slider
    private void addDots(int position){
        dots = new TextView[4];
        dotslayout.removeAllViews();
        for(int i = 0; i < dots.length; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            dotslayout.addView(dots[i]);
        }
        if (dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.dot_color));
        }

    }

    ViewPager.OnPageChangeListener changeListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPos = position;

            if (position == 0){
                letsGetStarted.setVisibility(View.GONE);
            }
            else if (position == 1){
                letsGetStarted.setVisibility(View.GONE);
            }else if(position == 2){
                letsGetStarted.setVisibility(View.GONE);
            }
            else{
                animation = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.bottom_anim);
                letsGetStarted.setAnimation(animation);
                letsGetStarted.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
