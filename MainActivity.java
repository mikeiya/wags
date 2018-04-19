package wags.gravatar;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MemoryLoader memoryLoader = new MemoryLoader();
    FileCacher fileCacher;

    private String File_name = "Offline.txt";

    public void saveOffline(String file,String textfile){
        try{
            FileOutputStream os = openFileOutput(file, Context.MODE_PRIVATE);
            os.write(textfile.getBytes());
            os.close();
            Toast.makeText(this,"Offline file saved!",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"No Offline file saved!",Toast.LENGTH_SHORT).show();
        }
    }

    public  String readOffline(String file){
        String textfile = "";
        try{
            FileInputStream is= openFileInput(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            textfile = new String(buffer);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"No Offline file saved!",Toast.LENGTH_SHORT).show();
        }
        return textfile;
    }

    private RecyclerView recyclerView;
    private ArrayList<items> itemList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.r_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        String url = "https://api.stackexchange.com/2.2/users?site=stackoverflow";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    saveOffline(File_name,jsonArray.toString());
                    readOffline(File_name);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject itemss = jsonArray.getJSONObject(i);
                        String username = itemss.getString("display_name");
                        String badges = itemss.getString("badge_counts");
                        String gravatarUrl = itemss.getString("profile_image");
                        itemList.add(new items(gravatarUrl, username, badges));

                    }
                    itemsBackend itemAdapter = new itemsBackend(MainActivity.this, itemList);
                    recyclerView.setAdapter(itemAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }
}
