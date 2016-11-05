package com.triviamusic.triviamusicandroid.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jadac_000 on 05/11/2016.
 */

public class Api {
    private String SERVER_IP = "10.0.2.2";
    public static final String TAG = "API";
    private Context context;
    private RequestQueue queue;


    public Api(Context context) {
        this.context = context;
        // Get a RequestQueue
        queue = RequestQueue_Singeton.getInstance(context).getRequestQueue();
    }

    public void songs(String category, final VolleyCallback callback) {
        // Instantiate the RequestQueue.

        String url = "http://" + SERVER_IP + ":3000/songs/" + category;

        // Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        Log.d("response", response.toString());
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                try {
                    callback.onSuccess(new JSONObject("{\"result\":\"error\"}"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // Set the tag on the request.
        stringRequest.setTag(TAG);
        // Add the request to the RequestQueue.
        //queue.add(stringRequest);
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        RequestQueue_Singeton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void home() {
        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(context);
        String url = "http://" + SERVER_IP + ":3000/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("response", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "That didn't work!");
            }
        });
        // Set the tag on the request.
        stringRequest.setTag(TAG);
        // Add the request to the RequestQueue.
        //queue.add(stringRequest);
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        RequestQueue_Singeton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void cancel() {
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }

    public interface VolleyCallback {
        void onSuccess(JSONObject result);
    }
}
