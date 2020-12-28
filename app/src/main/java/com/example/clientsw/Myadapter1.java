package com.example.clientsw;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientsw.Interfaces.ShopAPI;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Myadapter1 extends RecyclerView.Adapter<Myadapter1.ViewHolder> {

    Context context;
    List<Shop> shops;


    public Myadapter1(Context context,List<Shop> shops)
    {
        this.context = context;
        this.shops = shops;
    }

    @NonNull
    @Override
    public Myadapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showdatas,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

      final Shop a = shops.get(position);

       holder.textView.setText(a.getId()+" "+a.getNom()+"\n"+a.getPrix()+" "+a.getQuantite());
       holder.button1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Gson gson = new GsonBuilder()
                       .setLenient()
                       .create();

               Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.43.59:8080/BOUTIQUE/")
                       .addConverterFactory(GsonConverterFactory.create(gson)).build();


               ShopAPI shopAPI = retrofit.create(ShopAPI.class);

               Call<Void> call = shopAPI.deleteProd(a.getId());
               notifyDataSetChanged();
               call.enqueue(new Callback<Void>() {
                   @Override
                   public void onResponse(Call<Void> call, Response<Void> response) {
                       if (!response.isSuccessful())
                       {
                           Log.d("Bad","Noooott");
                       }

                       shops.remove(position);
                       notifyDataSetChanged();
                       Log.d("good","gg");
                   }

                   @Override
                   public void onFailure(Call<Void> call, Throwable t) {
                       Log.d("error",t.getMessage());
                       notifyDataSetChanged();
                   }
               });
           }
       });
       holder.button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //Log.d("item",a.getNom());

             //  Toast.makeText(context,a.getNom()+"\n"+a.getPrix()+"\n"+a.getQuantite(),Toast.LENGTH_LONG).show();
             //  Snackbar.make(holder.itemView.findViewById())
               Intent edit = new Intent(context,Edit.class);
               edit.putExtra("id",a.getId());
               edit.putExtra("nom",a.getNom());
               edit.putExtra("prix",a.getPrix());
               edit.putExtra("quantite",a.getQuantite());
               context.startActivity(edit);





           }
       });
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button button,button1;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview1);
            button = itemView.findViewById(R.id.editBtn);
            button1 = itemView.findViewById(R.id.editBtn1);


        }
    }

    public int returnPosition(int p)
    {
        return p;
    }

}
