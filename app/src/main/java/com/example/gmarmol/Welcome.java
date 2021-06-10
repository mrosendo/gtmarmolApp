package com.example.gmarmol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    String url = "http://192.168.1.53:8090/gmarmolapi/";
    private OrderAdapterList.RecyclerViewClick listener;
    ArrayList<Order> orderArrayList;
    String uNit, cardName;
    List<Order> elements;
    TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        uNit = getIntent().getExtras().getString("u_nit");
        cardName = getIntent().getExtras().getString("card_name");

        tx = findViewById(R.id.textView);
        tx.setText(cardName);

        //TRAER ORDENES
        getOrdersAPI(uNit);

        //DESPLEGAR ORDENES
        //init();

    }

    //OBTENER ORDENES
    public void getOrdersAPI(String nit) {
        String searchOrder = this.url + "order/" + nit;

        RequestQueue mQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, searchOrder, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("order");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONArray data = jsonArray.optJSONArray(i);



                                for (int j = 0; j < data.length(); j++) {
                                    JSONObject obj = data.getJSONObject(j);

                                    Order order = new Order();
                                    order.setDocNum(obj.getInt("DocNum"));
                                    order.setDocDate(obj.getString("FechaString"));

                                    //System.out.println(order.getDocDate());

                                    orderArrayList.add(order);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    /*public void init() {
        elements = orderArrayList;

        OrderAdapterList listAdapter = new OrderAdapterList(elements, this, listener);
        RecyclerView recyclerView = findViewById(R.id.orderList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }*/

    /*private void setOnClickListener() {
        listener = new OrderAdapterList.RecyclerViewClick() {
            @Override
            public void onCLick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), OrderStatus.class);
                intent.putExtra("username", elements.get(position).getDocDate());
                startActivity(intent);
            }
        };
    }*/
}