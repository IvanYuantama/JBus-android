package com.ivanYuantamaPradiptaJBusRD.jbus_android;

import static com.ivanYuantamaPradiptaJBusRD.jbus_android.LoginActivity.loggedAccount;
import static com.ivanYuantamaPradiptaJBusRD.jbus_android.ManageBusActivity.busManageId;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Bus;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.BaseApiService;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.UtilsApi;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBusActivity extends AppCompatActivity {

    private BaseApiService mApiService;
    private Context mContext;
    private Bus tempDetailBus;

    private Button buttonPayment;
    private TextView nameBus, capacityBus, priceBus, departureBus, arrivalBus, typeBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bus);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        mContext = this;
        mApiService = UtilsApi.getApiService();
        nameBus = findViewById(R.id.detailbus_name2);
        capacityBus = findViewById(R.id.detailbus_capacity2);
        priceBus = findViewById(R.id.detailbus_price2);
        departureBus = findViewById(R.id.detailbus_departure2);
        arrivalBus = findViewById(R.id.detailbus_arrival2);
        typeBus = findViewById(R.id.detailbus_bustype2);
        buttonPayment = findViewById(R.id.button_makebooking);
        handledetailTitle();
        buttonPayment.setOnClickListener(v -> {
            Intent intent = new Intent(this, PaymentActivity.class);
            startActivity(intent);
        });
    }

    protected void handledetailTitle() {
        mApiService.getBusbyId(busManageId).enqueue(new Callback<Bus>() {
            @Override
            public void onResponse(Call<Bus> call, Response<Bus> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Bus res = response.body();
                tempDetailBus = res;
                nameBus.setText("" + tempDetailBus.name);
                capacityBus.setText("" + tempDetailBus.capacity);
                priceBus.setText("" + tempDetailBus.price.price);
                departureBus.setText("" + tempDetailBus.departure.stationName);
                arrivalBus.setText("" + tempDetailBus.arrival.stationName);
                typeBus.setText("" + tempDetailBus.busType);
            }

            @Override
            public void onFailure(Call<Bus> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}