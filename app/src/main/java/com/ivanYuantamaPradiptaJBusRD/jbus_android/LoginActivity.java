package com.ivanYuantamaPradiptaJBusRD.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Account;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.BaseResponse;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.BaseApiService;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class LoginActivity digunakan untuk menangani UI dari layout login
 *
 * @author Ivan Yuantama Pradipta
 * @version 1.00
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Field yang terdapat pada LoginActivity seperti textview, edittext, dan button
     */
    private BaseApiService mApiService;
    private Context mContext;
    private EditText email, password;
    private TextView registerNow = null;
    private Button loginButton = null;
    public static Account loggedAccount;

    /**
     *
     * @param savedInstanceState untuk membuat layout LoginActivity
     */
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
            viewToast(this, "You has been login");
            moveActivity(this, MainActivity.class);
        });

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        mContext = this;
        mApiService = UtilsApi.getApiService();
        email = findViewById(R.id.email_log);
        password = findViewById(R.id.password_log);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> {
            handleLogin();
        });
    }

    /**
     *
     * @param ctx activity sekarang
     * @param cls acitivity setelahnya
     */
    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    /**
     *
     * @param ctx activity sekarang
     * @param message message dari response
     */
    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Untuk menangani fitur login
     */
    protected void handleLogin() {
        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();

        if (emailS.isEmpty() || passwordS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mApiService.login(emailS, passwordS).enqueue(new Callback<BaseResponse<Account>>() {
            @Override
            public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Account> res = response.body();

                if (res.success){
                    loggedAccount = res.payload;
                    moveActivity(mContext, MainActivity.class);
                    Toast.makeText(mContext, "Welcome to JBus", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Account>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}