package com.example.jsonvolley;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
{
    private TextView titleText;
    private Button parseButton;
    private String url = "https://api.myjson.com/bins/naqsl";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleText = findViewById(R.id.display_text);
        parseButton = findViewById(R.id.parse_data);
        parseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                jsonParse();
            }
        });
    }

    private void jsonParse()
    {
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            JSONArray jsonArray = response.getJSONArray("Employees");
                            for (int i = 0 ; i < jsonArray.length(); i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String name = object.getString("First-name");
                                int age = object.getInt("Age");
                                String mail = object.getString("mail");

                                titleText.append(name + "\n" + age+ "\n" + mail + "\n\n");
                                requestQueue.stop();
                            }

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                        requestQueue.stop();
                    }
                });

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(objectRequest);
    }
}
