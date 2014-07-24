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
class KJ_JSONParser {

    private int indexPos = 0;
    private String JSONString = "";
    private int JSONStringLength = 0;
 
    
    KJ_JSONParser(String txt)
    {
        JSONString = txt;
        indexPos = 0;
        JSONStringLength = JSONString.length();
    }
    
       
    void clear()
    {
        JSONString = "";
        indexPos = JSONStringLength = 0;
    }
    
    HashMap<String, KJ_JSONValue> parse() throws KJ_JSONException
    {       
       
       findNextDelimiter('{');
               
        if(indexPos >= JSONStringLength) 
            throw new KJ_JSONException("Expected { but did not find it " );

       return loadJSONObject();
    }
    
    private HashMap<String, KJ_JSONValue> loadJSONObject() throws KJ_JSONException
    {
        // only called when a { has been found so it assumes it is inside a JSON Object
       String id; 
       HashMap<String, KJ_JSONValue> hm = new HashMap<>();
       
       indexPos++;
       skipWhitespace();   //allows possibility of an empty object
       char c = JSONString.charAt(indexPos);
              
        while ((indexPos < JSONStringLength) && c !='}') {
            findNextDelimiter('\"');
            id = loadString();
            findNextDelimiter(':');
            indexPos++;            
            hm.put(id, loadValue());
        // search for additional items in the JSON object
            skipWhitespace();
        if (indexPos >= JSONStringLength) 
            throw new KJ_JSONException("String ended unexpectedly at " + indexPos);

        c = JSONString.charAt(indexPos++);
        if(c == ',')
            continue;
        
         if( c != '}')
             throw new KJ_JSONException("Expected , or } but found " + c + 
                    " at index " + indexPos  + "\nContext is " + getStringforErrorMsg());       
        }
        
        return hm;
    }
    
    private KJ_JSONValue loadValue() throws KJ_JSONException
    {
        skipWhitespace();
        if(indexPos >= JSONStringLength)
            throw new KJ_JSONException("String ended unexpectedly at " + indexPos);
        
        char c = JSONString.charAt(indexPos);
        KJ_JSONValue returnVal = null;
        
        if(c == '\"')   // value is a string
        {
            String s  = loadString();
            returnVal = new KJ_JSONValue(s);
        }
        
        if((Character.toLowerCase(c) == 't') || (Character.toLowerCase(c) == 'f'))
            returnVal = new KJ_JSONValue(loadBoolean(c));
        
        if(Character.isDigit(c) || c == '+' || c == '-')   // value is a number
            returnVal = new KJ_JSONValue(loadNumber(c));
        
        if(c == '{')
            returnVal = new KJ_JSONValue(loadJSONObject());

        if(c == '[')            
            returnVal = new KJ_JSONValue(loadJSONArray());
        
        if(c == 'n') // could be value of null
            returnVal = loadNull();
            
        return returnVal;
    }
    
    private KJ_JSONArray loadJSONArray() throws KJ_JSONException
    {
        // only called when a [ has been found so it assumes it is inside a JSON Array
       
       KJ_JSONValue value;
       KJ_JSONArray arr = new KJ_JSONArray();
       
       indexPos++;
       skipWhitespace();  //allows possibility of empty array
       char c = JSONString.charAt(indexPos);
              
        while ((indexPos < JSONStringLength) && c !=']') {           
            value = loadValue();
            arr.add(value);
        // search for additional items in the JSON array
            skipWhitespace();
        if (indexPos >= JSONStringLength) 
            throw new KJ_JSONException("Array ended unexpectedly at " + indexPos);

        c = JSONString.charAt(indexPos++);
        if(c == ',')
            continue;
        
         if( c != ']')
             throw new KJ_JSONException("Expected , or ] but found " + c + 
                    " at index " + indexPos + "\nContext is " + getStringforErrorMsg());       
        }
        
        return arr;
    }

    private double loadNumber(char c) throws KJ_JSONException
    {
        
        StringBuilder sb = new StringBuilder("").append(c);
        int start = indexPos;
        
        while(++indexPos < JSONStringLength  && !Character.isWhitespace(c))
        {
            c = JSONString.charAt(indexPos);
            if((c == '}') ||( c == ','))
                break;
            sb.append(c);             
        }     
        String s = sb.toString();
        
        try{          
            return Double.parseDouble(s);
        }
        catch(NumberFormatException nfe)
        {
            throw new KJ_JSONException("Expected number but found " + s + 
                    " at index " + start + "\nContext is " + getStringforErrorMsg());
        }
    }
    
