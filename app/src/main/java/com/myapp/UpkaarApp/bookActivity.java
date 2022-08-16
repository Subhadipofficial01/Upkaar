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

public class bookActivity extends TransformationAppCompatActivity {

    ImageView bgbook;
    TextView slogan_book,slogan_header;
    LinearLayout header,book_form;
    Animation bganim, formanim;
    Dialog gratitudedialog;
    Button accept, donate_final;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_books);
        //Hooks
        bgbook = findViewById(R.id.bgbook);
        slogan_book = findViewById(R.id.splashSlogan_book);
        header = findViewById(R.id.bookHeader);
        slogan_header = findViewById(R.id.donate_books_slogan);
        book_form = findViewById(R.id.book_form);
        donate_final = findViewById(R.id.donate_book_final);

        gratitudedialog = new Dialog(this);

        bgbook.animate().translationY(-2000).setDuration(800).setStartDelay(800);
        slogan_book.animate().translationY(200).alpha(0).setDuration(800).setStartDelay(1000);

        bganim = AnimationUtils.loadAnimation(this, R.anim.donate_bottom_anim);
        formanim = AnimationUtils.loadAnimation(this, R.anim.form_anim);

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
                book_form.setVisibility(View.VISIBLE);
                book_form.setAnimation(formanim);

            }
        }, 300);
    }
    @Override
    public void onBackPressed() {
        book_form.setVisibility(View.GONE);
        header.setVisibility(View.GONE);
        super.onBackPressed();

    }
    private void validateFields() {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 40);
        myAnim.setInterpolator(interpolator);
        donate_final.startAnimation(myAnim);
        if (!validateName() | !validatePh() | !validateAddress() | !validateBook() | !validateAuthor() | !validateType() | !validateAmount()) {
            return;
        }
        showgratitudePopUp();

    }

    private boolean validateType() {
        AppCompatEditText book_type = findViewById(R.id.donation_book_type);
        String type = book_type.getText().toString();
        if (type.isEmpty()){
            book_type.setError("Field cannot be empty");
            book_type.setBackgroundDrawable(getDrawable(R.drawable.donation_fields_error));
            return false;
        }else {
            book_type.setError(null);
            book_type.setBackground(getDrawable(R.drawable.donation_field2));
            return true;
        }

    }

    private boolean validateAuthor() {
        AppCompatEditText book_author = findViewById(R.id.donation_book_author);
        String author = book_author.getText().toString();
        if (author.isEmpty()){
            book_author.setError("Field cannot be empty");
            book_author.setBackgroundDrawable(getDrawable(R.drawable.donation_fields_error));
            return false;
        }else {
            book_author.setError(null);
            book_author.setBackground(getDrawable(R.drawable.donation_field2));
            return true;
        }
    }

    private boolean validateBook() {
        AppCompatEditText book_name = findViewById(R.id.donation_book_name);
        String name_val = book_name.getText().toString();
        if (name_val.isEmpty()){
            book_name.setError("Field cannot be empty");
            book_name.setBackgroundDrawable(getDrawable(R.drawable.donation_fields_error));
            return false;
        }else {
            book_name.setError(null);
            book_name.setBackground(getDrawable(R.drawable.donation_field2));
            return true;
        }
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
        AppCompatEditText donation_name = findViewById(R.id.book_donation_name);

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
        AppCompatEditText donation_ph = findViewById(R.id.book_donation_ph);
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
        AppCompatEditText donation_address = findViewById(R.id.book_donation_address);

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
                donation_address.setBackground(getDrawable(R.drawable.donation_field2));
                return true;
            }
    }

    private boolean validateAmount() {
        AppCompatEditText amount = findViewById(R.id.no_booktextfields);

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

