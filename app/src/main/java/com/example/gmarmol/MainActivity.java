package com.example.gmarmol;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.DialogInterface;
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
    String cardName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nit = findViewById(R.id.nitInput);
        Button login = findViewById(R.id.loginButton);
        mQueue = Volley.newRequestQueue(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = nit.getText().toString();
                if (!text.equals("")) {
                    //nitAPIQuery(text);
                } else {
                    alertM("Error de verificación", "El campo no puede estar vacío");
                }
            }
        });
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