package com.pawan.busfarebeta.app;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Pawan on 7/20/2017.
 */

public class Utilities {

    public static void log(String s){
        Log.d("UTILITIES", s);
    }

    public static void toast(Context c, String s){
        // TODO: 7/20/2017 SHOW TOAST
        Toast.makeText(c, s ,Toast.LENGTH_LONG).show();
    }
}
