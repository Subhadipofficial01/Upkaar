package com.myapp.UpkaarApp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skydoves.transformationlayout.OnTransformFinishListener;
import com.skydoves.transformationlayout.TransformationCompat;
import com.skydoves.transformationlayout.TransformationLayout;
import com.skydoves.transformationlayout.TransitionExtensionKt;

import static com.myapp.UpkaarApp.SplashScreen.hermit;


public class MainActivity extends AppCompatActivity {

    LinearLayout revealView, layoutButtons, revealView2, layoutButtons2, revealView3, layoutButtons3, revealView4, layoutButtons4;
    Animation  alphaAnimation;
    CardView food, money, books, games;
    TextView food_donate, money_donate, books_donate, games_donate;
    Button logout;

    TransformationLayout transLayout_1, transLayout_2, transLayout_3, transLayout_4;
    boolean isnotvisible_1 = true;
    boolean isnotvisible_2 = true;
    boolean isnotvisible_3 = true;
    boolean isnotvisible_4 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TransitionExtensionKt.onTransformationStartContainer(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Card 1
        food = findViewById(R.id.food_card);
        revealView = findViewById(R.id.layout_view);
        layoutButtons = findViewById(R.id.view_container);
        food_donate = findViewById(R.id.donate_btn);
        transLayout_1 = findViewById(R.id.transformationLayout_0);
        //Card 2
        money = findViewById(R.id.money_card);
        revealView2 = findViewById(R.id.layout_view_1);
        layoutButtons2 = findViewById(R.id.view_container_1);
        money_donate = findViewById(R.id.donate_btn_1);
        transLayout_2 = findViewById(R.id.transformationLayout_1);
        //Card 3
        books = findViewById(R.id.book_card);
        revealView3 = findViewById(R.id.layout_view_2);
        layoutButtons3 = findViewById(R.id.view_container_2);
        books_donate = findViewById(R.id.donate_btn_2);
        transLayout_3 = findViewById(R.id.transformationLayout_2);
        //Card 4
        games = findViewById(R.id.games_card);
        revealView4 = findViewById(R.id.layout_view_3);
        layoutButtons4 = findViewById(R.id.view_container_3);
        games_donate = findViewById(R.id.donate_btn_3);
        transLayout_4 = findViewById(R.id.transformationLayout_3);

        logout = findViewById(R.id.logout_btn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Do you want to Logout?");
                alertDialogBuilder.setTitle("Logout Alert");
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(MainActivity.this,"Logging Out",Toast.LENGTH_LONG).show();
                        Intent home = new Intent(getApplicationContext(), Login.class);
                        hermit = false;
                        startActivity(home);
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

                alertDialogBuilder.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.card_button);

        food_donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), foodActivity.class);
                TransformationCompat.INSTANCE.startActivity(transLayout_1, intent);
            }
        });


        money_donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), moneyActivity.class);
                TransformationCompat.INSTANCE.startActivity(transLayout_2, intent2);
            }
        });
        books_donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(), bookActivity.class);
                TransformationCompat.INSTANCE.startActivity(transLayout_3, intent3);
            }
        });
        games_donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getApplicationContext(), gamesActivity.class);
                TransformationCompat.INSTANCE.startActivity(transLayout_4, intent4);
            }
        });



    }

    public void cardAnimatin(View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = view.getRight();
            int cy = view.getBottom();

            float finalRadius = (float) Math.hypot(cx, cy);
            if (isnotvisible_1){
                Animator anim = ViewAnimationUtils.createCircularReveal(revealView, cx, cy, 0f, finalRadius);
                anim.setDuration(500);
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        layoutButtons.setVisibility(View.VISIBLE);
                        layoutButtons.startAnimation(alphaAnimation);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                revealView.setVisibility(View.VISIBLE);
                if (!isnotvisible_2){
                    cancelBtn_1(money);
                }
                if (!isnotvisible_3){
                    cancelBtn_2(books);
                }
                if (!isnotvisible_4){
                    cancelBtn_3(games);
                }
                anim.start();
                isnotvisible_1 = false;

            }
        }
        else {
            view.setVisibility(View.INVISIBLE);
        }
    }
    public void cardAnimatin_1(View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = view.getRight();
            int cy = view.getBottom();

            float finalRadius = (float) Math.hypot(cx, cy);
            if (isnotvisible_2){
                Animator anim = ViewAnimationUtils.createCircularReveal(revealView2, cx, cy, 0f, finalRadius);
                anim.setDuration(500);
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        layoutButtons2.setVisibility(View.VISIBLE);
                        layoutButtons2.startAnimation(alphaAnimation);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                revealView2.setVisibility(View.VISIBLE);
                if (!isnotvisible_1){
                    cancelBtn(food);
                }
                if (!isnotvisible_3){
                    cancelBtn_2(books);
                }
                if (!isnotvisible_4){
                    cancelBtn_3(games);
                }
                anim.start();
                isnotvisible_2 = false;

            }
        }
        else {
            view.setVisibility(View.INVISIBLE);
        }
    }
    public void cardAnimatin_2(View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = books.getRight();
            int cy = books.getBottom();

            float finalRadius = (float) Math.hypot(cx, cy);
            if (isnotvisible_3){
                Animator anim = ViewAnimationUtils.createCircularReveal(revealView3, cx, cy, 0f, finalRadius);
                anim.setDuration(500);
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        layoutButtons3.setVisibility(View.VISIBLE);
                        layoutButtons3.startAnimation(alphaAnimation);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                revealView3.setVisibility(View.VISIBLE);
                if (!isnotvisible_1){
                    cancelBtn(food);
                }
                if (!isnotvisible_2){
                    cancelBtn_1(money);
                }
                if (!isnotvisible_4){
                    cancelBtn_3(games);
                }
                anim.start();
                isnotvisible_3 = false;

            }
        }
        else {
            view.setVisibility(View.INVISIBLE);
        }
    }
    public void cardAnimatin_3(View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = view.getRight();
            int cy = view.getBottom();

            float finalRadius = (float) Math.hypot(cx, cy);
            if (isnotvisible_4){
                Animator anim = ViewAnimationUtils.createCircularReveal(revealView4, cx, cy, 0f, finalRadius);
                anim.setDuration(500);
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        layoutButtons4.setVisibility(View.VISIBLE);
                        layoutButtons4.startAnimation(alphaAnimation);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                revealView4.setVisibility(View.VISIBLE);
                if (!isnotvisible_1){
                    cancelBtn(food);
                }
                if (!isnotvisible_2){
                    cancelBtn_1(money);
                }
                if (!isnotvisible_3){
                    cancelBtn_2(books);
                }
                anim.start();
                isnotvisible_4 = false;

            }
        }
        else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public void cancelBtn(View view){
        int cx = food.getRight();
        int cy = food.getBottom();


        float finalRadius = (float) Math.hypot(cx, cy);
        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(revealView, cx, cy, finalRadius, 0f);

            anim.setDuration(400);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    layoutButtons.setVisibility(View.GONE);
                    revealView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            anim.start();
            isnotvisible_1 = true;
        }
        else {
            view.setVisibility(View.GONE);
        }
    }
    public void cancelBtn_1(View view){
        int cx = money.getRight();
        int cy = money.getBottom();

        float finalRadius = (float) Math.hypot(cx, cy);
        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(revealView2, cx, cy, finalRadius, 0f);

            anim.setDuration(400);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    revealView2.setVisibility(View.GONE);
                    layoutButtons2.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            anim.start();
            isnotvisible_2 = true;
        }
        else {
            view.setVisibility(View.GONE);
        }
    }
    public void cancelBtn_2(View view){
        int cx = books.getRight();
        int cy = books.getBottom();

        float finalRadius = (float) Math.hypot(cx, cy);
        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(revealView3, cx, cy, finalRadius, 0f);

            anim.setDuration(400);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    revealView3.setVisibility(View.GONE);
                    layoutButtons3.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            anim.start();
            isnotvisible_3 = true;
        }
        else {
            view.setVisibility(View.GONE);
        }
    }
    public void cancelBtn_3(View view){
        int cx = games.getRight();
        int cy = games.getBottom();

        float finalRadius = (float) Math.hypot(cx, cy);
        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(revealView4, cx, cy, finalRadius, 0f);

            anim.setDuration(400);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    revealView4.setVisibility(View.GONE);
                    layoutButtons4.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            anim.start();
            isnotvisible_4 = true;
        }
        else {
            view.setVisibility(View.GONE);
        }
    }




}
