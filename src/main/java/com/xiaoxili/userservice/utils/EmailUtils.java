package com.xiaoxili.userservice.utils;

public class EmailUtils {
    public static String getEmailMessage(String name, String host, String token) {
        return name + "\n\n !DO NOT Click the link below! \n\n" + getVerifyURL(host, token);
    }

    private static String getVerifyURL(String host, String token) {
        return host + "/api/users/confirm_user_account?token=" + token;
    }
    
}
