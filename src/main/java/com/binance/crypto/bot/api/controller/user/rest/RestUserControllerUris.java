package com.binance.crypto.bot.api.controller.user.rest;

public class RestUserControllerUris {

    public static final String ROOT = "/api/users";
    public static final String USER = "/{userId}";
    public static final String LIST = "/list";
    public static final String USER_LOGIN_DATA = "/login-data";
    public static final String PASSWORD_EXPIRE_CHECK = "/password-expire-check";
    public static final String REGENERATE_QR_CODE = "/{userId}/regenerate-qr-code";

    private RestUserControllerUris() {
    }
}
