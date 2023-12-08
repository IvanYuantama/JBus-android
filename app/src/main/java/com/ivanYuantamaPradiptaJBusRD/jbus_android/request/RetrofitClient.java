package com.ivanYuantamaPradiptaJBusRD.jbus_android.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class Retrofitclient digunakan untuk menangani retrofit
 *
 * @author Ivan Yuantama Pradipta
 * @version 1.00
 */
public class RetrofitClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient(String baseUrl){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient())
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(getCustomJson()))
                    .build();
        }
        return retrofit;
    }
    private static OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request newRequest = originalRequest.newBuilder()
//ganti value header di bawah ini dengan nama kalian
                            .addHeader("Client-Name", "Ivan Yuantama")
                            .build();
                    return chain.proceed(newRequest);
                })
                .build();
    }

    private static Gson getCustomJson(){
        return new GsonBuilder().setDateFormat("MMMM dd, yyyy hh:mm:ss").create();
    }
}
