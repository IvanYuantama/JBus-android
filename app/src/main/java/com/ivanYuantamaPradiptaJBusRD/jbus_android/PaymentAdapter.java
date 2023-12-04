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
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Payment;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class PaymentAdapter extends ArrayAdapter<Payment> {

    public PaymentAdapter(@NonNull Context context, List<Payment> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.schedulebus_view, parent, false);
        }

        Payment pay = getItem(position);
        if (pay != null) {
            TextView listText = currentItemView.findViewById(R.id.list_text_schedule);
            listText.setText("" + pay.id);
        }
        return currentItemView;
    }

}
