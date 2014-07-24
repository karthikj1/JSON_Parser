/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package karthik.json;

/**
 *
 * @author karthik
 */
public class KJ_JSONException extends Exception{


    public KJ_JSONException()
    {
        super();
    }
    
    public KJ_JSONException(String msg)
    {
        super(msg);
    }

    public KJ_JSONException(String msg, Throwable t)
    {
        super(msg, t);
    }


}
