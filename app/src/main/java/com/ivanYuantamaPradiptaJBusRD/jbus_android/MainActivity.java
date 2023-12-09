package com.ivanYuantamaPradiptaJBusRD.jbus_android;

import static com.ivanYuantamaPradiptaJBusRD.jbus_android.LoginActivity.loggedAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Account;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Bus;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.BaseApiService;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class MainActivity digunakan untuk menangani UI dari layout main
 *
 * @author Ivan Yuantama Pradipta
 * @version 1.00
 */

public class MainActivity extends AppCompatActivity {

    /**
     * Field yang terdapat pada MainActivity seperti listview, button, dll
     */
    private BusArrayAdapter busArray = null;
    private ListView listBusView = null;

    private Button[] btns;
    private int currentPage = 0;
    private int pageSize = 10; // kalian dapat bereksperimen dengan field ini
    private int listSize;
    private int noOfPages;
    private List<Bus> listBus = new ArrayList<>();
    private Button prevButton = null;
    private Button nextButton = null;
    private ListView busListView = null;
    private HorizontalScrollView pageScroll = null;
    private BaseApiService mApiService;
    private Context mContext;

    /**
     *
     * @param savedInstanceState untuk membuat layout MainActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main JBus");
        busArray = new BusArrayAdapter(this, Bus.sampleBusList(100));
        listBusView = findViewById(R.id.list_bus);
        listBusView.setAdapter(busArray);
        mContext = this;
        mApiService = UtilsApi.getApiService();

        prevButton = findViewById(R.id.prev_page);
        nextButton = findViewById(R.id.next_page);
        pageScroll = findViewById(R.id.page_number_scroll);
        busListView = findViewById(R.id.list_bus);

//        listBus = Bus.sampleBusList(30);
//        listSize = listBus.size();

        handleGetListBus();

//        paginationFooter();
//        goToPage(currentPage);

        prevButton.setOnClickListener(v -> {
            currentPage = currentPage != 0? currentPage-1 : 0;
            goToPage(currentPage);
        });
        nextButton.setOnClickListener(v -> {
            currentPage = currentPage != noOfPages -1? currentPage+1 : currentPage;
            goToPage(currentPage);
        });
    }

    /**
     *
     * @param menu menampilkan menu pada main activity
     * @return mengembalikan nilai true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    /**
     *
     * @param item menampilkan item yang tersedia pada menu
     * @return mengembalikan nilai true
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.profile_button){
            Intent intent = new Intent(this, AboutMeActivity.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId() == R.id.payment_button){
            Intent intent = new Intent(this, PaymentListActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method ntuk paginasi
     */
    private void paginationFooter() {
        int val = listSize % pageSize;
        val = val == 0 ? 0:1;
        noOfPages = listSize / pageSize + val;
        LinearLayout ll = findViewById(R.id.btn_layout);
        btns = new Button[noOfPages];
        if (noOfPages <= 6) {
            ((FrameLayout.LayoutParams) ll.getLayoutParams()).gravity = Gravity.CENTER;
        }
        for (int i = 0; i < noOfPages; i++) {
            btns[i]=new Button(this);
            btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btns[i].setText(""+(i+1));

            btns[i].setTextColor(getResources().getColor(R.color.black));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, 100);
            ll.addView(btns[i], lp);
            final int j = i;
            btns[j].setOnClickListener(v -> {
                currentPage = j;
                goToPage(j);
            });
        }
    }

    /**
     *
     * @param index untuk pindah ke page yg ditentukan
     */
    private void goToPage(int index) {
        for (int i = 0; i< noOfPages; i++) {
            if (i == index) {
                btns[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
                btns[i].setTextColor(getResources().getColor(android.R.color.white));
                btns[i].setTextSize(8);
                scrollToItem(btns[index]);
                viewPaginatedList(listBus, currentPage);
            } else {
                btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btns[i].setTextColor(getResources().getColor(android.R.color.white));
                btns[i].setTextSize(7);
            }
        }
    }

    /**
     *
     * @param item item untuk di scroll
     */
    private void scrollToItem(Button item) {
        int scrollX = item.getLeft() - (pageScroll.getWidth() - item.getWidth()) / 2;
        pageScroll.smoothScrollTo(scrollX, 0);
    }

    /**
     *
     * @param listBus list pada bus
     * @param page page untuk paginate
     */
    private void viewPaginatedList(List<Bus> listBus, int page) {
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, listBus.size());
        List<Bus> paginatedList = listBus.subList(startIndex, endIndex);
        busArray = new BusArrayAdapter(this, paginatedList);
        listBusView.setAdapter(busArray);
    }

    /**
     * Menghandle list bus
     */
    private void handleGetListBus(){
        mApiService.getAllBus().enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Bus> resListBus = response.body();
                listBus = resListBus;
                listSize = listBus.size();
                paginationFooter();
                goToPage(currentPage);
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}