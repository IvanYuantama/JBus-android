package com.ivanYuantamaPradiptaJBusRD.jbus_android.model;

/**
 * Class BaseResponse digunakan untuk menangani response dari API
 *
 * @author Ivan Yuantama Pradipta
 * @version 1.00
 */
public class BaseResponse<T> {
    /**
     * Data yg ada pada baseresponse seperti success, message, dan payload
     */
    public boolean success;
    public String message;
    public T payload;

}
