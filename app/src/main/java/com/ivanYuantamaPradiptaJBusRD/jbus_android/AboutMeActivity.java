package com.ivanYuantamaPradiptaJBusRD.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

public class AboutMeActivity extends AppCompatActivity {

    private TextView userText, emailText, balanceText = null;
    private Button buttonTopUp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        userText = findViewById(R.id.user_about_bind);
        emailText = findViewById(R.id.email_about_bind);
        balanceText = findViewById(R.id.balance_about_bind);
        buttonTopUp = findViewById(R.id.button_topup);
        userText.setText("Ivan Yuantama");
        emailText.setText("ivanyuantama@gmail.com");
        balanceText.setText("20000");
    }

//    protected void setButtonTopUp(int amount){
//        int total = a
//    }
}