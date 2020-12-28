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

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPage extends AppCompatActivity {
    private EditText prenom,nom;
    private Button btnsend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        prenom = findViewById(R.id.prenom_editext);
        nom = findViewById(R.id.nom_editext);
        btnsend = findViewById(R.id.button_send);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String prenom1 = prenom.getText().toString();
               final String nom1 = nom.getText().toString();

               // Client c = new Client(prenom1,nom1);
                //Shop pp = new Shop("Mango",500,2);

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
               // OkHttpClient.Builder http = new OkHttpClient.Builder()
                  //      .connectTimeout(100, TimeUnit.SECONDS);

                Retrofit retrofit = new Retrofit.Builder().baseUrl(ShopAPI.ipaddress)
                        .addConverterFactory(GsonConverterFactory.create(gson)).build();
                ShopAPI shopAPI = retrofit.create(ShopAPI.class);

                Call<List<Client>> call = shopAPI.getClients();

                call.enqueue(new Callback<List<Client>>() {
                    @Override
                    public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                        if (!response.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Not good",Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(getApplicationContext(),"Goood",Toast.LENGTH_LONG).show();
                        List<Client> clients = response.body();

                        for (Client c:clients
                             ) {

                            if ((c.getPseudo().equals(prenom1)) && (c.getPassword().equals(nom1)))
                            {
                                Intent intent = new Intent(getApplicationContext(),AddProduit.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                prenom.setError("wrong");
                                nom.setError("Wrong");

                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Client>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

                //Client c = new Client(prenom1,nom1);

//                Call<Client> call = shopAPI.insertClients(c);
//
//
//                call.enqueue(new Callback<Client>() {
//                    @Override
//                    public void onResponse(Call<Client> call, Response<Client> response) {
//                        if (!response.isSuccessful()) {
//
//                            Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_LONG).show();
//                            return;
//                        }
//                        Client client = response.body();
//                        Toast.makeText(getApplicationContext(),""+client,Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<Client> call, Throwable t) {
//                        Toast.makeText(getApplicationContext(),"Echec",Toast.LENGTH_LONG).show();
//                    }
//                });
            }
        });

    }
}