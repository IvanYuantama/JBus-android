package com.ivanYuantamaPradiptaJBusRD.jbus_android;

import static com.ivanYuantamaPradiptaJBusRD.jbus_android.ManageBusActivity.busManageId;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Account;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.BaseResponse;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Bus;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Schedule;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.BaseApiService;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBusScheduleActivity extends AppCompatActivity {

    private Bus tempBus;
    private BaseApiService mApiService;
    private Context mContext;
    private TextView nameBus;
    private EditText date, month, year, hour, minute, second;
    private ScheduleAdapter scheduleArray = null;
    private ListView listScheduleView = null;
    private Button buttonAddSchedule;

    public static List<Schedule> tempSchedule = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus_schedule);
        mContext = this;
        mApiService = UtilsApi.getApiService();


        date = findViewById(R.id.date_schedule);
        month = findViewById(R.id.month_schedule);
        year = findViewById(R.id.year_schedule);
        hour = findViewById(R.id.hour_schedule);
        minute = findViewById(R.id.minutes_schedule);
        second = findViewById(R.id.second_schedule);
        buttonAddSchedule = findViewById(R.id.button_add_schedule);

        nameBus = findViewById(R.id.namebus_schedule);
        listScheduleView = findViewById(R.id.list_bus_schedule);
        handleTitle();

        buttonAddSchedule.setOnClickListener(v -> {
            handleAddSchedule();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_bus_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.detail_button){
            Intent intent = new Intent(this, DetailBusActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void handleTitle() {
        mApiService.getBusbyId(busManageId).enqueue(new Callback<Bus>() {
            @Override
            public void onResponse(Call<Bus> call, Response<Bus> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Bus res = response.body();
                tempBus = res;
                nameBus.setText(tempBus.name);
                tempSchedule = tempBus.schedules;
                scheduleArray = new ScheduleAdapter(mContext, tempBus.schedules);
                listScheduleView.setAdapter(scheduleArray);
            }

            @Override
            public void onFailure(Call<Bus> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void handleAddSchedule(){
        String idS = "" + busManageId;
        String dateS = date.getText().toString();
        String monthS = month.getText().toString();
        String yearS = year.getText().toString();
        String hourS = hour.getText().toString();
        String minuteS = minute.getText().toString();
        String secondS = second.getText().toString();


        if (idS.isEmpty() || dateS.isEmpty() || monthS.isEmpty() || yearS.isEmpty() || hourS.isEmpty() || minuteS.isEmpty() || secondS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String tempTimestamp = yearS + "-" + monthS + "-" + dateS + " " + hourS + ":" + minuteS + ":" + secondS;
        mApiService.addSchedule(busManageId, tempTimestamp).enqueue(new Callback<BaseResponse<Bus>>() {
            @Override
            public void onResponse(Call<BaseResponse<Bus>> call, Response<BaseResponse<Bus>> response) {
                // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Bus> res = response.body();

                if (res.success){
                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                    startActivity(getIntent());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Bus>> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}