package com.example.clientsw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clientsw.Interfaces.ShopAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Inscription extends AppCompatActivity {

    private EditText prenom,nom,adresse,telephone,pseudo,password;
    private Button button,connexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        prenom = findViewById(R.id.prenom_editext);
        nom = findViewById(R.id.name_editext);
        adresse = findViewById(R.id.adresse_editext);
        telephone = findViewById(R.id.tel_editext);
        pseudo = findViewById(R.id.pseudo_editext);
        password = findViewById(R.id.password_editext);
        button = findViewById(R.id.button_send);
        connexion = findViewById(R.id.connexion);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prenom1 = prenom.getText().toString();
                String nom1 = nom.getText().toString();
                String addresse1= adresse.getText().toString();
                int tel = Integer.parseInt(telephone.getText().toString());
                String pseudo1 = pseudo.getText().toString();
                String pwd = password.getText().toString();

                Client client = new Client(prenom1,nom1,addresse1,tel,pseudo1,pwd);

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder().baseUrl(ShopAPI.ipaddress)
                        .addConverterFactory(GsonConverterFactory.create(gson)).build();
                ShopAPI shopAPI = retrofit.create(ShopAPI.class);

                Call<Client> call = shopAPI.insertClients(client);

                call.enqueue(new Callback<Client>() {
                    @Override
                    public void onResponse(Call<Client> call, Response<Client> response) {
                        if (!response.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Not Good",Toast.LENGTH_LONG).show();
                        }

                        Client c =response.body();
                        Toast.makeText(getApplicationContext(),""+c,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),LoginPage.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Client> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginPage.class);
                startActivity(intent);
            }
        });
    }
}