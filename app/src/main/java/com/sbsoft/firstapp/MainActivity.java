package com.sbsoft.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    ArrayList<News> newsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsArrayList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView_main);
        adapter=new RecyclerAdapter(this,newsArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Uri uri=getIntent().getData();
        if (uri!=null){
           List<String> params=uri.getPathSegments();
           String id=params.get(params.size()-1);
           Toast.makeText(this,"url: "+id,Toast.LENGTH_LONG).show();
        }


//        String url2 = "http://newsapi.org/v2/top-headlines?country=in&apiKey=3fbe4d52b80a40538bdd55378f969ff8";
//        String url3="https://gnews.io/api/v4/top-headlines?token=119a720143e2c2cbe1f498a106d0f5ea";
        String url="https://gnews.io/api/v4/search?q=india&token=119a720143e2c2cbe1f498a106d0f5ea";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest

                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray=response.getJSONArray("articles");


                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                newsArrayList.add(new News(jsonObject.getString("description"),
                                        jsonObject.getString("image"),
                                        jsonObject.getString("url"),
                                        jsonObject.getString("publishedAt")));
                               // Toast.makeText(getApplicationContext(),jsonObject.getString("url"),Toast.LENGTH_SHORT).show();

                            }

                            adapter.notifyDataSetChanged();
                            
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),String.valueOf(e),Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                       Toast.makeText(getApplicationContext(),String.valueOf(error),Toast.LENGTH_LONG).show();
                        // TODO: Handle error

                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);



    }
}