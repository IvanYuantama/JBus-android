package com.ivanYuantamaPradiptaJBusRD.jbus_android;

import static com.ivanYuantamaPradiptaJBusRD.jbus_android.LoginActivity.loggedAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Account;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.BaseResponse;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Renter;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.BaseApiService;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRenterActivity extends AppCompatActivity {

    private BaseApiService mApiService;
    private Context mContext;
    private EditText regRentCompany, regRentAddress, regRentPhone;
    private Button regRentButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_renter);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        mContext = this;
        mApiService = UtilsApi.getApiService();

        regRentCompany = findViewById(R.id.company_reg_rent);
        regRentAddress = findViewById(R.id.address_reg_rent);
        regRentPhone = findViewById(R.id.phone_reg_rent);
        regRentButton = findViewById(R.id.reg_rent_button);
        regRentButton.setOnClickListener(v -> {
            handleRegRent();
        });
    }

    protected void handleRegRent() {
        // handling empty field
        int idS = loggedAccount.id;
        String company = regRentCompany.getText().toString();
        String address = regRentAddress.getText().toString();
        String phone = regRentPhone.getText().toString();

        if (company.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mApiService.registerRenter(idS, company, address, phone).enqueue(new Callback<BaseResponse<Renter>>() {
            @Override
            public void onResponse(Call<BaseResponse<Renter>> call, Response<BaseResponse<Renter>> response) {
                // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Renter> res = response.body();
                // if success finish this activity (back to login activity)
                if (res.success){
                    loggedAccount.company = res.payload;
                    finish();
                    Toast.makeText(mContext, "Berhasil daftar sebagai renter", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, AboutMeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Renter>> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}