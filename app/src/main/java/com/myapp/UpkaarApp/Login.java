package com.myapp.UpkaarApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import static com.myapp.UpkaarApp.SplashScreen.hermit;

public class Login extends AppCompatActivity {

    Button callSignUp, login_btn,forgot;
    ImageView image;
    TextView logoText, sloganText;
    AppCompatEditText field_email;
    ShowHidePasswordEditText field_pass;
    LottieAnimationView lottie;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        //Hooks
        auth = FirebaseAuth.getInstance();
        forgot = findViewById(R.id.forgot_btn);
        login_btn = findViewById(R.id.login_btn);
        image = findViewById(R.id.logoImage);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        field_email = findViewById(R.id.username);
        field_pass = findViewById(R.id.password);
        callSignUp = findViewById(R.id.signUP);
        lottie = findViewById(R.id.loading);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this);
                alertDialogBuilder.setMessage("Do you want to reset your password?");
                alertDialogBuilder.setTitle("Forgot Password?");
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                       resetPassword();
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


        callSignUp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(Login.this, SignUp.class);

                Pair[] pairs = new Pair[8];

                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(logoText, "logo_text");
                pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
                pairs[3] = new Pair<View, String>(field_email, "field_email");
                pairs[4] = new Pair<View, String>(field_pass, "field_pass");
                pairs[5] = new Pair<View, String>(login_btn, "btn_login");
                pairs[6] = new Pair<View, String>(callSignUp, "new_user");
                pairs[7] = new Pair<View, String>(forgot, "forgot_trans");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                    startActivity(signUp, options.toBundle());
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
        super.onBackPressed();
        this.finish();
    }

    private boolean validateEmail(){
        String  val = field_email.getText().toString();
        if (val.isEmpty()){
            field_email.setError("Field cannot be empty");
            field_email.setBackgroundDrawable(getDrawable(R.drawable.field_error));
            return false;

        }
        else{
            field_email.setError(null);
            field_email.setBackgroundDrawable(getDrawable(R.drawable.field_round));
            //field_email.setError(false);
            return true;
        }
    }

    private boolean validatePassword(){
        String val = field_pass.getText().toString();

        if (val.isEmpty()){
            field_pass.setError("Field cannot be empty");
            field_pass.setBackground(getDrawable(R.drawable.field_error));
            return false;

        }
        else{
            field_pass.setError(null);
            field_pass.setBackground(getDrawable(R.drawable.field_round));
            //field_pass.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view){
        Button button = findViewById(R.id.login_btn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 40);
        myAnim.setInterpolator(interpolator);

        callSignUp.setVisibility(View.GONE);

        button.startAnimation(myAnim);
        field_email.clearFocus();
        field_pass.clearFocus();
        lottie.setProgress(0);
        lottie.setVisibility(View.VISIBLE);
        lottie.playAnimation();
        if (!validateEmail() | !validatePassword()){
            lottie.setVisibility(View.GONE);
            callSignUp.setVisibility(View.VISIBLE);
            return;

        }
        else {
            isUser();
        }
    }
    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }

    private void isUser() {
        final String  userEmail = field_email.getText().toString().trim();
        final String passCode = field_pass.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        Query checkUser = reference.orderByChild("email").equalTo(EncodeString(userEmail));
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    field_email.setError(null);
                    String passwordfromDB = dataSnapshot.child(EncodeString(userEmail)).child("password").getValue(String.class);

                    if (passCode.equals(passwordfromDB)){
                        field_pass.setError(null);
                        field_pass.setBackgroundDrawable(getDrawable(R.drawable.field_round));
                        field_pass.clearFocus();
                        field_email.clearFocus();
                        makeSignIn();
                    }
                    else {
                        lottie.setVisibility(View.GONE);
                        callSignUp.setVisibility(View.VISIBLE);
                        field_pass.setError("Wrong Password");
                        field_pass.setBackgroundDrawable(getDrawable(R.drawable.field_error));
                        field_pass.requestFocus();

                    }
                }
                else {
                    lottie.setVisibility(View.GONE);
                    callSignUp.setVisibility(View.VISIBLE);
                    field_email.setError("No such User exists");
                    field_email.setBackgroundDrawable(getDrawable(R.drawable.field_error));
                    field_email.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void makeSignIn(){
        auth = FirebaseAuth.getInstance();
        final String  useremail = field_email.getText().toString();
        final String passcode = field_pass.getText().toString();

        auth.signInWithEmailAndPassword(useremail,passcode).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                        //pass
                    }
                else {
                    Intent home = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(home);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    hermit = true;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);

                }

            }
        });
    }

    public void resetPassword(){
        String email = field_email.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Enter your registered email",Toast.LENGTH_LONG).show();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Login.this,"We have sent you instructions to reset your password!",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(Login.this, "Failed to send reset email!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
