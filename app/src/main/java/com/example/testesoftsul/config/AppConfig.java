package com.example.testesoftsul.config;

import android.content.SharedPreferences;

public class AppConfig {

    private static String serverHost = "http://192.168.100.76:3001";
    private static String createUserEndPoint =  "api/auth/register";
    private static String loginEndpoint = "api/auth/login";
    private static String getFiliaisEndPoint = "api/filiais/";
    private static String createFilialEnndPoint = "api/filial";
    private static String updateFilialEndPoint = "api/filial/";
    private static String deleteFilialEndPoint = "api/filial/";

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
}
