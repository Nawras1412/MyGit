package com.example.smartremindersapp2;

import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.libraries.places.api.model.Place;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.net.URI;
import java.net.URISyntaxException;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.*;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.client.utils.URIBuilder;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.httpclient.android.*;
import cz.msebera.android.httpclient.util.*;
//HttpUriRequest
import cz.msebera.android.httpclient.client.utils.URIBuilder;
//import cz.msebera.android.httpclient.CloseableHttpClient;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.client.methods.RequestBuilder;
//import cz.msebera.android.httpclient.HttpClientBuilder;
import cz.msebera.android.httpclient.client.HttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class GooglePlacesClient
{
    private static final String GOOGLE_API_KEY  = "***";

    private final HttpClient client = new DefaultHttpClient();

//    public static void main(final String[] args) throws ParseException, IOException, URISyntaxException
//    {
//        new GooglePlacesClient().performSearch("establishment", 8.6668310, 50.1093060);
//    }

    public void performSearch(final String types, final double lon, final double lat) throws ParseException, IOException, URISyntaxException
    {
        final URIBuilder builder = new URIBuilder().setScheme("https").setHost("maps.googleapis.com").setPath("/maps/api/place/search/json");

        builder.addParameter("location", lat + "," + lon);
        builder.addParameter("radius", "5");
        builder.addParameter("types", types);
        builder.addParameter("sensor", "true");
        builder.addParameter("key", GooglePlacesClient.GOOGLE_API_KEY);

        final HttpUriRequest request = new HttpGet(builder.build());

        //final HttpResponse execute = this.client.execute(request);
        //final HttpResponse execute = this.client.execute(request);


        //final String response = EntityUtils.toString(execute.getEntity());

        //System.out.println(response);
    }

    public void getResponseThread(final String url,String lat1 , String lang1,String reminderTitle) {
        new Thread(new Runnable() {
            public void run() {
                boolean dissmiss=false;
                String cadHTTP = getResponse(url);
                String Name;
                String lat;
                String lng;
                String address;
                int end_indx_place;
                do {
                     Name = getparameters(cadHTTP, "\"name\" : \"", "\"");
                     lat = getparameters(cadHTTP, "lat\" : ", ",");
                     lng = getparameters(cadHTTP, "lng\" : ", "\n");
                     address = getparameters(cadHTTP, " \"vicinity\" : \"", "\"");
                     end_indx_place = cadHTTP.indexOf(" \"vicinity\" : \"") + 15;
                    cadHTTP = cadHTTP.substring(end_indx_place);
//
//                System.out.println("NAme: "+Name);
//                System.out.println("lat: "+lat);
//                System.out.println("lng: "+lng);
//                System.out.println("address: "+address);
//                System.out.println("end  "+cadHTTP.substring(end_indx_place+10));
                    float[] distance = new float[1];
                    Location.distanceBetween(Double.parseDouble(lat), Double.parseDouble(lng), Double.parseDouble(lat1), Double.parseDouble(lang1), distance);
                    if (distance[1] < 3000) {
                        /// hey nawras please create notification here

                        // use reminderTitle for the title of the notification
                        // use name for the name of the found location and address
                        // example of the massage ::   (name) found near you at (address)
                        // give options : dissmiss or suggest another place


                        // if he pressed dissmiss set : dissmiss= true

                    }
                }
                while(dissmiss==false);
                //System.out.println(cadHTTP);
                Message msg = new Message();
                msg.obj = cadHTTP;
                handlerHTTP.sendMessage(msg);
            }
        }).start();
    }

    private String getparameters(String response,String searchString,String end){
        int firstIndex=response.indexOf(searchString)+searchString.length();
        String mySubstring=response.substring(firstIndex);
        //System.out.println(mySubstring);
        int lastIndex=firstIndex+mySubstring.indexOf(end);
        return (response.substring(firstIndex,lastIndex));


    }
    private String getResponse(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet del = new HttpGet(url);
        del.setHeader("content-type", "application/json");

        String respStr;
        try {
            HttpResponse resp = httpClient.execute(del);
            respStr = EntityUtils.toString(resp.getEntity());
        } catch(Exception ex) {
            Log.e("RestService","Error!", ex);
            respStr = "";
        }

        Log.e("getResponse",respStr);
        return respStr;
    }

    private Handler handlerHTTP = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String res = (String) msg.obj;
            //CONTINUE HERE

        }
    };

}



