package com.ivanYuantamaPradiptaJBusRD.jbus_android;

import static com.ivanYuantamaPradiptaJBusRD.jbus_android.AddBusScheduleActivity.tempSchedule;
import static com.ivanYuantamaPradiptaJBusRD.jbus_android.LoginActivity.loggedAccount;
import static com.ivanYuantamaPradiptaJBusRD.jbus_android.ManageBusActivity.busManageId;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Account;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.BaseResponse;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Bus;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Payment;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Schedule;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.BaseApiService;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    private BaseApiService mApiService;
    private Context mContext;
    private int buyerId, renterId, busId;
    //private List<String> busPaymentSeat;

    private Spinner departureSpinner;
    private List<Schedule> schBus = new ArrayList<>();
    private List<String> schBusString = new ArrayList<>();
    private Schedule departureSelected;
    private String departureSelectedString;

    private List<String> seatString = new ArrayList<>();

    private EditText busSeatEditText;
    private ListView listSeat;
    private Button buttonBook, addSeatButton;
    private BusSeatAdapter seatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mContext = this;
        mApiService = UtilsApi.getApiService();
        buyerId = loggedAccount.id;
        renterId = loggedAccount.company.id;
        busId = busManageId;
        schBus = tempSchedule;
        departureSpinner = findViewById(R.id.bus_departure_dropdown);
        busSeatEditText= findViewById(R.id.bus_seat_textview);
        buttonBook = findViewById(R.id.button_book);
        listSeat = findViewById(R.id.list_seat_view);
        addSeatButton = findViewById(R.id.button_addseat);

        for(Schedule s : schBus){
            schBusString.add("" + s.departureSchedule);
        }

        ArrayAdapter scheduleAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, schBusString);
        scheduleAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        departureSpinner.setAdapter(scheduleAdapter);
        // menambahkan OISL (OnItemSelectedListener) untuk spinner
        departureSpinner.setOnItemSelectedListener(departureOISL);

        addSeatButton.setOnClickListener(v -> {
            handleSeatButton();
        });

        buttonBook.setOnClickListener(v -> {
            handleMakeBooking();
        });
    }

    AdapterView.OnItemSelectedListener departureOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            // mengisi field selectedBusType sesuai dengan item yang dipilih
            departureSelected = schBus.get(position);
            departureSelectedString = "" + departureSelected.departureSchedule;
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private void handleSeatButton(){
        if(busSeatEditText.getText().toString().contains("RD")){
            seatString.add(busSeatEditText.getText().toString());
            seatAdapter = new BusSeatAdapter(mContext, seatString);
            listSeat.setAdapter(seatAdapter);
        }
        else {
            Toast.makeText(mContext, "Harus pake RD yaa", Toast.LENGTH_SHORT).show();
        }
    }
    private void handleMakeBooking(){
        if (busSeatEditText.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.makeBooking(buyerId, renterId, busId, seatString, departureSelectedString).enqueue(new Callback<BaseResponse<Payment>>() {
            @Override
            public void onResponse(Call<BaseResponse<Payment>> call, Response<BaseResponse<Payment>> response) {
                // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Payment> res = response.body();
                // if success finish this activity (back to login activity)
                if (res.success){
                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Payment>> call, Throwable t) {
                Toast.makeText(mContext, "Sukses booking", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, DetailBusActivity.class);
                startActivity(intent);
            }
        });
    }
}