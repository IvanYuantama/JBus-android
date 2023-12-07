package com.ivanYuantamaPradiptaJBusRD.jbus_android;

import static com.ivanYuantamaPradiptaJBusRD.jbus_android.ManageBusActivity.busManageId;
import static com.ivanYuantamaPradiptaJBusRD.jbus_android.PaymentListActivity.selectedPaymentId;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.BaseResponse;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Bus;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Invoice;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Payment;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.BaseApiService;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentDetailActivity extends AppCompatActivity {

    private BaseApiService mApiService;
    private Context mContext;
    private TextView departureDate, status, rating;
    private Payment payTemp;
    private Button acceptButton, cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);
        mContext = this;
        mApiService = UtilsApi.getApiService();
        departureDate = findViewById(R.id.paymentdetail_departuretitle2);
        status = findViewById(R.id.paymentdetail_statustitle2);
        rating = findViewById(R.id.paymentdetail_ratingtitle2);
        acceptButton = findViewById(R.id.button_accept);
        cancelButton = findViewById(R.id.button_cancel);
        handlePaymentDetail();
        acceptButton.setOnClickListener(v -> {
            handleAccept();
        });
        cancelButton.setOnClickListener(v -> {
            handleCancel();
        });
    }

    protected void handlePaymentDetail() {
        mApiService.getPaymentbyId(selectedPaymentId).enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Payment res = response.body();
                payTemp = res;
                departureDate.setText("" + payTemp.departureDate);
                status.setText("" + payTemp.status);
                rating.setText("" + payTemp.rating);
                if(payTemp.status != Invoice.PaymentStatus.WAITING){
                    acceptButton.setVisibility(View.GONE);
                    cancelButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void handleAccept(){
        mApiService.accept(payTemp.id).enqueue(new Callback<BaseResponse<Payment>>() {
            @Override
            public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Payment> resAccept = response.body();
                payTemp = resAccept.payload;
                if(resAccept.success){
                    Toast.makeText(mContext, resAccept.message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, PaymentListActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void handleCancel(){
        mApiService.cancel(payTemp.id).enqueue(new Callback<BaseResponse<Payment>>() {
            @Override
            public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Payment> resCancel = response.body();
                payTemp = resCancel.payload;
                if(resCancel.success){
                    Toast.makeText(mContext, resCancel.message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, PaymentListActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}