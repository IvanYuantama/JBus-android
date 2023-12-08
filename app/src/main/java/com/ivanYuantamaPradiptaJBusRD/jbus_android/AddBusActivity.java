package com.ivanYuantamaPradiptaJBusRD.jbus_android;

import static com.ivanYuantamaPradiptaJBusRD.jbus_android.LoginActivity.loggedAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Account;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.BaseResponse;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Bus;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.BusType;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Facility;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Station;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.BaseApiService;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class AddBusActivity digunakan untuk menangani UI dari layout addbus
 *
 * @author Ivan Yuantama Pradipta
 * @version 1.00
 */
public class AddBusActivity extends AppCompatActivity {

    /**
     * Field yang terdapat pada AddBusActivity seperti checkbox, spinner, dll
     */
    private BaseApiService mApiService;
    private Context mContext;
    private EditText nameBus, capacityBus, priceBus;
    private Button buttonAddBus;

    private BusType[] busType = BusType.values();
    private BusType selectedBusType;
    private Spinner busTypeSpinner, departureSpinner, arrivalSpinner;

    private List<Station> stationList = new ArrayList<>();
    private int selectedDeptStationID;
    private int selectedArrStationID;

    private CheckBox acCheckBox, wifiCheckBox, toiletCheckBox, lcdCheckBox;
    private CheckBox coolboxCheckBox, lunchCheckBox, baggageCheckBox, electricCheckBox;

    private List<Facility> selectedFacilities = new ArrayList<>();


    /**
     * busTypeOISL digunakan untuk busTypeSpinner
     */
    AdapterView.OnItemSelectedListener busTypeOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            // mengisi field selectedBusType sesuai dengan item yang dipilih
            selectedBusType = busType[position];
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    /**
     * deptOISL digunakan untuk deptartureSpinner
     */
    AdapterView.OnItemSelectedListener deptOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            // mengisi field selectedBusType sesuai dengan item yang dipilih
            selectedDeptStationID = stationList.get(position).id;
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    /**
     * arrOISL digunakan untuk arrivalSpinner
     */
    AdapterView.OnItemSelectedListener arrOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            // mengisi field selectedBusType sesuai dengan item yang dipilih
            selectedArrStationID = stationList.get(position).id;
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    /**
     *
     * @param savedInstanceState untuk membuat layout AddBusActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        mContext = this;
        mApiService = UtilsApi.getApiService();
        nameBus = findViewById(R.id.busname_text);
        capacityBus = findViewById(R.id.capacity_text);
        priceBus = findViewById(R.id.price_text);
        buttonAddBus = findViewById(R.id.button_add_bus);
        acCheckBox = findViewById(R.id.ac_checkbox);
        wifiCheckBox = findViewById(R.id.wifi_checkbox);
        toiletCheckBox = findViewById(R.id.toilet_checkbox);
        lcdCheckBox = findViewById(R.id.lcd_checkbox);
        coolboxCheckBox = findViewById(R.id.coolbox_checkbox);
        lunchCheckBox = findViewById(R.id.lunch_checkbox);
        baggageCheckBox = findViewById(R.id.baggage_checkbox);
        electricCheckBox = findViewById(R.id.socket_checkbox);
        departureSpinner = this.findViewById(R.id.departure_dropdown);
        arrivalSpinner = this.findViewById(R.id.arrival_dropdown);
        busTypeSpinner = this.findViewById(R.id.bus_type_dropdown);
        ArrayAdapter adBus = new ArrayAdapter(this, android.R.layout.simple_list_item_1, busType);
        adBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        busTypeSpinner.setAdapter(adBus);
        // menambahkan OISL (OnItemSelectedListener) untuk spinner
        busTypeSpinner.setOnItemSelectedListener(busTypeOISL);
        handleAddBus();
        buttonAddBus.setOnClickListener(v -> {
            handleFacility();
            handleButtonBus();
        });
    }

    /**
     * Untuk menangani fitur add bus
     */
    protected void handleAddBus(){
        mApiService.getAllStation().enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                stationList = response.body(); //simpan response body ke listStation
                List<String> stationStringList = new ArrayList<>();
                stationList.forEach(v -> {
                    stationStringList.add(v.stationName);
                });

                ArrayAdapter deptBus = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, stationStringList);
                deptBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                departureSpinner.setAdapter(deptBus);
                departureSpinner.setOnItemSelectedListener(deptOISL);

                ArrayAdapter arrBus = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, stationStringList);
                arrBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                arrivalSpinner.setAdapter(arrBus);
                arrivalSpinner.setOnItemSelectedListener(arrOISL);
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Untuk menangani fitur add facility
     */
    protected void handleFacility(){
        selectedFacilities.clear(); // Clear the list before updating
        if (acCheckBox.isChecked()) { selectedFacilities.add(Facility.AC);}
        if (wifiCheckBox.isChecked()) { selectedFacilities.add(Facility.WIFI);}
        if (toiletCheckBox.isChecked()) { selectedFacilities.add(Facility.TOILET);}
        if (lcdCheckBox.isChecked()) { selectedFacilities.add(Facility.LCD_TV);}
        if (coolboxCheckBox.isChecked()) { selectedFacilities.add(Facility.COOL_BOX);}
        if (lunchCheckBox.isChecked()) { selectedFacilities.add(Facility.LUNCH);}
        if (baggageCheckBox.isChecked()) { selectedFacilities.add(Facility.LARGE_BAGGAGE);}
        if (electricCheckBox.isChecked()) { selectedFacilities.add(Facility.ELECTRIC_SOCKET);}
    }

    /**
     * Untuk menangani fitur create bus
     */
    protected void handleButtonBus(){
        // handling empty field
        int idS = loggedAccount.id;
        String busNameS = nameBus.getText().toString();
        String capacityS = capacityBus.getText().toString();
        String priceS = priceBus.getText().toString();

        if (busNameS.isEmpty() || capacityS.isEmpty() || priceS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        int capacity = Integer.valueOf(capacityS);
        int price = Integer.valueOf(priceS);

        mApiService.create(idS, busNameS, capacity, selectedFacilities, selectedBusType, price, selectedDeptStationID, selectedArrStationID).enqueue(new Callback<BaseResponse<Bus>>() {
            @Override
            public void onResponse(Call<BaseResponse<Bus>> call, Response<BaseResponse<Bus>> response) {
                // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Bus> res = response.body();
                // if success finish this activity (back to login activity)
                if (res.success){
                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, ManageBusActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Bus>> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}