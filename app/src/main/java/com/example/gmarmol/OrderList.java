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

public class OrderList extends AppCompatActivity {
    private OrderAdapterList.RecyclerViewClick listener;
    String url = "http://192.168.1.53:8090/gmarmolapi/";
    ArrayList<Order> orderArrayList = new ArrayList<>();
    List<Order> elements;
    String uNit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        uNit = getIntent().getExtras().getString("nit");

        getOrdersAPI(uNit);
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

                            JSONArray jsonArray = response.getJSONArray("orden");


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONArray data = jsonArray.optJSONArray(i);

                                for (int j = 0; j < data.length(); j++) {
                                    JSONObject obj = data.getJSONObject(j);

                                    Order order = new Order();
                                    order.setDocNum(obj.getInt("DocNum"));
                                    order.setDocDate(obj.getString("FechaString"));
                                    orderArrayList.add(order);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //IMPLEMENTAR AQUI METODO PARA RECYCLERVIEW
                        init(orderArrayList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    public void init(ArrayList<Order> list) {
        elements = list;

        setOnClickListener();

        RecyclerView recyclerView = findViewById(R.id.listOrders);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        OrderAdapterList listAdapter = new OrderAdapterList(elements, this, listener);
        recyclerView.setAdapter(listAdapter);
    }

    private void setOnClickListener() {

        listener = new OrderAdapterList.RecyclerViewClick() {
            @Override
            public void onCLick(View v, int position) {
            }
        };
    }
}