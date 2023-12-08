package com.ivanYuantamaPradiptaJBusRD.jbus_android;

import static com.ivanYuantamaPradiptaJBusRD.jbus_android.LoginActivity.loggedAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Account;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.BaseResponse;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Bus;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.BaseApiService;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class ManageBusActivity digunakan untuk menangani UI dari layout managebus
 *
 * @author Ivan Yuantama Pradipta
 * @version 1.00
 */
public class ManageBusActivity extends AppCompatActivity {
    /**
     * Field yang terdapat pada ManageBusActivity seperti textview, listview, dll
     */
    private BaseApiService mApiService;
    private Context mContext;

    private ManageBusArrayAdapter manageBusArray = null;
    private ListView listManageBusView = null;
    private TextView listBusButton = null;
    private List<Bus> listManageBus = new ArrayList<>();

    public static int busManageId;

    /**
     *
     * @param savedInstanceState untuk membuat layout ManageBusActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bus);
        setTitle("Manage Bus");

        listManageBusView = findViewById(R.id.list_manage_bus);

        mContext = this;
        mApiService = UtilsApi.getApiService();
        handleManage();

        listManageBusView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Selected item at position: " + position, Toast.LENGTH_LONG).show();
                busManageId = listManageBus.get(position).id;
                Intent intent = new Intent(mContext, AddBusScheduleActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     *
     * @param menu menampilkan menu
     * @return mengembalikan nilai true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manage_bus_menu, menu);
        return true;
    }

    /**
     *
     * @param item menampilkan isi dari menu
     * @return mengembalikan item yang di select
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_bus_button){
            Intent intent = new Intent(this, AddBusActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Untuk mendapatkan list bus dari backend
     */
    protected void handleManage() {
        int idS = LoginActivity.loggedAccount.id;
        mApiService.getMyBus(idS).enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Bus> res = response.body();

                listManageBus = res;
                manageBusArray = new ManageBusArrayAdapter(mContext, listManageBus);
                listManageBusView.setAdapter(manageBusArray);

            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}