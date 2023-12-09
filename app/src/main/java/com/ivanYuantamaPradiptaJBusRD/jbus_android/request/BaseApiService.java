package com.ivanYuantamaPradiptaJBusRD.jbus_android.request;

import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Account;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.BaseResponse;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Bus;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.BusType;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Facility;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Invoice;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Renter;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Station;
import com.ivanYuantamaPradiptaJBusRD.jbus_android.model.Payment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Class BaseApiService digunakan untuk menangani API dari backend
 *
 * @author Ivan Yuantama Pradipta
 * @version 1.00
 */
public interface BaseApiService {
    /**
     *
     * @param id account id yang ingin diminta
     * @return mengembalikan account dengan id yg sesuai
     */
    @GET("account/{id}")
    Call<Account> getAccountbyId (@Path("id") int id);

    /**
     *
     * @param name nama yg akan di register
     * @param email email yg akan di register
     * @param password password yg akan di register
     * @return mengembalikan response dari backend
     */
    @POST("account/register")
    Call<BaseResponse<Account>> register (
            @Query("name") String name,
            @Query("email") String email,
            @Query("password") String password
    );

    /**
     *
     * @param email email untuk login
     * @param password password untuk login
     * @return mengembalikan response dari backend
     */
    @POST("account/login")
    Call<BaseResponse<Account>> login (
            @Query("email") String email,
            @Query("password") String password
    );

    /**
     *
     * @param id account id untuk topUp
     * @param amount uang yang akan ditopup
     * @return mengembalikan response dari backend
     */
    @POST("account/{id}/topUp")
    Call<BaseResponse<Double>> topUp (
           @Path("id") int id,
           @Query("amount") double amount
    );

    /**
     *
     * @param id account id yg didaftarkan sebagai renter
     * @param companyName companyName yg didaftarkan sebagai renter
     * @param address address yg didaftarkan sebagai renter
     * @param phoneNumber phoneNumber yg didaftarkan sebagai renter
     * @return mengembalikan response dari backend
     */
    @POST("account/{id}/registerRenter")
    Call<BaseResponse<Renter>> registerRenter (
            @Path("id") int id,
            @Query("companyName") String companyName,
            @Query("address") String address,
            @Query("phoneNumber") String phoneNumber
    );

    /**
     *
     * @param accountId accountid yang membuat bus
     * @param name nama dari bus
     * @param capacity kapasitas dari bus
     * @param facilities fasilitas dari bus
     * @param busType tipe dari bus
     * @param price harga dari bus
     * @param stationDepartureId id dari departure bus
     * @param stationArrivalId id dari arrival bus
     * @return mengembalikan response dari backend
     */
    @POST("bus/create")
    Call<BaseResponse<Bus>> create (
            @Query("accountId") int accountId,
            @Query("name") String name,
            @Query("capacity") int capacity,
            @Query("facilities") List<Facility> facilities,
            @Query("busType") BusType busType,
            @Query("price") int price,
            @Query("stationDepartureId") int stationDepartureId,
            @Query("stationArrivalId") int stationArrivalId
    );

    /**
     *
     * @param accountId account id yang diminta busnya
     * @return mengembalikan response dari backend berupa bus
     */
    @GET("bus/getMyBus")
    Call<List<Bus>> getMyBus(
            @Query("accountId") int accountId
    );

    /**
     *
     * @param busId id dari bus yang akan ditambahkan jadwalnya
     * @param time waktu sekarang
     * @return mengembalikan response dari backend
     */
    @POST("bus/addSchedule")
    Call<BaseResponse<Bus>> addSchedule (
            @Query("busId") int busId,
            @Query("time") String time
    );

    /**
     *
     * @param id id dari bus yg ingin diminta
     * @return mengembalikan response dari backend berupa bus
     */
    @GET("bus/{id}")
    Call<Bus> getBusbyId (@Path("id") int id);

    /**
     *
     * @return mengembalikan response dari backend berupa list bus
     */
    @GET("bus/getAll")
    Call<List<Bus>> getAllBus();

    /**
     *
     * @param id id dari payment yg diminta
     * @return mengembalikan response dari backend
     */
    @GET("payment/{id}")
    Call<Payment> getPaymentbyId (@Path("id") int id);

    /**
     *
     * @param buyerId id dari buyer
     * @param renterId id dari renter
     * @param busId id dari bus
     * @param busSeats listseat pada bus
     * @param departureDate tanggal keberangkatan bus
     * @return mengembalikan response dari backend
     */
    @POST("payment/makeBooking")
    Call<BaseResponse<Payment>> makeBooking (
            @Query("buyerId") int buyerId,
            @Query("renterId") int renterId,
            @Query("busId") int busId,
            @Query("busSeats") List<String> busSeats,
            @Query("departureDate") String departureDate
    );

    /**
     *
     * @return mengembalikan response dari backend berupa list payment
     */
    @GET("payment/getAll")
    Call<List<Payment>> getAllPayment();

    /**
     *
     * @param id id dari payment yang di accept
     * @return mengembalikan response dari backend
     */
    @POST("payment/{id}/accept")
    Call<BaseResponse<Payment>> accept (@Path("id") int id);

    /**
     *
     * @param id id dari payment yang di cancel
     * @return mengembalikan response dari backend
     */
    @POST("payment/{id}/cancel")
    Call<BaseResponse<Payment>> cancel (@Path("id") int id);

    /**
     *
     * @param id id dari payment
     * @param busRating rating bus dari user
     * @return mengembalikan rating
     */
    @POST("payment/{id}/rating")
    Call<BaseResponse<Payment>> rating (
            @Path("id") int id,
            @Query("busRating") Invoice.BusRating busRating
    );

    /**
     *
     * @return mengembalikan response dari backend berupa list station
     */
    @GET("station/getAll")
    Call<List<Station>> getAllStation();
}

