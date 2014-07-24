/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package karthik.json;

import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author karthik
 */
public class KJ_JSONObject {
    private HashMap<String, KJ_JSONValue> m;
    String JSONString = "";
    
    public KJ_JSONObject()
    {
        m = new HashMap<String, KJ_JSONValue>();
    }
    
    KJ_JSONObject(HashMap<String, KJ_JSONValue> hm)
    {
        m = hm;
    }
    
    public KJ_JSONObject(String text) throws KJ_JSONException
    {
        m = new KJ_JSONParser(text).parse();
    }
    
    public void setText(String text)
    {
        JSONString = text;        
    }
    
    public KJ_JSONObject parse() throws KJ_JSONException
    {
        m = new KJ_JSONParser(JSONString).parse();
        return this;
    }
    
    public KJ_JSONObject put(String key, KJ_JSONValue val)
    {
        m.put(key, val);
        return this;        
    }

    public KJ_JSONObject put(String key, double val)
    {
        m.put(key, new KJ_JSONValue(val));
        return this;        
    }

    public KJ_JSONObject put(String key, boolean val)
    {
        m.put(key, new KJ_JSONValue(val));
        return this;        
    }
    
    public String[] getKeys()
    {   
        ArrayList<String> keyArray = new ArrayList<>();
        String[] keyStrings = new String[m.size()];
        for(String s: m.keySet())
            keyArray.add(s);
        return keyArray.toArray(keyStrings);
    }
    
    public String toString()
    {
       StringBuilder sb = new StringBuilder("{\n");
       for(String key : m.keySet())
           sb.append("\"" + key + "\" : " + m.get(key).toString() + ",\n");
       if(sb.length() > 2)
            sb.delete(sb.length() - 2, sb.length() - 1);
       sb.append("}");
       return sb.toString();         
    }
    
    public String getString(String key) throws KJ_JSONException
    {
        KJ_JSONValue val = m.get(key);
        if(val == null)
            throw new KJ_JSONException(key + " not found");
        
        String s = val.getString();
        if(s == null)
            throw new KJ_JSONException(key + " is not a String type");
        
        return s;
    }

    public double getDouble(String key) throws KJ_JSONException
    {
       KJ_JSONValue val = m.get(key);
        if(val == null)
            throw new KJ_JSONException(key + " not found");
        
        Double dbl = val.getDouble();   
        if(dbl == null)
            throw new KJ_JSONException(key + " is not a double type");
        
        return dbl;  
    }
    
    public KJ_JSONArray getJSONArray(String key) throws KJ_JSONException
    {
       KJ_JSONValue val = m.get(key);
        if(val == null)
            throw new KJ_JSONException(key + " not found");
        
        KJ_JSONArray arr = val.getJSONArray();
        if(arr == null)
            throw new KJ_JSONException(key + " is not an array type");
        
        return arr;  
    } 
    
    
    public KJ_JSONObject getJSONObject(String key) throws KJ_JSONException
    {
       KJ_JSONValue val = m.get(key);
        if(val == null)
            throw new KJ_JSONException(key + " not found");
        
        KJ_JSONObject jObj = val.getJSONObject();
        if(jObj == null)
            throw new KJ_JSONException(key + " is not a JSONObject type");
        
        return jObj;  
    } 
    
    public int getInt(String key) throws KJ_JSONException
    {
       KJ_JSONValue val = m.get(key);
        if(val == null)
            throw new KJ_JSONException(key + " not found");
        
        Double dbl = val.getDouble();
        if(dbl == null || (dbl % 1) != 0)
            throw new KJ_JSONException(key + " is not an int type");
        
            return dbl.intValue();
       }
    
    public boolean has(String key)
    {
        return m.containsKey(key);
    }
}
