package com.myapp.UpkaarApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.transition.Fade;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.skydoves.transformationlayout.TransformationAppCompatActivity;

public class foodActivity extends TransformationAppCompatActivity {

    ImageView bg;
    TextView splashSlogan, donate_slogan, dynamic_food_type;
    LinearLayout header, form, dynamic_form;
    Spinner food_drop_down;
    Button donate_final;
    Dialog gratitudedialog;
    Button accept;
    Animation bganim, formanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_food);

        gratitudedialog = new Dialog(this);
        bg = findViewById(R.id.bgfood);
        splashSlogan = findViewById(R.id.splashSlogan);
        header = findViewById(R.id.foodHeader);
        donate_slogan = findViewById(R.id.donate_slogan);
        form = findViewById(R.id.food_form);
        bganim = AnimationUtils.loadAnimation(this, R.anim.donate_bottom_anim);
        formanim = AnimationUtils.loadAnimation(this, R.anim.form_anim);
        food_drop_down = findViewById(R.id.dropdown_menu);
        dynamic_form = findViewById(R.id.dynamic_field);
        dynamic_food_type = findViewById(R.id.food_type);
        donate_final = findViewById(R.id.donate_final);

        donate_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });

        bg.animate().translationY(-2000).setDuration(800).setStartDelay(800);
        splashSlogan.animate().translationY(200).alpha(0).setDuration(800).setStartDelay(1000);

        String[] items = new String[]{"Raw Food", "Cooked Food"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(foodActivity.this,
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        food_drop_down.setAdapter(adapter);

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
                        donate_slogan.setVisibility(View.VISIBLE);
                        donate_slogan.setAnimation(bganim);
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
                form.setVisibility(View.VISIBLE);
                form.setAnimation(formanim);

            }
        }, 300);

    }

    @Override
    public void onBackPressed() {
        form.setVisibility(View.GONE);
        header.setVisibility(View.GONE);
        super.onBackPressed();

    }

    private void validateFields() {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 40);
        myAnim.setInterpolator(interpolator);
        donate_final.startAnimation(myAnim);
        if (!validateName() | !validatePh() | !validateAddress() | !validateItem_name() | !validateItem_type() | !validateAmount() | !validateRadio()) {
            return;
        }
        showgratitudePopUp();

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
        AppCompatEditText donation_name = findViewById(R.id.donation_name);

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
        AppCompatEditText donation_ph = findViewById(R.id.donation_ph);
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
        AppCompatEditText donation_address = findViewById(R.id.donation_address);

        String ph_no = donation_address.getText().toString();
        if (ph_no.isEmpty()) {
            donation_address.setError("Field cannot be empty");
            donation_address.setBackground(getDrawable(R.drawable.donation_fields_error));
            return false;

        } else if (ph_no.length() < 10) {
            donation_address.setError("Address too small");
            donation_address.setBackground(getDrawable(R.drawable.donation_fields_error));
            return false;
        }
        else{
                donation_address.setError(null);
                donation_address.setBackground(getDrawable(R.drawable.donation_fields));
                return true;
            }

    }

    private boolean validateItem_name() {
        AppCompatEditText donation_item_name = findViewById(R.id.donation_food_name);

        String ph_no = donation_item_name.getText().toString();
        if (ph_no.isEmpty()) {
            donation_item_name.setError("Field cannot be empty");
            donation_item_name.setBackground(getDrawable(R.drawable.donation_fields_error));
            return false;

        } else {
            donation_item_name.setError(null);
            donation_item_name.setBackground(getDrawable(R.drawable.donation_fields));
            return true;
        }

    }

    private boolean validateItem_type() {
        AppCompatEditText donation_item_type = findViewById(R.id.donation_food_type);

        String ph_no = donation_item_type.getText().toString();
        if (ph_no.isEmpty()) {
            donation_item_type.setError("Field cannot be empty");
            donation_item_type.setBackground(getDrawable(R.drawable.donation_fields_error));
            return false;

        } else {
            donation_item_type.setError(null);
            donation_item_type.setBackground(getDrawable(R.drawable.donation_fields));
            return true;
        }

    }

    private boolean validateAmount() {
        AppCompatEditText amount = findViewById(R.id.amt_textfields);

        String amount_val = amount.getText().toString();
        if (amount_val.isEmpty()) {
            amount.setError("Field cannot be empty");
            amount.setBackground(getDrawable(R.drawable.donation_fields_error));
            return false;

        } else if (amount_val.equals("0")) {
            amount.setError("Donation amount Invalid");
            amount.setBackground(getDrawable(R.drawable.donation_fields_error));
            return false;
        }
        else {
            amount.setError(null);
            amount.setBackground(getDrawable(R.drawable.donation_fields));
            return true;
        }

    }

    private boolean validateRadio() {
        RadioGroup radioGroup = findViewById(R.id.radio);
        AppCompatEditText amount = findViewById(R.id.amt_textfields);

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            amount.setError("Please select kg(s)/serving(s)");
            amount.setBackgroundDrawable(getDrawable(R.drawable.donation_fields_error));
            return false;
        } else {
            amount.setError(null);
            amount.setBackgroundDrawable(getDrawable(R.drawable.donation_fields));
            return true;
        }
    }
}

