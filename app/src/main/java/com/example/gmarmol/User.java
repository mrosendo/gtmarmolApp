package com.example.gmarmol;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

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

public class User extends AppCompatActivity {
    String url = "http://192.168.1.53:8090/gmarmolapi/";
    private RequestQueue mQueue;
    String cardName, uNIT;
    //ArrayList<Order> orderArrayList;

    //BUSCAR USUARIO
    public boolean nitAPIQuery(String nit) {
        boolean usr = false;
        String searchNITurl = this.url + "nit/" + nit;
        mQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, searchNITurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("cliente");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONArray data = jsonArray.optJSONArray(i);
                                if (data.length() != 0) {
                                    for (int j = 0; j < data.length(); j++) {
                                        JSONObject obj = data.getJSONObject(j);
                                        cardName = obj.getString("CardName");
                                        uNIT = obj.getString("U_NIT");
                                    }
                                } else {
                                    usr = false;
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alertM("Fallo de conexión", "No es posible conectarse con el servidor en este momento");
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}
