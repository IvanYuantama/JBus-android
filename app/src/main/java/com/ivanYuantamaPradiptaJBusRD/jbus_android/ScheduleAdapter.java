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
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Class ScheduleAdapater digunakan untuk menangani UI dari listview schedule
 *
 * @author Ivan Yuantama Pradipta
 * @version 1.00
 */
public class ScheduleAdapter extends ArrayAdapter<Schedule> {

    public ScheduleAdapter(@NonNull Context context, List<Schedule> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.schedulebus_view, parent, false);
        }

        Schedule sch = getItem(position);
        if (sch != null) {
            TextView listText = currentItemView.findViewById(R.id.list_text_schedule);
            listText.setText("" + sch.departureSchedule);
        }
        return currentItemView;
    }

}
