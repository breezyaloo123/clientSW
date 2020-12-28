package com.example.clientsw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clientsw.Interfaces.ShopAPI;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddProduit extends AppCompatActivity {

    private EditText nomp,quantitep,prixp;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produit);
        nomp = findViewById(R.id.nomp_editext);
        quantitep = findViewById(R.id.q_editext);
        prixp = findViewById(R.id.p_editext);
        button = findViewById(R.id.button_send);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                // OkHttpClient.Builder http = new OkHttpClient.Builder()
                //      .connectTimeout(100, TimeUnit.SECONDS);

                Retrofit retrofit = new Retrofit.Builder().baseUrl(ShopAPI.ipaddress)
                        .addConverterFactory(GsonConverterFactory.create(gson)).build();
                ShopAPI shopAPI = retrofit.create(ShopAPI.class);

                String name = nomp.getText().toString();
                int quantite = Integer.parseInt(quantitep.getText().toString());
                int price = Integer.parseInt(prixp.getText().toString());

                Shop pp = new Shop(name,price,quantite);

                Call<Shop> call = shopAPI.insertProducts(pp);

                call.enqueue(new Callback<Shop>() {
                    @Override
                    public void onResponse(Call<Shop> call, Response<Shop> response) {
                        if (!response.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"not good",Toast.LENGTH_LONG).show();
                        }

                        Shop s = response.body();
                        Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_LONG).show();
                       // Snackbar.make(findViewById(R.id.mycoord),"Success",Snackbar.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);

                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<Shop> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }
}