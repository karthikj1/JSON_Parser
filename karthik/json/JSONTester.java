/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karthik.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author karthik
 */
public class JSONTester
    {

    static final String forecastURLAddress = "http://api.wunderground.com/api/30c683619c2a884b/forecast10day/q/";
    static final String URLAddress = "http://api.wunderground.com/api/30c683619c2a884b/conditions/q/";
    static final String hourlyURLAddress = "http://api.wunderground.com/api/30c683619c2a884b/hourly10day/q/";

    static final String newLine = System.getProperty("line.separator");

    public static void main(String[] args)
        {
        try{
             URL tgtURL = new URL(forecastURLAddress.concat("NewYork".concat(".json")));
            getJSONData(tgtURL);
        }
        catch(IOException | KJ_JSONException ioe){
            System.out.println(ioe.getMessage());
        }
        }

    public static KJ_JSONObject getJSONData(URL address) throws IOException, KJ_JSONException
        {
        // gets JSON data from server and returns KJ_JSONObject with the information returned
        String line;
        HttpURLConnection con;
        StringBuilder data = new StringBuilder("");

        con = (HttpURLConnection) (address.openConnection());
        con.connect(); // optional line
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        while ((line = in.readLine()) != null)
            data.append(line + newLine);

        in.close();
        con.disconnect();
        return new KJ_JSONObject(data.toString());
        }
    }
