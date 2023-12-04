package com.ivanYuantamaPradiptaJBusRD.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Payment;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Station;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.BaseApiService;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentListActivity extends AppCompatActivity {

    private BaseApiService mApiService;
    private Context mContext;
    private List<Payment> listPayment = new ArrayList<>();
    private PaymentAdapter payDap;

    private ListView listPaymentView;
    private int selectedPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        mContext = this;
        mApiService = UtilsApi.getApiService();
        listPaymentView = findViewById(R.id.list_payment_view);
        handleListPayment();
    }

    protected void handleListPayment(){
        mApiService.getAllPayment().enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                listPayment = response.body(); //simpan response body ke listStation
                payDap = new PaymentAdapter(mContext, listPayment);
                listPaymentView.setAdapter(payDap);
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}