/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package karthik.json;

import java.util.ArrayList;

/**
 *
 * @author karthik
 */
public class KJ_JSONArray {
    
    private ArrayList<KJ_JSONValue> arr;
    
    public KJ_JSONArray()
    {
        arr = new ArrayList<>();
    }
    
    public KJ_JSONArray put(int i)
    {
        arr.add(new KJ_JSONValue((double) i));
        return this;
    }
    
    
    public KJ_JSONArray put(String s)
    {
        arr.add(new KJ_JSONValue(s));
        return this;
    }
    
    
    public KJ_JSONArray put(boolean b)
    {
        arr.add(new KJ_JSONValue(b));
        return this;
    }
    
    
    public KJ_JSONArray put(Double dbl)
    {
        arr.add(new KJ_JSONValue(dbl));
        return this;
    }
    
     public KJ_JSONArray put(KJ_JSONObject jObj)
    {
        arr.add(new KJ_JSONValue(jObj));
        return this;
    }
    
    public KJ_JSONArray put(KJ_JSONArray jArr)
    {
        arr.add(new KJ_JSONValue(jArr));
        return this;
    }
    
    public double getDouble(int index) throws KJ_JSONException
    {
        KJ_JSONValue val = arr.get(index);
        Double dbl = val.getDouble();
        if(dbl == null)
            throw new KJ_JSONException("Not a double type");
        
        return dbl; 

    }
    
    public String getString(int index) throws KJ_JSONException
    {
        KJ_JSONValue val = arr.get(index);
        String s = val.getString();
        if(s == null)
            throw new KJ_JSONException("Not a string type");
        
        return s; 
    }

    public boolean getBoolean(int index) throws KJ_JSONException
    {
        KJ_JSONValue val = arr.get(index);
        Boolean b = val.getBoolean();
        if(b == null)
            throw new KJ_JSONException("Not a boolean type");
        
        return b; 
    }
    
    
    public KJ_JSONObject getJSONObject(int index) throws KJ_JSONException
    {
        KJ_JSONValue val = arr.get(index);
        KJ_JSONObject jObj = val.getJSONObject();
        if(jObj == null)
            throw new KJ_JSONException("Not a JSON Object type");
        
        return jObj; 
    }
    
    
    public KJ_JSONArray getJSONArray(int index) throws KJ_JSONException
    {
        KJ_JSONValue val = arr.get(index);
        KJ_JSONArray jObj = val.getJSONArray();
        
        if(jObj == null)
            throw new KJ_JSONException("Not a JSON Array type");
        
        return jObj; 
    }
    
    public int length()
    {
        return arr.size();
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder("").append("[\n");
        for (KJ_JSONValue valItem : arr) {
            sb.append(valItem.toString()).append(",\n");
        }
        if(sb.length() > 2)
            sb.delete(sb.length() - 2, sb.length() - 1);
        sb.append("]\n");
        return sb.toString();
    }
    
    KJ_JSONValue[] toArray()
    {        
        return arr.toArray(new KJ_JSONValue[arr.size()]);
    }
    
    
    void add(KJ_JSONValue val)
    {
        arr.add(val);
    }
}
