package com.tergech.nixon.mffapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Tonui on 6/27/2017.
 */

public class SaveSharedPreference {

    static final String PREF_USER_NAME= "username";
    static final String PREF_PHONE_NUMBER= "phone_number";
    static final String PREF_SAVE_DRIVER_ID ="driver_id";
    static final String PREF_SAVE_PASS_ID ="pass_id";



    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    //saving driver id

    public static void setDriverid(Context ctx, String driver_id)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_SAVE_DRIVER_ID, driver_id);
        editor.commit();
    }
    //saving pass id
    public static void setPassid(Context ctx, String pass_id)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_SAVE_PASS_ID, pass_id);
        editor.commit();
    }

    public static void setPhoneNumber(Context ctx, String phone)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_PHONE_NUMBER, phone);
        editor.commit();
    }
    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }
    //getting driver id
    public static String getDriverID(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_SAVE_DRIVER_ID, "");
    }

    //getting pass id
    public static String getPassID(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_SAVE_PASS_ID, "");
    }

    public static String getPhoneNumber(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_PHONE_NUMBER, "");
    }


    //logging out
    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
}
