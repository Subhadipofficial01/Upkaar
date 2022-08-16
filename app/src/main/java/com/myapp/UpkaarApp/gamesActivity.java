package com.myapp.UpkaarApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skydoves.transformationlayout.TransformationAppCompatActivity;

public class gamesActivity extends TransformationAppCompatActivity {

    ImageView bggame;
    TextView slogan_game, slogan_header;
    LinearLayout header, game_form;
    Animation bganim, formanim;
    Dialog gratitudedialog;
    Button accept, donate_final;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_games);
        //Hooks
        bggame = findViewById(R.id.bggames);
        slogan_game = findViewById(R.id.splashSlogan_game);
        slogan_header = findViewById(R.id.donate_games_slogan);
        header = findViewById(R.id.gamesHeader);
        game_form = findViewById(R.id.games_form);
        donate_final = findViewById(R.id.donate_game_final);

        gratitudedialog = new Dialog(this);

        donate_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });
        bggame.animate().translationY(-2000).setDuration(800).setStartDelay(800);
        slogan_game.animate().translationY(200).alpha(0).setDuration(800).setStartDelay(1000);

        bganim = AnimationUtils.loadAnimation(this, R.anim.donate_bottom_anim);
        formanim = AnimationUtils.loadAnimation(this, R.anim.form_anim);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                header.setVisibility(View.VISIBLE);

                header.animate().alpha(1).setDuration(500).setStartDelay(100);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        slogan_header.setVisibility(View.VISIBLE);
                        slogan_header.setAnimation(bganim);
                        formAnimate();
                    }
                }, 800);
            }
        }, 1100);


    }

    private void formAnimate() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                game_form.setVisibility(View.VISIBLE);
                game_form.setAnimation(formanim);

            }
        }, 300);
    }

    @Override
    public void onBackPressed() {
        game_form.setVisibility(View.GONE);
        header.setVisibility(View.GONE);
        super.onBackPressed();

    }

    private void validateFields() {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 40);
        myAnim.setInterpolator(interpolator);
        donate_final.startAnimation(myAnim);
        if (!validateName() | !validatePh() | !validateAddress() | !validateGame() | !validateType() | !validateAmount()) {
            return;
        }
        showgratitudePopUp();

    }
    private boolean validateType() {
        AppCompatEditText game_type = findViewById(R.id.donation_games_type);
        String type = game_type.getText().toString();
        if (type.isEmpty()) {
            game_type.setError("Field cannot be empty");
            game_type.setBackgroundDrawable(getDrawable(R.drawable.donation_fields_error));
            return false;
        } else if (type.length() < 3) {
            game_type.setError("Type name too small");
            game_type.setBackgroundDrawable(getDrawable(R.drawable.donation_fields_error));
            return false;
        } else {
            game_type.setError(null);
            game_type.setBackground(getDrawable(R.drawable.donation_fields));
            return true;
        }

    }

    private boolean validateGame() {
        AppCompatEditText game_name = findViewById(R.id.donation_games_name);
        String name_val = game_name.getText().toString();
        if (name_val.isEmpty()) {
            game_name.setError("Field cannot be empty");
            game_name.setBackgroundDrawable(getDrawable(R.drawable.donation_fields_error));
            return false;
        } else if (name_val.length() < 3) {
            game_name.setError("Game name too small");
            game_name.setBackgroundDrawable(getDrawable(R.drawable.donation_fields_error));
            return false;
        }
        else{
                game_name.setError(null);
                game_name.setBackground(getDrawable(R.drawable.donation_fields));
                return true;
            }
    }

    private void showgratitudePopUp() {
        android.transition.Fade fade = new Fade();
        fade.setDuration(2000);
        gratitudedialog.setContentView(R.layout.donation_gratitude);
        accept = gratitudedialog.findViewById(R.id.ok);

        gratitudedialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gratitudedialog.getWindow().setEnterTransition(fade);
            gratitudedialog.getWindow().setExitTransition(fade);

        }
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent popLog = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(popLog);
                finish();
            }
        });
        gratitudedialog.show();
    }

    private boolean validateName() {
        AppCompatEditText donation_name = findViewById(R.id.game_donation_name);

        String name_val = donation_name.getText().toString();
        if (name_val.isEmpty()) {
            donation_name.setError("Field cannot be empty");
            donation_name.setBackgroundDrawable(getDrawable(R.drawable.donation_fields_error));
            return false;
        } else if (name_val.length() < 3) {
            donation_name.setError("Name too short");
            donation_name.setBackgroundDrawable(getDrawable(R.drawable.donation_fields_error));
            return false;
        } else {
            donation_name.setError(null);
            donation_name.setBackground(getDrawable(R.drawable.donation_fields));
            return true;
        }
    }

    private boolean validatePh() {
        AppCompatEditText donation_ph = findViewById(R.id.game_donation_ph);
        String MobilePattern = "[0-9]{10}";
        String ph_no = donation_ph.getText().toString();
        if (ph_no.isEmpty()) {
            donation_ph.setError("Field cannot be empty");
            donation_ph.setBackground(getDrawable(R.drawable.donation_fields_error));
            return false;

        } else if (!ph_no.matches(MobilePattern)) {
            donation_ph.setError("Invalid Phone Number");
            donation_ph.setBackground(getDrawable(R.drawable.donation_fields_error));
            return false;
        } else {
            donation_ph.setError(null);
            donation_ph.setBackground(getDrawable(R.drawable.donation_fields));
            return true;
        }

    }

    private boolean validateAddress() {
        AppCompatEditText donation_address = findViewById(R.id.game_donation_address);

        String ph_no = donation_address.getText().toString();
        if (ph_no.isEmpty()) {
            donation_address.setError("Field cannot be empty");
            donation_address.setBackground(getDrawable(R.drawable.donation_fields_error));
            return false;

        }else if (ph_no.length()<10){
            donation_address.setError("Address too small");
            donation_address.setBackground(getDrawable(R.drawable.donation_fields_error));
            return false;
        }
        else {
            donation_address.setError(null);
            donation_address.setBackground(getDrawable(R.drawable.donation_fields));
            return true;
        }

    }

    private boolean validateAmount() {
        AppCompatEditText amount = findViewById(R.id.game_amt_textfields);

        String amount_val = amount.getText().toString();
        if (amount_val.isEmpty()) {
            amount.setError("Field cannot be empty");
            amount.setBackground(getDrawable(R.drawable.donation_fields_error));
            return false;

        } else if (amount_val.equals("0")) {
            amount.setError("Donation amount Invalid");
            amount.setBackground(getDrawable(R.drawable.donation_fields_error));
            return false;
        } else {
            amount.setError(null);
            amount.setBackground(getDrawable(R.drawable.donation_fields));
            return true;
        }

    }
}
