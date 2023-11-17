package com.ivanYuantamaPradiptaJBusRD.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private TextView registerNow = null;
    private Button loginButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerNow = findViewById(R.id.text_register);
        loginButton = findViewById(R.id.login_button);

        registerNow.setOnClickListener(v -> {
            moveActivity(this, RegisterActivity.class);
        });

        loginButton.setOnClickListener(v -> {
            viewToast(this, "This is for login");
            moveActivity(this, MainActivity.class);
        });

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
}