    private KJ_JSONValue loadNull() throws KJ_JSONException
    {
        
        char c = JSONString.charAt(indexPos++);
        StringBuilder sb = new StringBuilder("");
        
        int start = indexPos;
        
        while((indexPos < JSONStringLength) && Character.isLetter(c)) {
            sb.append(c);
            c = JSONString.charAt(indexPos++);                    
        } 
             
        indexPos--;
        String s = sb.toString();
        
        if(s.trim().equalsIgnoreCase("null"))
            return null;
                
        throw new KJ_JSONException("Expected null but found " + s + 
                "at index " + start + "\nContext is " + getStringforErrorMsg());
    }
    
    private boolean loadBoolean(char c) throws KJ_JSONException
    {
        
        StringBuilder sb = new StringBuilder("").append(c);
        int start = indexPos;
        
        while(++indexPos < JSONStringLength && Character.isLetter(c))
        {
            c = JSONString.charAt(indexPos);
            if ((c == '}') ||( c == ','))
                break;
            sb.append(c);        
        }     
        String s = sb.toString();
        
        if(s.trim().equalsIgnoreCase("true"))
            return Boolean.valueOf("true");
        
        if(s.trim().equalsIgnoreCase("false"))
            return Boolean.valueOf("false");
        
        throw new KJ_JSONException("Expected true/false but found " + s + 
                "at index " + start);
    }
    
     private String loadString() throws KJ_JSONException
    {
        // processes strings - allows backslash escapes but doesn't process them properly yet
        // only called when starting " has already been found
        
        char c;
        StringBuilder sb = new StringBuilder("");
                         
        int start = ++indexPos;
        while(indexPos < JSONStringLength)
        {
            c = JSONString.charAt(indexPos++);
            if(c == '\"')
                return sb.toString();
            
            if(c == '\\')   // escaped character
            {
                if(indexPos < JSONStringLength)
                {
                    c = JSONString.charAt(indexPos++);
                    switch(java.lang.Character.toLowerCase(c))
                    {
                        case '\\':
                            sb.append('\\');
                            break;
                        case '\"':
                            sb.append('\"');
                            break;
                        case 'n': 
                            sb.append('\n');
                            break;
                        case 'r' : 
                            sb.append('\r');
                            break;
                        case 't' :  
                            sb.append('\t');
                            break;
                        case 'f' :                         
                            sb.append('\f');
                            break;
                        case 'b':                             
                            sb.append('\b');
                            break;
                        case 'u':
                            if(indexPos > JSONStringLength - 5)
                                throw new KJ_JSONException("Expected hexadecimal "
                                        + "but string ended unexpectedly at " + indexPos);
                            // process hex input
                            String hexString = JSONString.substring(indexPos, indexPos + 3);
                            try{
                            sb.append((char) Integer.parseInt(hexString, 16));
                            }
                            catch (NumberFormatException nfe){
                                throw new KJ_JSONException("Expected hexadecimal "
                                        + "but found " + hexString + " at " + indexPos + "\nContext is " + getStringforErrorMsg());
                            }
                            indexPos += 4;
                    }
                }
                else
                    throw new KJ_JSONException("String ended unexpectedly at " + indexPos);
                continue;
                
            }

            sb.append(c);
        }     
        
        if(indexPos == JSONStringLength)
           throw new KJ_JSONException("Did not find closing \" for ID starting at index " 
                   + start + " - " + sb.toString());
        return null;
    }
    
    private void skipWhitespace()
    {
        while( indexPos < JSONStringLength && Character.isWhitespace(JSONString.charAt(indexPos)))
            indexPos++;
    }
    
    private void findNextDelimiter(char delim) throws KJ_JSONException
    {        
        // used when delim is definitely required.
        // throws an exception if the delimiter is not found as the next non-whitespace
        
        skipWhitespace();
        if (indexPos >= JSONStringLength) {
            throw new KJ_JSONException("String ended unexpectedly at " + indexPos);
        }

        char c = JSONString.charAt(indexPos);
        if (c != delim)
        {
            throw new KJ_JSONException("Expected " + delim + " but found " + c
                    + " at index " + indexPos + "\nContext is " + getStringforErrorMsg());
        }
    }
    
    private String getStringforErrorMsg(){
        int start, end;
        
        start = (indexPos > 10) ? indexPos - 10 : 0;
        end = indexPos + 10;
        
        return JSONString.substring(start, end);
        
    }
}

