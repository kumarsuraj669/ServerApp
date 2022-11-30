package com.example.server;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class register extends AppCompatActivity {
    EditText fName, lName, email, password;
    Button register;
    StringRequest request;
    String url = "https://634d-122-180-200-178.in.ngrok.io/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(register.this);

        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FullName = fName.getText().toString() + " " + lName.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();

                if (FullName.isEmpty() || Email.isEmpty() || Password.isEmpty()){
                    Toast.makeText(register.this, "Fill all the details Please", Toast.LENGTH_SHORT).show();
                } else {

                    makeJsonObject(FullName,Email,Password);
                    requestQueue.add(request);

                }
            }
        });
    }

    private void makeJsonObject(String name, String email, String password){

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(register.this, response, Toast.LENGTH_SHORT).show();
                if (response.matches("Saved in the database")){
                    Intent intent = new Intent(register.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> postParam = new HashMap<String, String>();
                postParam.put("name", name);
                postParam.put("email", email);
                postParam.put("password", password);
                return postParam;
            }
        };
    }
}