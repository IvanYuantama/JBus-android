package com.ivanYuantamaPradiptaJBusRD.jbus_android;

import static com.ivanYuantamaPradiptaJBusRD.jbus_android.LoginActivity.loggedAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

/**
 * Class AboutMeActivity digunakan untuk menangani UI dari layout aboutme
 *
 * @author Ivan Yuantama Pradipta
 * @version 1.00
 */
public class AboutMeActivity extends AppCompatActivity {

    /**
     * Field yang terdapat pada AboutMeActivity seperti textview, edittext, dan button
     */
    private BaseApiService mApiService;
    private Context mContext;
    private TextView userText, emailText, balanceText, initialName, textRenter, textNotRenter = null;
    private EditText textTopUp = null;
    private Button buttonTopUp, buttonRenter, buttonNotRenter = null;

    /**
     *
     * @param savedInstanceState untuk membuat layout AboutMeActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        setTitle("About Me");

        mContext = this;
        mApiService = UtilsApi.getApiService();

        initialName = findViewById(R.id.initial_name);
        userText = findViewById(R.id.schedule_bus);
        emailText = findViewById(R.id.email_about_bind);
        balanceText = findViewById(R.id.balance_about_bind);
        textTopUp = findViewById(R.id.textInputEditText);
        buttonTopUp = findViewById(R.id.button_topup);
        textRenter = findViewById(R.id.text_renter);
        textNotRenter = findViewById(R.id.text_notrenter);
        buttonRenter = findViewById(R.id.button_renter);
        buttonNotRenter = findViewById(R.id.button_notrenter);
        handleProfile();
        initialName.setText("" + LoginActivity.loggedAccount.name.toUpperCase().charAt(0));
        userText.setText(loggedAccount.name);
        emailText.setText(loggedAccount.email);
        balanceText.setText("" + loggedAccount.balance);

        if(loggedAccount.company == null) {
            textRenter.setVisibility(View.GONE);
            buttonRenter.setVisibility(View.GONE);
            buttonNotRenter.setOnClickListener(v -> {
                Intent intent = new Intent(this, RegisterRenterActivity.class);
                startActivity(intent);
            });
        }
        else {
            textNotRenter.setVisibility(View.GONE);
            buttonNotRenter.setVisibility(View.GONE);
            buttonRenter.setOnClickListener(v -> {
                Intent intent = new Intent(this, ManageBusActivity.class);
                startActivity(intent);
            });
        }
        buttonTopUp.setOnClickListener(v -> handleTopUp());
    }

    /**
     * Menghandle topUp
     */
    protected void handleTopUp() {
        // handling empty field
        int idS = loggedAccount.id;
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
                    loggedAccount.balance += res.payload.doubleValue();
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

    /**
     * Menghandle untuk mendapatkan akun dari idnya
     */
    private void handleProfile(){
        mApiService.getAccountbyId(loggedAccount.id).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Account res = response.body();
                loggedAccount = res;
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}