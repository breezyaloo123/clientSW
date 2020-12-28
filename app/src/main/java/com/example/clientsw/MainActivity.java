package com.example.clientsw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientsw.Interfaces.ShopAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
  private TextView textView;
  private ListView listView;
  private Button button;
  private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview1);
       // listView = findViewById(R.id.listview1);
        button = findViewById(R.id.editBtn);
        recyclerView = findViewById(R.id.recycleview1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        getAllProducts();





    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllProducts();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getAllProducts();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        getAllProducts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllProducts();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String nom = parent.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(),""+nom,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getAllProducts()
    {
        final List<Shop> list = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ShopAPI.ipaddress)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();


        ShopAPI shopAPI = retrofit.create(ShopAPI.class);

        Call<List<Shop>> call = shopAPI.getProduits();

        Log.d("good",""+call);

        call.enqueue(new Callback<List<Shop>>() {
            @Override
            public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code : " + response.code());
                    Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(),"apres if",Toast.LENGTH_LONG).show();
                List<Shop> shops = response.body();


                list.clear();
                for(Shop s: shops)
                {
                    String res = "";
                    res = "Nom : "+s.getNom()+"\n"+"Prix : "+s.getPrix()+"\n"+" Quantite : "+s.getQuantite()+"\n\n";
                    //Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
                    Shop a = new Shop(s.getId(),s.getNom(),s.getPrix(),s.getQuantite());
                    list.add(a);
                    //ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(),R.layout.showdatas,R.id.textview1,list);
                    Log.d("good",list.toString());
                    //  textView.append(res);
                    int p = list.indexOf(s);

                }
                Myadapter1 myadapter = new Myadapter1(getApplication(),list);
                recyclerView.setAdapter(myadapter);

                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Shop>> call, Throwable t) {
                textView.setText(t.getMessage());
                Toast.makeText(getApplicationContext(),"echec",Toast.LENGTH_LONG).show();
            }
        });
    }
}