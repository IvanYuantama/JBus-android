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

public class BusArrayAdapter extends ArrayAdapter<Bus> {

    public BusArrayAdapter(@NonNull Context context, List<Bus> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.bus_view, parent, false);
        }

        Bus bus = getItem(position);
        if (bus != null) {
            TextView listText = currentItemView.findViewById(R.id.list_text);
            listText.setText(bus.name);
        }
        return currentItemView;
    }

}
