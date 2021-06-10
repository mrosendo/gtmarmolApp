package com.example.gmarmol;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String url = "http://192.168.1.53:8090/gmarmolapi/";
    private RequestQueue mQueue;
    EditText nitText;
    String uNit, cardName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nitText = findViewById(R.id.nitInput);
        Button login = findViewById(R.id.loginButton);

        mQueue = Volley.newRequestQueue(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = nitText.getText().toString();
                if (!text.equals("")) {
                    nitAPIQuery(text);
                } else {
                    alertM("Error de verificación", "El campo no puede estar vacío");
                }
            }
        });
    }

    //BUSCAR USUARIO
    public void nitAPIQuery(String nit) {
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
                                        uNit = obj.getString("U_NIT");
                                        cardName = obj.getString("CardName");
                                        screen2();
                                    }
                                } else {
                                    alertM("Error de verificación", "El NIT ingresado no existe");
                                    nitText.setText("");
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
        //return exist;
    }

    //CAMBIO A PANTALLA DE BIENVENIDA
    public void screen2() {
        Intent ac = new Intent(this, Welcome.class);
        ac.putExtra("u_nit", this.uNit);
        ac.putExtra("card_name", cardName);
        startActivity(ac);
    }

    //MENSAJE DE ALERTA
    public void alertM(String title, String ms) {
        AlertDialog dlg = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(ms)
                .setPositiveButton("Regresar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dlg.show();
    }
}