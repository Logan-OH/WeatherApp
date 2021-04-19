package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button2;
    //ImageView imageView;
    TextView country, city_v, temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Variables
        editText = findViewById(R.id.editText);
        button2 = findViewById(R.id.button2);
        country = findViewById(R.id.country);
        city_v = findViewById(R.id.city);
        temp = findViewById(R.id.temp);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findWeather();

            }
        });

    }

        public void findWeather(){
            //Api can only call one location
            String city = editText.getText().toString();
            String url = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&units=imperial&appid=462f445106adc1d21494341838c10019";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Calling api information
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        //Calls the search for the specific country
                        JSONObject object1 =jsonObject.getJSONObject("sys");
                        String country_find = object1.getString("country");
                        country.setText(country_find);

                        //Calls the search for the city
                        String city_find = jsonObject.getString("name");
                        city_v.setText(city_find);

                        //Calls temp from api
                        JSONObject object2 =jsonObject.getJSONObject("main");
                        String temp_find = object2.getString("temp");
                        temp.setText(temp_find);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText( MainActivity.this,error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
        }
}