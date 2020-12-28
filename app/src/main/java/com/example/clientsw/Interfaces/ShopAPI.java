package com.example.clientsw.Interfaces;

import com.example.clientsw.Client;
import com.example.clientsw.Shop;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ShopAPI {
    final String ipaddress="http://10.153.42.189:8080/BOUTIQUE/";
    @GET("produits")
    Call<List<Shop>> getProduits();

    @GET("produit/{id}")
    Call<Shop> getOneProduct(@Path("id") int id);

    @POST("add")
    Call<Shop> insertProducts(@Body Shop p);

    @PUT("updates/{id}")
    Call<Shop> modifyProd(@Body Shop shop,@Path("id") int id);



    @DELETE("delete/{id}")
    Call<Void> deleteProd(@Path("id") int id);


    @GET("clients")
    Call<List<Client>> getClients();

    @POST("add1")
    Call<Client> insertClients(@Body Client c);


}
