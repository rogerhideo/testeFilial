package com.example.testefilial.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AppConfig {

    private static String serverHost = "http://192.168.100.76:3002";
    private static String createUserEndPoint =  "api/auth/register";
    private static String loginEndpoint = "api/auth/login";
    private static String getFiliaisEndPoint = "api/filiais/";
    private static String createFilialEnndPoint = "api/filial";
    private static String updateFilialEndPoint = "api/filial/";
    private static String deleteFilialEndPoint = "api/filial/";
    private static String userId = new String();
    private static String accessToken = new String();

    public static String getServerHost( ) {
        return serverHost;
    }
    public static void setServerHost(String url) {
        serverHost = url;
    }

    public static String getCreateUserEndPoint( ) {
        return createUserEndPoint;
    }
    public static void setCreateUserEndPoint(String endpoint) { createUserEndPoint = endpoint; }

    public static String getLoginEndpoint( ) {
        return loginEndpoint;
    }
    public static void setLoginEndpoint(String endpoint) {
        loginEndpoint = endpoint;
    }

    public static String getGetFiliaisEndPoint( ) { return getFiliaisEndPoint; }
    public static void setGetFiliaisEndPoint(String endpoint) { getFiliaisEndPoint = endpoint; }

    public static String getCreateFilialEnndPoint( ) {
        return createFilialEnndPoint;
    }
    public static void setCreateFilialEnndPoint(String endpoint) { createFilialEnndPoint = endpoint; }

    public static String getUpdateFilialEndPoint( ) {
        return updateFilialEndPoint;
    }
    public static void setUpdateFilialEndPoint(String endpoint) { updateFilialEndPoint = endpoint; }

    public static String getDeleteFilialEndPoint( ) {
        return deleteFilialEndPoint;
    }
    public static void setDeleteFilialEndPoint(String endpoint) { deleteFilialEndPoint = endpoint; }

    public static String getUserId(Context context) {
        try {
            if (AppConfig.userId == null) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(
                        "userId",
                        Context.MODE_PRIVATE
                );
                AppConfig.userId = sharedPreferences.getString("userId", null);
            }
        } catch (Exception e) {
            Log.e( "testeFilial::","AppConfig.getUserId()" +  e);
        }

        return AppConfig.userId;
    }

    public static void setUserId(String userId, Context context) {
        AppConfig.userId = userId;

        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(
                    "userId",
                    Context.MODE_PRIVATE
            );
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userId", userId);
            editor.apply();
        } catch (Exception e) {
            Log.e( "testeFilial::","AppConfig.setUserId()" +  e);
        }
    }

    public static String getAccessToken(Context context) {
        try {
            if (AppConfig.accessToken == null) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(
                        "accessToken",
                        Context.MODE_PRIVATE
                );
                AppConfig.accessToken = sharedPreferences.getString("accessToken", null);
            }
        } catch (Exception e) {
            Log.e( "testeFilial::","AppConfig.getAccessToken()" +  e);
        }

        return AppConfig.accessToken;
    }

    public static void setAccessToken(String accessToken, Context context) {
        AppConfig.accessToken = accessToken;
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(
                    "accessToken",
                    Context.MODE_PRIVATE
            );
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("accessToken", accessToken);
            editor.apply();
        } catch (Exception e) {
            Log.e( "testeFilial::","AppConfig.setAccessToken()" +  e);
        }
    }
}
