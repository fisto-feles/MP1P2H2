package com.homework.mp1p2h2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button btnPostActivity;
    ListView lvPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPostActivity = (Button) findViewById(R.id.btnPostActivity);
        lvPosts = (ListView) findViewById(R.id.lvPosts);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://jsonplaceholder.typicode.com/posts";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> list = new ArrayList<>();

                try {
                    for (int i=0; i<response.length(); i++) {
                        JSONObject item = response.getJSONObject(i);
                        list.add(Integer.toString(item.getInt("id")) + "\nTITULO: " + item.getString("title") + "\nCUERPO: " + item.getString("body"));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, list);
                    lvPosts.setAdapter(adapter);
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, "ERROR AL LLENAR LA LISTA!!!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                Toast.makeText(MainActivity.this, "ERROR AL OBTENER LOS DATOS!!!", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonArrayRequest);

        btnPostActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PostActivity.class);
                startActivity(i);
            }
        });
    }
}