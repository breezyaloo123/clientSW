package com.example.clientsw;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Myadapter extends ArrayAdapter<String> {
    public Myadapter(Context context, ArrayList<String> codes) {
        super(context, 0,codes);
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {

        final String item = getItem(position);
        if (convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.showdatas,parent);
        }

        final TextView textView = convertView.findViewById(R.id.textview1);
        Button button = convertView.findViewById(R.id.editBtn);
        Button button1 = convertView.findViewById(R.id.editBtn1);

       // textView.setText(item);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("good",item);
            }
        });
        return convertView;
    }
}
