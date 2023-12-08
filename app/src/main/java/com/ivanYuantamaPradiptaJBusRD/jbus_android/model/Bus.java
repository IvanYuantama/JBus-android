package com.ivanYuantamaPradiptaJBusRD.jbus_android.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Class bus digunakan untuk menangani bus yang ada
 *
 * @author Ivan Yuantama Pradipta
 * @version 1.00
 */
public class Bus extends Serializable {
    /**
     * Merupakan berbagai data dari bus seperti nama bus, harga, dll
     */
    public int accountId;
    public String name;
    public List<Facility> facilities;
    public Price price;
    public int capacity;
    public BusType busType;
    public Station departure;
    public Station arrival;
    public List<Schedule> schedules;

    public static List<Bus> sampleBusList(int size) {
        List<Bus> busList = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            Bus bus = new Bus();
            bus.name = "Bus " + i;
            busList.add(bus);
        }

        return busList;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
