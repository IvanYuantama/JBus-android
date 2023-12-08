package com.ivanYuantamaPradiptaJBusRD.jbus_android.model;


import java.sql.Timestamp;
import java.util.List;

/**
 * Class payment digunakan untuk menangani payment pada pengguna
 *
 * @author Ivan Yuantama Pradipta
 * @version 1.00
 */
public class Payment extends Invoice {
    /**
     * merupakan data yang terdapat pada payment seperti busid, departure, dan seat bus
     */
    private int busId;
    public Timestamp departureDate;
    public List<String> busSeats;

}