package com.ivanYuantamaPradiptaJBusRD.jbus_android;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Bus;

import java.util.ArrayList;
import java.util.List;

/**
 * Class BusSeatAdapater digunakan untuk menangani UI dari listview busseat
 *
 * @author Ivan Yuantama Pradipta
 * @version 1.00
 */

public class BusSeatAdapter extends ArrayAdapter<String> {

    public BusSeatAdapter(@NonNull Context context, List<String> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.schedulebus_view, parent, false);
        }

        String str = getItem(position);
        if (str != null) {
            TextView listText = currentItemView.findViewById(R.id.list_text_schedule);
            listText.setText(str);
        }
        return currentItemView;
    }

}

