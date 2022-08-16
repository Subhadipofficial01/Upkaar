package com.myapp.UpkaarApp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

public class SignUp extends AppCompatActivity {

    AppCompatEditText signupemail,name;
    ShowHidePasswordEditText confirmpass, newpass;
    Button loginBtn, backtologin;
    ImageView logoImage;
    TextView slogan, continue_;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    //popUp
    Dialog epicdialog;
    TextView popup_title, popup_desc;
    Button accept;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        auth = FirebaseAuth.getInstance();
        //Popup
        epicdialog = new Dialog(this);
        //signUp screen
        confirmpass = findViewById(R.id.confirmPassword);
        newpass = findViewById(R.id.newPassword);
        signupemail = findViewById(R.id.signUpEmail);
        loginBtn = findViewById(R.id.signUp_btn);
        backtologin = findViewById(R.id.back_to_login);
        name = findViewById(R.id.name);
        logoImage = findViewById(R.id.signUplogo);
        slogan =  findViewById(R.id.signUpslogan);
        continue_= findViewById(R.id.signuptocontinue);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");

        backtologin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back2login = new Intent(SignUp.this, Login.class);

                Pair[] pairs = new Pair[8];

                pairs[0] = new Pair<View, String>(logoImage, "logo_image");
                pairs[1] = new Pair<View, String>(slogan, "logo_text");
                pairs[2] = new Pair<View, String>(continue_, "logo_desc");
                pairs[3] = new Pair<View, String>(signupemail, "field_email");
                pairs[4] = new Pair<View, String>(newpass, "field_pass");
                pairs[5] = new Pair<View, String>(loginBtn, "btn_login");
                pairs[6] = new Pair<View, String>(backtologin, "new_user");
                pairs[7] = new Pair<View, String>(confirmpass, "forgot_trans");

                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
                    startActivity(back2login, options.toBundle());
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    },1000);
                }

            }
        });


        }

    @Override
    public void onBackPressed(){
        Intent back2login = new Intent(SignUp.this, Login.class);

        Pair[] pairs = new Pair[8];

        pairs[0] = new Pair<View, String>(logoImage, "logo_image");
        pairs[1] = new Pair<View, String>(slogan, "logo_text");
        pairs[2] = new Pair<View, String>(continue_, "logo_desc");
        pairs[3] = new Pair<View, String>(signupemail, "field_email");
        pairs[4] = new Pair<View, String>(newpass, "field_pass");
        pairs[5] = new Pair<View, String>(loginBtn, "btn_login");
        pairs[6] = new Pair<View, String>(backtologin, "new_user");
        pairs[7] = new Pair<View, String>(confirmpass, "forgot_trans");

        ActivityOptions options = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
            startActivity(back2login, options.toBundle());
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },1000);
        }
    }
    private boolean validateName(){
        String val = name.getText().toString();
        String namePattern = "^(?!.*[_.]{2})[a-zA-Z][a-zA-Z0-9_.]{3,12}$";
        if (val.isEmpty()){
            name.setError("Field cannot be empty");
            name.setBackgroundDrawable(getDrawable(R.drawable.field_error));
            return false;

        }else if (val.length()>=15){
            name.setError("Username too long");
            name.setBackgroundDrawable(getDrawable(R.drawable.field_error));
            return false;
        }else if(!val.matches(namePattern)){
            name.setError("Name must have minimum length 3 and with no white space");
            name.setBackgroundDrawable(getDrawable(R.drawable.field_error));
            return true;
        }
        else{
            name.setError(null);
            //name.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateEmail(){
        String  val = signupemail.getText().toString();
        String pattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        if (val.isEmpty()){
            signupemail.setError("Field cannot be empty");
            signupemail.setBackgroundDrawable(getDrawable(R.drawable.field_error));
            return false;

        }else if (!val.matches(pattern)){
            signupemail.setError("Invalid email address");
            signupemail.setBackgroundDrawable(getDrawable(R.drawable.field_error));
            return false;

        }
        else{
            signupemail.setError(null);
            //signupemail.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword(){
        String val = newpass.getText().toString();
        String valcon = confirmpass.getText().toString();
        String regPass = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                ".{8,}" +               //at least 8 characters
                "$";
        if (val.isEmpty()){
            newpass.setError("Field cannot be empty");
            confirmpass.setError("Field cannot be empty");
            newpass.setBackground(getDrawable(R.drawable.field_error));
            confirmpass.setBackground(getDrawable(R.drawable.field_error));
            return false;

        }else if (!val.matches(regPass)){
            newpass.setError("Password must contain one uppercase, one lowercase, a special character, one number and of length 8");
            newpass.setBackground(getDrawable(R.drawable.field_error));
            return false;
        }else if (!val.equals(valcon)){
            confirmpass.setError("Password and confirm password doesn't match");
            confirmpass.setBackground(getDrawable(R.drawable.field_error));
            return false;
        }
        else{
            newpass.setError(null);
            confirmpass.setError(null);
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void registerUser(View view){
        Button button = findViewById(R.id.signUp_btn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 40);
        myAnim.setInterpolator(interpolator);

        button.startAnimation(myAnim);
        if (!validateName() | !validateEmail() | !validatePassword()){
            return;
        }

        String email = signupemail.getText().toString();
        String password = newpass.getText().toString();
        String Username = name.getText().toString();

        Userhelper userhelper = new Userhelper(EncodeString(email), password, Username);
        createAcccount();

        reference.child(EncodeString(email)).setValue(userhelper);



    }
    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }

    private void createAcccount() {
        AppCompatEditText signupemail = findViewById(R.id.signUpEmail);
        ShowHidePasswordEditText signuppwd = findViewById(R.id.newPassword);
        String email = signupemail.getText().toString();
        String password = signuppwd.getText().toString();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignUp.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    showConfimPopup();
                                }
                            }, 500);
                        }
                    }
                });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showConfimPopup(){
        android.transition.Fade fade = new Fade();
        fade.setDuration(2000);
        epicdialog.setContentView(R.layout.signup_confirm_popup);
        accept = epicdialog.findViewById(R.id.accept_btn);

        epicdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            epicdialog.getWindow().setEnterTransition(fade);
            epicdialog.getWindow().setExitTransition(fade);

        }
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent popLog = new Intent(getApplicationContext(), Login.class);
                startActivity(popLog);
                finish();
            }
        });
        epicdialog.show();
    }

}
