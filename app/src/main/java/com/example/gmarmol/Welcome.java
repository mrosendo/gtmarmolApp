package com.example.gmarmol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Welcome extends AppCompatActivity {
    String uNit, cardName;
    TextView nitTX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        uNit = getIntent().getExtras().getString("u_nit");
        cardName = getIntent().getExtras().getString("card_name");

        nitTX = findViewById(R.id.textView);
        nitTX.setText(cardName);

        //BOTON PARA FACTURAS
        Button billbtn = findViewById(R.id.billbutton);
        billbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //BOTONO PARA ORDENES
        Button orderbtn = findViewById(R.id.orderbutton);
        orderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderScreen();
            }
        });


    }

    public void orderScreen() {
        Intent ac = new Intent(this, OrderList.class);
        ac.putExtra("nit", uNit);
        startActivity(ac);
    }
}