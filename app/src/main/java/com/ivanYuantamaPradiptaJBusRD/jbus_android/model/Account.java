package com.ivanYuantamaPradiptaJBusRD.jbus_android.model;
/**
 * Class account digunakan untuk menangani user account dari pengguna
 *
 * @author Ivan Yuantama Pradipta
 * @version 1.00
 */
public class Account extends Serializable {
    /**
     * Menggambarkan data dari pengguna seperti email, password, dll
     */

    public String name;
    public String email;
    public String password;
    public double balance;
    public Renter company;
}
