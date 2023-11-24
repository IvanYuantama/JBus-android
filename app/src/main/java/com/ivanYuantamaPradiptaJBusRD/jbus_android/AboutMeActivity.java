package com.ivanYuantamaPradiptaJBusRD.jbus_android;

import static com.ivanYuantamaPradiptaJBusRD.jbus_android.LoginActivity.loggedAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Account;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.BaseResponse;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.BaseApiService;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {

    private BaseApiService mApiService;
    private Context mContext;
    private TextView userText, emailText, balanceText, initialName = null;
    private EditText textTopUp = null;
    private Button buttonTopUp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        initialName = findViewById(R.id.initial_name);
        userText = findViewById(R.id.user_about_bind);
        emailText = findViewById(R.id.email_about_bind);
        balanceText = findViewById(R.id.balance_about_bind);
        textTopUp = findViewById(R.id.textInputEditText);
        buttonTopUp = findViewById(R.id.button_topup);

        initialName.setText("" + LoginActivity.loggedAccount.name.charAt(0));
        userText.setText(loggedAccount.name);
        emailText.setText(loggedAccount.email);
        balanceText.setText("" + loggedAccount.balance);

        buttonTopUp.setOnClickListener(v -> handleTopUp());
    }

    protected void handleTopUp() {
        // handling empty field
        int idS = LoginActivity.loggedAccount.id;
        String amountTest = textTopUp.getText().toString();


        if (amountTest.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        double amountAdd = Double.valueOf(amountTest);
        mApiService.topUp(idS, amountAdd).enqueue(new Callback<BaseResponse<Double>>() {
            @Override
            public void onResponse(Call<BaseResponse<Double>> call, Response<BaseResponse<Double>> response) {
                // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Double> res = response.body();

                if (res.success){
                    finish();
                    LoginActivity.loggedAccount.balance += res.payload.doubleValue();
                    startActivity(getIntent());
                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse<Double>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}