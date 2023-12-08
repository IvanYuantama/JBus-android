package com.ivanYuantamaPradiptaJBusRD.jbus_android.model;

import java.sql.Timestamp;

/**
 * Class invoice digunakan untuk menangani invoice dari booking
 *
 * @author Ivan Yuantama Pradipta
 * @version 1.00
 */

public class Invoice extends Serializable {
    /**
     * Merupakan data yang terdapat pada invoice seperti rating, status, dll
     */
    public Timestamp time;
    public int buyerId;
    public int renterId;
    public BusRating rating;
    public PaymentStatus status;

    public enum BusRating {
        NONE,
        NEUTRAL,
        GOOD,
        BAD,
    }

    public enum PaymentStatus {
        FAILED,
        WAITING,
        SUCCESS,
    }
}
