package com.gkudva.instagramclient;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class InstaClient extends AppCompatActivity {

    public static final String ClientID = "22ab27dc46ef4823a30f8e64e5ef8a63";
    private ArrayList<InstaFotos> instaFotos;
    private InstaAdapter adapter;
    private SwipeRefreshLayout swipeContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insta_client);
        instaFotos = new ArrayList<>();
        adapter = new InstaAdapter(this,instaFotos);
        ListView lv = (ListView) findViewById(R.id.lvFotos);
        lv.setAdapter(adapter);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        getData();
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    public void getData()
    {
        String URL = "https://api.instagram.com/v1/media/popular?client_id=" + ClientID;


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                adapter.clear();
                JSONArray fotosJson = new JSONArray();

                try {
                    fotosJson = response.getJSONArray("data");
                    for (int i =0; i < fotosJson.length(); i++)
                    {
                        JSONObject fotosObj = fotosJson.getJSONObject(i);
                        InstaFotos fotos = new InstaFotos();
                        fotos.caption = fotosObj.getJSONObject("caption").getString("text");
                        fotos.username = fotosObj.getJSONObject("user").getString("username");
                        fotos.imgURL = fotosObj.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        fotos.imgHeight = fotosObj.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        fotos.likeCount = fotosObj.getJSONObject("likes").getInt("count");
                        fotos.total_comments = fotosObj.getJSONObject("comments").getInt("count");
                        int size = fotosObj.getJSONObject("comments").getJSONArray("data").length();
                        if (size >= 2)
                        {
                            /*If this photo has atleast two comments fetch the last two comments*/
                            JSONObject commentObj = (JSONObject)fotosObj.getJSONObject("comments").getJSONArray("data").get(size - 1);
                            fotos.comment_usrname = commentObj.getJSONObject("from").getString("username");
                            fotos.comment = commentObj.getString("text");
                            JSONObject commentObj1 = (JSONObject)fotosObj.getJSONObject("comments").getJSONArray("data").get(size - 2);
                            fotos.comment_usrname1 = commentObj1.getJSONObject("from").getString("username");
                            fotos.comment1 = commentObj1.getString("text");
                        }
                        fotos.profilepicURL = fotosObj.getJSONObject("user").getString("profile_picture");
                        fotos.createdTime = fotosObj.getJSONObject("caption").getString("created_time");


                        instaFotos.add(fotos);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();

                swipeContainer.setRefreshing(false);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_insta_client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
