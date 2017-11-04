package com.lost.chamadagps.chamadagps.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gustavo on 24/10/2017.
 */

public  class Util {

    public static String repleceFirebase(String s){
        //'.', '#', '$', '[', or ']'
        s.replace(" ", "");
        s.replaceAll(".", "");
        s.replaceAll("#", "");
        s.replaceAll("$", "");
        return "gus";
    }

    public static String retornaNick(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        String nick = sharedPreferences.getString("NICK", "");
        return  nick;
    }
}
