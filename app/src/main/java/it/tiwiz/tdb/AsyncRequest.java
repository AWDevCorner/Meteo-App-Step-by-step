package it.tiwiz.tdb;

import android.content.Context;
import android.content.Entity;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class AsyncRequest extends AsyncTask<String,Void,String> {
    private final static String URL_FORMAT = "https://api.metwit.com/v2/weather/?location_lat=%s&location_lng=%s";

    @Override
    protected String doInBackground(String... strings) {
        if((strings == null) || (strings.length != 2)){
            return null;
        }
        final String requestUri = String.format(URL_FORMAT, strings[0], strings[1]);
        Log.d("DevCorner","doInBackground:\n\t\t" + requestUri);
        final HttpClient httpClient = new DefaultHttpClient();
        final HttpGet httpRequest = new HttpGet(requestUri);
        String response;
        try {
            final HttpResponse httpResponse = httpClient.execute(httpRequest);
            response = EntityUtils.toString(httpResponse.getEntity());
        } catch (IOException e) {
            return e.getMessage();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("DevCorner", "onPostExecute:\n\t\t" + s);
    }
}
