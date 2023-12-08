package com.ivanYuantamaPradiptaJBusRD.jbus_android.model;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Class schedule digunakan untuk menangani schedule pada bus
 *
 * @author Ivan Yuantama Pradipta
 * @version 1.00
 */

public class Schedule {
    /**
     * Data yang terdapat pada class schedule seperti departureSchedule dan seatAvailability
     */
    public Timestamp departureSchedule;
    public Map<String, Boolean> seatAvailability;
}
