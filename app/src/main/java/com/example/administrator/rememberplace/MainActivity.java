package com.example.administrator.rememberplace;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.gms.maps.model.LatLng;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView myListView;
    static ArrayList<String> places = new ArrayList<>();
    static ArrayList<LatLng> locations = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences =
                this.getSharedPreferences("com.example.administrator.rememberplace", Context.MODE_PRIVATE);
        myListView = (ListView) findViewById(R.id.myListView);
        ArrayList<String> lat = new ArrayList<>();
        ArrayList<String> Lng = new ArrayList<>();
        places.clear();
        lat.clear();
        Lng.clear();
        locations.clear();
        try {
            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("address",ObjectSerializer.serialize(new ArrayList<String>())));
            lat = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lat", ObjectSerializer.serialize(new ArrayList<String>())));
            Lng = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("Lng", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (places.size() > 0 && lat.size() > 0 && Lng.size() > 0) {
            if (places.size() ==  lat.size()&&lat.size()==  Lng.size()) {
                for (int i = 0; i < lat.size(); i++) {
                    locations.add(new LatLng(Double.valueOf(lat.get(i)), Double.valueOf(Lng.get(i))));
                }
            }
        }else{
            places.add("Add a new place...");
            locations.add(new LatLng(0, 0));

        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, places);
        myListView.setAdapter(arrayAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("placeNumber", i);
                startActivity(intent);
            }
        });
//        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                arrayAdapter.remove(places.get(position));
//                arrayAdapter.notifyDataSetChanged();
//
//                return false;
//            }
//        });
    }
}
