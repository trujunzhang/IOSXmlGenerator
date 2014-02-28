package de.greenrobot.daogenerator.utils;

/**
 * Created by djzhang on 2/28/14.
 */
public class StringUtils {


    public static String convertFirstChatToUpper(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static String convertFirstChatToLower(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }
}
