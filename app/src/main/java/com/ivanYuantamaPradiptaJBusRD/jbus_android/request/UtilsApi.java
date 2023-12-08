package com.ivanYuantamaPradiptaJBusRD.jbus_android.request;

/**
 * Class UtilsApi digunakan untuk menangani base url api
 *
 * @author Ivan Yuantama Pradipta
 * @version 1.00
 */
public class UtilsApi {
    public static final String BASE_URL_API = "http://10.0.2.2:5000/";
    public static BaseApiService getApiService() {
        return
                RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}

