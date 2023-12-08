package com.ivanYuantamaPradiptaJBusRD.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Account;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.BaseResponse;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.BaseApiService;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class RegisterActivity digunakan untuk menangani UI dari layout register
 *
 * @author Ivan Yuantama Pradipta
 * @version 1.00
 */
public class RegisterActivity extends AppCompatActivity {
    /**
     * Field yang terdapat pada RegisterActivity seperti edittext dan button
     */
    private BaseApiService mApiService;
    private Context mContext;
    private EditText name, email, password;
    private Button registerButton = null;

    /**
     *
     * @param savedInstanceState untuk membuat layout RegisterActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        mContext = this;
        mApiService = UtilsApi.getApiService();
        name = findViewById(R.id.user_reg);
        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.password_reg);
        registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(v -> {
            handleRegister();
        });
    }

    /**
     * Untuk menangani fitur register
     */
    protected void handleRegister() {
        // handling empty field
        String nameS = name.getText().toString();
        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();

        if (nameS.isEmpty() || emailS.isEmpty() || passwordS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mApiService.register(nameS, emailS, passwordS).enqueue(new Callback<BaseResponse<Account>>() {
            @Override
            public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
                // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Account> res = response.body();
                // if success finish this activity (back to login activity)
                if (res.success){
                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Account>> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}