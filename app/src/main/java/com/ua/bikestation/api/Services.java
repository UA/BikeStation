package com.ua.bikestation.api;

import com.ua.bikestation.model.BikeStation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Services {
    @GET("BisikletDuraklariJSON")
    Call<List<BikeStation>> getBicycleStation();
}
