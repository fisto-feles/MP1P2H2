package com.homework.mp1p2h2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {

    Button btnSearch;
    EditText etSearch;
    TextView tvUserId, tvTitle, tvBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        etSearch = (EditText) findViewById(R.id.etSearch);
        tvUserId = (TextView) findViewById(R.id.tvUserId);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvBody = (TextView) findViewById(R.id.tvBody);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        RequestQueue queue = Volley.newRequestQueue(this);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://jsonplaceholder.typicode.com/posts/"+etSearch.getText().toString();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            tvUserId.setText(Integer.toString(response.getInt("userId")));
                            tvTitle.setText(response.getString("title"));
                            tvBody.setText(response.getString("body"));
                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), "ERROR AL LLENAR LOS CAMPOS!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                        Toast.makeText(getApplicationContext(), "ERROR AL OBTENER LOS DATOS!!!", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(jsonObjectRequest);
            }
        });
    }
}