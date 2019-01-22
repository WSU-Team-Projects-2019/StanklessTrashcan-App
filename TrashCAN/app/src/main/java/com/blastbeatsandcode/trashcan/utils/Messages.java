package com.blastbeatsandcode.trashcan.utils;


import android.content.Context;
import android.widget.Toast;

/*
 * Utilities to quickly show messages to the user
 */
public class Messages {
    /*
     * Show a toast to the given context with the given string
     */
    public static void makeToast (Context context, String str)
    {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }
}