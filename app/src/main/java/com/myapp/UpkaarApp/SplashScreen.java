package com.myapp.UpkaarApp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME=3000;

    ImageView imageView;
    View view, view1, view2;
    TextView textView, textView2;
    private FirebaseAuth auth;
    public static boolean hermit = true;
    Animation sideAnim, bottomAnim, fadeAnim;
    SharedPreferences onBoardingScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        auth = FirebaseAuth.getInstance();

        //Hooks
        imageView = findViewById(R.id.imageView);
        view = findViewById(R.id.bottomView);
        view1 = findViewById(R.id.viewTop);
        view2 = findViewById(R.id.viewsecond);
        textView = findViewById(R.id.title);
        textView2 = findViewById(R.id.textView2);

        //Animation
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        fadeAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        //Animate
        imageView.setAnimation(fadeAnim);
        view.setAnimation(bottomAnim);
        view1.setAnimation(sideAnim);
        view2.setAnimation(sideAnim);
        textView2.setAnimation(bottomAnim);

        //Handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);

                boolean isfirstTIMe = onBoardingScreen.getBoolean("firstTime", true);

                if (isfirstTIMe) {
                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("firstTime", false);
                    editor.apply();

                    Intent magic = new Intent(SplashScreen.this, OnBoarding.class);
                    startActivity(magic);
                    finish();
                }else if (auth.getCurrentUser() != null && hermit) {
                    Intent home = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(home);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }

                else {
                    Intent magic = new Intent(SplashScreen.this, Login.class);

                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View, String>(imageView, "logo_image");
                    pairs[1] = new Pair<View, String>(textView, "logo_text");

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pairs);
                        startActivity(magic, options.toBundle());
                        bye();
                    }

                }

            }
        }, SPLASH_TIME);
    }

    private void bye() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },1000);
    }
    public static boolean getHermit(){
        return hermit;
    }
}

