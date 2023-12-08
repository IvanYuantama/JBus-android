package com.ivanYuantamaPradiptaJBusRD.jbus_android.model;

/**
 * Class station digunakan untuk menangani station pada bus
 *
 * @author Ivan Yuantama Pradipta
 * @version 1.00
 */
public class Station extends Serializable {
    /**
     * Data yang terdapat pada class station seperti city, address, dll
     */
    public String stationName;
    public City city;
    public String address;
}