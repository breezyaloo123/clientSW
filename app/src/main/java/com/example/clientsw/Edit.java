package com.example.clientsw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientsw.Interfaces.ShopAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Edit extends AppCompatActivity {

    private EditText name,price,quantity;
    private TextView textView;
    private Button enregistrer,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        name = findViewById(R.id.name1);
        price = findViewById(R.id.price);
        quantity = findViewById(R.id.quantite);
        textView = findViewById(R.id.textview_id);
        enregistrer = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        String nom = getIntent().getStringExtra("nom");
        int idd = getIntent().getIntExtra("id",0);
           final int prix = getIntent().getIntExtra("prix",0);
      int quantite = getIntent().getIntExtra("quantite",0);
      textView.setText(""+idd);
        name.setText(nom);
        price.setText(""+prix);
        quantity.setText(""+quantite);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });

        enregistrer.setOnClickListener(new View.OnClickListener() {
            int id = Integer.parseInt(textView.getText().toString());
            String nn = name.getText().toString();
            int prixx = Integer.parseInt(price.getText().toString());
            int qq = Integer.parseInt(quantity.getText().toString());

            Shop aa = new Shop(nn,prixx,qq);

            @Override
            public void onClick(View v) {

               Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder().baseUrl(ShopAPI.ipaddress)
                        .addConverterFactory(GsonConverterFactory.create(gson)).build();
                ShopAPI shopAPI = retrofit.create(ShopAPI.class);

                Call<Shop> call = shopAPI.modifyProd(aa,id);
                call.enqueue(new Callback<Shop>() {
                    @Override
                    public void onResponse(Call<Shop> call, Response<Shop> response) {
                        if (!response.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"not goood",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"goood"+response.body(),Toast.LENGTH_LONG).show();
                        }

//                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Shop> call, Throwable t) {

                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }
}