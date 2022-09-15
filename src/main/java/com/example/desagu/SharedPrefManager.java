package com.example.desagu;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

public static SharedPrefManager mInstance;
private static Context mCtx;

private static  final String SHARED_PREF_NAME = "mimi";
private static  final String KEY_USERNAME ="User_name";
private static  final String KEY_USER_EMAIL ="Email";
private static  final String KEY_EMPLOYEE_NO = "50";

    public SharedPrefManager(Context context) {
        mCtx = context;
    }
        public static synchronized SharedPrefManager getInstance(Context context){
            if (mInstance == null) {
                mInstance = new SharedPrefManager(context);
            }

            return mInstance;
        }
        public boolean userLogin(String Employee_no, String User_name ,String Email){
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor =sharedPreferences.edit();

            editor.putString(KEY_EMPLOYEE_NO,Employee_no);
            editor.putString(KEY_USER_EMAIL,Email);
            editor.putString(KEY_USERNAME,User_name);

            editor.apply();

            return true;

        }
        public boolean isLoggedIn(){

            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            if(sharedPreferences.getString(KEY_USERNAME, null)!= null){
                return true;
            }
            return false;
        }
        public boolean logout(){
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            return true;

        }
        public String getEmployee(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        return sharedPreferences.getString(KEY_EMPLOYEE_NO, null);
        }
    }