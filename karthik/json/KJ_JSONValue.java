/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package karthik.json;

import java.util.HashMap;

/**
 *
 * @author karthik
 */
class KJ_JSONValue {
       enum ValType{
        STRING, JSONOBJECT, JSONARRAY, BOOLEANTRUE, BOOLEANFALSE, NUMBER, NULL 
    }
   
    ValType type=ValType.NULL;
    String s="";
    double num=0;
    KJ_JSONObject m;
    KJ_JSONArray valueArray;
    
    KJ_JSONValue(String str)
    {
        type = ValType.STRING;
        s = str;
    }
    
    KJ_JSONValue(double dbl)
    {
        type = ValType.NUMBER;
        num = dbl;
    }
    
    KJ_JSONValue(Object obj)
    {
        if(obj == null)
            type = ValType.NULL;
    }
    
    KJ_JSONValue(boolean b)
    {
        if(b)
            type = ValType.BOOLEANTRUE;
        else
            type = ValType.BOOLEANFALSE;                    
    }
    
    KJ_JSONValue(HashMap<String, KJ_JSONValue> hm)
    {
        type = ValType.JSONOBJECT;
        m = new KJ_JSONObject(hm);
    }
    
    KJ_JSONValue(KJ_JSONObject hm)
    {
        type = ValType.JSONOBJECT;
        m = hm;
    }
    
    KJ_JSONValue(KJ_JSONArray arr)
    {
        type = ValType.JSONARRAY;
        valueArray = arr;        
    }   
    
    public double getDouble() 
    {
        if (type == ValType.NUMBER)
            return num;
        else 
            return (Double) null;
    } 
    
    public String getString()
    {
        if (type == ValType.STRING)
            return s;
        else 
            return null;
    } 
    
    public Boolean getBoolean()
    {
        switch (type)
        {
            case BOOLEANTRUE:
                return true;
            case BOOLEANFALSE:
                return false;
            default:
                return (Boolean) null;
        }
    } 
    
    public KJ_JSONObject getJSONObject()
    {
        if (type == ValType.JSONOBJECT)
            return m;
        else 
            return null;
    }
    
    public KJ_JSONArray getJSONArray()
    {
        if (type == ValType.JSONARRAY)
            return valueArray;
        else 
            return null;
    }
    
    public String toString()
    {
        switch(type)
        {
            case NULL:
                return "null";
            case BOOLEANTRUE:
                return "true";
            case BOOLEANFALSE:
                return "false";
            case STRING:
                return "\"" + s + "\"";
            case NUMBER:
                return String.valueOf(num);
            case JSONOBJECT:
                return m.toString();
            case JSONARRAY:
                return valueArray.toString();
            
            default:
                return "";
                       
        }
    }
}
