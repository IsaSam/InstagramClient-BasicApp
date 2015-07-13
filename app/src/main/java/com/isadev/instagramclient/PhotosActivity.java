package com.isadev.instagramclient;


import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import com.loopj.android.http.*;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.isadev.instagramclient.R;
import java.util.ArrayList;

public class PhotosActivity extends ActionBarActivity {

    public static final String CLIENT_ID = "ee5565d75ea746388070e537c10441e1";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        //SEND OUT API REQUEST to POPULAR PHOTOS
        photos = new ArrayList<>();
        // 1. create the adapter
        aPhotos = new InstagramPhotosAdapter(this, photos);
        // 2. find the list view from the layout
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        // 3. set the adapter binding it to the Listview
        lvPhotos.setAdapter(aPhotos);
        //fetch the popular photos
        fetchPopularPhotos();
    }
    // Trigger API  request
    public void fetchPopularPhotos(){
       /*
       Client ID	ee5565d75ea746388070e537c10441e1
       -Popular: https://api.instagram.com/v1/media/popular?access_token=ACCESS-TOKEN
        -Response
       */
       String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        //Create the network client
        AsyncHttpClient client = new AsyncHttpClient();
        //Trigger the GET request
        client.get(url, null,  new JsonHttpResponseHandler() {
        //on Success (worked, 200)

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Interate each of the photo items and decode the item into a java object
                JSONArray photosJSON = null;
                try {
                    photosJSON = response.getJSONArray("data"); //array of posts
                    //Expecting a JSON object
                    // Type: { "data" [x] => "type"}
                    //iterate array of posts
                    for (int i = 0; i < photosJSON.length(); i++){
                        //get the json object at the ...
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        //decode the attributes of the json into a data model
                        InstagramPhoto photo = new InstagramPhoto();
                        // Author Name:  { "data" => [x] => "user" => "username"	}
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        // Caption: { "data" => [x] => "caption" => "text"}
                        photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        // photo.type = photoJSON.getJSONObject("type").getString("text");
                        photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        //height
                        photo.imageHeigth = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        //Likes
                        photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                        //add decoded object to the...
                        photos.add(photo);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // callback
                aPhotos.notifyDataSetChanged();
            }

            //on failure (fail)

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // DO SOMETHING
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
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
