package com.myapp.UpkaarApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.myapp.UpkaarApp.R;
import com.skydoves.transformationlayout.TransformationAppCompatActivity;

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
import android.widget.RadioGroup;
import android.widget.TextView;

public class moneyActivity extends TransformationAppCompatActivity {
    ImageView bgmoney;
    TextView splash_money, donation_header;
    LinearLayout header, money_form;
    Dialog gratitudedialog;
    Animation bganim, formanim;
    Button accept, donate_final;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_money);
        //Hooks
        bgmoney = findViewById(R.id.bgmoney);
        splash_money = findViewById(R.id.splashSlogan_money);
        header = findViewById(R.id.moneyHeader);
        donation_header = findViewById(R.id.money_donate_slogan);
        money_form = findViewById(R.id.money_form);
        donate_final = findViewById(R.id.donate_money_final);

        gratitudedialog = new Dialog(this);

        bganim = AnimationUtils.loadAnimation(this, R.anim.donate_bottom_anim);
        formanim = AnimationUtils.loadAnimation(this, R.anim.form_anim);

        bgmoney.animate().translationY(-2000).setDuration(800).setStartDelay(800);
        splash_money.animate().translationY(200).alpha(0).setDuration(800).setStartDelay(1000);

        donate_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });

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
                        donation_header.setVisibility(View.VISIBLE);
                        donation_header.setAnimation(bganim);
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
                money_form.setVisibility(View.VISIBLE);
                money_form.setAnimation(formanim);

            }
        }, 300);

    }
    @Override
    public void onBackPressed() {
        money_form.setVisibility(View.GONE);
        header.setVisibility(View.GONE);
        super.onBackPressed();

    }

    private void validateFields() {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 40);
        myAnim.setInterpolator(interpolator);
        donate_final.startAnimation(myAnim);
        if (!validateName() | !validatePh() | !validateAddress() | !validateAmount()) {
            return;
        }
        showgratitudePopUp();

    }

    private void showgratitudePopUp() {
        android.transition.Fade fade = new Fade();
        fade.setDuration(2000);
        gratitudedialog.setContentView(R.layout.donation_gratitude2);
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
        AppCompatEditText donation_name = findViewById(R.id.money_donation_name);

        String name_val = donation_name.getText().toString();
        if (name_val.isEmpty()) {
            donation_name.setError("Field cannot be empty");
            donation_name.setBackgroundDrawable(getDrawable(R.drawable.donation_fields_error));
            return false;
        }else if (name_val.length()<3){
            donation_name.setError("Name too short");
            donation_name.setBackgroundDrawable(getDrawable(R.drawable.donation_fields_error));
            return false;
        }

        else {
            donation_name.setError(null);
            donation_name.setBackground(getDrawable(R.drawable.donation_field2));
            return true;
        }
    }

    private boolean validatePh() {
        AppCompatEditText donation_ph = findViewById(R.id.money_donation_ph);
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
            donation_ph.setBackground(getDrawable(R.drawable.donation_field2));
            return true;
        }

    }

    private boolean validateAddress() {
        AppCompatEditText donation_address = findViewById(R.id.money_donation_address);

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
        AppCompatEditText amount = findViewById(R.id.money_amt_textfields);

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
            amount.setBackground(getDrawable(R.drawable.donation_field2));
            return true;
        }

    }
}
